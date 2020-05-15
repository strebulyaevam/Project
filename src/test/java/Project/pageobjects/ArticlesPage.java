package Project.pageobjects;

import Project.Session;
import Project.helpers.TestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticlesPage {

    private static Logger Log = LogManager.getLogger(ArticlesPage.class);

    By loc_menuitem_best = By.xpath("//a[@href='https://habr.com/ru/top/' and @class='toggle-menu__item-link toggle-menu__item-link_active' and contains(text(), 'Лучшие')]/parent::li");
    By loc_menu_items = By.cssSelector("div.tabs__level.tabs__level_bottom a.toggle-menu__item-link");

    public void waitUntilLoad (Session session){
        TestHelper.isPageLoad(session.getWaiter(), loc_menuitem_best, "The Best articles");
    }

    public By get_loc_post_title_bynum (int post_num) {return By.cssSelector("ul.content-list.content-list_posts.shortcuts_items>li:nth-child(" + post_num +") a.post__title_link");}
    public By get_loc_readmore_btn_bypostnum (int post_num) {return By.cssSelector("ul.content-list.content-list_posts.shortcuts_items>li:nth-child(" + post_num +") a.btn.btn_x-large.btn_outline_blue.post__habracut-btn");}

    public List<String> getAllArticleMenuItems(Session session){
        return TestHelper.getAllMenuItems(session.getWebDriver(), loc_menu_items);
    }

    public SelectedArticlePage clickOnReadMoreButton(Session session, int post_num) throws Exception {
        TestHelper.clickOnElem(session.getWaiter(), get_loc_readmore_btn_bypostnum(post_num), "Read more btn for " + post_num + "article");
        return new SelectedArticlePage();
    }
}
