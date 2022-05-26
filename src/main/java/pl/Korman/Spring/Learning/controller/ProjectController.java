package pl.Korman.Spring.Learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//Kontroler wołający szablon html
@Controller
@RequestMapping("/projects")
class ProjectController {
    @GetMapping
    String showProjects(){
        return "projects";
    }
}
