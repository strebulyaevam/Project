package Project.pageobjects;

import io.qameta.allure.Step;
import Project.Session;
import Project.helpers.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class RegistrPage {
    private static Logger Log = LogManager.getLogger(RegistrPage.class);

    By loc_email = By.cssSelector("input[type='email']");

@Step
    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_email, "RegistrationPage");
    }
}
