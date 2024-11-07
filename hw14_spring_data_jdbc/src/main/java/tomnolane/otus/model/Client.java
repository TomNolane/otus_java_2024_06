package tomnolane.otus.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Table("client")
public class Client implements Persistable<Long> {
    @Id
    @Nonnull
    private final Long id;

    @Nonnull
    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @Transient
    private final boolean isNew;

    public Client(Long id, @Nonnull String name, Address address, Set<Phone> phones, boolean isNew) {
        this.id = id;
        this.name = name;
        this.address = address;

        if (phones == null) {
            phones = new HashSet<>();
        }

        this.phones = phones;
        this.isNew = isNew;
    }

    @PersistenceCreator
    private Client(Long id, @Nonnull String name, Address address, Set<Phone> phones) {
        this(id, name, address, phones, false);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isEmptyPhones() {
        return this.phones.isEmpty();
    }

    public String getPhonesToString() {
        return StringUtils.join(this.phones, ", ");
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    public void addPhones(Phone phone) {
        this.phones.add(phone);
    }
}
