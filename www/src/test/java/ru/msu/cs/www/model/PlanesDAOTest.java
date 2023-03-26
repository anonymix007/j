package ru.msu.cs.www.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cs.www.model.dao.PlanesDAO;
import ru.msu.cs.www.model.entity.Planes;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanesDAOTest {
    PlanesDAO dao;
    Planes aircraftTest1;
    Planes aircraftTest2;
    String runId;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getAircraftDAO();
        this.runId = UUID.randomUUID().toString();

        this.aircraftTest1 = new Planes();
        this.aircraftTest1.setModelName("TestName1" + this.runId);

        this.aircraftTest2 = new Planes();
        this.aircraftTest2.setModelName("TestName2" + this.runId);

        this.dao.add(this.aircraftTest1);
        this.dao.add(this.aircraftTest2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.aircraftTest1);
        this.dao.delete(this.aircraftTest2);

        this.dao = null;
        this.runId = null;
        this.aircraftTest1 = null;
        this.aircraftTest2 = null;
    }

    @Test
    public void testGetByName() {
        Collection<Planes> allTestNames = this.dao.getAircraftByFilter(
                PlanesDAO.getFilterBuilder()
                        .modelName(this.runId)
                        .build()
        );
        Set<Planes> expected = new HashSet<>();
        expected.add(this.aircraftTest1);
        expected.add(this.aircraftTest2);

        Set<Planes> got = new HashSet<>(allTestNames);

        assertEquals(expected, got);

        Collection<Planes> onlyTest1 = this.dao.getAircraftByFilter(
                PlanesDAO.getFilterBuilder()
                        .modelName("TestName1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.aircraftTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }
}
