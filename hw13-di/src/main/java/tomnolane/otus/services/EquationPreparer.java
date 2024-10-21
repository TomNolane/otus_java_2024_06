package tomnolane.otus.services;

import tomnolane.otus.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
