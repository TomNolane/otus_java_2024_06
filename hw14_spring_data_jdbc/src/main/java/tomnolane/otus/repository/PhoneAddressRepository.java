package tomnolane.otus.repository;

import org.springframework.data.repository.CrudRepository;
import tomnolane.otus.model.Phone;

public interface PhoneAddressRepository extends CrudRepository<Phone, Long> {
}
