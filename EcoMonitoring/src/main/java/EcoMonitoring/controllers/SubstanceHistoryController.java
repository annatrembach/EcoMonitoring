package EcoMonitoring.controllers;

import EcoMonitoring.models.Objects;
import EcoMonitoring.models.SubstanceHistory;
import EcoMonitoring.models.Substances;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubstanceHistoryController {

    @Autowired
    Repository<SubstanceHistory> repository;

    @GetMapping("/SubstanceHistory")
    public String substanceHistoryPage() {
        return "AboutSubstanceHistories/SubstanceHistory";
    }

    @GetMapping("/CreateSubstanceHistory")
    public String createSubstanceHistory() {
        return "AboutSubstanceHistories/CreateSubstanceHistory";
    }

    @PostMapping("/CreateSubstanceHistory")
    public String createSubstanceHistoryPost(SubstanceHistory substanceHistory) {
        repository.create(substanceHistory);
        return "AboutSubstanceHistories/SubstanceHistory";
    }

    @GetMapping("/FindSubstanceHistory")
    public String findSubstanceHistory() {
        return "AboutSubstanceHistories/FindSubstanceHistory";
    }

    @PostMapping("/FindSubstanceHistory")
    public String findSubstanceHistoryPost(@RequestParam("id") Long id, Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            model.addAttribute("substanceHistory", substanceHistory);
        } else {
            model.addAttribute("error", "SubstanceHistory not found");
        }
        return "AboutSubstanceHistories/FindSubstanceHistory";
    }

    @GetMapping("/FindSubstanceHistoryToUpdate")
    public String findSubstanceHistoryToUpdate() {
        return "AboutSubstanceHistories/FindSubstanceHistoryToUpdate";
    }

    @PostMapping("/FindSubstanceHistoryToUpdate")
    public String findSubstanceHistoryToUpdatePost(@RequestParam("id") Long id, Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            model.addAttribute("substanceHistory", substanceHistory);
            return "AboutSubstanceHistories/UpdateSubstanceHistory";
        } else {
            model.addAttribute("error", "SubstanceHistory not found");
            return "AboutSubstanceHistories/FindSubstanceHistoryToUpdate";
        }
    }

    @GetMapping("/UpdateSubstanceHistory")
    public String updateSubstanceHistory() {
        return "AboutSubstanceHistories/UpdateSubstanceHistory";
    }

    @PostMapping("/UpdateSubstanceHistory")
    public String UpdateSubstanceHistoryPost(@RequestParam("id") Long id,
                                             @RequestParam("substanceValue") double substanceValue,
                                             @RequestParam("yearOfObservation") String yearOfObservation,
                                             @RequestParam("objectId") Long objectId,
                                             @RequestParam("substanceId") Long substanceId,
                                             Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            substanceHistory.setSubstanceValue(substanceValue);
            substanceHistory.setYearOfObservation(yearOfObservation);
            substanceHistory.setObject(repository.findById(Objects.class, objectId));
            substanceHistory.setSubstance(repository.findById(Substances.class, substanceId));

            repository.update(substanceHistory);
            model.addAttribute("success", "SubstanceHistory updated successfully.");
        } else {
            model.addAttribute("error", "SubstanceHistory not found for updating.");
        }
        return "AboutSubstanceHistories/SubstanceHistory";
    }

    @GetMapping("/FindSubstanceHistoryToDelete")
    public String findSubstanceHistoryToDelete() {
        return "AboutSubstanceHistories/FindSubstanceHistoryToDelete";
    }

    @PostMapping("/FindSubstanceHistoryToDelete")
    public String findSubstanceHistoryToDeletePost(@RequestParam("id") Long id, Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            model.addAttribute("substanceHistory", substanceHistory);
            return "AboutSubstanceHistories/DeleteSubstanceHistory";
        } else {
            model.addAttribute("error", "SubstanceHistory not found");
            return "AboutSubstanceHistories/FindSubstanceHistoryToDelete";
        }
    }

    @GetMapping("/DeleteSubstanceHistory")
    public String deleteSubstanceHistory() {
        return "AboutSubstanceHistories/DeleteSubstanceHistory";
    }

    @PostMapping("/DeleteSubstanceHistory")
    public String deleteSubstanceHistoryPost(SubstanceHistory substanceHistory) {
        repository.delete(substanceHistory);
        return "AboutSubstanceHistories/SubstanceHistory";
    }
}
