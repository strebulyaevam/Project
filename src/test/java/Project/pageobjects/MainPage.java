package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import config.ProjectConfig;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainPage {
    private static Logger Log = LogManager.getLogger(MainPage.class);

    @Autowired
    ProjectConfig cfg;

    By loc_upc_event = By.xpath("//a[@class='evnt-filtered-link'][contains(text(), 'Upcoming events')]");

    @Step
    public void openMainPage (Session session) throws Exception {
        TestHelper.getURL(session.getWebDriver(), cfg.hostname());
        TestHelper.isPageLoad(session.getWaiter(), loc_upc_event, "Main Menu");
    }

    public String getTitle (Session session) {
        return TestHelper.getTitle(session.getWebDriver());
    }

}
