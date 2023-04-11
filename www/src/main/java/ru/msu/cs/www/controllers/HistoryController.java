package ru.msu.cs.www.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.www.model.entity.Passengers;
import ru.msu.cs.www.model.entity.Tickets;
import ru.msu.cs.www.model.dao.impl.PassengersDAOImpl;
import ru.msu.cs.www.model.dao.impl.TicketsDAOImpl;
import ru.msu.cs.www.model.dao.TicketsDAO;
import ru.msu.cs.www.model.entity.Flights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class HistoryController {

    @GetMapping("/history/")
    public String history(Model model) {
        model.addAttribute("result", false);
        return "history";
    }

    @PostMapping("/history/")
    public String history_result(
            @RequestParam(name = "user_id") Integer user_id,
            Model model
    ) {
        Passengers passenger = new PassengersDAOImpl().getById(user_id);
        Collection<Tickets> tickets = new TicketsDAOImpl().getTicketsByFilter(TicketsDAO.getFilterBuilder().userId(user_id).build());
        List<Flights> flights = new ArrayList<>();
        for (Tickets ticket : tickets) {
            flights.add(ticket.getFlightId());
        }
        model.addAttribute("result", true);
        model.addAttribute("passenger", passenger);
        model.addAttribute("flights", flights);
        return "history";
    }
}
