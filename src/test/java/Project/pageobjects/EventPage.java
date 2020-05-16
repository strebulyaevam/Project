package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class EventPage {

    private static Logger Log = LogManager.getLogger(EventPage.class);

    By loc_upc_events_btn = By.xpath("//span[@class = 'evnt-tab-text desktop' and contains(text(), 'Upcoming Events')]");
    By loc_select_events_btn_count = By.cssSelector("a.evnt-tab-link.nav-link.active>.evnt-tab-counter.evnt-label");
    By loc_events_cards = By.cssSelector(".evnt-cards-container .evnt-event-card");
    By loc_card_sections = By.cssSelector(".evnt-card-wrapper>div");
    By loc_head_sections = By.cssSelector(".evnt-event-details-table>div");
    By loc_card_elem_text = By.cssSelector("span");

    @Step ("wait Until Event Page is Loaded")
    public EventPage waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_upc_events_btn, "Upcoming Events");
        return this;
    }

    @Step ("click On Event Button")
     public void clickOnEventButton (Session session) throws Exception {
       TestHelper.clickOnElem(session.getWaiter(), loc_upc_events_btn, "Upcoming events btn");
    }

    @Step ("get Event Btn Count")
    public int getEventBtnCount (Session session) throws Exception {
        Log.info("Try to get events count from Events button");
        return Integer.parseInt(TestHelper.getTextFromElem(session.getWebDriver(), session.getWaiter(), loc_select_events_btn_count, "Events counter"));
    }

    @Step ("get All Upcoming Events Count")
    public int getAllUpcomingEventsCount (Session session) {
        Log.info("Try to get All upcoming events count from the Events list");
       return session.getWebDriver().findElements(loc_events_cards).size();
    }

    public boolean checkEventCardСorrectnessByNum (Session session, int num) {
        boolean result = false;
        WebElement cardHead;
        WebElement cardBody;
        WebElement cardLocation;
        WebElement cardLang;

        WebElement card = session.getWebDriver().findElements(loc_events_cards).get(num);
        if (card.findElements(loc_card_sections).get(0).getAttribute("class").equals("evnt-card-heading")){
            cardHead = card.findElements(loc_card_sections).get(0);
        }
        else {
            Log.error("Card-heading is not the first element at the card");
            return false;
        }
        if (card.findElements(loc_card_sections).get(1).getAttribute("class").equals("evnt-card-body")){
            cardBody = card.findElements(loc_card_sections).get(1);
        }
        else {
            Log.error("Card-body is not the second element at the card");
            return false;
        }
        if (cardHead.findElements(loc_head_sections).get(0).getAttribute("class").equals("evnt-details-cell online-cell") ||
            cardHead.findElements(loc_head_sections).get(0).getAttribute("class").equals("evnt-details-cell location-cell"))
        {
            cardLocation = cardHead.findElements(loc_head_sections).get(0);
            if (cardLocation.findElement(loc_card_elem_text).getText().isEmpty())
            {
                Log.error("Card-Location is empty");
                return false;
            }
        }
        else {
            Log.error("Card-location is not the first element of the heading");
            return false;
        }
        if (cardHead.findElements(loc_head_sections).get(1).getAttribute("class").equals("evnt-details-cell language-cell")){
            cardLang = card.findElements(loc_card_sections).get(1);
        }
        else {
            Log.error("Card-lang is not the second element of the heading");
            return false;
        }

    }
}

