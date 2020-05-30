package ru.netology.banklogin.test;

import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.page.LoginPage;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Configuration.browser;

public class BankLoginTest {

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccesfullLogin() throws SQLException {
        valifLogin();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data 3 times")
    void shouldSuccesfullLoginThreeTimes() throws SQLException {
        valifLogin();
        valifLogin();
        valifLogin();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with random exist in base and active user")
    void shouldSuccesfullLoginWithRandomActiveUser() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getRandomAuthInfo("active");
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.verifyDashboardPageVisiblity();
    }

    @Test
    @DisplayName("Should get error notification if login with random exist in base and active user and random verification code")
    void shoulGetEroorNotificatioIFLoginWithRandomActiveUserAndRandomVerificationCode() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getRandomAuthInfo("active");
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        val verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisiblity();
    }

    @Test
    @DisplayName("Should get error notification if user is exist in base but login with wrong password")
    void shouldGetErrorNotificationIfLoginWithWrongPassword() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfoWithTestDataLoginAndWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisiblity();
    }

    @Test
    @DisplayName("Should get error notification if random user is exist in base but blocked")
    void shouldGetErrorNotificatinIfLoginWithRandomBlockedUser() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getRandomAuthInfo("blocked");
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyErrorNotificationVisiblity();
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificatinIfLoginWithRandomUserWithoutAddingToBase() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getRandomAuthInfoWithoutAddingToBase();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisiblity();
    }

    void valifLogin() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfoWithTestData();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.verifyDashboardPageVisiblity();
    }
}

