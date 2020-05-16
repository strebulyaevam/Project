package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    By loc_body_sections = By.cssSelector(".evnt-card-cell div");
    By loc_card_elem_text = By.cssSelector("span");
    By loc_card_dates_elem_text = By.cssSelector(".evnt-dates-cell.dates span");
    By loc_speaker_data = By.cssSelector(".speakers div[data-name]");
    By loc_loader = By.cssSelector(".evnt-global-loader");

    @Step ("wait Until Event Page is Loaded")
    public EventPage waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_upc_events_btn, "Upcoming Events");
        waitLoader(session);
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

    @Step ("Check Event Card Сorrectness By Num ")
    public boolean checkEventCardСorrectnessByNum (Session session, int num) {
        boolean result = true;
        WebElement cardHead;
        WebElement cardBody;
        WebElement cardLocation;
        WebElement cardLang;
        WebElement cardEventName;
        WebElement cardDates;

        WebElement card = session.getWebDriver().findElements(loc_events_cards).get(num);
        Log.info("Check top level structure of -" + num + "- card");
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
        if (card.findElements(loc_card_sections).get(2).getAttribute("class").equals("evnt-card-body")){
            cardBody = card.findElements(loc_card_sections).get(1);
        }
        else {
            Log.error("Card-body is not the second element at the card");
            return false;
        }
        Log.info("Top level structure: heading and body of -" + num + "- card is successfully");

        Log.info("Check heading structure of -" + num + "- card");
        if (cardHead.findElements(loc_head_sections).get(0).getAttribute("class").equals("evnt-details-cell online-cell") ||
            cardHead.findElements(loc_head_sections).get(0).getAttribute("class").equals("evnt-details-cell location-cell"))
        {
            cardLocation = cardHead.findElements(loc_head_sections).get(0);
            if (cardLocation.findElement(loc_card_elem_text).getText().isEmpty())
            {
                Log.error("Card-Location is empty");
                result = false;
            }
        }
        else {
            Log.error("Card-location is not the first element of the heading");
            result = false;
        }
        if (cardHead.findElements(loc_head_sections).get(1).getAttribute("class").equals("evnt-details-cell language-cell")){
            cardLang = card.findElements(loc_card_sections).get(1);
            if (cardLang.findElement(loc_card_elem_text).getText().isEmpty())
            {
                Log.error("Card-Language is empty");
                result = false;
            }
        }
        else {
            Log.error("Card-lang is not the second element of the heading");
            result = false;
        }
        Log.info("Heading structure of -" + num + "- card is checked with result: " + result);


        Log.info("Check body structure of -" + num + "- card");
        if (cardBody.findElements(loc_body_sections).get(0).getAttribute("class").equals("evnt-event-name"))
        {
            cardEventName = cardHead.findElements(loc_head_sections).get(0);
            if (cardEventName.findElement(loc_card_elem_text).getText().isEmpty())
            {
                Log.error("Card EventName is empty");
                result = false;
            }
        }
        else {
            Log.error("Event name is not the first element of the body");
            result = false;
        }
        if (cardBody.findElements(loc_head_sections).get(1).getAttribute("class").equals("evnt-event-dates")){
            cardDates = card.findElements(loc_card_sections).get(1);
            if (cardDates.findElements(loc_card_dates_elem_text).get(0).getAttribute("class").equals("date"))
            {
                if (cardDates.findElements(loc_card_dates_elem_text).get(0).getText().isEmpty())
                {
                    Log.error("Card-Date is empty");
                    result = false;
                }
            }
            else{
                Log.error("Card Date is not the second element of the body");
                result = false;
            }

            if (cardDates.findElements(loc_card_dates_elem_text).get(1).getText().isEmpty()) // Registration info
            {
                Log.error("Registration info is empty");
                result = false;
            }
        }
        else {
            Log.error("Card-dates section is absent in the body");
            result = false;
        }
        Log.info("Body structure of -" + num + "- card is checked with result: " + result);

        Log.info("Check speakers for -" + num + "- card");
        try{
            card.findElement(loc_speaker_data);
        }
        catch (Exception e){
            Log.error("Speakers are not found");
            result = false;
        }
        Log.info("Speakers of -" + num + "- card are checked with result: " + result);
        Log.info("Checking of -" + num + "- card is completed with result: " + result);

        return result;
    }


    @Step ("Check All Upcoming Event Cards")
    public boolean checkAllUpcomingEventCards (Session session) {
        boolean result = true;

        int eventsAmount = session.getWebDriver().findElements(loc_events_cards).size();

        if (eventsAmount == 0) {
            Log.error("Upcoming Events are not found");
            return false;
        }

        Log.info("Start the Event cards checking");
        for (int i=0; i<eventsAmount; i++) {
            if(checkEventCardСorrectnessByNum (session, i) == false) {
                result = false;
            }
        }
        Log.info("All Upcoming Event Cards checking is completed with result: " + result);
        return result;
    }


    @Step ("Wait the loader")
    public void waitLoader (Session session) {
        try{
            session.getWaiter().until(ExpectedConditions.invisibilityOfElementLocated(loc_loader));
            Log.info("Events are loaded");
        } catch (Exception e) {
            Log.error("Events are not loaded", e);
            throw e;
        }
    }

}

