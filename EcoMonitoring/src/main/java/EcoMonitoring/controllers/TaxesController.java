package EcoMonitoring.controllers;

import EcoMonitoring.models.Taxes;
import EcoMonitoring.models.Substances;
import EcoMonitoring.models.Enums.TaxType;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaxesController {

    @Autowired
    Repository<Taxes> repository;

    @GetMapping("/Taxes")
    public String taxesPage(Model model,
                            @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                            @RequestParam(name = "searchField", required = false) String searchField,
                            @RequestParam(name = "searchValue", required = false) String searchValue) {
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            List<Taxes> taxes = repository.findByFieldAndSorting(Taxes.class, searchField, searchValue, fieldForSort, true);
            model.addAttribute("taxes", taxes);
        } else {
            List<Taxes> taxes = repository.findWithSorting(Taxes.class, fieldForSort, true);
            model.addAttribute("taxes", taxes);
        }
        return "AboutTaxes/Taxes";
    }

    @GetMapping("/CreateTax")
    public String createTax(Model model) {
        List<Substances> substances = repository.findAll(Substances.class);
        model.addAttribute("substances", substances);
        model.addAttribute("taxTypes", TaxType.values());
        return "AboutTaxes/CreateTax";
    }

    @PostMapping("/CreateTax")
    public String createTaxPost(Taxes tax,
                                @RequestParam("substanceId") Long substanceId) {
        tax.setSubstance(repository.findById(Substances.class, substanceId));
        repository.create(tax);
        return "redirect:/Taxes";
    }

    @GetMapping("/FindTax")
    public String findTax() {
        return "AboutTaxes/FindTax";
    }

    @PostMapping("/FindTax")
    public String findTaxPost(@RequestParam("id") Long id, Model model) {
        Taxes tax = repository.findById(Taxes.class, id);
        if (tax != null) {
            model.addAttribute("tax", tax);
        } else {
            model.addAttribute("error", "Tax not found");
        }
        return "AboutTaxes/FindTax";
    }

    @GetMapping("/UpdateTax")
    public String updateTax(Long id, Model model) {
        Taxes tax = repository.findById(Taxes.class, id);
        if (tax != null) {
            model.addAttribute("tax", tax);
            model.addAttribute("substances", repository.findAll(Substances.class));
            model.addAttribute("taxTypes", TaxType.values());
        } else {
            model.addAttribute("error", "Tax not found.");
        }
        return "AboutTaxes/UpdateTax";
    }

    @PostMapping("/UpdateTax")
    public String updateTaxPost(@RequestParam("id") Long id,
                                @RequestParam("rate") double rate,
                                @RequestParam("taxType") TaxType taxType,
                                @RequestParam("substanceId") Long substanceId,
                                Model model) {
        Taxes tax = repository.findById(Taxes.class, id);
        if (tax != null) {
            tax.setRate(rate);
            tax.setTaxType(taxType);
            tax.setSubstance(repository.findById(Substances.class, substanceId));
            repository.update(tax);
            model.addAttribute("success", "Tax updated successfully.");
        } else {
            model.addAttribute("error", "Tax not found for updating.");
        }
        return "redirect:/Taxes";
    }

    @GetMapping("/DeleteTax")
    public String deleteTax(Long id, Model model) {
        Taxes tax = repository.findById(Taxes.class, id);
        if (tax != null) {
            model.addAttribute("tax", tax);
        } else {
            model.addAttribute("error", "Tax not found.");
        }
        return "AboutTaxes/DeleteTax";
    }

    @PostMapping("/DeleteTax")
    public String deleteTaxPost(@RequestParam("id") Long id) {
        Taxes tax = repository.findById(Taxes.class, id);
        if (tax != null) {
            repository.delete(tax);
        }
        return "redirect:/Taxes";
    }
}
