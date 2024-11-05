package EcoMonitoring.services;

import EcoMonitoring.models.Objects;
import EcoMonitoring.models.TaxesHistory;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaxService {

    public double airTax(double substanceValue, double taxRate) {
        double result = 0;
            result = substanceValue * taxRate;
        return result;
    }

    public double waterTax(double substanceValue, double taxRate, double Kos) {
        double result = 0;
            result = substanceValue * taxRate * Kos;
        return result;
    }

    public double storageTax(double substanceValue, double taxRate, double Kt, double Ko) {
        double result = 0;
            result += substanceValue * taxRate * Kt * Ko;
        return result;
    }

    public double radioactiveTax(double substanceValue, double taxRate, double rns, double C1hc, double V1hc, double C2hc, double V2hc) {
        return substanceValue * taxRate + (rns * C1hc * V1hc + rns * C2hc * V2hc) * (1 / 32);
    }

    public double radioactiveStorageTax(double S, double taxRate, double V, double T) {
        return S = taxRate * V * T;
    }

}

