package EcoMonitoring.services;

import org.springframework.stereotype.Service;

@Service
public class CrisisDamageService {

    //AIR
    public static double calculateAirPollutionDamage(double MiEmission, double Sp, double KneB, double Kv, double Kmp, double Kpp) {
        return MiEmission * Sp * KneB * Kv * Kmp * Kpp;
    }

    //WATER
    public double calculateWaterReturnDamage(double Cif, double Cid, double Qif, double T, double Kkat, double Kp, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 10 * (Kkat * Kp * 1.5 * (emissionAmount * Yi));
    }

    public double calculateWaterReturnSeaWatersDamage(double Cif, double Cid, double Qif, double T, double Kc, double Ka, double Kb, double Kd, double m, double Mi, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 10 * (Kc * Ka * Kb * Kd * 3 * (emissionAmount * Yi)) / 3;
    }

    public double calculateWaterAccidentClearDamage(double Cif, double Cid, double Qif, double T, double Kkat, double Kp, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 10 * (1.5 * Kkat * Kp * 1.5 * (emissionAmount * Yi)) / 1.5;
    }

    public double calculateWaterReturnAccidentClearSeaWatersDamage(double Cif, double Cid, double Qif, double T, double Kc, double Ka, double Kb, double Kd, double m, double Mi, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 10 * (3 * Kc * Ka * Kb * Kd * 3 * (emissionAmount * Yi)) / 3;
    }

    public double calculateWaterArbitraryUseDamage(double W, double Tap) {
        return 10 * (5 * W * Tap);
    }

    public double calculateCompensationWaterUnderground(double Cif, double Cid, double Qif, double T, double Kkat, double Krp, double L, double Yi) {
        double emissionAmount = (Cif - Cid) * Qif * T * 1e-6;
        return 10 * (Kkat * Krp * L * emissionAmount * Yi);
    }

    //SOIL
    // Розрахунок шкоди від забруднення ґрунтів
    public static double calculateSoilPollutionDamage(double A, double GOZ, double PD, double KN, double Ko, double Vr) {
        return A * GOZ * PD * KN * Ko + Vr;
    }

    //FOREST
    // Розрахунок вартості рекультивації
    public static double calculateReclamationCost(double Ks, double Kk, double Kz, double S) {
        return Ks * Kk * Kz * S;
    }

    // Визначення розміру втрат лісогосподарського виробництва
    public static double calculateForestProductionLoss(double K, double Nv, double Pd) {
        return (1 - K) * Nv * Pd;
    }

    // Розрахунок збитків від пошкодженої заготовленої лісопродукції
    public static double calculateDamagedTimberLoss(double Cd, double Cdp, double Md) {
        return (Cd - Cdp) * Md;
    }

    // Розрахунок недоотриманого доходу лісокористувачів
    public static double calculateForegoneIncome(double Cp, double Zpv, double Prp, double Vsp, double v, double Pl, double Rp) {
        return Cp * (Zpv + Prp * (Vsp - v)) * Pl + Rp;
    }

    // Розрахунок шкоди від невикористання лісових ресурсів
    public static double calculateForestResourceLoss(double L, double Cl, double Rp) {
        return L * Cl + Rp;
    }

    // Розрахунок шкоди від неможливості використання властивостей лісу
    public static double calculateForestUsageLoss(double T, double NGOLD, double Pl, double RpTKL) {
        return T * NGOLD * Pl * RpTKL;
    }

    // Розрахунок витрат на підготовку ґрунту під створення лісових культур
    public static double calculateSoilPreparationCost(double Rpg, double Vza, double Pl) {
        return Rpg + Vza * Pl;
    }

    // Розрахунок шкоди лісовим розсадникам
    public static double calculateNurseryDamage(double Nzk, double Rv, double Zk) {
        return Nzk + Rv + Zk;
    }

    // Розрахунок шкоди мисливському господарству
    public static double calculateHuntingLoss(double FVB, double count) {
        return FVB * 5 * count;
    }
}

