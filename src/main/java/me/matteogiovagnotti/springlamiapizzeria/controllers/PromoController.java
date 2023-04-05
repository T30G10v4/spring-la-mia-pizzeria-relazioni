package me.matteogiovagnotti.springlamiapizzeria.controllers;

import jakarta.validation.Valid;
import me.matteogiovagnotti.springlamiapizzeria.exceptions.PizzaNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import me.matteogiovagnotti.springlamiapizzeria.models.Promo;
import me.matteogiovagnotti.springlamiapizzeria.services.PizzaService;
import me.matteogiovagnotti.springlamiapizzeria.services.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/promos")
public class PromoController {

    @Autowired
    private PromoService promoService;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId")Optional<Integer> id, Model model){

        Promo promo = new Promo();
        promo.setBeginDate(LocalDate.now());
        promo.setEndDate(LocalDate.now().plusMonths(1));

        if(id.isPresent()) {

            try {
                Pizza pizza = pizzaService.getById(id.get());
                promo.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }

        model.addAttribute("promo", promo);
        return "/promos/create";

    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute Promo formPromo, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return "/promos/create";

        Promo createdPromo = promoService.create(formPromo);
        return "redirect:/pizzas/" + Integer.toString(createdPromo.getPizza().getId());

    }

}
