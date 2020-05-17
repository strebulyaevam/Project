package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class EventDetails {
    private static Logger Log = LogManager.getLogger(EventDetails.class);

    By loc_event_header = By.cssSelector("section[class*='evnt-hero-header'] h1");
    By loc_loader = By.cssSelector(".evnt-global-loader");
    By loc_attend_btn = By.cssSelector("section[class*='evnt-hero-header'] button.evnt-button.reg-button.attend");

    @Step("wait until Event details page is Loaded")
    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_event_header, "Event details");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step ("Check if event Subject Is Empty")
    public boolean eventSubjectIsEmpty (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_event_header)).getText().isEmpty();
    }

    @Step ("Check Wish to attend btn presence")
    public boolean attendBtnPresence (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_event_header)).isDisplayed();
    }
}
