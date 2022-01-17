package org.elsys.ip.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    @GetMapping("/calc")
    public String getForm(Model model) {
        model.addAttribute("form", new Form());
        return "form";
    }

    @PostMapping("/calc")
    public String submitForm(@ModelAttribute Form form, Model model) {
        model.addAttribute("form", form);
        return "result.html";
    }

}