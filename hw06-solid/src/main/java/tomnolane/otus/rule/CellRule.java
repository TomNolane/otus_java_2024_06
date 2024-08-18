package tomnolane.otus.rule;

import tomnolane.otus.Banknote;
import tomnolane.otus.cell.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CellRule {

    public static int sum(int denomination, int number) {
        return denomination * number;
    }

    public static Map<Banknote, Integer> calculateWithdrawMoney(int needMoney, Set<Cell> cellSet) throws IllegalArgumentException {
        final var result = new HashMap<Banknote, Integer>();
        var takenSumFromCells = 0;
        var requiredMoney = needMoney;

        for (var cell : cellSet) {
            var denomination = cell.getBanknoteType().getDenomination();
            var numberBanknotes = cell.getNumberOfBanknotes();

            if (requiredMoney >= denomination && numberBanknotes != 0) {
                var needBanknotes = requiredMoney / denomination;
                var takenBanknotes = cell.takeBanknotes(needBanknotes);
                takenSumFromCells += CellRule.sum(denomination, takenBanknotes);
                requiredMoney = needMoney - takenSumFromCells;
                result.put(cell.getBanknoteType(), takenBanknotes);
            }
        }

        if (needMoney != takenSumFromCells) {
            throw new IllegalArgumentException("Amount of " + needMoney + " rubles you cant take.");
        }

        return result;
    }

    public static int calculateBalance(Set<Cell> cellSet) {
        int rubles = 0;

        for (var cell : cellSet) {
            rubles += CellRule.sum( cell.getBanknoteType().getDenomination(), cell.getNumberOfBanknotes());
        }
        
        return rubles;
    }
}
