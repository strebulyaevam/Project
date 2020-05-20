package Project;

import Project.pageobjects.EventDetails;
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

@Test(description = "Test Event detail page")
@ContextConfiguration(classes = TestConfig.class)
@Epic(value = "Project")
@Feature(value = "Test Event detail page")
@Owner(value = "Стребуляева М.")
public class TestEventDetailPage  extends AbstractTestNGSpringContextTests {

    private static Logger Log = LogManager.getLogger(TestEventDetailPage.class);

    @Autowired
    TopMenu topMenu;

    @Autowired
    Session session;

    @Autowired
    MainPage mainPage;


    @Story(value = "Check Event Detail card")
    @Test(description = "Check the structure of Event detail card")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkEventDetailCard() throws Exception {
        mainPage.openMainPage(session);
        EventPage eventPage = topMenu.clickOnEventItem(session).waitUntilLoad(session);
        eventPage.clickOnUpcEventButton(session);
        Log.info("Try to check Upcoming events amount");
        Assert.assertEquals(eventPage.getEventBtnCount(session), eventPage.getAllUpcomingEventsCount(session), "Upcoming events amount doesn't conform with Upcoming btn counting");
        Log.info("Upcoming events amount conform with Upcoming btn counting");

        Log.info(" Event Detail first card will be opened");
        EventDetails eventDetails = eventPage.clickOnEventCardByNum(session, 0);
        eventDetails.waitUntilLoad(session);
        Log.info("Checking if subject at the Event Detail card is not null");
        Assert.assertFalse(eventDetails.eventSubjectIsEmpty(session), "subject at the Event Detail card is null");
        Log.info("Checking if 'Wish to attend' btn is at the Event Detail card");
        Assert.assertTrue(eventDetails.attendBtnPresence(session), "'Wish to attend' btn is absent at the Event Detail card");

        eventDetails.clickShowMoreLink(session);
        Log.info("Checking if Event info is at the Event Detail card");
        Assert.assertFalse(eventDetails.infoTextIsEmpty(session), "Event info is absent at the Event Detail card");
        Log.info("Checking if Event Date is at the Event Detail card");
        Assert.assertFalse(eventDetails.eventDateIsEmpty(session), "Event Date is absent at the Event Detail card");
        Log.info("Checking if Event Time is at the Event Detail card");
        Assert.assertFalse(eventDetails.eventTimeIsEmpty(session), "Event Time is absent at the Event Detail card");
        Log.info("Checking if Event location is at the Event Detail card");
        Assert.assertFalse(eventDetails.eventLocationIsEmpty(session), "Event location is absent at the Event Detail card");
    }

    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
