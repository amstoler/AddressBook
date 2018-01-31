package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    AddressRepository addressRepository;

    @RequestMapping("/")
    public String listAddresses(Model model) {
        model.addAttribute("persons", addressRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String addressForm(Model model) {
        model.addAttribute("person", new Person());
        return "addressform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "addressform";
        }
        addressRepository.save(person);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showAddress(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", addressRepository.findOne(id));
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", addressRepository.findOne(id));
        return "addressform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id) {
        addressRepository.delete(id);
        return "redirect:/";
    }
}

    @GetMapping("/search")
    public String getSearch()
    {
        return "addresssearchform";
    }

    @PostMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search",searchString);
        model.addAttribute("addresses",addressRepository.findAllByNameContainingIgnoreCase(searchString));
        return "list";
    }
}


