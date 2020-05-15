package Project;

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

@Test(description = "Test Habr Home page")
@ContextConfiguration(classes = TestConfig.class)
@Epic(value = "Lab9")
@Feature(value = "Test Habr Home page")
@Owner(value = "Стребуляева М.")
public class TestHabrHomePage extends AbstractTestNGSpringContextTests {

    @Autowired
    TopMenu topMenu;

    @Autowired
    Session session;



    @Story(value = "Opening of the Habr Home Page")
    @Test(description = "Opening of the Habr Home Page")
    @Severity(value = SeverityLevel.BLOCKER)
    public void openHabrHomePage() throws Exception {
        mainMenu.openMainPage(session);
        Assert.assertEquals(mainMenu.getTitle(session), "Лучшие публикации за сутки / Хабр");
    }

    @Story(value = "TopBar displaying")
    @Test(description = "TopBar displaying")
    public void topBarIsOntheHomePage() throws Exception {
        List<String> expResult = ImmutableList.<String>builder()
                .add("Все потоки")
                .add("Разработка")
                .add("Научпоп")
                .add("Администрирование")
                .add("Дизайн")
                .add("Менеджмент")
                .add("Маркетинг")
                .build();

        mainMenu.openMainPage(session);
        topMenu.waitUntilLoad(session);
        List<String> actualResult = topMenu.getAllTopMenuItems(session);
        Assert.assertEquals(expResult, actualResult);
    }

    @Story(value = "First item of TopBar selection")
    @Test(description = "First item of TopBar selection")
    public void firstItemOfTopBarIsSelected() throws Exception {
        mainMenu.openMainPage(session);
        topMenu.waitUntilLoad(session);
        Assert.assertTrue(topMenu.isMenuItemSelected(session,"Все потоки"));
    }

    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
