package ru.msu.cs.www.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cs.www.model.dao.PassengersDAO;
import ru.msu.cs.www.model.entity.Passengers;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassengersDAOTest {
    PassengersDAO dao;
    Passengers usersTest1;
    Passengers usersTest2;
    String runId;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getUsersDAO();
        this.runId = UUID.randomUUID().toString();

        this.usersTest1 = new Passengers();
        this.usersTest1.setFullName("TestFullName1" + this.runId);
        this.usersTest1.setAddress("TestAddress1" + this.runId);
        this.usersTest1.setEmail("test1" + this.runId + "@mail.com");
        this.usersTest1.setPhoneNumber("+1" + this.runId);

        this.usersTest2 = new Passengers();
        this.usersTest2.setFullName("TestFullName2" + this.runId);
        this.usersTest2.setAddress("TestAddress2" + this.runId);
        this.usersTest2.setEmail("test2" + this.runId + "@mail.com");
        this.usersTest2.setPhoneNumber("+2" + this.runId);

        this.dao.add(this.usersTest1);
        this.dao.add(this.usersTest2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.usersTest1);
        this.dao.delete(this.usersTest2);

        this.dao = null;
        this.runId = null;
        this.usersTest1 = null;
        this.usersTest2 = null;
    }

    @Test
    public void testGetByFullName() {
        Collection<Passengers> all = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .fullName(this.runId)
                        .build()
        );
        Set<Passengers> expected = new HashSet<>();
        expected.add(this.usersTest1);
        expected.add(this.usersTest2);

        Set<Passengers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passengers> onlyTest1 = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .fullName("TestFullName1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.usersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByAddress() {
        Collection<Passengers> all = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .address(this.runId)
                        .build()
        );
        Set<Passengers> expected = new HashSet<>();
        expected.add(this.usersTest1);
        expected.add(this.usersTest2);

        Set<Passengers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passengers> onlyTest1 = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .address("TestAddress1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.usersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByEmail() {
        Collection<Passengers> all = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .email(this.runId)
                        .build()
        );
        Set<Passengers> expected = new HashSet<>();
        expected.add(this.usersTest1);
        expected.add(this.usersTest2);

        Set<Passengers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passengers> onlyTest1 = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .email("test1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.usersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByPhoneNumber() {
        Collection<Passengers> all = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .phoneNumber(this.runId)
                        .build()
        );
        Set<Passengers> expected = new HashSet<>();
        expected.add(this.usersTest1);
        expected.add(this.usersTest2);

        Set<Passengers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passengers> onlyTest1 = this.dao.getUsersByFilter(
                PassengersDAO.getFilterBuilder()
                        .phoneNumber("+1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.usersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }
}
