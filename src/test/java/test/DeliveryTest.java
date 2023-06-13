package test;

import Data.DataGenerator;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static Data.DataGenerator.generateDate;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryTest {
    SelenideElement city = $("[data-test-id = 'city'] input");
    SelenideElement data = $("[data-test-id = 'date'] input");
    SelenideElement name = $("[data-test-id = 'name'] input");
    SelenideElement phone = $("[data-test-id = 'phone'] input");
    SelenideElement agreement = $("[data-test-id= 'agreement']");
    SelenideElement scheduleButton = $(".button__text");
    SelenideElement rescheduleButton = $("[data-test-id = 'replan-notification'] .notification__content button");
    SelenideElement successRegistration = $("[data-test-id = 'success-notification'] .notification__content");
    SelenideElement rescheduleRegistration = $("[data-test-id = 'replan-notification'] .notification__content");
    String successMessage = "Встреча успешно запланирована на ";
    String warningMessage = "У вас уже запланирована встреча на другую дату. Перепланировать?";
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        city.setValue(validUser.getCity());
        data.sendKeys(Keys.chord(Keys.CONTROL, "a"), BACK_SPACE);
        data.setValue(firstMeetingDate);
        name.setValue(validUser.getName());
        phone.setValue(validUser.getPhone());
        agreement.click();
        scheduleButton.click();
        successRegistration.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.text(successMessage + firstMeetingDate));
        data.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        data.setValue(secondMeetingDate);
        scheduleButton.click();
        rescheduleRegistration.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.text(warningMessage));
        rescheduleButton.click();
        successRegistration.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.text(successMessage + secondMeetingDate));
    }

}
