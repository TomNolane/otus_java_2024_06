package tomnolane.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tomnolane.otus.base.AbstractHibernateTest;
import tomnolane.otus.crm.model.Address;
import tomnolane.otus.crm.model.Client;
import tomnolane.otus.crm.model.Phone;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class DataTemplateHibernateTest extends AbstractHibernateTest {

    @Test
    @DisplayName(" корректно сохраняет, изменяет и загружает клиента по заданному id")
    @SuppressWarnings({"java:S6204"})
    void shouldSaveAndFindCorrectClientById() {
        // given
        // Это надо раскомментировать, у выполненного ДЗ, все тесты должны проходить
        // Кроме удаления комментирования, тестовый класс менять нельзя
        var client = new Client(
                null,
                "Vasya",
                new Address(null, "AnyStreet"),
                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));

        // when
        var savedClient = transactionManager.doInTransaction(session -> {
            clientTemplate.insert(session, client);
            return client;
        });

        // then
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(client.getName());

        // when
        var loadedSavedClient = transactionManager.doInReadOnlyTransaction(
                session -> clientTemplate.findById(session, savedClient.getId()).map(Client::clone));

        // then
        assertThat(loadedSavedClient)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(savedClient);

        // when
        savedClient.setName("updatedName");
        transactionManager.doInTransaction(session -> {
            clientTemplate.update(session, savedClient);
            return null;
        });

        // then
        Optional<Client> loadedClient = transactionManager.doInReadOnlyTransaction(
                session -> clientTemplate.findById(session, savedClient.getId()).map(Client::clone));
        assertThat(loadedClient).isPresent();
        assertThat(loadedClient).get().usingRecursiveComparison().isEqualTo(savedClient);

        // when
        var clientList = transactionManager.doInReadOnlyTransaction(session ->
                clientTemplate.findAll(session).stream().map(Client::clone).collect(Collectors.toList()));

        // then
        assertThat(clientList).hasSize(1);
        assertThat(clientList.get(0)).usingRecursiveComparison().isEqualTo(savedClient);

        // when
        clientList = transactionManager.doInReadOnlyTransaction(
                session -> clientTemplate.findByEntityField(session, "name", "updatedName").stream()
                        .map(Client::clone)
                        .collect(Collectors.toList()));

        // then
        assertThat(clientList).hasSize(1);
        assertThat(clientList.get(0)).usingRecursiveComparison().isEqualTo(savedClient);
    }
}
