package EcoMonitoring.controllers;

import EcoMonitoring.models.CrisisDamageHistory;
import EcoMonitoring.models.CrisisDamageParams.AirParams;
import EcoMonitoring.models.CrisisDamageParams.ForestParams;
import EcoMonitoring.models.CrisisDamageParams.SoilParams;
import EcoMonitoring.models.EmissionParams.*;
import EcoMonitoring.models.Enums.CrisisDamageType;
import EcoMonitoring.models.Objects;
import EcoMonitoring.models.Substances;
import EcoMonitoring.repository.Repository;
import EcoMonitoring.services.CrisisDamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static EcoMonitoring.models.Enums.EmissionType.*;

@Controller
public class CrisisDamageHistoryController {

    @Autowired
    private Repository<CrisisDamageHistory> repository;

    @Autowired
    private CrisisDamageService crisisDamageService;

    @GetMapping("/CrisisDamageHistory")
    public String crisisDamageHistoryPage(Model model,
                                          @RequestParam(name = "fieldForSort", required = false, defaultValue = "id") String fieldForSort,
                                          @RequestParam(name = "searchField", required = false) String searchField,
                                          @RequestParam(name = "searchValue", required = false) String searchValue) {
        List<CrisisDamageHistory> histories;
        if (searchField != null && searchValue != null && !searchField.isEmpty() && !searchValue.isEmpty()) {
            histories = repository.findByFieldAndSorting(CrisisDamageHistory.class, searchField, searchValue, fieldForSort, true);
        } else {
            histories = repository.findWithSorting(CrisisDamageHistory.class, fieldForSort, true);
        }
        model.addAttribute("histories", histories);
        return "AboutCrisisDamageHistory/CrisisDamageHistory";
    }

    @GetMapping("/CreateCrisisDamageHistory")
    public String createCrisisDamageHistory(Model model) {
        model.addAttribute("objects", repository.findAll(Objects.class));
        model.addAttribute("substances", repository.findAll(Substances.class));
        model.addAttribute("damageTypes", CrisisDamageType.values());
        return "AboutCrisisDamageHistory/CreateCrisisDamageHistory";
    }

    @PostMapping("/CreateCrisisDamageHistory")
    public String createCrisisDamageHistoryPost(
            @RequestParam("objectId") Long objectId,
            @RequestParam("substanceId") Long substanceId,
            @RequestParam("crisisDamageType") CrisisDamageType crisisDamageType,
            @RequestParam("yearOfDamage") String year,
            @RequestParam("damageType") String damageType,
            AirParams airParameters,
            SoilParams soilParameters,
            ForestParams forestParameters,
            @RequestParam(value = "W", required = false) Double W,
            @RequestParam(value = "Tap", required = false) Double Tap,
            WaterReturnParams waterReturnParameters,
            WaterReturnSeaWatersParams waterReturnSeaWatersParameters,
            WaterReturnAccidentClearParams waterReturnAccidentClearParameters,
            WaterReturnAccidentClearSeaWatersParams waterReturnAccidentClearSeaWatersParameters,
            UndergroundWaterParams undergroundWaterParameters) {

        Objects object = repository.findById(Objects.class, objectId);
        Substances substance = repository.findById(Substances.class, substanceId);

        double damageAmount = switch (crisisDamageType) {
            case Air -> CrisisDamageService.calculateAirPollutionDamage(
                    airParameters.miEmission, airParameters.sp, airParameters.kneB,
                    airParameters.kv, airParameters.kmp, airParameters.kpp);
            case WaterReturn -> crisisDamageService.calculateWaterReturnDamage(
                    waterReturnParameters.Cif1, waterReturnParameters.Cid1, waterReturnParameters.Qif1,
                    waterReturnParameters.T1, waterReturnParameters.Kkat1, waterReturnParameters.Kp1, waterReturnParameters.Yi1);
            case WaterReturnSeaWaters -> crisisDamageService.calculateWaterReturnSeaWatersDamage(
                    waterReturnSeaWatersParameters.Cif2, waterReturnSeaWatersParameters.Cid2, waterReturnSeaWatersParameters.Qif2,
                    waterReturnSeaWatersParameters.T2, waterReturnSeaWatersParameters.Kc2,
                    waterReturnSeaWatersParameters.Ka2, waterReturnSeaWatersParameters.Kb2, waterReturnSeaWatersParameters.Kd2,
                    waterReturnSeaWatersParameters.m2, waterReturnSeaWatersParameters.Mi2, waterReturnSeaWatersParameters.Yi2);
            case WaterReturnAccidentClear -> crisisDamageService.calculateWaterAccidentClearDamage(
                    waterReturnAccidentClearParameters.Cif3, waterReturnAccidentClearParameters.Cid3, waterReturnAccidentClearParameters.Qif3,
                    waterReturnAccidentClearParameters.T3, waterReturnAccidentClearParameters.Kkat3,
                    waterReturnAccidentClearParameters.Kp3, waterReturnAccidentClearParameters.Yi3);
            case WaterReturnAccidentClearSeaWaters ->
                    crisisDamageService.calculateWaterReturnAccidentClearSeaWatersDamage(
                            waterReturnAccidentClearSeaWatersParameters.Cif4, waterReturnAccidentClearSeaWatersParameters.Cid4,
                            waterReturnAccidentClearSeaWatersParameters.Qif4, waterReturnAccidentClearSeaWatersParameters.T4,
                            waterReturnAccidentClearSeaWatersParameters.Kc4, waterReturnAccidentClearSeaWatersParameters.Ka4,
                            waterReturnAccidentClearSeaWatersParameters.Kb4, waterReturnAccidentClearSeaWatersParameters.Kd4,
                            waterReturnAccidentClearSeaWatersParameters.m4, waterReturnAccidentClearSeaWatersParameters.Mi4,
                            waterReturnAccidentClearSeaWatersParameters.Yi4);
            case WaterArbitraryUse -> crisisDamageService.calculateWaterArbitraryUseDamage(W, Tap);
            case WaterUnderground -> crisisDamageService.calculateCompensationWaterUnderground(
                    undergroundWaterParameters.Cif5, undergroundWaterParameters.Cid5, undergroundWaterParameters.Qif5,
                    undergroundWaterParameters.T5, undergroundWaterParameters.Kkat5,
                    undergroundWaterParameters.Kp5, undergroundWaterParameters.L5, undergroundWaterParameters.Yi5);
            case SoilPollutionDamage -> CrisisDamageService.calculateSoilPollutionDamage(
                    soilParameters.a, soilParameters.goz, soilParameters.pd, soilParameters.kn, soilParameters.ko, soilParameters.vr);
            case ForestReclamationCost -> CrisisDamageService.calculateReclamationCost(
                    forestParameters.ks, forestParameters.kk, forestParameters.kz, forestParameters.s);
            case ForestProductionLoss -> CrisisDamageService.calculateForestProductionLoss(
                    forestParameters.nv, forestParameters.pl, forestParameters.rp);
            case ForestDamagedTimberLoss -> CrisisDamageService.calculateDamagedTimberLoss(
                    forestParameters.cd, forestParameters.cdp, forestParameters.md);
            case ForestForegoneIncome -> CrisisDamageService.calculateForegoneIncome(
                    forestParameters.cp, forestParameters.zpv, forestParameters.prp, forestParameters.vsp,
                    forestParameters.v, forestParameters.pl, forestParameters.rp);
            case ForestResourceLoss -> CrisisDamageService.calculateForestResourceLoss(
                    forestParameters.s, forestParameters.pl, forestParameters.rp);
            case ForestUsageLoss -> CrisisDamageService.calculateForestUsageLoss(
                    forestParameters.tForest, forestParameters.ngold, forestParameters.pl, forestParameters.rpTkl);
            case ForestPreparationCost -> CrisisDamageService.calculateSoilPreparationCost(
                    forestParameters.rpg, forestParameters.vza, forestParameters.pl);
            case ForestNurseryDamage -> CrisisDamageService.calculateNurseryDamage(
                    forestParameters.nzk, forestParameters.rv, forestParameters.zk);
            case ForestHuntingLoss -> CrisisDamageService.calculateHuntingLoss(
                    forestParameters.fvb, forestParameters.count);
            default -> throw new IllegalArgumentException("Unsupported damage type: " + crisisDamageType);
        };

        CrisisDamageHistory history = new CrisisDamageHistory(crisisDamageType, damageAmount, year, object, substance);
        repository.create(history);

        return "redirect:/CrisisDamageHistory";
    }

