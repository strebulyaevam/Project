package Project.pageobjects;


import Project.Session;
import Project.helpers.TestHelper;
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

    By loc_top_menu = By.cssSelector("ul.evnt-navigation li a");
    By loc_topmenu_item_byname(String item_name){ return By.xpath(" //ul[@class='evnt-navigation navbar-nav']//li/a[contains(text(), '" + item_name + "')]");}
    By loc_loginbtn = By.cssSelector(".evnt-header-button.login a");

    @Step
    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_1st_item, "Top Menu");
    }

    @Step("Проверка кликабельности элемента меню {menuname}")
    public void clickTopMenuItemByName (Session session, String menuname) throws Exception {
        Log.info("Try to click on " + menuname + " menu item in TOP menu");
        TestHelper.clickOnElem(session.getWaiter(), loc_topmenu_item_byname(menuname), menuname + " menu item in TOP menu");
        Log.info("Get menu " + menuname + " successfully");
    }

    @Step
    public List<String> getAllTopMenuItems(Session session){
        return TestHelper.getAllMenuItems(session.getWebDriver(), loc_top_menu);
    }

    @Step("Проверка выбора элемента меню {item_name}")
    public boolean isMenuItemSelected (Session session, String item_name){
        return session.getWebDriver().findElement(loc_topmenu_item_byname(item_name)).getAttribute("class").contains("active");
    }

    @Step
    public void clickOnLoginButton(Session session) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), loc_loginbtn, "Login btn");
    }
}
