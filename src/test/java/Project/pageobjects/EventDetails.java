package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class EventDetails {
    private static Logger Log = LogManager.getLogger(EventDetails.class);

    By loc_event_header = By.cssSelector("section[class*='evnt-hero-header'] h1");
    By loc_loader = By.cssSelector(".evnt-global-loader");
    By loc_attend_btn = By.cssSelector("section[class*='evnt-hero-header'] button.evnt-button.reg-button.attend");
    By loc_show_more = By.cssSelector("span.show-more");
    By loc_info_text = By.cssSelector(".evnt-more-info-cell.about-cell p.evnt-more-info-title+p");
    By loc_event_date = By.cssSelector("section.evnt-agenda-panel li.evnt-day-tab span");
    By loc_event_time = By.cssSelector("section.evnt-agenda-panel div.evnt-timeline-cell.agenda-time span");
    By loc_event_location = By.xpath("//div[div[div[i[@class='fa fa-map-marker']]]]//div[@class='evnt-icon-info']/h4");


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
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_attend_btn)).isDisplayed();
    }

    @Step("Click Show More link")
    public void clickShowMoreLink (Session session) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_show_more, "Show more btn");
    }

    @Step ("Check if info Text Is Empty")
    public boolean infoTextIsEmpty (Session session) throws Exception {
        try {
            return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_info_text)).getText().isEmpty();
        }
        catch (Exception e){
            return true;
        }
    }

    @Step ("Check if event Date Is Empty")
    public boolean eventDateIsEmpty (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_event_date)).getText().isEmpty();
    }

    @Step ("Check if event Time Is Empty")
    public boolean eventTimeIsEmpty (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_event_time)).getText().isEmpty();
    }

    @Step ("Check if location is Empty")
    public boolean eventLocationIsEmpty (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_event_location)).getText().isEmpty();
    }
}
