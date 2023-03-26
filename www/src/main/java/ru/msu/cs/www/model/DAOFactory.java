package ru.msu.cs.www.model;

import ru.msu.cs.www.model.dao.PlanesDAO;
import ru.msu.cs.www.model.dao.AirlinesDAO;
import ru.msu.cs.www.model.dao.AirportsDAO;
import ru.msu.cs.www.model.dao.BonusProgramDAO;
import ru.msu.cs.www.model.dao.FlightsDAO;
import ru.msu.cs.www.model.dao.TicketsDAO;
import ru.msu.cs.www.model.dao.PassengersDAO;
import ru.msu.cs.www.model.dao.impl.PlanesDAOImpl;
import ru.msu.cs.www.model.dao.impl.AirlinesDAOImpl;
import ru.msu.cs.www.model.dao.impl.AirportsDAOImpl;
import ru.msu.cs.www.model.dao.impl.BonusProgramDAOImpl;
import ru.msu.cs.www.model.dao.impl.FlightsDAOImpl;
import ru.msu.cs.www.model.dao.impl.TicketsDAOImpl;
import ru.msu.cs.www.model.dao.impl.PassengersDAOImpl;

public class DAOFactory {
    private static PlanesDAOImpl planesDAO = null;
    private static AirlinesDAOImpl airlinesDAO = null;
    private static AirportsDAOImpl airportsDAO = null;
    private static BonusProgramDAOImpl bonusProgramDAO = null;
    private static FlightsDAOImpl flightsDAO = null;
    private static TicketsDAOImpl ticketsDAO = null;
    private static PassengersDAOImpl passengersDAO = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance(){
        if (instance == null){
            instance = new DAOFactory();
        }
        return instance;
    }

    public PlanesDAO getAircraftDAO(){
        if (planesDAO == null){
            planesDAO = new PlanesDAOImpl();
        }
        return planesDAO;
    }

    public AirlinesDAO getAirlinesDAO(){
        if (airlinesDAO == null){
            airlinesDAO = new AirlinesDAOImpl();
        }
        return airlinesDAO;
    }

    public AirportsDAO getAirportsDAO(){
        if (airportsDAO == null){
            airportsDAO = new AirportsDAOImpl();
        }
        return airportsDAO;
    }

    public BonusProgramDAO getBonusProgramDAO(){
        if (bonusProgramDAO == null){
            bonusProgramDAO = new BonusProgramDAOImpl();
        }
        return bonusProgramDAO;
    }

    public FlightsDAO getFlightsDAO(){
        if (flightsDAO == null){
            flightsDAO = new FlightsDAOImpl();
        }
        return flightsDAO;
    }

    public TicketsDAO getTicketsDAO(){
        if (ticketsDAO == null){
            ticketsDAO = new TicketsDAOImpl();
        }
        return ticketsDAO;
    }

    public PassengersDAO getUsersDAO(){
        if (passengersDAO == null){
            passengersDAO = new PassengersDAOImpl();
        }
        return passengersDAO;
    }
}
