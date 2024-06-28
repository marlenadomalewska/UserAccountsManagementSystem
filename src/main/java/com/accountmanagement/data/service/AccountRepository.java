package com.accountmanagement.data.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.accountmanagement.data.entity.Account;
import com.accountmanagement.data.entity.Gender;

@Repository
public class AccountRepository {

	private final JdbcTemplate jdbcTemplate;

	public AccountRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public Collection<Account> getAll() {
		String sql = "SELECT id_acc, username, password, gender, age, creation_timestamp FROM account ORDER BY username ASC;";
		RowMapper<Account> lambdaMapper = (rs, rowNum) -> new Account(rs.getInt("id_acc"), rs.getString("username"), rs.getString("password"),
			parseGender(rs.getString("gender")), rs.getInt("age"), rs.getTimestamp("creation_timestamp").toLocalDateTime());
		return jdbcTemplate.query(sql, lambdaMapper);
	}

	public Optional<Account> getById(int idAcc) {
		String sql = "SELECT id_acc, username, password, gender, age, creation_timestamp FROM account WHERE id_acc=?;";
		ResultSetExtractor<Optional<Account>> rsExtractor = rs -> {
			Account account = null;
			if (rs.next()) {
				account = new Account(rs.getInt("id_acc"), rs.getString("username"), rs.getString("password"),
					parseGender(rs.getString("gender")), rs.getInt("age"), rs.getTimestamp("creation_timestamp").toLocalDateTime());
			}
			return Optional.ofNullable(account);

		};
		return jdbcTemplate.query(sql, rsExtractor, idAcc);
	}

	public void delete(int idAcc) {
		jdbcTemplate.update("DELETE FROM account WHERE id_acc=?;", idAcc);
	}

	public void add(Account account) {
		jdbcTemplate.update(
			"INSERT INTO account (username, password, gender, age, creation_timestamp) VALUES(?,?,?,?, current_timestamp::timestamp(0))",
			account.getUsername(), account.getPassword(), account.getGender().getValue(), account.getAge());
	}

	// for test purposes
	public void addWithId(Account account) {
		jdbcTemplate.update(
			"INSERT INTO account (id_acc, username, password, gender, age, creation_timestamp) VALUES(?,?,?,?,?, current_timestamp::timestamp(0))",
			account.getIdAcc(),
			account.getUsername(), account.getPassword(), account.getGender().getValue(), account.getAge());
	}

	public void edit(Account account) {
		jdbcTemplate.update(
			"UPDATE account SET "
				+ "gender=?, "
				+ "age=? "
				+ "WHERE id_acc=?",
			account.getGender().getValue(), account.getAge(), account.getIdAcc());
	}

	public boolean isUsernameAvailable(String username) {
		String sql = "SELECT COUNT(id_acc) FROM account WHERE username ILIKE ?";
		ResultSetExtractor<Integer> rsExtractor = rs -> {
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		};
		int count = jdbcTemplate.query(sql, rsExtractor, username);
		return count == 0;
	}

	private Gender parseGender(String value) {
		if ("M".equals(value)) {
			return Gender.MALE;
		} else if ("F".equals(value)) {
			return Gender.FEMALE;
		} else {
			return Gender.OTHER;
		}
	}

}
