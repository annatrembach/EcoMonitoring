package EcoMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubstanceHistoryController {

    @GetMapping("/SubstanceHistory")
    public String substancesHistoryPage(Model model) {
        return "SubstanceHistory";
    }
}
