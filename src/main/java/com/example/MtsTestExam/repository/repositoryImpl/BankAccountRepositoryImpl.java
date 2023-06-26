package com.example.MtsTestExam.repository.repositoryImpl;

import com.example.MtsTestExam.repository.repositoryInterface.BankAccountRepository;
import com.example.MtsTestExam.entity.tables.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private final String FIND_ALL = "select * from bank_account";
    private static final String SAVE_BANK_ACCOUNT_QUERY = "INSERT INTO bank_account(account_number, account_currency, client_id) VALUES(?, ?, ?)";
    private static final String FIND_BANK_ACCOUNT_BY_ACCOUNT_NUMBER = "SELECT * FROM bank_account WHERE account_number = ?";
    private static final String FIND_BANK_ACCOUNTS_BY_CLIENT_ID_QUERY = "SELECT * FROM bank_account WHERE client_id = ?";
    private static final String DELETE_BANK_ACCOUNT_BY_CLIENT_ID_AND_BANK_ACCOUNT_ID_QUERY = "DELETE FROM bank_account WHERE client_id = ? AND account_number = ?";
    private final JdbcTemplate jdbcTemplate;

    RowMapper<BankAccount> bankAccountRowMapper = (rs, rowNum) -> new BankAccount(
            UUID.fromString(rs.getString(1)),
            rs.getString(2),
            UUID.fromString(rs.getString(3))
    );

    @Override
    public BankAccount save(BankAccount bankAccount) {
        UUID uuid = UUID.randomUUID();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_BANK_ACCOUNT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, bankAccount.getAccountCurrency());
            ps.setString(3, String.valueOf(bankAccount.getClientId()));
            return ps;
        });
        bankAccount.setAccountNumber(uuid);
        return bankAccount;
    }

    @Override
    public List<BankAccount> findBankAccountsByClientId(UUID clientId) {
        return jdbcTemplate.query(FIND_BANK_ACCOUNTS_BY_CLIENT_ID_QUERY, bankAccountRowMapper, clientId);
    }

    @Override
    public void deleteBankAccountByClientIdAndBankAccountId(UUID clientId, UUID bankAccountNumber) {
        jdbcTemplate.update(DELETE_BANK_ACCOUNT_BY_CLIENT_ID_AND_BANK_ACCOUNT_ID_QUERY, clientId, bankAccountNumber);
    }

    @Override
    public Optional<BankAccount> findBankAccountByAccountNumber(UUID bankAccountNumber) {
        return jdbcTemplate.query(FIND_BANK_ACCOUNT_BY_ACCOUNT_NUMBER, bankAccountRowMapper, bankAccountNumber).stream().findFirst();
    }

}
