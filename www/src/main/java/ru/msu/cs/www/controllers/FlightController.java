package ru.msu.cs.www.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import ru.msu.cs.www.model.dao.impl.FlightsDAOImpl;
import ru.msu.cs.www.model.dao.FlightsDAO;
import ru.msu.cs.www.model.entity.Flights;
import ru.msu.cs.www.model.entity.Airports;
import ru.msu.cs.www.model.dao.impl.AirportsDAOImpl;
import ru.msu.cs.www.model.dao.impl.PlanesDAOImpl;
import ru.msu.cs.www.model.dao.impl.AirlinesDAOImpl;

import java.util.Collection;
import ru.msu.cs.www.utils.TimeConvertUtil;

@Controller
public class FlightController {

    @GetMapping(value = {"/", "/flights"})
    public String main(Model model) {
        Collection<Flights> flights = new FlightsDAOImpl().getFlightsByFilter(FlightsDAO.getFilterBuilder().build());
        model.addAttribute("flights", flights);
        return "home";
    }

    @GetMapping("/flights/add/")
    public String add_flight_page(Model model) {
        model.addAttribute("add", true);
        model.addAttribute("edit", false);
        model.addAttribute("action", "/flights/add/");
        return "flight";
    }

    @PostMapping("/flights/add/")
    public String add_flight(
            @RequestParam(name = "aeroDep") Integer aeroDep,
            @RequestParam(name = "aeroArr") Integer aeroArr,
            @RequestParam(name = "timeDep") String timeDep,
            @RequestParam(name = "timeArr") String timeArr,
            @RequestParam(name = "cnt_seats", required = false) Integer cnt_seats
    ) {
        Flights flight = new Flights();
        Airports airportArr = new AirportsDAOImpl().getById(aeroArr);
        Airports airportDep = new AirportsDAOImpl().getById(aeroDep);
        flight.setAirportIdArr(airportArr);
        flight.setAirportIdDep(airportDep);
        flight.setTimeArr(TimeConvertUtil.fromString(timeArr));
        flight.setTimeDep(TimeConvertUtil.fromString(timeDep));
        flight.setCntSeats(cnt_seats);
        flight.setCntAvailableSeats(cnt_seats);
        flight.setAircraftId(new PlanesDAOImpl().getById(1));
        flight.setAirlineId(new AirlinesDAOImpl().getById(1));
        new FlightsDAOImpl().add(flight);
        return "redirect:/flights";
    }

    @GetMapping("/flights/edit/{id}")
    public String edit_flight_page(@PathVariable(name = "id") Long flightId, Model model) {
        model.addAttribute("add", false);
        model.addAttribute("edit", true);
        model.addAttribute("action", String.format("/flights/edit/%d", flightId));
        return "flight";
    }

    @PostMapping("/flights/edit/{id}")
    public String edit_flight(
            @RequestParam(name = "aeroDep", required = false) Integer aeroDep,
            @RequestParam(name = "aeroArr", required = false) Integer aeroArr,
            @RequestParam(name = "timeDep", required = false) String timeDep,
            @RequestParam(name = "timeArr", required = false) String timeArr,
            @RequestParam(name = "cnt_seats", required = false) Integer cnt_seats,
            @RequestParam(name = "cnt_available_seats", required = false) Integer cnt_available_seats,
            @PathVariable(name = "id") Integer flightId
    ) {
        Flights flight = new FlightsDAOImpl().getById(flightId);
        if (aeroArr != null) {
            Airports airportArr = new AirportsDAOImpl().getById(aeroArr);
            flight.setAirportIdArr(airportArr);
        }
        if (aeroDep != null) {
            Airports airportDep = new AirportsDAOImpl().getById(aeroDep);
            flight.setAirportIdDep(airportDep);
        }
        System.out.println(timeDep);
        if (timeDep != null && !timeDep.equals("")) {
            flight.setTimeDep(TimeConvertUtil.fromString(timeDep));
        }
        if (timeArr != null && !timeArr.equals("")) {
            flight.setTimeArr(TimeConvertUtil.fromString(timeArr));
        }
        if (cnt_seats != null) {
            flight.setCntSeats(cnt_seats);
        }
        if (cnt_available_seats != null) {
            flight.setCntAvailableSeats(cnt_available_seats);
        }
        new FlightsDAOImpl().update(flight);
        return "redirect:/flights";
    }

    @GetMapping("/flights/delete/{id}")
    public String delete_flight(@PathVariable(name = "id") Integer flightId) {
        Flights flight = new FlightsDAOImpl().getById(flightId);
        new FlightsDAOImpl().delete(flight);
        return "redirect:/flights";
    }
}
