package Project;

import Project.pageobjects.MainPage;
import com.google.common.collect.ImmutableList;
import io.qameta.allure.*;
import Project.pageobjects.TopMenu;
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



    @Story(value = "Opening of the Epam Home Page")
    @Test(description = "Opening of the Epam Home Page")
    @Severity(value = SeverityLevel.BLOCKER)
    public void openHabrHomePage() throws Exception {
        mainPage.openMainPage(session);
        Assert.assertEquals(mainPage.getTitle(session), "Events Portal");
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
        Assert.assertEquals(expResult, actualResult);
    }


    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
