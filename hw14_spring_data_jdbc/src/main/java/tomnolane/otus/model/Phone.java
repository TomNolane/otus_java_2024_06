package tomnolane.otus.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone {
    @Id
    @Column("phone_id")
    private final Long id;
    private final String number;
    @Nonnull
    private final Long client_id;

    public Phone(Long id, String number, Long client_id) {
        this.id = id;
        this.number = number;
        this.client_id = client_id;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Long getClientId() {
        return this.client_id;
    }

    @Override
    public String toString() {
        return number;
    }
}
