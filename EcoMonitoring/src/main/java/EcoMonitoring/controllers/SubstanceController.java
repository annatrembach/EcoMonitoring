package EcoMonitoring.controllers;

import EcoMonitoring.models.Objects;
import EcoMonitoring.models.Substances;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubstanceController {

    @Autowired
    Repository<Substances> repository;

    @GetMapping("/Substances")
    public String substancesPage() {
        return "AboutSubstances/Substances";
    }

    @GetMapping("/CreateSubstance")
    public String createSubstance() {
        return "AboutSubstances/CreateSubstance";
    }

    @PostMapping("/CreateSubstance")
    public String createSubstancePost(Substances substance) {
        repository.create(substance);
        return "AboutSubstances/Substances";
    }

    @GetMapping("/FindSubstance")
    public String findSubstance() {
        return "AboutSubstances/FindSubstance";
    }

    @PostMapping("/FindSubstance")
    public String findSubstancePost(@RequestParam("id") Long id, Model model) {
        Substances substance = repository.findById(Substances.class, id);
        if (substance != null) {
            model.addAttribute("substance", substance);
        } else {
            model.addAttribute("error", "Substance not found");
        }
        return "AboutSubstances/FindSubstance";
    }

    @GetMapping("/FindSubstanceToUpdate")
    public String findSubstanceToUpdate() {
        return "AboutSubstances/FindSubstanceToUpdate";
    }

    @PostMapping("/FindSubstanceToUpdate")
    public String findSubstanceToUpdatePost(@RequestParam("id") Long id, Model model) {
        Substances substance = repository.findById(Substances.class, id);
        if (substance != null) {
            model.addAttribute("substance", substance);
            return "AboutSubstances/UpdateSubstance";
        } else {
            model.addAttribute("error", "Substance not found");
            return "AboutSubstances/FindSubstanceToUpdate";
        }
    }

    @GetMapping("/UpdateSubstance")
    public String updateSubstance() {
        return "AboutSubstances/UpdateSubstance";
    }

    @PostMapping("/UpdateSubstance")
    public String UpdateSubstancePost(@RequestParam("id") Long id,
                                      @RequestParam("name") String name,
                                      @RequestParam("type") int type,
                                      Model model) {
        Substances substance = repository.findById(Substances.class, id);
        if (substance != null) {
            substance.setName(name);
            substance.setType(type);

            repository.update(substance);
            model.addAttribute("success", "Substance updated successfully.");
        } else {
            model.addAttribute("error", "Substance not found for updating.");
        }
        return "AboutSubstances/Substances";
    }

    @GetMapping("/FindSubstanceToDelete")
    public String findSubstanceToDelete() {
        return "AboutSubstances/FindSubstanceToDelete";
    }

    @PostMapping("/FindSubstanceToDelete")
    public String findSubstanceToDeletePost(@RequestParam("id") Long id, Model model) {
        Substances substance = repository.findById(Substances.class, id);
        if (substance != null) {
            model.addAttribute("substance", substance);
            return "AboutSubstances/DeleteSubstance";
        } else {
            model.addAttribute("error", "Substance not found");
            return "AboutSubstances/FindSubstanceToDelete";
        }
    }

    @GetMapping("/DeleteSubstance")
    public String deleteSubstance() {
        return "AboutSubstances/DeleteSubstance";
    }

    @PostMapping("/DeleteSubstance")
    public String deleteSubstancePost(Substances substance) {
        repository.delete(substance);
        return "AboutSubstances/Substances";
    }
}
