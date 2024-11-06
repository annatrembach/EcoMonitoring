package EcoMonitoring.controllers;

import EcoMonitoring.models.*;
import EcoMonitoring.repository.Repository;
import EcoMonitoring.services.HealthRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HealthRiskHistoryController {

    @Autowired
    Repository<HealthRiskHistory> repository;

    @Autowired
    public HealthRiskService healthRiskService;

    @GetMapping("/HealthRiskHistory")
    public String healthRiskHistoryPage(Model model,
                                        @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                        @RequestParam(name = "searchField", required = false) String searchField,
                                        @RequestParam(name = "searchValue", required = false) String searchValue) {
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            List<HealthRiskHistory> healthRiskHistories = repository.findByFieldAndSorting(HealthRiskHistory.class, searchField, searchValue, fieldForSort, true);
            model.addAttribute("histories", healthRiskHistories);
        } else {
            List<HealthRiskHistory> healthRiskHistories = repository.findWithSorting(HealthRiskHistory.class, fieldForSort, true);
            model.addAttribute("histories", healthRiskHistories);
        }
        return "AboutHealthRiskHistory/HealthRiskHistory";
    }

    @GetMapping("/CreateHealthRiskHistory")
    public String createHealthRiskHistory(Model model) {
        model.addAttribute("objects", repository.findAll(Objects.class));
        model.addAttribute("healthRisks", repository.findAll(HealthRisk.class));
        return "AboutHealthRiskHistory/CreateHealthRiskHistory";
    }


    @PostMapping("/CreateHealthRiskHistory")
    public String createHealthRiskHistoryPost(HealthRiskHistory healthRiskHistory,
                                              @RequestParam("healthRiskId") Long healthRiskId,
                                              @RequestParam("yearOfObservation") String yearOfObservation,
                                              @RequestParam("objectId") Long objectId) {

        healthRiskHistory.setObject(repository.findById(Objects.class, objectId));
        healthRiskHistory.setHealthRisk(repository.findById(HealthRisk.class, healthRiskId));
        healthRiskHistory.setYearOfObservation(yearOfObservation);

        List<SubstanceHistory> filteredSubstanceHistories = repository.findByYearAndObject(SubstanceHistory.class, "yearOfObservation", yearOfObservation, "object", objectId);

        double amountOfSubstance = filteredSubstanceHistories.stream()
                .mapToDouble(SubstanceHistory::getSubstanceValue)
                .sum();

        if(healthRiskHistory.healthRisk.agentType == AgentType.NonCarcinogenic)
        {   healthRiskHistory.setRiskLevel(RiskLevel.nonCarcinogenicRiskLevel(healthRiskService.calculateHQ(amountOfSubstance, healthRiskHistory.healthRisk.parameter)));
            double temp = healthRiskService.calculateHQ(amountOfSubstance, healthRiskHistory.healthRisk.parameter);
            healthRiskHistory.setResultRiskNumber(temp);
        } else if(healthRiskHistory.healthRisk.agentType == AgentType.Carcinogenic) {
            healthRiskHistory.setRiskLevel(RiskLevel.carcinogenicRiskLevel(healthRiskService.calculateCR(amountOfSubstance, healthRiskHistory.healthRisk.parameter)));
            double temp = healthRiskService.calculateCR(amountOfSubstance, healthRiskHistory.healthRisk.parameter);
            healthRiskHistory.setResultRiskNumber(temp);
        }
        repository.create(healthRiskHistory);
        return "redirect:/HealthRiskHistory";
    }

    @GetMapping("/UpdateHealthRiskHistory")
    public String updateHealthRiskHistory(@RequestParam("id") Long id, Model model) {
        HealthRiskHistory healthRiskHistory = repository.findById(HealthRiskHistory.class, id);
        if (healthRiskHistory != null) {
            model.addAttribute("healthRiskHistory", healthRiskHistory);
            model.addAttribute("healthRisks", repository.findAll(HealthRisk.class));
        } else {
            model.addAttribute("error", "Health risk history not found.");
        }
        return "AboutHealthRiskHistory/UpdateHealthRiskHistory";
    }

    @PostMapping("/UpdateHealthRiskHistory")
    public String updateHealthRiskHistoryPost(@RequestParam("id") Long id, HealthRiskHistory updatedHistory, @RequestParam("healthRiskId") Long healthRiskId) {
        HealthRiskHistory healthRiskHistory = repository.findById(HealthRiskHistory.class, id);
        if (healthRiskHistory != null) {
            healthRiskHistory.setYearOfObservation(updatedHistory.getYearOfObservation());
            healthRiskHistory.setResultRiskNumber(updatedHistory.getResultRiskNumber());
            healthRiskHistory.setObject(updatedHistory.getObject());
            repository.update(healthRiskHistory);
        }
        return "redirect:/HealthRiskHistory";
    }

    @GetMapping("/DeleteHealthRiskHistory")
    public String deleteHealthRiskHistory(@RequestParam("id") Long id, Model model) {
        HealthRiskHistory healthRiskHistory = repository.findById(HealthRiskHistory.class, id);
        if (healthRiskHistory != null) {
            model.addAttribute("healthRiskHistory", healthRiskHistory);
        } else {
            model.addAttribute("error", "Health risk history not found.");
        }
        return "AboutHealthRiskHistory/DeleteHealthRiskHistory";
    }

    @PostMapping("/DeleteHealthRiskHistory")
    public String deleteHealthRiskHistoryPost(@RequestParam("id") Long id) {
        HealthRiskHistory healthRiskHistory = repository.findById(HealthRiskHistory.class, id);
        if (healthRiskHistory != null) {
            repository.delete(healthRiskHistory);
        }
        return "redirect:/HealthRiskHistory";
    }
}
