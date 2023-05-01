package org.fastrackit.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import org.fastrackit.config.BaseTestConfig;
import org.fastrackit.dataprovider.User;
import org.fastrackit.dataprovider.UserDataProvider;
import org.fasttrackit.DemoShopPage;
import org.fasttrackit.LoginModal;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Feature("User Login")
public class LoginTest extends BaseTestConfig {
    DemoShopPage page;

    @BeforeTest
    public void setup() {
        page = new DemoShopPage();
        page.openDemoShopApp();
    }

    @AfterMethod
    public void reset() {
        Selenide.refresh();
        page.getFooter().resetPage();
    }

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "validUserDataProvider")
    public void userCanLoginOnDemoShopPage(User user) {
        page.getHeader().clickOnTheLoginButton();
        LoginModal loginModal = new LoginModal();
        loginModal.fillInUsername(user.getUsername());
        loginModal.fillInPassword(user.getPassword());
        loginModal.clickSubmitButton();
        assertEquals(page.getHeader().getGreetingsMsg(), user.getExpectedGreetingsMsg(), "Greetings message is : " + user.getExpectedGreetingsMsg());
    }

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "invalidUserDataProvider")
    public void userCanNotLoginOnDemoShopPageWithInvalidUser(User user) {
        page.getHeader().clickOnTheLoginButton();
        LoginModal loginModal = new LoginModal();
        loginModal.fillInUsername(user.getUsername());
        loginModal.fillInPassword(user.getPassword());
        loginModal.clickSubmitButton();

        assertTrue(loginModal.isDisplayed());
        assertTrue(loginModal.isErrorMsgDisplayed(), "Login error message is displayed.");
        assertEquals(loginModal.getErrorMsg(), user.getErrorMessage());
        assertEquals(page.getHeader().getGreetingsMsg(), user.getDefaultGreetingsMsg(), "Greetings message is : Hello guest!");
    }
}
