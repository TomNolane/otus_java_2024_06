package tomnolane.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import tomnolane.otus.Banknote;

import java.util.HashMap;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TinkoffATMTest {
    TinkoffATM atm;
    final int balance = 16500;

    @BeforeEach
    public void setUp() {
        final var initBanknotes = new HashMap<Banknote, Integer>();
        initBanknotes.put(Banknote.HUNDRED, 20);
        initBanknotes.put(Banknote.FIVE_HUNDRED, 3);
        initBanknotes.put(Banknote.FIVE_THOUSAND, 1);
        initBanknotes.put(Banknote.THOUSAND, 2);
        initBanknotes.put(Banknote.TWO_THOUSAND, 3);

        atm = new TinkoffATM(initBanknotes); //total ATM balance: 16500
    }

    @Test
    public void correctTakeMoneyTest() {
        final int expectedTakeMoney = 16300;
        atm.takeMoney(expectedTakeMoney);

        assertThat(balance-expectedTakeMoney).isEqualTo(atm.getBalance());
    }

    @Test
    public void incorrectTakeMoneyTest() {
        final int expectedTakeMoney = 22200;
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> atm.takeMoney(expectedTakeMoney));

        assertEquals("There are not enough funds in your account", exception.getMessage());
    }

    @Test
    public void incorrectOddTakeMoneyTest() {
        final int expectedTakeMoney = 13201;
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> atm.takeMoney(expectedTakeMoney));

        assertEquals(String.format("Amount of %d rubles you cant take.", expectedTakeMoney), exception.getMessage());
    }

    @Test
    public void putMoneyTest() {
        final var putBanknotes = new HashMap<Banknote, Integer>();
        putBanknotes.put(Banknote.HUNDRED, 20);
        putBanknotes.put(Banknote.FIVE_HUNDRED, 3);
        putBanknotes.put(Banknote.FIVE_THOUSAND, 1);
        putBanknotes.put(Banknote.THOUSAND, 2);
        putBanknotes.put(Banknote.TWO_THOUSAND, 3);

        atm.putMoney(putBanknotes);

        assertThat(balance + 16500).isEqualTo(atm.getBalance());
    }

    @Test
    public void getBalanceTest() {
        assertThat(atm.getBalance()).isEqualTo(balance);
    }
}