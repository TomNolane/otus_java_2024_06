package tomnolane.otus.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone")
public class Phone implements Cloneable{
    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    public Phone(String number) {
        this.id = null;
        this.number = number;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Phone clone() {
        return new Phone(this.id, this.number, this.client);
    }

    @Override
    public String toString() {
        return "Phone{" + "id=" + id + ", number='" + number + '\'' + '}';
    }
}
