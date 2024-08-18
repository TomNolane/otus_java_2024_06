package tomnolane.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.atm.TinkoffATM;

import java.util.HashMap;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var initBanknotes = new HashMap<Banknote, Integer>();
        initBanknotes.put(Banknote.HUNDRED, 20);
        initBanknotes.put(Banknote.FIVE_HUNDRED, 3);
        initBanknotes.put(Banknote.FIVE_THOUSAND, 1);
        initBanknotes.put(Banknote.THOUSAND, 2);
        initBanknotes.put(Banknote.TWO_THOUSAND, 3);

        final var atm = new TinkoffATM(initBanknotes);
        logger.info("ATM balance: {}", atm.getBalance()); //expected ATM balance: 16500

        var addNewBanknotes = new HashMap<Banknote, Integer>();
        addNewBanknotes.put(Banknote.HUNDRED, 3);
        addNewBanknotes.put(Banknote.FIVE_HUNDRED, 1);
        atm.putMoney(addNewBanknotes);
        logger.info("New ATM balance after add: {}", atm.getBalance()); //expected ATM balance: 17300

        try {
            atm.takeMoney(22800);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("ATM balance after take: {}", atm.getBalance());
    }
}