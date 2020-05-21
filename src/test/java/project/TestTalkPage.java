package project;


import project.pageobjects.*;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.List;

@Test(description = "Test Talk page")
@ContextConfiguration(classes = TestConfig.class)
@Epic(value = "Project")
@Feature(value = "Test Talk page filtration")
@Owner(value = "Стребуляева М.")
public class TestTalkPage extends AbstractTestNGSpringContextTests {

    private static Logger Log = LogManager.getLogger(TestTalkPage.class);

    @Autowired
    TopMenu topMenu;

    @Autowired
    Session session;

    @Autowired
    MainPage mainPage;

    @Story(value = "View of filtration result")
    @Test(description = "Check result consistency")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkResultConsistency() throws Exception {
        mainPage.openMainPage(session);
        TalksPage talksPage = topMenu.clickOnTalksLibItem(session);
        talksPage.waitUntilLoad(session);
        talksPage.clickMoreFilters(session);
        talksPage.selectCategoryFilter(session, "Design");
        Log.info("Set Up Design Category");
        talksPage.selectLocationFilter(session, "Belarus");
        Log.info("Set Up Belarus Location");
        talksPage.selectLanguageFilter(session, "ENGLISH");
        Log.info("Set Up ENGLISH Language");

        Log.info("Try to check Talks amount");
        Assert.assertTrue(talksPage.getTalksCount(session)>0);
        Log.info("Talks amount - " + talksPage.getTalksCount(session));

        List<String> links =talksPage.findAllTalkLinks(session);
        Log.info("Found links: " + links.size());

        TalkCard talkCard = new TalkCard();

        int i = 0;
        for (String link : links) {
            talkCard.openTalkCardByLink(session, link);
            talkCard.waitUntilLoad(session);
            Log.info("Try to find Design categories for -" + i + "- Talk");
            List<String> categories = talkCard.getCategories(session);
            Assert.assertTrue(categories.contains("Design"));
            Log.info("OK");

            Log.info("Try to find Belarus location for -" + i + "- Talk");
            Assert.assertTrue(talkCard.getLocation(session).contains("Belarus"));
            Log.info("OK");

            Log.info("Try to find English Language for -" + i + "- Talk");
            Assert.assertEquals(talkCard.getLanguage(session),"ENGLISH");
            Log.info("OK");

            Log.info("Processed " + (++i) + " of " + links.size());
        }
    }

    @Story(value = "View of search result")
    @Test(description = "Check search result consistency")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkSearchResult() throws Exception {
        mainPage.openMainPage(session);
        TalksPage talksPage = topMenu.clickOnTalksLibItem(session);
        talksPage.waitUntilLoad(session);
        talksPage.inputSearchText(session, "Azure");
        talksPage.waitUntilLoad(session);
        Log.info("Try to check founded Talks amount");
        Assert.assertTrue(talksPage.getTalksCount(session)>0);
        Log.info("Talks amount - " + talksPage.getTalksCount(session));

        Log.info("Checking search the result consistency");
        Assert.assertTrue(talksPage.checkTalkTitlesContainString(session, "Azure"));
        Log.info("search the result consistency - OK");
    }

    @AfterClass
    public void tearDown() {
        session.quit();
    }

}
