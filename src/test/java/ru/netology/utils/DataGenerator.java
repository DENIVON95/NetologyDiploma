package ru.netology.utils;

import lombok.Value;

public class DataGenerator {
    @Value
    public static class CardData {
        String cardNumber;
        String month;
        String year;
        String ownerName;
        String cvc;
    }

    public static CardData getApprovedCard() {
        return new CardData("4444 4444 4444 4441", "11", "24", "Ivan Ivanov", "324");
    }

    public static CardData getDeclinedCard() {
        return new CardData("4444 4444 4444 4442", "11", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidCardDataIfEmptyAllFields() {
        return new CardData("", "", "", "", "");
    }

    public static CardData getInvalidCardNumberIfEmpty() {
        return new CardData("", "11", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidCardNumberIfLess16Sym() {
        return new CardData("4444 4444 4444 44", "11", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidCardNumberIfOutOfDatabase() {
        return new CardData("5578334444444441", "11", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidMonthIfEmpty() {
        return new CardData("4444 4444 4444 4441", "", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidNumberOfMonthIfMore12() {
        return new CardData("4444 4444 4444 4441", "35", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidNumberOfMonthIfOneDigit() {
        return new CardData("4444 4444 4444 4441", "3", "24", "Ivan Ivanov", "324");
    }


    public static CardData getInvalidNumberOfMonthIfZero() {
        return new CardData("4444 4444 4444 4441", "00", "24", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidYearIfZero() {
        return new CardData("4444 4444 4444 4441", "11", "00", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidYearIfInTheFarFuture() {
        return new CardData("4444 4444 4444 4441", "11", "70", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidNumberOfYearIfOneDigit() {
        return new CardData("4444 4444 4444 4441", "11", "7", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidYearIfEmpty() {
        return new CardData("4444 4444 4444 4441", "11", "", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidYearIfBeforeCurrentYear() {
        return new CardData("4444 4444 4444 4441", "11", "13", "Ivan Ivanov", "324");
    }

    public static CardData getInvalidCardOwnerNameIfEmpty() {
        return new CardData("4444 4444 4444 4441", "11", "24", "", "324");
    }

    public static CardData getInvalidCardOwnerNameIfNumericAndSpecialCharacters() {
        return new CardData("4444 4444 4444 4441", "11", "24", "67893!№", "324");
    }

    public static CardData getInvalidCardOwnerNameIfRussianLetters() {
        return new CardData("4444 4444 4444 4441", "11", "24", "Иван Иванов", "324");
    }

    public static CardData getInvalidCvcIfEmpty() {
        return new CardData("4444 4444 4444 4441", "11", "24", "Ivan Ivanov", "");
    }

    public static CardData getInvalidCvcIfOneDigit() {
        return new CardData("4444 4444 4444 4441", "11", "24", "Ivan Ivanov", "5");
    }

    public static CardData getInvalidCvcIfTwoDigits() {
        return new CardData("4444 4444 4444 4441", "11", "24", "Ivan Ivanov", "25");
    }

    public static CardData getInvalidCvvIfThreeZero() {
        return new CardData("4444 4444 4444 4441", "11", "24", "Ivan Ivanov", "000");
    }

}
