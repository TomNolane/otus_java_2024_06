package tomnolane.otus.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tomnolane.otus.model.Address;
import tomnolane.otus.model.Client;
import tomnolane.otus.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientResultSetExtractor implements ResultSetExtractor<List<Client>> {
    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var clientList = new ArrayList<Client>();
        Long prevClientId = null;
        while (rs.next()) {
            var clientId = rs.getLong("id");
            Client client = null;
            if (prevClientId == null || !prevClientId.equals(clientId)) {
                Address address = null;
                if(rs.getString("street") != null) {
                    address = new Address(rs.getLong("address_id"), rs.getString("street"), false);
                }
                client = new Client(
                    clientId, rs.getString("name"), address, null, false);

                clientList.add(client);
                prevClientId = clientId;
            } else {
                client = clientList.get(clientList.size() - 1);
            }

            Long phoneId = rs.getLong("phone_id");
            if(phoneId != 0) {
                Phone phone = new Phone(phoneId, rs.getString("number"), clientId);
                client.addPhones(phone);
            }
        }
        return clientList;
    }
}
