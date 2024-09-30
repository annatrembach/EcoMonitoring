package EcoMonitoring.controllers;

import EcoMonitoring.models.Objects;
import EcoMonitoring.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ObjectsController {

    @Autowired
    Repository<Objects> repository;

    @GetMapping("/Objects")
    public String objectsPage() {
        return "AboutObjects/Objects";
    }

    @GetMapping("/CreateObject")
    public String createObject() {
        return "AboutObjects/CreateObject";
    }
    @PostMapping("/CreateObject")
    public String createObjectPost(Objects object) {
        repository.create(object);
        return "AboutObjects/Objects";
    }

    @GetMapping("/FindObject")
    public String findObject() {
        return "AboutObjects/FindObject";
    }
    @PostMapping("/FindObject")
    public String findObjectPost(@RequestParam("id") Long id, Model model) {
        Objects object = repository.findById(Objects.class, id);
        if (object != null) {
            model.addAttribute("object", object);
        } else {
            model.addAttribute("error", "Object not found");
        }
        return "AboutObjects/FindObject";
    }

    @GetMapping("/FindObjectToUpdate")
    public String findObjectToUpdate(){
        return "AboutObjects/FindObjectToUpdate";
    }
    @PostMapping("/FindObjectToUpdate")
    public String findObjectToUpdatePost(@RequestParam("id") Long id, Model model) {
        Objects object = repository.findById(Objects.class, id);
        if (object != null) {
            model.addAttribute("object", object);
            return "AboutObjects/UpdateObject";
        } else {
            model.addAttribute("error", "Object not found");
            return "AboutObjects/FindObjectToUpdate";
        }
    }
    @GetMapping("/UpdateObject")
    public String updateObject() {
        return "AboutObjects/UpdateObject";
    }
    @PostMapping("/UpdateObject")
    public String UpdateObjectPost(@RequestParam("id") Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("location") String location,
                                    Model model) {
        Objects object = repository.findById(Objects.class, id);
        if (object != null) {
            object.setName(name);
            object.setLocation(location);

            repository.update(object);
            model.addAttribute("success", "Object updated successfully.");
        } else {
            model.addAttribute("error", "Object not found for updating.");
        }
        return "AboutObjects/Objects";
    }

    @GetMapping("/FindObjectToDelete")
    public String findObjectToDelete(){
        return "AboutObjects/FindObjectToDelete";
    }
    @PostMapping("/FindObjectToDelete")
    public String findObjectToDeletePost(@RequestParam("id") Long id, Model model) {
        Objects object = repository.findById(Objects.class, id);
        if (object != null) {
            model.addAttribute("object", object);
            return "AboutObjects/DeleteObject";
        } else {
            model.addAttribute("error", "Object not found");
            return "AboutObjects/FindObjectToDelete";
        }
    }

    @GetMapping("/DeleteObject")
    public String deleteObject(){
        return "AboutObjects/DeleteObject";
    }
    @PostMapping("/DeleteObject")
    public String deleteObjectPost(Objects object) {
        repository.delete(object);
        return "AboutObjects/Objects";
    }
}
