package ru.msu.cs.www.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import ru.msu.cs.www.model.entity.Passengers;
import ru.msu.cs.www.model.dao.PassengersDAO;
import ru.msu.cs.www.model.dao.impl.PassengersDAOImpl;

import java.util.Collection;

@Controller
public class PassengerController {

    @GetMapping("/passengers/")
    public String passengers(Model model) {
        Collection<Passengers> passengers = new PassengersDAOImpl().getUsersByFilter(PassengersDAO.getFilterBuilder().build());
        model.addAttribute("passengers", passengers);
        return "passengers";
    }

    @GetMapping("/passengers/add/")
    public String add_passengers_page(Model model) {
        model.addAttribute("add", true);
        model.addAttribute("edit", false);
        model.addAttribute("action", "/passengers/add/");
        return "passenger";
    }

    @PostMapping("/passengers/add/")
    public String add_passenger(
            @RequestParam(name = "passengerName") String passengerName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone
    ) {
        Passengers passenger = new Passengers();
        passenger.setFullName(passengerName);
        passenger.setEmail(email);
        passenger.setPhoneNumber(phone);
        new PassengersDAOImpl().add(passenger);
        return "redirect:/passengers/";
    }

    @GetMapping("/passengers/edit/{id}")
    public String edit_passenger_page(@PathVariable(name = "id") Integer passengerId, Model model) {
        model.addAttribute("add", false);
        model.addAttribute("edit", true);
        model.addAttribute("action", String.format("/passengers/edit/%d", passengerId));
        return "passenger";
    }

    @PostMapping("/passengers/edit/{id}")
    public String edit_passenger(
            @PathVariable(name = "id") Integer passengerId,
            @RequestParam(name = "passengerName") String passengerName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone
    ) {
        Passengers passenger = new PassengersDAOImpl().getById(passengerId);

        if (email != null) {
            passenger.setEmail(email);
        }
        if (phone != null) {
            passenger.setPhoneNumber(phone);
        }
        if (passengerName != null) {
            passenger.setFullName(passengerName);
        }
        new PassengersDAOImpl().update(passenger);
        return "redirect:/passengers/";
    }

    @GetMapping("/passengers/delete/{id}")
    public String delete_passenger(@PathVariable(name = "id") Integer passengerId) {
        Passengers passenger = new PassengersDAOImpl().getById(passengerId);
        new PassengersDAOImpl().delete(passenger);
        return "redirect:/passengers/";
    }
}
