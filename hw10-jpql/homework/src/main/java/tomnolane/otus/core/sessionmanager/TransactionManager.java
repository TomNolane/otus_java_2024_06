package java.tomnolane.otus.core.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

    <T> T doInReadOnlyTransaction(TransactionAction<T> action);
}
