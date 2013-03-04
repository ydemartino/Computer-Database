package com.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.model.Company;

@Repository
public class DBCompanyDAO implements CompanyDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final class CompanyMapper implements RowMapper<Company> {
		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Company c = new Company();
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			return c;
		}	
	}
	
	@Override
	public Company getCompany(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM company WHERE id = ?",
				new Object[] { id }, new CompanyMapper());
	}

	@Override
	public List<Company> getCompanies() {
		return jdbcTemplate.query("SELECT * FROM company ORDER BY name", new CompanyMapper());
	}

}
