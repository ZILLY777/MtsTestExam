package com.example.MtsTestExam.repository.repositoryImpl;

import com.example.MtsTestExam.repository.repositoryInterface.ClientRepository;
import com.example.MtsTestExam.entity.tables.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private static final String SAVE_CLIENT_ACCOUNT_QUERY = "INSERT INTO client(id, surname, name, patronymic, document, document_data, birthday) values(?,?,?,?,?,?,?)";
    private static final String FIND_CLIENT_BY_DOCUMENT_TYPE_AND_DATA = "SELECT * FROM client WHERE document LIKE ? AND document_data LIKE ?";
    private static final String FIND_CLIENT_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String FIND_ALL_CLIENTS_QUERY = "SELECT * FROM client";
    private static final String DELETE_CLIENT_BY_CLIENT_ID = "DELETE FROM client WHERE id = ? ";
    private final JdbcTemplate jdbcTemplate;

    RowMapper<Client> clientRowMapper = (rs, rowNum) -> new Client(
            UUID.fromString(rs.getString("id")),
            rs.getString("surname"),
            rs.getString("name"),
            rs.getString("patronymic"),
            rs.getString("document"),
            rs.getString("document_data"),
            rs.getDate("birthday").toLocalDate()
    );

    @Override
    public Client save(Client client) {
        UUID uuid = UUID.randomUUID();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_CLIENT_ACCOUNT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, client.getSurname());
            ps.setString(3, client.getName());
            ps.setString(4, client.getPatronymic());
            ps.setString(5, client.getDocument());
            ps.setString(6, client.getDocumentData());
            ps.setDate(7, java.sql.Date.valueOf(client.getBirthday()));
            return ps;
        });
        client.setId(uuid);
        return client;
    }

    @Override
    public List<Client> findAllClient() {
        return jdbcTemplate.query(FIND_ALL_CLIENTS_QUERY, clientRowMapper);
    }

    @Override
    public void deleteClient(UUID id) {
        jdbcTemplate.update(DELETE_CLIENT_BY_CLIENT_ID, id);
    }

    @Override
    public Optional<Client> findClientByDocumentTypeAndData(String type, String data) {
        return jdbcTemplate.query(FIND_CLIENT_BY_DOCUMENT_TYPE_AND_DATA,clientRowMapper,type, data).stream().findFirst();
    }

    @Override
    public Optional<Client> findClientById(UUID id) {
        return jdbcTemplate.query(FIND_CLIENT_BY_ID, clientRowMapper, id).stream().findFirst();
    }
}
