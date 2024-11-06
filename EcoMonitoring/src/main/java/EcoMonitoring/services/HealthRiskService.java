package EcoMonitoring.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthRiskService {

    private static final double AVERAGE_HUMAN_BODY_WEIGHT = 70.0;
    private static final int FREQUENCY_EXPOSURE_DAYS = 365;
    private static final double SCALING_FACTOR = 1000.0;

    public double convertTonsPerYearToMgPerDay(double amountOfSubstance) {
        return amountOfSubstance / (31536 * SCALING_FACTOR);
    }

    public double calculateHQ(double amountOfSubstance, double parameter) {
        return convertTonsPerYearToMgPerDay(amountOfSubstance) / parameter;
    }

    public double calculateHI(List<Double> hazardQuotients) {
        return hazardQuotients.stream().mapToDouble(Double::doubleValue).sum();
    }

    public double calculateCR(double amountOfSubstance, double parameter) {
        double LADD = (AVERAGE_HUMAN_BODY_WEIGHT * FREQUENCY_EXPOSURE_DAYS) / (amountOfSubstance * 1_000_000);
        return LADD * parameter;
    }
}
