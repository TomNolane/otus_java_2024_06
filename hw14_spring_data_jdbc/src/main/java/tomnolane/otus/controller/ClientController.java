package tomnolane.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tomnolane.otus.model.Address;
import tomnolane.otus.model.Client;
import tomnolane.otus.model.Phone;
import tomnolane.otus.service.ClientService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getAllClients(Model clientModel) {
        clientModel.addAttribute("listOfClients", clientService.findAll());
        return "clients";
    }

    @GetMapping("/{id}")
    public String getClientById(Model clientModel, @PathVariable("id") Long id) {
        clientModel.addAttribute("client", clientService.findById(id).orElse(null));
        return "selected_client";
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addClient(@RequestParam String clientName, @RequestParam(required = false) String address
            , @RequestParam(required = false) String phones) {
        if(clientName.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Address clientAddress = null;
        Set<Phone> clientPhone = null;

        if(!address.isBlank()) {
            clientAddress = new Address(null, address, true);
        }

        if(!phones.isBlank()) {
            List<String> phoneList = List.of(phones.split(","));
            clientPhone = new HashSet<>();
            for (String number : phoneList) {
                clientPhone.add(new Phone(null, number, null));
            }
        }

        Client client = new Client(null, clientName, clientAddress, clientPhone, true);
        clientService.save(client);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
