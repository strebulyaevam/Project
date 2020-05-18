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
        Assert.assertEquals(eventPage.getEventBtnCount(session), eventPage.getAllUpcomingEventsCount(session));
        Log.info("Upcoming events amount conform with Upcoming btn counting");

        Log.info(" Event Detail card will be opened");
        EventDetails eventDetails = eventPage.clickOnEventCardByNum(session, 0);
        eventDetails.waitUntilLoad(session);
        Log.info("Checking if subject at the Event Detail card is not null");
        Assert.assertFalse(eventDetails.eventSubjectIsEmpty(session));
        Log.info("Checking if 'Wish to attend' btn is at the Event Detail card");
        Assert.assertTrue(eventDetails.attendBtnPresence(session));

        eventDetails.clickShowMoreLink(session);
        Log.info("Checking if Event info is at the Event Detail card");
        Assert.assertFalse(eventDetails.infoTextIsEmpty(session));
        Log.info("Checking if Event Date is at the Event Detail card");
        Assert.assertFalse(eventDetails.eventDateIsEmpty(session));
        Log.info("Checking if Event Time is at the Event Detail card");
        Assert.assertFalse(eventDetails.eventTimeIsEmpty(session));
        Log.info("Checking if Event location is at the Event Detail card");
        Assert.assertFalse(eventDetails.eventLocationIsEmpty(session));
    }

    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
