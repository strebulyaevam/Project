package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalksPage {
    private static Logger Log = LogManager.getLogger(TalksPage.class);

    By loc_filter_category = By.cssSelector("#filter_category");
    By loc_filter_location = By.cssSelector("#filter_location");
    By loc_filter_language  = By.cssSelector("#filter_language");
    By loc_loader = By.cssSelector(".evnt-global-loader");
    By loc_more_filters = By.cssSelector(".evnt-toogle-filters-text.show-more span");
    By loc_talks_result_links = By.cssSelector("div.evnt-cards-container.with-sorting div.evnt-talks-column.cell-6 a");
    By loc_talks_result_titles = By.cssSelector("div.evnt-cards-container.with-sorting div.evnt-talks-column.cell-6 h1 span");

    By loc_filter_category_item_by_name(String item_name){ return By.cssSelector("div[aria-labelledby='filter_category'] label[data-value='" + item_name +"']");}
    By loc_filter_location_item_by_name(String item_name){ return By.cssSelector("div[aria-labelledby='filter_location'] label[data-value='" + item_name +"']");}
    By loc_filter_language_item_by_name(String item_name){ return By.cssSelector("div[aria-labelledby='filter_language'] label[data-value='" + item_name +"']");}
    By loc_search = By.cssSelector(".evnt-search-filter .evnt-text-fields.form-control.evnt-search");


    @Step("wait until TalksPage is Loaded")
    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_filter_category, "TalksPage");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step ("select Category Filter - {categoryName}")
    public void selectCategoryFilter(Session session, String categoryName) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_filter_category, "filter_category");
        TestHelper.clickOnElem(session.getWaiter(), loc_filter_category_item_by_name(categoryName), "Category menu item");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step ("select Location Filter - {locationName}")
    public void selectLocationFilter(Session session, String locationName) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_filter_location, "filter_location");
        TestHelper.clickOnElem(session.getWaiter(), loc_filter_location_item_by_name(locationName), "location menu item");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step ("select Location Filter - {languageName}")
    public void selectLanguageFilter(Session session, String languageName) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_filter_language, "filter_location");
        TestHelper.clickOnElem(session.getWaiter(), loc_filter_language_item_by_name(languageName), "language menu item");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step("Click MoreFilters link")
    public void clickMoreFilters (Session session) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_more_filters, "MoreFilters link");
    }

    @Step ("get Talks Count")
    public int getTalksCount (Session session) {
        Log.info("Try to get All Talks count from the result list");
        return session.getWebDriver().findElements(loc_talks_result_links).size();
    }

    public List<String> findAllTalkLinks(Session session) throws Exception {
        List<WebElement> elements = Collections.EMPTY_LIST;
        elements = session.getWebDriver().findElements(loc_talks_result_links);
        Log.info("talks elements.size() = " + elements.size());

        if (elements == null)
            return Collections.EMPTY_LIST;

        List<String> links = new ArrayList<>();
        for (WebElement element : elements) {
            String link = element.getAttribute("href");
            if (link != null && link.length() > 0) {
                links.add(link);
            }
        }
        return links;
    }

    @Step ("input Search Text")
    public void inputSearchText(Session session, String text) throws Exception {
        TestHelper.sendKeysForElem(session.getWebDriver(), session.getWaiter(), loc_search, text, "Search by Talk Name");
    }

    public boolean checkTalkTitlesContainString(Session session, String text) throws Exception {
        boolean result = true;
        List<WebElement> elements = Collections.EMPTY_LIST;
        elements = session.getWebDriver().findElements(loc_talks_result_titles);
        Log.info("talks elements.size() = " + elements.size());

        if (elements == null)
            return false;

        for (WebElement element : elements) {
            if (!element.getText().contains(text)) {
                Log.error("Search text '" + text + "' is absent in talk Title: " + element.getText());
                result = false;
            }
        }
        return result;
    }

}
