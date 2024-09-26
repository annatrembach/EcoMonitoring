package EcoMonitoring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ObjectsController {

    @GetMapping("/Objects")
    public String objectsPage(Model model) {
        return "Objects";
    }
}
