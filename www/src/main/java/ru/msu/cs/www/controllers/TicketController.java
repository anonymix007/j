package ru.msu.cs.www.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.cs.www.model.dao.impl.FlightsDAOImpl;
import ru.msu.cs.www.model.dao.impl.TicketsDAOImpl;
import ru.msu.cs.www.model.dao.impl.PassengersDAOImpl;
import ru.msu.cs.www.model.entity.Flights;
import ru.msu.cs.www.model.entity.Tickets;
import ru.msu.cs.www.model.entity.Passengers;

import javax.persistence.PersistenceException;

@Controller
public class TicketController {

    @GetMapping("/tickets/add/")
    public String tickets_page() {
        return "tickets";
    }

    @GetMapping("/tickets/success/")
    public String tickets_success(Model model) {
        model.addAttribute("success", true);
        model.addAttribute("error", false);
        model.addAttribute("err_msg", "OK");
        return "tickets_result";
    }

    @GetMapping("/tickets/error/")
    public String tickets_error(Model model,
                                @RequestParam(name = "err_msg", defaultValue = "") String err) {
        model.addAttribute("error", true);
        model.addAttribute("success", false);
        model.addAttribute("err_msg", err);

        return "tickets_result";
    }

    @PostMapping("/tickets/add/")
    public String tickets_add(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "flight_id") Integer flight_id,
            @RequestParam(name = "user_id") Integer user_id,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "price") Integer price
    ) {
        try {
            Flights flight = new FlightsDAOImpl().getById(flight_id);
            Passengers passenger = new PassengersDAOImpl().getById(user_id);
            Tickets ticket = new Tickets();
            ticket.setFlightId(flight);
            ticket.setStatus(status);
            ticket.setUserId(passenger);
            ticket.setPrice(price);
            new TicketsDAOImpl().add(ticket);
            flight.setCntAvailableSeats(flight.getCntAvailableSeats() - 1);
            new FlightsDAOImpl().update(flight);
            return "redirect:/tickets/success/";
        }catch (PersistenceException e) {
            e.printStackTrace();
            if (e.getCause() instanceof ConstraintViolationException cve) {
                redirectAttributes.addAttribute("err_msg", "Constraint " + cve.getConstraintName() + " is violated");
            } else {
                redirectAttributes.addAttribute("err_msg", e.getMessage());
            }
            return "redirect:/tickets/error/";
        } catch (Exception e) {
            redirectAttributes.addAttribute("err_msg", e.getMessage());
            return "redirect:/tickets/error/";
        }
    }
}
