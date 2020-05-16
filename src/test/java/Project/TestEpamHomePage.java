package Project;

import Project.pageobjects.MainPage;
import com.google.common.collect.ImmutableList;
import io.qameta.allure.*;
import Project.pageobjects.TopMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.List;

@Test(description = "Test Epam Home page")
@ContextConfiguration(classes = TestConfig.class)
@Epic(value = "Project")
@Feature(value = "Test Epam Home page")
@Owner(value = "Стребуляева М.")
public class TestEpamHomePage extends AbstractTestNGSpringContextTests {

    @Autowired
    TopMenu topMenu;

    @Autowired
    MainPage mainPage;

    @Autowired
    Session session;

    private static Logger Log = LogManager.getLogger(TestEpamHomePage.class);

    @Story(value = "Opening of the Epam Home Page")
    @Test(description = "Opening of the Epam Home Page")
    @Severity(value = SeverityLevel.BLOCKER)
    public void openEpamHomePage() throws Exception {
        Log.info("Try to open Epam home page and check the title");
        mainPage.openMainPage(session);
        Assert.assertEquals(mainPage.getTitle(session), "Events Portal");
        Log.info("Epam home page title is correct");
    }

    @Story(value = "TopBar displaying")
    @Test(description = "TopBar displaying")
    public void topBarIsOntheHomePage() throws Exception {
        List<String> expResult = ImmutableList.<String>builder()
                .add("CALENDAR")
                .add("EVENTS")
                .add("TALKS LIBRARY")
                .add("SPEAKERS")
                .build();

        mainPage.openMainPage(session);
        topMenu.waitUntilLoad(session);
        List<String> actualResult = topMenu.getAllTopMenuItems(session);
        Log.info("Try to check Epam home page menu items");
        Assert.assertEquals(expResult, actualResult);
        Log.info("Epam home page menu items are correct");
    }


    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
