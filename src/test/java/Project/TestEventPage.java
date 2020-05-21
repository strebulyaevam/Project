package Project;

import Project.pageobjects.EventPage;
import Project.pageobjects.MainPage;
import Project.pageobjects.TopMenu;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

@Test(description = "Test Epam Event page")
@ContextConfiguration(classes = TestConfig.class)
@Epic(value = "Project")
@Feature(value = "Test Epam Event page")
@Owner(value = "Стребуляева М.")
public class TestEventPage  extends AbstractTestNGSpringContextTests {
    private static Logger Log = LogManager.getLogger(TestEventPage.class);

    @Autowired
    TopMenu topMenu;

    @Autowired
    Session session;

    @Autowired
    MainPage mainPage;


    @Story(value = "View of upcoming events")
    @Test(description = "Check Event Count")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkEventCount() throws Exception {
        mainPage.openMainPage(session);
        EventPage eventPage = topMenu.clickOnEventItem(session).waitUntilLoad(session);
        eventPage.clickOnUpcEventButton(session);
        Log.info("Try to check Upcoming events amount");
        Assert.assertEquals(eventPage.getEventBtnCount(session), eventPage.getAllUpcomingEventsCount(session));
        Log.info("Upcoming events amount conform with Upcoming btn counting");
    }

    @Story(value = "View of upcoming events")
    @Test(description = "Check Event Cards")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkEventCards() throws Exception {
        mainPage.openMainPage(session);
        EventPage eventPage = topMenu.clickOnEventItem(session).waitUntilLoad(session);
        eventPage.clickOnUpcEventButton(session);
        Log.info("Checking if upcoming events amount is not null");
        Assert.assertTrue(eventPage.getAllUpcomingEventsCount(session)>0);
        Log.info("Success - upcoming events amount is not null");
        Log.info("Checking the structure of Event cards");
//        Assert.assertTrue(eventPage.checkEventCardСorrectnessByNum(session, 2));
        Assert.assertTrue(eventPage.checkAllUpcomingEventCards(session), "Structure of some Event cards is NOT correct");
        Log.info("Success - Structure of All Event cards is correct");
    }

    @Story(value = "View of upcoming events")
    @Test(description = "Check next week Events dates ")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkNextWeekEventsDates() throws Exception {
        mainPage.openMainPage(session);
        EventPage eventPage = topMenu.clickOnEventItem(session).waitUntilLoad(session);
        eventPage.clickOnUpcEventButton(session);
        Log.info("Checking if upcoming events amount is not null");
        Assert.assertTrue(eventPage.getAllUpcomingEventsCount(session)>0);
        Log.info("Success - upcoming events amount is not null");
        Log.info("Checking dates of the next week Events");
        Assert.assertTrue(eventPage.checkNextWeekCardDates(session));
        Log.info("Success - Structure of All Event cards is correct");
    }

    @Story(value = "View of upcoming events")
    @Test(description = "Check Paset event count by location")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkPastEventCount() throws Exception {
        mainPage.openMainPage(session);
        EventPage eventPage = topMenu.clickOnEventItem(session).waitUntilLoad(session);
        eventPage.clickOnPstEventButton(session);
        eventPage.clickOnLocationMenuItem(session, "Canada");
        eventPage.clickOnPstEventButton(session);
        Log.info("Try to check past events by location amount");
        Assert.assertEquals(eventPage.getEventBtnCount(session), eventPage.getAllUpcomingEventsCount(session));
        Log.info("past events amount conform with past btn counting");
    }

    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
