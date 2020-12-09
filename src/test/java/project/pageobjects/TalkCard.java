package project.pageobjects;

import project.Session;
import project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalkCard {
    private static Logger Log = LogManager.getLogger(EventDetails.class);

    By loc_event_header = By.cssSelector(".evnt-talk-title");
    By loc_loader = By.cssSelector(".evnt-global-loader");
    By loc_category = By.cssSelector("div.evnt-talk-details.topics div.evnt-topic.evnt-talk-topic label");
    By loc_location = By.cssSelector(".evnt-talk-details.location.evnt-now-past-talk span");
    By loc_language = By.cssSelector(".evnt-talk-details.language.evnt-now-past-talk span");

    @Step ("open Talk Card")
    public void openTalkCardByLink (Session session, String url) throws Exception {
        TestHelper.getURL(session.getWebDriver(), url);
    }

    @Step("wait until TalkCard page is Loaded")
    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_event_header, "TalkCard page");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step ("Get Categories from TalkCard")
    public List<String> getCategories (Session session) throws Exception {
        List<WebElement> elements = Collections.EMPTY_LIST;
        elements = session.getWebDriver().findElements(loc_category);
        Log.info("Categories elements.size() = " + elements.size());

        if (elements == null)
            return Collections.EMPTY_LIST;

        List<String> categories = new ArrayList<>();
        for (WebElement element : elements) {
            String category = element.getText().trim();
            if (category != null && category.length() > 0) {
                categories.add(category);
            }
        }
        return categories;
    }

    @Step ("Get Location from TalkCard")
    public String getLocation (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_location)).getText().trim();
    }

    @Step ("Get Language from TalkCard")
    public String getLanguage (Session session) throws Exception {
        return session.getWaiter().until(ExpectedConditions.presenceOfElementLocated(loc_language)).getText().trim();
    }
}
