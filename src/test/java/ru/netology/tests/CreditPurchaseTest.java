package ru.netology.tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import jdk.jfr.Description;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.netology.utils.DataGenerator.*;
import static ru.netology.utils.DbConnectionHelper.*;

public class CreditPurchaseTest extends BaseTest {

    MainPage mainPage;
    PaymentPage paymentPage;

    @BeforeEach
    void setUpPayWithCredit() {
        mainPage = new MainPage();
        paymentPage = new PaymentPage();
        mainPage.payWithCredit();
    }

    @Test
    @Description("Проверка успешной оплаты тура в кредит при заполнении корректными данными карты")
    public void shouldSuccessCreditRequestWithValidCard() {
        val cardData = getApprovedCard();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldSuccessNotification();

        val expectedStatus = "APPROVED";
        val actualStatus = getCardStatusForCreditRequest();
        assertEquals(expectedStatus, actualStatus);

        val bankIdExpected = getBankId();
        val paymentIdActual = getPaymentIdForCreditRequest();
        assertNotNull(bankIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(bankIdExpected, paymentIdActual);
    }

    @Test
    @Description("Покупка тура с помощью отклоняемой карты")
    public void shouldFailurePayIfWithDeclinedCard() {
        val cardData = getDeclinedCard();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldFailureNotification();

        val expectedStatus = "DECLINED";
        val actualStatus = getCardStatusForCreditRequest();
        assertEquals(expectedStatus, actualStatus);

        val bankIdExpected = getBankId();
        val paymentIdActual = getPaymentIdForCreditRequest();
        assertNotNull(bankIdExpected);
        assertNotNull(paymentIdActual);
        assertEquals(bankIdExpected, paymentIdActual);
    }


    @Test
    @Description("Валидация поля 'Номер карты': пустое поле")
    public void shouldFailurePaymentIfEmptyCardNumber() {
        val cardData = getInvalidCardNumberIfEmpty();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @Description("Валидация поля 'Номер карты': Поле содержит количество цифр менее 16")
    public void shouldFailurePaymentIfCardNumberIfLess16Sym() {
        val cardData = getInvalidCardNumberIfLess16Sym();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Номер карты не содержится в базе данных банка")
    public void shouldFailurePaymentIfCardNumberIfOutOfBase() {
        val cardData = getInvalidCardNumberIfOutOfDatabase();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldFailureNotification();
    }

    @Test
    @Description("Валидация поля 'Владелец': пустое поле.")
    public void shouldFailurePaymentIfEmptyCardholderName() {
        val cardData = getInvalidCardOwnerNameIfEmpty();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @Description("Валидация поля 'Владелец': Поле содержит цифры и спец символы")
    public void shouldFailurePaymentIfNameNumeric() {
        val cardData = getInvalidCardOwnerNameIfNumericAndSpecialCharacters();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Валидация поля 'Владелец': русские буквы")
    public void shouldFailurePaymentIfNameHasRussianLetters() {
        val cardData = getInvalidCardOwnerNameIfRussianLetters();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Отправка пустой формы покупки тура")
    public void shouldFailurePaymentIfTourFieldsAreEmpty() {
        val cardData = getInvalidCardDataIfEmptyAllFields();
        paymentPage.fillCardData(cardData);
        final ElementsCollection fieldSub = $$(".input__sub");
        final SelenideElement cardNumberFieldSub = fieldSub.get(1);
        final SelenideElement monthFieldSub = fieldSub.get(2);
        final SelenideElement yearFieldSub = fieldSub.get(3);
        final SelenideElement cardholderFieldSub = fieldSub.get(4);
        final SelenideElement cvvFieldSub = fieldSub.get(5);
        cardNumberFieldSub.shouldHave(text("Поле обязательно для заполнения"));
        monthFieldSub.shouldHave(text("Поле обязательно для заполнения"));
        yearFieldSub.shouldHave(text("Поле обязательно для заполнения"));
        cardholderFieldSub.shouldHave(text("Поле обязательно для заполнения"));
        cvvFieldSub.shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @Description("Валидация поля 'CVC/CVV': пустое поле")
    public void shouldFailurePaymentIfEmptyCvc() {
        val cardData = getInvalidCvcIfEmpty();
        paymentPage.fillCardData(cardData);
        final ElementsCollection fieldSub = $$(".input__sub");
        final SelenideElement cvvFieldSub = fieldSub.get(2);
        cvvFieldSub.shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @Description("Валидация поля 'CVC/CVV': Поле содержит одну цифру")
    public void shouldFailurePaymentIfCvcHasOneDigit() {
        val cardData = getInvalidCvcIfOneDigit();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Валидация поля 'CVC/CVV': Поле содержит две цифры")
    public void shouldFailurePaymentIfCvcHasTwoDigits() {
        val cardData = getInvalidCvcIfTwoDigits();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Валидация поля 'CVC/CVV': Поле содержит три нуля")
    public void shouldFailurePaymentIfCvvHasThreeZeros() {
        val cardData = getInvalidCvvIfThreeZero();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Валидация поля 'Месяц': пустое поле")
    public void shouldFailurePaymentIfEmptyNumberOfMonth() {
        val cardData = getInvalidMonthIfEmpty();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @Description("Валидация поля 'Месяц': Поле содержит двузначное число более 12")
    public void shouldFailurePaymentIfNumberOfMonthIsMore12() {
        val cardData = getInvalidNumberOfMonthIfMore12();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @Description("Валидация поля 'Месяц': Поле содержит одно число")
    public void shouldFailurePaymentIfNumberOfMonthIsOneDigit() {
        val cardData = getInvalidNumberOfMonthIfOneDigit();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Валидация поля 'Месяц': Поле содержит два нуля")
    public void shouldFailurePaymentIfNumberOfMonthHasTwoZeros() {
        val cardData = getInvalidNumberOfMonthIfZero();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @Description("Валидация поля 'Год': Поле содержит два нуля")
    public void shouldFailurePaymentIfYearHasTwoZeros() {
        val cardData = getInvalidYearIfZero();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldExpiredDatePassNotification();
    }


    @Test
    @Description("Валидация поля 'Год': Поле содержит одно число")
    public void shouldFailurePaymentIfYearOneDigit() {
        val cardData = getInvalidNumberOfYearIfOneDigit();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @Description("Оплата по карте с истекшим сроком действия (введен год ранее текущего)")
    public void shouldFailurePaymentIfYearBeforeCurrentYear() {
        val cardData = getInvalidYearIfBeforeCurrentYear();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldExpiredDatePassNotification();
    }


    @Test
    @Description("Валидация поля 'Год': Пустое поле")
    public void shouldFailurePaymentIfEmptyYear() {
        val cardData = getInvalidYearIfEmpty();
        paymentPage.fillCardData(cardData);
        paymentPage.shouldEmptyFieldNotification();
    }

}
