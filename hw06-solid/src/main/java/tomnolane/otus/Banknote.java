package tomnolane.otus;

public enum Banknote {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    HUNDRED(100);

    private final int denomination;

    Banknote(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return this.denomination;
    }
}