    @GetMapping("/UpdateCrisisDamageHistory")
    public String updateCrisisDamageHistory(@RequestParam("id") Long id, Model model) {
        CrisisDamageHistory history = repository.findById(CrisisDamageHistory.class, id);
        if (history != null) {
            model.addAttribute("history", history);
            model.addAttribute("objects", repository.findAll(Objects.class));
            model.addAttribute("substances", repository.findAll(Substances.class));
            model.addAttribute("damageTypes", CrisisDamageType.values());
        } else {
            model.addAttribute("error", "Crisis damage history not found.");
        }
        return "AboutCrisisDamageHistory/UpdateCrisisDamageHistory";
    }

    @PostMapping("/UpdateCrisisDamageHistory")
    public String updateCrisisDamageHistoryPost(@RequestParam("id") Long id,
                                                @RequestParam("objectId") Long objectId,
                                                @RequestParam("substanceId") Long substanceId,
                                                @RequestParam("damageType") CrisisDamageType damageType,
                                                @RequestParam("yearOfDamage") String year,
                                                @RequestParam("damageAssessment") double damageAssessment) {

        CrisisDamageHistory history = repository.findById(CrisisDamageHistory.class, id);
        if (history != null) {
            history.setObject(repository.findById(Objects.class, objectId));
            history.setSubstance(repository.findById(Substances.class, substanceId));
            history.setCrisisDamageType(damageType);
            history.setYearOfCrisisDamage(year);
            history.setDamageAssessment(damageAssessment);
            repository.update(history);
        }
        return "redirect:/CrisisDamageHistory";
    }

    @GetMapping("/DeleteCrisisDamageHistory")
    public String deleteCrisisDamageHistory(@RequestParam("id") Long id, Model model) {
        CrisisDamageHistory history = repository.findById(CrisisDamageHistory.class, id);
        if (history != null) {
            model.addAttribute("history", history);
        } else {
            model.addAttribute("error", "Crisis damage history not found.");
        }
        return "AboutCrisisDamageHistory/DeleteCrisisDamageHistory";
    }

    @PostMapping("/DeleteCrisisDamageHistory")
    public String deleteCrisisDamageHistoryPost(@RequestParam("id") Long id) {
        CrisisDamageHistory history = repository.findById(CrisisDamageHistory.class, id);
        if (history != null) {
            repository.delete(history);
        }
        return "redirect:/CrisisDamageHistory";
    }
}
