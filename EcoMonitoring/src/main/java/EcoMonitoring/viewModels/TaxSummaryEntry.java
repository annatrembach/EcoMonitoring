package EcoMonitoring.viewModels;

import EcoMonitoring.models.Objects;

public class TaxSummaryEntry {
    public String year;
    public double sum;
    public Objects object;

    public String getYear() {
        return year;
    }

    public double getSum() {
        return sum;
    }
}
