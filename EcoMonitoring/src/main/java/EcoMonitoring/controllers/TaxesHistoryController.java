package EcoMonitoring.controllers;

import EcoMonitoring.models.Objects;
import EcoMonitoring.models.Taxes;
import EcoMonitoring.models.TaxesHistory;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaxesHistoryController {

    @Autowired
    Repository<TaxesHistory> repository;

    @GetMapping("/TaxesHistory")
    public String taxesHistoryPage(Model model,
                                   @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                   @RequestParam(name = "searchField", required = false) String searchField,
                                   @RequestParam(name = "searchValue", required = false) String searchValue) {
        List<TaxesHistory> taxesHistory;
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            taxesHistory = repository.findByFieldAndSorting(TaxesHistory.class, searchField, searchValue, fieldForSort, true);
        } else {
            taxesHistory = repository.findWithSorting(TaxesHistory.class, fieldForSort, true);
        }
        model.addAttribute("taxesHistory", taxesHistory);
        return "AboutTaxesHistory/TaxesHistory";
    }

    @GetMapping("/CreateTaxesHistory")
    public String createTaxesHistory(Model model) {
        List<Objects> objects = repository.findAll(Objects.class);
        List<Taxes> taxes = repository.findAll(Taxes.class);

        model.addAttribute("objects", objects);
        model.addAttribute("taxesHistory", taxes);

        return "AboutTaxesHistory/CreateTaxesHistory";
    }

    @PostMapping("/CreateTaxesHistory")
    public String createTaxesHistoryPost(TaxesHistory taxesHistory,
                                         @RequestParam("objectId") Long objectId,
                                         @RequestParam("taxId") Long taxId) {
        taxesHistory.setObject(repository.findById(Objects.class, objectId));
        taxesHistory.setTax(repository.findById(Taxes.class, taxId));
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
            model.addAttribute("taxesHistory", taxesHistory);
        } else {
            model.addAttribute("error", "TaxesHistory not found");
        }
        return "AboutTaxesHistory/FindTaxesHistory";
    }

    @GetMapping("/UpdateTaxesHistory")
    public String updateTaxesHistory(@RequestParam("id") Long id, Model model) {
        TaxesHistory taxesHistory = repository.findById(TaxesHistory.class, id);
        if (taxesHistory != null) {
            model.addAttribute("taxesHistory", taxesHistory);
        } else {
            model.addAttribute("error", "TaxesHistory not found.");
        }
        return "AboutTaxesHistory/UpdateTaxesHistory";
    }

    @PostMapping("/UpdateTaxesHistory")
    public String updateTaxesHistoryPost(@RequestParam("id") Long id,
                                         @RequestParam("yearOfCalculation") String yearOfCalculation,
                                         @RequestParam("sum") double sum,
                                         @RequestParam("objectId") Long objectId,
                                         @RequestParam("taxId") Long taxId,
                                         Model model) {
        TaxesHistory taxesHistory = repository.findById(TaxesHistory.class, id);
        if (taxesHistory != null) {
            taxesHistory.setYearOfCalculation(yearOfCalculation);
            taxesHistory.setSum(sum);
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
            model.addAttribute("taxesHistory", taxesHistory);
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
