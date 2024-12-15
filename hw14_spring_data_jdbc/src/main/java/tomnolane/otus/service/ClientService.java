package tomnolane.otus.service;

import org.springframework.stereotype.Service;
import tomnolane.otus.model.Client;
import tomnolane.otus.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client customer) {
        return clientRepository.save(customer);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
