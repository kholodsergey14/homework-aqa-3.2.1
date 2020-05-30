package ru.netology.banklogin.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;

public class DashboardPage {
    @FindBy(css = "[data-test-id=dashboard]")
    private SelenideElement heading;

    public void verifyDashboardPageVisiblity() {
        heading.shouldBe(visible);
    }
}
