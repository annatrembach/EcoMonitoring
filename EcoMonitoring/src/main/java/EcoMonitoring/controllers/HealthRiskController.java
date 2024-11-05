package EcoMonitoring.controllers;

import EcoMonitoring.models.HealthRisk;
import EcoMonitoring.models.Substances;
import EcoMonitoring.models.AgentType;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HealthRiskController {

    @Autowired
    Repository<HealthRisk> repository;

    @Autowired
    Repository<Substances> substancesRepository;

    @GetMapping("/HealthRisks")
    public String healthRisksPage(Model model,
                                  @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                  @RequestParam(name = "searchField", required = false) String searchField,
                                  @RequestParam(name = "searchValue", required = false) String searchValue) {
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            List<HealthRisk> healthRisks= repository.findByFieldAndSorting(HealthRisk.class, searchField, searchValue, fieldForSort, true);
            model.addAttribute("healthRisks", healthRisks);
        } else {
            List<HealthRisk> healthRisks = repository.findWithSorting(HealthRisk.class, fieldForSort, true);
            model.addAttribute("healthRisks", healthRisks);
        }
        return "AboutHealthRisks/HealthRisks";
    }

    @GetMapping("/CreateHealthRisk")
    public String createHealthRisk(Model model) {
        model.addAttribute("substances", substancesRepository.findAll(Substances.class));
        model.addAttribute("agentTypes", AgentType.values());
        return "AboutHealthRisks/CreateHealthRisk";
    }

    @PostMapping("/CreateHealthRisk")
    public String createHealthRiskPost(HealthRisk healthRisk, @RequestParam("substanceId") Long substanceId) {
        healthRisk.setSubstance(substancesRepository.findById(Substances.class, substanceId));
        repository.create(healthRisk);
        return "redirect:/HealthRisks";
    }

    @GetMapping("/UpdateHealthRisk")
    public String updateHealthRisk(@RequestParam("id") Long id, Model model) {
        HealthRisk healthRisk = repository.findById(HealthRisk.class, id);
        if (healthRisk != null) {
            model.addAttribute("healthRisk", healthRisk);
            model.addAttribute("substances", substancesRepository.findAll(Substances.class));
            model.addAttribute("agentTypes", AgentType.values());
        } else {
            model.addAttribute("error", "Health risk not found.");
        }
        return "AboutHealthRisks/UpdateHealthRisk";
    }

    @PostMapping("/UpdateHealthRisk")
    public String updateHealthRiskPost(@RequestParam("id") Long id, HealthRisk updatedHealthRisk, @RequestParam("substanceId") Long substanceId) {
        HealthRisk healthRisk = repository.findById(HealthRisk.class, id);
        if (healthRisk != null) {
            healthRisk.setAgentType(updatedHealthRisk.getAgentType());
            healthRisk.setParameter(updatedHealthRisk.getParameter());
            healthRisk.setSubstance(substancesRepository.findById(Substances.class, substanceId));
            repository.update(healthRisk);
        }
        return "redirect:/HealthRisks";
    }

    @GetMapping("/DeleteHealthRisk")
    public String deleteHealthRisk(@RequestParam("id") Long id, Model model) {
        HealthRisk healthRisk = repository.findById(HealthRisk.class, id);
        if (healthRisk != null) {
            model.addAttribute("healthRisk", healthRisk);
        } else {
            model.addAttribute("error", "Health risk not found.");
        }
        return "AboutHealthRisks/DeleteHealthRisk";
    }

    @PostMapping("/DeleteHealthRisk")
    public String deleteHealthRiskPost(@RequestParam("id") Long id) {
        HealthRisk healthRisk = repository.findById(HealthRisk.class, id);
        if (healthRisk != null) {
            repository.delete(healthRisk);
        }
        return "redirect:/HealthRisks";
    }
}
