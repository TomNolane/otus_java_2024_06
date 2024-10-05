package tomnolane.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Cloneable {
    @Id
    @SequenceGenerator(name = "street_gen", sequenceName = "street_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "street_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @OneToOne
    private Client client;

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Address clone() {
        return new Address(this.id, this.street);
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", street='" + street + '\'' + '}';
    }
}
