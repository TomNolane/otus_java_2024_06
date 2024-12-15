package tomnolane.otus.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tomnolane.otus.model.Client;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ClientRepository extends CrudRepository<Client, Long> {
    @Override
    @Query(value = """
select
    client.id,
    client.name,
    address.address_id,
    address.street,
    phone.phone_id,
    phone.number
from
    client
left join
    phone on client.id = phone.client_id
left join
    address on client.id = address.client_id
order by 
    client.id
""", resultSetExtractorClass = ClientResultSetExtractor.class)
    List<Client> findAll();

    Optional<Client> findByName(String name);

    @Query("select * from client where upper(name) = upper(:name)")
    Optional<Client> findByNameIgnoreCase(@Param("name") String name);

    @Modifying
    @Query("update client set name = :newName where id = :id")
    void updateName(@Param("id") long id, @Param("newName") String newName);
}
