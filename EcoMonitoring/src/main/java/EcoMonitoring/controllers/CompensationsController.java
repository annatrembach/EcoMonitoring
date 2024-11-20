package EcoMonitoring.controllers;

import EcoMonitoring.models.AgentType;
import EcoMonitoring.models.Compensations;
import EcoMonitoring.models.EmissionType;
import EcoMonitoring.models.Substances;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompensationsController {

    @Autowired
    Repository<Compensations> repository;

    @Autowired
    Repository<Substances> substancesRepository;

    @GetMapping("/Compensations")
    public String compensationsPage(Model model,
                                    @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                    @RequestParam(name = "searchField", required = false) String searchField,
                                    @RequestParam(name = "searchValue", required = false) String searchValue) {
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            List<Compensations> compensations = repository.findByFieldAndSorting(Compensations.class, searchField, searchValue, fieldForSort, true);
            model.addAttribute("compensations", compensations);
        } else {
            List<Compensations> compensations = repository.findWithSorting(Compensations.class, fieldForSort, true);
            model.addAttribute("compensations", compensations);
        }
        return "AboutCompensations/Compensations";
    }

    @GetMapping("/CreateCompensation")
    public String createCompensation(Model model) {
        model.addAttribute("substances", substancesRepository.findAll(Substances.class));
        model.addAttribute("emissionsTypes", EmissionType.values());
        return "AboutCompensations/CreateCompensation";
    }

    @PostMapping("/CreateCompensation")
    public String createCompensationPost(Compensations compensation, @RequestParam("substanceId") Long substanceId) {
        compensation.setSubstance(substancesRepository.findById(Substances.class, substanceId));
        repository.create(compensation);
        return "redirect:/Compensations";
    }

    @GetMapping("/UpdateCompensation")
    public String updateCompensation(@RequestParam("id") Long id, Model model) {
        Compensations compensation = repository.findById(Compensations.class, id);
        if (compensation != null) {
            model.addAttribute("compensation", compensation);
            model.addAttribute("emissionsTypes", EmissionType.values());
            model.addAttribute("substances", substancesRepository.findAll(Substances.class));
        } else {
            model.addAttribute("error", "Compensation not found.");
        }
        return "AboutCompensations/UpdateCompensation";
    }

    @PostMapping("/UpdateCompensation")
    public String updateCompensationPost(@RequestParam("id") Long id, Compensations updatedCompensation, @RequestParam("substanceId") Long substanceId) {
        Compensations compensation = repository.findById(Compensations.class, id);
        if (compensation != null) {
            compensation.setMPC(updatedCompensation.getMPC());
            Substances substance = substancesRepository.findById(Substances.class, substanceId);
            compensation.setSubstance(substance);
            repository.update(compensation);
        }
        return "redirect:/Compensations";
    }

    @GetMapping("/DeleteCompensation")
    public String deleteCompensation(@RequestParam("id") Long id, Model model) {
        Compensations compensation = repository.findById(Compensations.class, id);
        if (compensation != null) {
            model.addAttribute("compensation", compensation);
        } else {
            model.addAttribute("error", "Compensation not found.");
        }
        return "AboutCompensations/DeleteCompensation";
    }

    @PostMapping("/DeleteCompensation")
    public String deleteCompensationPost(@RequestParam("id") Long id) {
        Compensations compensation = repository.findById(Compensations.class, id);
        if (compensation != null) {
            repository.delete(compensation);
        }
        return "redirect:/Compensations";
    }
}
