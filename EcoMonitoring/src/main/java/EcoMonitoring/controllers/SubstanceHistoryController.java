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

import java.util.List;

@Controller
public class SubstanceHistoryController {

    @Autowired
    Repository<SubstanceHistory> repository;

    @GetMapping("/SubstanceHistory")
    public String substanceHistoryPage(Model model) {
        List<SubstanceHistory> substanceHistoryList = repository.findAll(SubstanceHistory.class);
        model.addAttribute("substanceHistoryList", substanceHistoryList);
        return "AboutSubstanceHistory/SubstanceHistory";
    }

    @GetMapping("/CreateSubstanceHistory")
    public String createSubstanceHistory() {
        return "AboutSubstanceHistory/CreateSubstanceHistory";
    }

    @PostMapping("/CreateSubstanceHistory")
    public String createSubstanceHistoryPost(SubstanceHistory substanceHistory, @RequestParam("objectId") Long objectId,
                                                                                @RequestParam("substanceId") Long substanceId) {
        substanceHistory.setObject(repository.findById(Objects.class, objectId));
        substanceHistory.setSubstance(repository.findById(Substances.class, substanceId));
        repository.create(substanceHistory);
        return "redirect:/SubstanceHistory";
    }

    @GetMapping("/FindSubstanceHistory")
    public String findSubstanceHistory() {
        return "AboutSubstanceHistory/FindSubstanceHistory";
    }

    @PostMapping("/FindSubstanceHistory")
    public String findSubstanceHistoryPost(@RequestParam("id") Long id, Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            model.addAttribute("substanceHistory", substanceHistory);
        } else {
            model.addAttribute("error", "SubstanceHistory not found");
        }
        return "AboutSubstanceHistory/FindSubstanceHistory";
    }

    @GetMapping("/UpdateSubstanceHistory")
    public String updateSubstanceHistory(Long id, Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            model.addAttribute("substanceHistory", substanceHistory);
        } else {
            model.addAttribute("error", "Substance History not found.");
        }
        return "AboutSubstanceHistory/UpdateSubstanceHistory";
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
        return "redirect:/SubstanceHistory";
    }

    @GetMapping("/DeleteSubstanceHistory")
    public String deleteSubstanceHistory(Long id, Model model) {
        SubstanceHistory substanceHistory = repository.findById(SubstanceHistory.class, id);
        if (substanceHistory != null) {
            model.addAttribute("substanceHistory", substanceHistory);
        } else {
            model.addAttribute("error", "Substance History not found.");
        }
        return "AboutSubstanceHistory/DeleteSubstanceHistory";
    }

    @PostMapping("/DeleteSubstanceHistory")
    public String deleteSubstanceHistoryPost(SubstanceHistory substanceHistory) {
        repository.delete(substanceHistory);
        return "redirect:/SubstanceHistory";
    }
}
