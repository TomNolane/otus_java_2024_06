package tomnolane.otus.cell;

import tomnolane.otus.Banknote;

public class TinkoffCell extends Cell {
    private int numberOfBanknotes = 0;

    public TinkoffCell(Banknote banknotes) {
        super(banknotes);
    }

    @Override
    public int putBanknotes(int number) {
        this.numberOfBanknotes += number;
        return number;
    }

    @Override
    public int takeBanknotes(int needBanknotes) {
        int result;

        if (numberOfBanknotes >= needBanknotes) {
            numberOfBanknotes -= needBanknotes;
            result = needBanknotes;
        } else {
            result = numberOfBanknotes;
            numberOfBanknotes = 0;
        }

        return result;
    }

    @Override
    public int getNumberOfBanknotes() {
        return numberOfBanknotes;
    }
}
