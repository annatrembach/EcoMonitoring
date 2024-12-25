package EcoMonitoring.controllers;

import EcoMonitoring.models.*;
import EcoMonitoring.models.Enums.TaxType;
import EcoMonitoring.repository.Repository;
import EcoMonitoring.services.TaxService;
import EcoMonitoring.viewModels.TaxSummaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaxesHistoryController {

    @Autowired
    Repository<TaxesHistory> repository;

    @Autowired
    public TaxService taxService;

    @GetMapping("/TaxesHistory")
    public String taxesHistoryPage(Model model,
                                   @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                   @RequestParam(name = "searchField", required = false) String searchField,
                                   @RequestParam(name = "searchValue", required = false) String searchValue,
                                   @RequestParam(name = "objectId", required = false) Long objectId) {
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            List<TaxesHistory> taxesHistories = repository.findByFieldAndSorting(TaxesHistory.class, searchField, searchValue, fieldForSort, true);
            model.addAttribute("taxesHistories", taxesHistories);
        } else {
            List<TaxesHistory> taxesHistories = repository.findWithSorting(TaxesHistory.class, fieldForSort, true);
            model.addAttribute("taxesHistories", taxesHistories);
        }


        List<TaxSummaryEntry> taxSummary = new ArrayList<>();
        List<Objects> objects = repository.findAll(Objects.class);
        for (Objects object : objects) {

            List<TaxesHistory> taxesHistories = object.getTaxesHistories();
            Map<String, Double> summaryMap = new HashMap<>();
            for (TaxesHistory taxeshistory : taxesHistories) {
                summaryMap.merge(taxeshistory.getYearOfCalculation(), taxeshistory.getSum(), Double::sum);
            }
            for (Map.Entry<String, Double> entry : summaryMap.entrySet()) {
                TaxSummaryEntry taxSummaryEntry = new TaxSummaryEntry();
                taxSummaryEntry.year = entry.getKey();
                taxSummaryEntry.sum = entry.getValue();
                taxSummaryEntry.object = object;
                taxSummary.add(taxSummaryEntry);
            }
            model.addAttribute("taxSummary", taxSummary);
        }

        return "AboutTaxesHistory/TaxesHistory";
    }


    @GetMapping("/CreateTaxesHistory")
    public String createTaxesHistory(Model model) {
        List<Objects> objects = repository.findAll(Objects.class);
        List<Taxes> taxes = repository.findAll(Taxes.class);

        model.addAttribute("objects", objects);
        model.addAttribute("taxes", taxes);

        return "AboutTaxesHistory/CreateTaxesHistory";
    }

    @PostMapping("/CreateTaxesHistory")
    public String createTaxesHistoryPost(
            @RequestParam("objectId") Long objectId,
            @RequestParam("taxId") Long taxId,
            @RequestParam("yearOfCalculation") String year,
            @RequestParam(required = false) Double Kos,
            @RequestParam(required = false) Double Kt,
            @RequestParam(required = false) Double Ko,
            @RequestParam(required = false) Double rns,
            @RequestParam(required = false) Double C1hc,
            @RequestParam(required = false) Double V1hc,
            @RequestParam(required = false) Double C2hc,
            @RequestParam(required = false) Double V2hc,
            @RequestParam(required = false) Double S,
            @RequestParam(required = false) Double V,
            @RequestParam(required = false) Double T
    ) {

        Objects object = repository.findById(Objects.class, objectId);
        Taxes tax = repository.findById(Taxes.class, taxId);
        double taxRate = tax.getRate();
        List<SubstanceHistory> filteredSubstanceHistories = repository.findByYearAndObject(SubstanceHistory.class, "yearOfCalculation", year, "object", objectId);

        double substanceValue = filteredSubstanceHistories.stream()
                .mapToDouble(SubstanceHistory::getSubstanceValue)
                .sum();

        double sum = switch (tax.getTaxType()) {
            case Air -> taxService.airTax(substanceValue, taxRate);
            case Water -> taxService.waterTax(substanceValue, taxRate, Kos);
            case Storage -> taxService.storageTax(substanceValue, taxRate, Kt, Ko);
            case Radioactive -> taxService.radioactiveTax(substanceValue, taxRate, rns, C1hc, V1hc, C2hc, V2hc);
            case RadioactiveStorage -> taxService.radioactiveStorageTax(S, taxRate, V, T);
            default -> throw new IllegalArgumentException("Невідомий тип податку");
        };

        TaxesHistory taxesHistory = new TaxesHistory(year, sum, object, tax);
        repository.create(taxesHistory);
        return "redirect:/TaxesHistory";
    }


    @GetMapping("/FindTaxesHistory")
    public String findTaxesHistory() {
        return "AboutTaxesHistory/FindTaxesHistory";
    }

    @PostMapping("/FindTaxesHistory")
    public String findTaxesHistoryPost(@RequestParam("id") Long id, Model model) {
        TaxesHistory taxesHistory = repository.findById(TaxesHistory.class, id);
        if (taxesHistory != null) {
            model.addAttribute("taxesHistories", taxesHistory);
        } else {
            model.addAttribute("error", "TaxesHistory not found");
        }
        return "AboutTaxesHistory/FindTaxesHistory";
    }

    @GetMapping("/UpdateTaxesHistory")
    public String updateTaxesHistory(@RequestParam("id") Long id, Model model) {
        TaxesHistory taxesHistory = repository.findById(TaxesHistory.class, id);
        if (taxesHistory != null) {
            model.addAttribute("taxesHistories", taxesHistory);
            model.addAttribute("objects", repository.findAll(Objects.class));
            model.addAttribute("taxes", repository.findAll(Taxes.class));
            model.addAttribute("taxTypes", TaxType.values());
        } else {
            model.addAttribute("error", "TaxesHistory not found.");
        }
        return "AboutTaxesHistory/UpdateTaxesHistory";
    }



    @PostMapping("/UpdateTaxesHistory")
    public String updateTaxesHistoryPost(@RequestParam("id") Long id,
                                         @RequestParam("sum") double sum,
                                         @RequestParam("yearOfCalculation") String yearOfCalculation,
                                         @RequestParam("objectId") Long objectId,
                                         @RequestParam("taxId") Long taxId,
                                         Model model) {
        TaxesHistory taxesHistory = repository.findById(TaxesHistory.class, id);
        if (taxesHistory != null) {
            taxesHistory.setSum(sum);
            taxesHistory.setYearOfCalculation(yearOfCalculation);
            taxesHistory.setObject(repository.findById(Objects.class, objectId));
            taxesHistory.setTax(repository.findById(Taxes.class, taxId));

            repository.update(taxesHistory);
            model.addAttribute("success", "TaxesHistory updated successfully.");
        } else {
            model.addAttribute("error", "TaxesHistory not found for updating.");
        }
        return "redirect:/TaxesHistory";
    }


    @GetMapping("/DeleteTaxesHistory")
    public String deleteTaxesHistory(@RequestParam("id") Long id, Model model) {
        TaxesHistory taxesHistory = repository.findById(TaxesHistory.class, id);
        if (taxesHistory != null) {
            model.addAttribute("taxesHistories", taxesHistory);
        } else {
            model.addAttribute("error", "TaxesHistory not found.");
        }
        return "AboutTaxesHistory/DeleteTaxesHistory";
    }

    @PostMapping("/DeleteTaxesHistory")
    public String deleteTaxesHistoryPost(TaxesHistory taxesHistory) {
        repository.delete(taxesHistory);
        return "redirect:/TaxesHistory";
    }

}
