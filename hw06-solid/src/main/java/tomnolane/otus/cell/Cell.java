package tomnolane.otus.cell;

import tomnolane.otus.Banknote;

public abstract class Cell {
    private final Banknote banknotes;

    public Cell(Banknote banknotes) {
        this.banknotes = banknotes;
    }

    public abstract int putBanknotes(int number);

    public abstract int takeBanknotes(int number);

    public abstract int getNumberOfBanknotes();

    public Banknote getBanknoteType(){
        return banknotes;
    }
}
