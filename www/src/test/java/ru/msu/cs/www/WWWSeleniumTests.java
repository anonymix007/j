package ru.msu.cs.www;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

import ru.msu.cs.www.model.dao.FlightsDAO;
import ru.msu.cs.www.model.dao.PassengersDAO;
import ru.msu.cs.www.model.dao.impl.FlightsDAOImpl;
import ru.msu.cs.www.model.entity.Flights;
import ru.msu.cs.www.model.dao.impl.PassengersDAOImpl;
import ru.msu.cs.www.model.entity.Passengers;

import java.util.Collection;

public class WWWSeleniumTests {

    String URL = "http://localhost:8080/";
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void settings() {
        final String ffDriver = "/usr/local/bin";
        System.setProperty("webdriver.firefox.marionette", ffDriver);
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1200, 1000));
        driver.manage().timeouts().implicitlyWait(10, SECONDS);

        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().pageLoadTimeout(10, SECONDS);
    }

    @Test(priority = 1)
    public void flights_test() {
        driver.get(URL);

        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Авиарейсы");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Авиарейсы");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/flights/add/'] button")).getText(), "Добавить авиарейс");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/tickets/add/'] button")).getText(), "Оформление билета");

        WebElement button = driver.findElement(By.cssSelector("a[href='/flights/add/'] button"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Добавление рейса");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Добавление рейса");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");

        driver.findElement(By.id("aeroDep")).sendKeys("1");
        driver.findElement(By.id("aeroArr")).sendKeys("2");
        driver.findElement(By.id("cnt_seats")).sendKeys("100");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('#timeDepart').setAttribute('value', '2022-03-11T00:00')");
        js.executeScript("document.querySelector('#timeArrive').setAttribute('value', '2022-03-11T12:12')");

        button = driver.findElement(By.cssSelector("button[type=submit]"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector(".accordion-item")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Russia - Moscow"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Russia - Saint Petersburg"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "2022-03-11T00:00"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "2022-03-11T12:12"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "100"));

        button = driver.findElement(By.cssSelector(".accordion-item button.btn-primary"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Редактирование рейса");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Редактирование рейса");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");

        driver.findElement(By.id("aeroDep")).sendKeys("2");
        driver.findElement(By.id("aeroArr")).sendKeys("3");
        driver.findElement(By.id("cnt_available_seats")).sendKeys("90");
        js.executeScript("document.querySelector('#timeDepart').setAttribute('value', '2022-04-11T00:00')");
        js.executeScript("document.querySelector('#timeArrive').setAttribute('value', '2022-04-11T12:12')");

        button = driver.findElement(By.cssSelector("button[type=submit]"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector(".accordion-item")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Russia - Saint Petersburg"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Russia - Novosibirsk"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "2022-04-11T00:00"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "2022-04-11T12:12"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "90"));

    }

    @Test(priority = 1)
    public void passengers_test() {
        driver.get(URL);

        WebElement elem = wait.until(visibilityOfElementLocated(By.cssSelector("a[href='/passengers/']")));
        elem.click();
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/add/'] button")).getText(), "Добавить пассажира");

        WebElement button = driver.findElement(By.cssSelector("a[href='/passengers/add/'] button"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Добавление пассажира");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Добавление пассажира");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");

        driver.findElement(By.cssSelector("#fullName")).sendKeys("Petrov Vasiliy Petrovich");
        driver.findElement(By.cssSelector("#passengerEmail")).sendKeys("petrov@mail.ru");
        driver.findElement(By.cssSelector("#passengerPhone")).sendKeys("+79158856472");

        button = driver.findElement(By.cssSelector("button[type=submit]"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector(".accordion-item")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Petrov Vasiliy Petrovich"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "petrov@mail.ru"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "+79158856472"));

        button = driver.findElement(By.cssSelector(".accordion-item button.btn-primary"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Редактирование пассажира");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Редактирование пассажира");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");

        driver.findElement(By.cssSelector("#fullName")).sendKeys("Petrov Vasiliy Aleksandrovich");
        driver.findElement(By.cssSelector("#passengerEmail")).sendKeys("petrovVA@mail.ru");
        driver.findElement(By.cssSelector("#passengerPhone")).sendKeys("+79158856473");

        button = driver.findElement(By.cssSelector("button[type=submit]"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector(".accordion-item")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Petrov Vasiliy Aleksandrovich"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "petrovVA@mail.ru"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "+79158856473"));

    }

    @Test(priority = 2)
    public void history_and_tickets_test() {
        driver.get(URL);

        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "90"));
        WebElement elem = wait.until(visibilityOfElementLocated(By.cssSelector("a[href='/tickets/add/']")));
        elem.click();

        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "Оформление билета");
        Assert.assertEquals(driver.findElement(By.cssSelector("h2")).getText(), "Оформление билета");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");

        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        Collection<Flights> flights = new FlightsDAOImpl().getFlightsByFilter(FlightsDAO.getFilterBuilder().build());
        Integer flightId = flights.iterator().next().getId();
        Collection<Passengers> passengers = new PassengersDAOImpl().getUsersByFilter(PassengersDAO.getFilterBuilder().build());
        Integer passengerId = passengers.iterator().next().getId();
        driver.findElement(By.id("flightId")).sendKeys(String.format("%d", flightId));
        driver.findElement(By.id("userId1")).sendKeys(String.format("%d", passengerId));
        driver.findElement(By.id("status")).sendKeys("paid");
        driver.findElement(By.id("price")).sendKeys("666");
        WebElement button = driver.findElement(By.cssSelector("button[type=submit]"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector("h2")), "Билет успешно оформлен!"));
        driver.get(URL);
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "89"));

        elem = driver.findElement(By.cssSelector("a[href='/history/']"));
        elem.click();
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "История полетов");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");
        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector("label")),"ID пассажира"));

        driver.findElement(By.id("userIdInput")).sendKeys(String.format("%d", passengerId));
        button = driver.findElement(By.cssSelector("button[type=submit]"));
        button.click();
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        Assert.assertEquals(driver.findElement(By.cssSelector("a.navbar-brand")).getText(), "Главная");
        Assert.assertEquals(driver.getTitle(), "История полетов");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/passengers/']")).getText(), "Пассажиры");
        Assert.assertEquals(driver.findElement(By.cssSelector("a[href='/history/']")).getText(), "История полетов");
        wait.until(visibilityOfElementLocated(By.cssSelector("form")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector("label")),"ID пассажира"));
        wait.until(visibilityOfElementLocated(By.cssSelector(".accordion-item")));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Russia - Saint Petersburg"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "Russia - Novosibirsk"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "2022-04-11T00:00"));
        wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion-item")), "2022-04-11T12:12"));
    }

    @Test(priority = 3)
    public void delete_test() {
        driver.get(URL);
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));

        WebElement button = driver.findElement(By.cssSelector(".accordion-item button.btn-dark"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        wait.until(not(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion")), "Russia - Novosibirsk")));

        WebElement elem = wait.until(visibilityOfElementLocated(By.cssSelector("a[href='/passengers/']")));
        elem.click();
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        button = driver.findElement(By.cssSelector(".accordion-item button.btn-dark"));
        button.click();
        wait.until(stalenessOf(button));
        wait.until(visibilityOfElementLocated(By.cssSelector("a.navbar-brand")));
        wait.until(not(textToBePresentInElement(driver.findElement(By.cssSelector(".accordion")), "+79158856473")));
    }

    @AfterClass
    public void finish() {
        driver.quit();
    }
}
