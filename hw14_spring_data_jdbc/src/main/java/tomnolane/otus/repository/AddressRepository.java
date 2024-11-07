package tomnolane.otus.repository;

import org.springframework.data.repository.CrudRepository;
import tomnolane.otus.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
