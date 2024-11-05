package EcoMonitoring.models;

public enum RiskLevel {
    Minimum, Low, Average, High;

    public static RiskLevel nonCarcinogenicRiskLevel(double HQ) {
        if (HQ > 3) {
            return High;
        } else if (HQ > 1.1 && HQ < 3) {
            return Average;
        } else if (HQ >= 0.11 && HQ <= 1.0) {
            return Low;
        } else if (HQ <= 0.1) {
            return Minimum;
        } else {
            throw new IllegalArgumentException("Invalid HQ value: " + HQ);
        }
    }

    public static RiskLevel carcinogenicRiskLevel(double cr) {
        if (cr > 1e-3) {
            return High;
        } else if (cr > 1e-4 && cr < 1e-3) {
            return Average;
        } else if (cr > 1e-6 && cr < 1e-4) {
            return Low;
        } else if (cr <= 1e-6) {
            return Minimum;
        } else {
            throw new IllegalArgumentException("Invalid cr value: " + cr);
        }
    }
}
