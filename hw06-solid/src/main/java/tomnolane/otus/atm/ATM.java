package tomnolane.otus.atm;

import tomnolane.otus.Banknote;

import java.util.Map;

public interface ATM {
    Map<Banknote, Integer> takeMoney(int amount);

    void putMoney(Map<Banknote, Integer> money);

    int getBalance();
}
