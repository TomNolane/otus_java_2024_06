package tomnolane.otus.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.Banknote;
import tomnolane.otus.cell.Cell;
import tomnolane.otus.cell.TinkoffCell;
import tomnolane.otus.rule.CellRule;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TinkoffATM implements ATM {
    private static final Logger logger = LoggerFactory.getLogger(TinkoffATM.class);
    private Set<Cell> cellSet = new TreeSet<>((o1, o2) -> {
        var denomination1 = o1.getBanknoteType().getDenomination();
        var denomination2 = o2.getBanknoteType().getDenomination();
        return Integer.compare(denomination2, denomination1);
    });

    public TinkoffATM(Map<Banknote, Integer> initBanknotes) {
        for (var banknoteSet : initBanknotes.entrySet()) {
            final var cell = new TinkoffCell(banknoteSet.getKey());
            cell.putBanknotes(banknoteSet.getValue());
            cellSet.add(cell);
        }
    }

    @Override
    public Map<Banknote, Integer> takeMoney(int needMoney) throws IllegalArgumentException {
        int totalMoneyInATM = getBalance();

        if (totalMoneyInATM == 0 || needMoney > totalMoneyInATM) {
            throw new IllegalArgumentException("There are not enough funds in your account");
        }

        return CellRule.calculateWithdrawMoney(needMoney, cellSet);
    }

    @Override
    public void putMoney(Map<Banknote, Integer> banknotes) {
        int rubles = 0;
        for (var cell : cellSet) {
            if (banknotes.containsKey(cell.getBanknoteType())) {
                rubles += banknotes.get(cell.getBanknoteType());
                cell.putBanknotes(banknotes.get(cell.getBanknoteType()));
            }
        }

        logger.info("You put {} rubles", rubles);
    }

    @Override
    public int getBalance() {
        return CellRule.calculateBalance(cellSet);
    }
}
