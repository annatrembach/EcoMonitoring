package EcoMonitoring.controllers;

import EcoMonitoring.models.*;
import EcoMonitoring.models.EmissionParams.*;
import EcoMonitoring.repository.Repository;
import EcoMonitoring.services.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompensationsHistoryController {

    @Autowired
    Repository<CompensationsHistory> repository;

    @Autowired
    CompensationService compensationService;

    @GetMapping("/CompensationsHistory")
    public String compensationsHistoryPage(Model model,
                                           @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                           @RequestParam(name = "searchField", required = false) String searchField,
                                           @RequestParam(name = "searchValue", required = false) String searchValue) {
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            List<CompensationsHistory> compensationsHistories = repository.findByFieldAndSorting(CompensationsHistory.class, searchField, searchValue, fieldForSort, true);
            model.addAttribute("histories", compensationsHistories);
        } else {
            List<CompensationsHistory> compensationsHistories = repository.findWithSorting(CompensationsHistory.class, fieldForSort, true);
            model.addAttribute("histories", compensationsHistories);
        }
        return "AboutCompensationsHistory/CompensationsHistory";
    }

    @GetMapping("/CreateCompensationsHistory")
    public String createCompensationsHistory(Model model) {
        model.addAttribute("objects", repository.findAll(Objects.class));
        model.addAttribute("compensations", repository.findAll(Compensations.class));
        return "AboutCompensationsHistory/CreateCompensationsHistory";
    }

    @PostMapping("/CreateCompensationsHistory")
    public String createCompensationsHistoryPost(
            @RequestParam("objectId") Long objectId,
            @RequestParam("compensationId") Long compensationId,
            @RequestParam("yearOfLoss") String year,
            @RequestParam("emissionType") EmissionType emissionType,
            @RequestParam(required = false) Double pvi,
            @RequestParam(required = false) Double pvnorm,
            @RequestParam(required = false) Double qv0,
            @RequestParam(required = false) Double P,
            @RequestParam(required = false) Double Kt,
            @RequestParam(required = false) Double Ksi,
            @RequestParam(required = false) Double TAir,
            WaterReturnParams waterReturnParameters,
            WaterReturnSeaWatersParams waterReturnSeaWatersParameters,
            WaterReturnAccidentClearParams waterReturnAccidentClearParameters,
            WaterReturnAccidentClearSeaWatersParams waterReturnAccidentClearSeaWatersParameters,
            UndergroundWaterParams undergroundWaterParameters,
            @RequestParam(required = false) Double W,
            @RequestParam(required = false) Double Tap) {


        Objects object = repository.findById(Objects.class, objectId);
        Compensations compensation = repository.findById(Compensations.class, compensationId);
        double MPC = compensation.getMPC();


        double compensationAmount = switch (emissionType) {
            case Air -> compensationService.calculateCompensationAir(
                pvi, pvnorm, qv0, TAir, P, MPC, Kt, Ksi);
            case WaterReturn -> compensationService.calculateCompensationWaterReturn(
                    waterReturnParameters.Cif1, waterReturnParameters.Cid1, waterReturnParameters.Qif1,
                    waterReturnParameters.T1, waterReturnParameters.Kkat1, waterReturnParameters.Kp1, waterReturnParameters.Yi1);
            case WaterReturnSeaWaters -> compensationService.calculateCompensationWaterReturnSeaWaters(
                    waterReturnSeaWatersParameters.Cif2, waterReturnSeaWatersParameters.Cid2, waterReturnSeaWatersParameters.Qif2,
                    waterReturnSeaWatersParameters.T2, waterReturnSeaWatersParameters.Kc2,
                    waterReturnSeaWatersParameters.Ka2, waterReturnSeaWatersParameters.Kb2, waterReturnSeaWatersParameters.Kd2,
                    waterReturnSeaWatersParameters.m2, waterReturnSeaWatersParameters.Mi2, waterReturnSeaWatersParameters.Yi2);
            case WaterReturnAccidentClear -> compensationService.calculateCompensationWaterReturnAccidentClear(
                    waterReturnAccidentClearParameters.Cif3, waterReturnAccidentClearParameters.Cid3, waterReturnAccidentClearParameters.Qif3,
                    waterReturnAccidentClearParameters.T3, waterReturnAccidentClearParameters.Kkat3,
                    waterReturnAccidentClearParameters.Kp3, waterReturnAccidentClearParameters.Yi3);
            case WaterReturnAccidentClearSeaWaters -> compensationService.calculateCompensationWaterReturnAccidentClearSeaWaters(
                    waterReturnAccidentClearSeaWatersParameters.Cif4, waterReturnAccidentClearSeaWatersParameters.Cid4,
                    waterReturnAccidentClearSeaWatersParameters.Qif4, waterReturnAccidentClearSeaWatersParameters.T4,
                    waterReturnAccidentClearSeaWatersParameters.Kc4, waterReturnAccidentClearSeaWatersParameters.Ka4,
                    waterReturnAccidentClearSeaWatersParameters.Kb4, waterReturnAccidentClearSeaWatersParameters.Kd4,
                    waterReturnAccidentClearSeaWatersParameters.m4, waterReturnAccidentClearSeaWatersParameters.Mi4,
                    waterReturnAccidentClearSeaWatersParameters.Yi4);
            case WaterArbitraryUse -> compensationService.calculateCompensationWaterArbitraryUse(W, Tap);
            case WaterUnderground -> compensationService.calculateCompensationWaterUnderground(
                    undergroundWaterParameters.Cif5, undergroundWaterParameters.Cid5, undergroundWaterParameters.Qif5,
                    undergroundWaterParameters.T5, undergroundWaterParameters.Kkat5,
                    undergroundWaterParameters.Kp5, undergroundWaterParameters.L5, undergroundWaterParameters.Yi5);
            default -> throw new IllegalArgumentException("Unknown compensation type");
        };

        CompensationsHistory compensationsHistory = new CompensationsHistory(year, compensationAmount, object, compensation, emissionType);
        repository.create(compensationsHistory);

        return "redirect:/CompensationsHistory";
    }

    @GetMapping("/UpdateCompensationsHistory")
    public String updateCompensationsHistory(@RequestParam("id") Long id, Model model) {
        CompensationsHistory compensationsHistory = repository.findById(CompensationsHistory.class, id);
        if (compensationsHistory != null) {
            model.addAttribute("compensationsHistory", compensationsHistory);
            model.addAttribute("compensations", repository.findAll(Compensations.class));
            model.addAttribute("objects", repository.findAll(Objects.class));
            model.addAttribute("substances", repository.findAll(Substances.class));

        } else {
            model.addAttribute("error", "Compensations history not found.");
        }
        return "AboutCompensationsHistory/UpdateCompensationsHistory";
    }

    @PostMapping("/UpdateCompensationsHistory")
    public String updateCompensationsHistoryPost(@RequestParam("id") Long id, CompensationsHistory updatedHistory, @RequestParam("compensationId") Long compensationId) {
        CompensationsHistory compensationsHistory = repository.findById(CompensationsHistory.class, id);
        if (compensationsHistory != null) {
            compensationsHistory.setYearOfLoss(updatedHistory.getYearOfLoss());
            compensationsHistory.setCompensationAmount(updatedHistory.getCompensationAmount());
            compensationsHistory.setObject(updatedHistory.getObject());
            compensationsHistory.setCompensation(repository.findById(Compensations.class, compensationId));
            repository.update(compensationsHistory);
        }
        return "redirect:/CompensationsHistory";
    }

    @GetMapping("/DeleteCompensationsHistory")
    public String deleteCompensationsHistory(@RequestParam("id") Long id, Model model) {
        CompensationsHistory compensationsHistory = repository.findById(CompensationsHistory.class, id);
        if (compensationsHistory != null) {
            model.addAttribute("compensationsHistory", compensationsHistory);
        } else {
            model.addAttribute("error", "Compensations history not found.");
        }
        return "AboutCompensationsHistory/DeleteCompensationsHistory";
    }

    @PostMapping("/DeleteCompensationsHistory")
    public String deleteCompensationsHistoryPost(@RequestParam("id") Long id) {
        CompensationsHistory compensationsHistory = repository.findById(CompensationsHistory.class, id);
        if (compensationsHistory != null) {
            repository.delete(compensationsHistory);
        }
        return "redirect:/CompensationsHistory";
    }
}
