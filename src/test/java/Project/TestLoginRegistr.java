package Project;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import Project.pageobjects.LoginPage;
import Project.pageobjects.RegistrPage;
import Project.pageobjects.TopMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

@Test(description = "Test Login and Registration")
@ContextConfiguration(classes = TestConfig.class)
@Epic(value = "Lab9")
@Feature(value = "Test Login and Registration")
@Owner(value = "Стребуляева М.")
public class TestLoginRegistr extends AbstractTestNGSpringContextTests {

    @Autowired
    MainMenu mainMenu;

    @Autowired
    TopMenu topMenu;

    @Autowired
    LoginPage loginPage;

    @Autowired
    RegistrPage registrPage;

    @Autowired
    Session session;


    @Story(value = "Login feature selection")
    @Test(description = "Login feature selection")
    public void buttonLoginDispalyTheCorrectPage() throws Exception {
        mainMenu.openMainPage(session);
        topMenu.waitUntilLoad(session);
        topMenu.clickOnLoginButton(session);
        loginPage.waitUntilLoad(session);
        Assert.assertTrue(mainMenu.titleContainString(session, "Вход"));
    }

    @Story(value = "Registration feature selection")
    @Test(description = "Registration feature selection")
    public void buttonRegistrationDispalyTheCorrectPage() throws Exception {
        mainMenu.openMainPage(session);
        topMenu.waitUntilLoad(session);
        topMenu.clickOnRegistrButton(session);
        registrPage.waitUntilLoad(session);
        Assert.assertTrue(mainMenu.titleContainString(session, "Регистрация"));
    }

    @AfterClass
    public void tearDown() {
        session.quit();
    }
}
