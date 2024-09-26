package EcoMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubstanceController {

    @GetMapping("/Substances")
    public String substancesPage(Model model) {
        return "Substances";
    }
}
