package ru.netology.test;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerate;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerate.Registration.getRegistrationUser;
import static ru.netology.data.DataGenerate.Registration.getUser;
import static ru.netology.data.DataGenerate.getRandomLogin;
import static ru.netology.data.DataGenerate.getRandomPassword;

public class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldRegisteredUserLogIn() { //следует авторизоваться зарегистрированному пользователю
        var registrationUser = getRegistrationUser("active");
        $("[data-test-id='login'] input").setValue(registrationUser.getLogin());
        $("[data-test-id='password'] input").setValue(registrationUser.getPassword());
        $(".button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldNoRegisteredUserLogIn() { // cледует авторизоваться незарегистрированному пользователю
        var user = getUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $(".button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);

    }

    @Test
    public void shouldEnterInvalidLogin() {
        var registrationUser = getRegistrationUser("active");
        var invalidLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(invalidLogin);
        $("[data-test-id='password'] input").setValue(registrationUser.getPassword());
        $(".button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);

    }

    @Test
    public void shouldEnterInvalidPassword() {
        var registrationUser = getRegistrationUser("active");
        var invalidPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registrationUser.getLogin());
        $("[data-test-id='password'] input").setValue(invalidPassword);
        $(".button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);

    }

    @Test
    public void shouldBlockedUserLogIn() { // следует авторизоваться заблокированному пользователю
        var user = getRegistrationUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $(".button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Ошибка! Пользователь заблокирован")).shouldBe(visible);

    }
}
