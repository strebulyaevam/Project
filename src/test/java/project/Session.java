package project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class Session {

    @Autowired
    WebDriver webDriver;

    WebDriverWait waiter;

    @PostConstruct
    private void init() {
        waiter = new WebDriverWait(webDriver, 4);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWaiter() {
        return waiter;
    }

    public void quit() {
        if (webDriver != null){
            webDriver.quit();
        }
    }
}
