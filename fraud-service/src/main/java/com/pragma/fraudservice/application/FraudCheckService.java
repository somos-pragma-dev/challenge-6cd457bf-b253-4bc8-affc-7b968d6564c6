package com.pragma.fraudservice.application;

import com.pragma.fraudservice.domain.model.FraudCheckResult;
import com.pragma.fraudservice.domain.model.FraudCheckRequest;
import com.pragma.fraudservice.domain.repository.FraudCheckRepository;
import com.pragma.fraudservice.infrastructure.client.RiskBureauClient;
import com.pragma.fraudservice.infrastructure.client.FraudEngineClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudCheckService {

    private final FraudCheckRepository fraudCheckRepository;
    private final FraudEngineClient fraudEngineClient;
    private final RiskBureauClient riskBureauClient;

    public Mono<FraudCheckResult> performFraudCheck(FraudCheckRequest request) {
        log.info("Iniciando validación antifraude para paymentId: {} y orderId: {}", 
                request.paymentId(), request.orderId());
        
        return validateWithFraudEngine(request)
                .flatMap(fraudResult -> {
                    if (fraudResult.isBlocked()) {
                        log.warn("Pago bloqueado por motor antifraude para paymentId: {}", 
                                request.paymentId());
                        return saveBlockedResult(request, fraudResult.getReason());
                    }
                    return validateWithRiskBureau(request)
                            .flatMap(riskResult -> {
                                if (riskResult.isHighRisk()) {
                                    log.warn("Alto riesgo detectado por buró para paymentId: {}", 
                                            request.paymentId());
                                    return saveHighRiskResult(request, riskResult.getRiskLevel());
                                }
                                return saveApprovedResult(request);
                            });
                })
                .doOnSuccess(result -> log.info("Validación antifraude completada para paymentId: {} con resultado: {}", 
                        request.paymentId(), result.getStatus()))
                .doOnError(error -> log.error("Error en validación antifraude para paymentId: {} - Error: {}", 
                        request.paymentId(), error.getMessage()));
    }

    private Mono<FraudCheckResult> validateWithFraudEngine(FraudCheckRequest request) {
        return fraudEngineClient.checkFraud(
                request.paymentId(),
                request.cardNumber(),
                request.amount(),
                request.customerId()
        ).defaultIfEmpty(createDefaultFraudResult(request.paymentId(), false, "APPROVED"));
    }

    private Mono<FraudCheckResult> validateWithRiskBureau(FraudCheckRequest request) {
        return riskBureauClient.assessRisk(
                request.customerId(),
                request.orderId(),
                request.amount()
        ).defaultIfEmpty(createDefaultRiskResult(request.paymentId(), false, "LOW"));
    }

    private FraudCheckResult createDefaultFraudResult(UUID paymentId, boolean blocked, String reason) {
        return FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(paymentId)
                .blocked(blocked)
                .reason(reason)
                .checkedAt(LocalDateTime.now())
                .build();
    }

    private FraudCheckResult createDefaultRiskResult(UUID paymentId, boolean highRisk, String riskLevel) {
        return FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(paymentId)
                .blocked(highRisk)
                .reason("RISK_ASSESSMENT: " + riskLevel)
                .checkedAt(LocalDateTime.now())
                .build();
    }

    private Mono<FraudCheckResult> saveBlockedResult(FraudCheckRequest request, String reason) {
        FraudCheckResult result = FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(request.paymentId())
                .orderId(request.orderId())
                .customerId(request.customerId())
                .status("BLOCKED")
                .blocked(true)
                .reason(reason)
                .checkedAt(LocalDateTime.now())
                .fraudScore(100)
                .build();
        return fraudCheckRepository.save(result)
                .thenReturn(result);
    }

    private Mono<FraudCheckResult> saveHighRiskResult(FraudCheckRequest request, String riskLevel) {
        FraudCheckResult result = FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(request.paymentId())
                .orderId(request.orderId())
                .customerId(request.customerId())
                .status("HIGH_RISK")
                .blocked(true)
                .reason("HIGH_RISK: " + riskLevel)
                .checkedAt(LocalDateTime.now())
                .fraudScore(75)
                .build();
        return fraudCheckRepository.save(result)
                .thenReturn(result);
    }

    private Mono<FraudCheckResult> saveApprovedResult(FraudCheckRequest request) {
        FraudCheckResult result = FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(request.paymentId())
                .orderId(request.orderId())
                .customerId(request.customerId())
                .status("APPROVED")
                .blocked(false)
                .reason("APPROVED")
                .checkedAt(LocalDateTime.now())
                .fraudScore(10)
                .build();
        return fraudCheckRepository.save(result)
                .thenReturn(result);
    }

    public Mono<FraudCheckResult> getFraudCheckResult(UUID paymentId) {
        log.info("Consultando resultado de fraude para paymentId: {}", paymentId);
        return fraudCheckRepository.findByPaymentId(paymentId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No se encontró resultado de fraude para paymentId: {}", paymentId);
                    return Mono.empty();
                }));
    }

    public Flux<FraudCheckResult> getFraudCheckHistory(String customerId, LocalDateTime fromDate) {
        log.info("Consultando historial de fraude para customerId: {} desde: {}", customerId, fromDate);
        return fraudCheckRepository.findByCustomerIdAndCheckedAtAfter(customerId, fromDate);
    }

    public Mono<Map<String, Object>> getFraudStatistics(LocalDateTime fromDate, LocalDateTime toDate) {
        log.info("Generando estadísticas de fraude desde: {} hasta: {}", fromDate, toDate);
        return fraudCheckRepository.findByCheckedAtBetween(fromDate, toDate)
                .collectList()
                .map(results -> {
                    Map<String, Object> stats = new HashMap<>();
                    long total = results.size();
                    long blocked = results.stream().filter(FraudCheckResult::isBlocked).count();
                    long approved = total - blocked;
                    double blockRate = total > 0 ? (blocked * 100.0 / total) : 0.0;
                    double avgScore = results.stream()
                            .mapToInt(FraudCheckResult::getFraudScore)
                            .average()
                            .orElse(0.0);
                    stats.put("totalChecks", total);
                    stats.put("blocked", blocked);
                    stats.put("approved", approved);
                    stats.put("blockRate", blockRate);
                    stats.put("averageFraudScore", avgScore);
                    stats.put("fromDate", fromDate);
                    stats.put("toDate", toDate);
                    return stats;
                });
    }
}