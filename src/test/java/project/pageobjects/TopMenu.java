package project.pageobjects;


import project.Session;
import project.helpers.TestHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopMenu {
    private static Logger Log = LogManager.getLogger(TopMenu.class);

    By loc_1st_item = By.cssSelector("ul.evnt-navigation.navbar-nav>li.nav-item a[href*='calendar']");
    By loc_loader = By.cssSelector(".evnt-global-loader");
    By loc_top_menu = By.cssSelector("ul.evnt-navigation li a");
    By loc_topmenu_item_byname(String item_name){ return By.xpath(" //ul[@class='evnt-navigation navbar-nav']//li/a[contains(text(), '" + item_name + "')]");}
    By loc_loginbtn = By.cssSelector(".evnt-header-button.login a");

    @Step ("wait Until Top menu is Loaded")
    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_1st_item, "Top Menu");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step("Menu item selection: {menuname}")
    public void clickTopMenuItemByName (Session session, String menuname) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_topmenu_item_byname(menuname), menuname + " menu item in TOP menu");
        TestHelper.waitLoader(session.getWaiter(), loc_loader);
    }

    @Step ("click On Event Item")
    public EventPage clickOnEventItem (Session session) throws Exception {
        clickTopMenuItemByName (session, "Events");
        return new EventPage();
    }

    @Step ("click On Talks Library")
    public TalksPage clickOnTalksLibItem (Session session) throws Exception {
        clickTopMenuItemByName (session, "Talks Library");
        return new TalksPage();
    }

    @Step ("get All Top Menu Items")
    public List<String> getAllTopMenuItems(Session session){
        return TestHelper.getAllMenuItems(session.getWebDriver(), loc_top_menu);
    }

    @Step("Checking of selected item - {item_name}")
    public boolean isMenuItemSelected (Session session, String item_name){
        return session.getWebDriver().findElement(loc_topmenu_item_byname(item_name)).getAttribute("class").contains("active");
    }

    @Step ("click On Login Button")
    public void clickOnLoginButton(Session session) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_loginbtn, "Login btn");
    }
}
