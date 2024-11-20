package EcoMonitoring.services;

import org.springframework.stereotype.Service;

@Service
public class CompensationService {

    public double calculateCompensationAir(double pvi, double pvnorm, double qv0, double T, double P, double MPC, double Kt, double Ksi) {
       double emissionAmount = 3.6e-6 * (pvi - pvnorm) * qv0 * T;
       double indRelativeDanger = 1/MPC;
       return emissionAmount * (1.1 * P) * indRelativeDanger * Kt * Ksi;
    }

    public double calculateCompensationWaterReturn(double Cif, double Cid, double Qif, double T, double Kkat, double Kp, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return Kkat * Kp * 1.5 * (emissionAmount * Yi);
    }

    public double calculateCompensationWaterReturnSeaWaters(double Cif, double Cid, double Qif, double T, double Kc, double Ka, double Kb, double Kd, double m, double Mi, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return Kc * Ka * Kb * Kd * 3 * (emissionAmount * Yi);
    }

    public double calculateCompensationWaterReturnAccidentClear(double Cif, double Cid, double Qif, double T, double Kkat, double Kp, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 1.5 * Kkat * Kp * 1.5 * (emissionAmount * Yi);
    }

    public double calculateCompensationWaterReturnAccidentClearSeaWaters(double Cif, double Cid, double Qif, double T, double Kc, double Ka, double Kb, double Kd, double m, double Mi, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 3 * Kc * Ka * Kb * Kd * 3 * (emissionAmount * Yi);
    }

    public double calculateCompensationWaterArbitraryUse(double W, double Tap) {
        return 5 * W * Tap;
    }

    public double calculateCompensationWaterUnderground(double Cif, double Cid, double Qif, double T, double Kkat, double Krp, double L, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return Kkat * Krp * L * emissionAmount * Yi;
    }
}
