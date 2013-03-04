package com.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

@Repository
public class DBComputerDAO implements ComputerDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String BASE_SELECT = "c.id c_id, c.name c_name, introduced, discontinued, cy.id cy_id, cy.name cy_name ";
	private static final String BASE_FROM = "FROM computer c LEFT JOIN company cy ON c.company_id = cy.id ";
	private static final String BASE_SQL = "SELECT " + BASE_SELECT + BASE_FROM;
	private static final String BASE_COUNT = "SELECT COUNT(*) " + BASE_FROM;
	private static final String WHERE_ID = "WHERE c.id = ? ";
	private static final String WHERE_FILTER = "WHERE UPPER(c.name) LIKE UPPER(?) ";
	private static final String LIMIT = "LIMIT ?, ? ";
	private static final String INSERT_SQL = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (computer_seq.nextval, :name, :introduced, :discontinued, :company)";
	private static final String UPDATE_SQL = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company WHERE id = :id";
	private static final String DELETE_SQL = "DELETE FROM computer WHERE id = ?";
	private static final String ORDER = "ORDER BY UPPER(%1$s) %2$s ";

	private static final class ComputerMapper implements RowMapper<Computer> {
		@Override
		public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Computer c = new Computer();
			c.setId(rs.getInt("c_id"));
			c.setName(rs.getString("c_name"));
			c.setIntroduced(rs.getDate("introduced"));
			c.setDiscontinued(rs.getDate("discontinued"));
			String companyName = rs.getString("cy_name");
			if (companyName != null) {
				Company company = new Company();
				company.setId(rs.getInt("cy_id"));
				company.setName(companyName);
				c.setCompany(company);
			}
			return c;
		}
	}

	private String getOrderBy(ComputerColumnSorter sorter) {
		return String
				.format(ORDER, sorter.getColumnName(), sorter.getOrderBy());
	}

	@Override
	public Computer getComputer(int id) {
		StringBuilder sb = new StringBuilder(BASE_SQL).append(WHERE_ID);
		return jdbcTemplate.queryForObject(sb.toString(), new Object[] { id },
				new ComputerMapper());
	}

	@Override
	public int getComputersCount() {
		return jdbcTemplate.queryForInt(BASE_COUNT);
	}

	@Override
	public List<Computer> getComputers() {
		return jdbcTemplate.query(BASE_SQL, new ComputerMapper());
	}

	@Override
	public List<Computer> getComputers(int page, ComputerColumnSorter sorter) {
		StringBuilder sb = new StringBuilder(BASE_SQL).append(
				getOrderBy(sorter)).append(LIMIT);
		return jdbcTemplate.query(sb.toString(), new Object[] {
				page * NB_PER_PAGE, NB_PER_PAGE }, new ComputerMapper());
	}

	@Override
	public int getComputersCount(String filtre) {
		StringBuilder sb = new StringBuilder(BASE_COUNT).append(WHERE_FILTER);
		return jdbcTemplate.queryForInt(sb.toString(),
				new Object[] { String.format("%%%s%%", filtre) });
	}

	@Override
	public List<Computer> getComputers(String filtre,
			ComputerColumnSorter sorter) {
		StringBuilder sb = new StringBuilder(BASE_SQL).append(WHERE_FILTER)
				.append(getOrderBy(sorter));
		return jdbcTemplate.query(sb.toString(),
				new Object[] { String.format("%%%s%%", filtre) },
				new ComputerMapper());
	}

	@Override
	public List<Computer> getComputers(String filtre, int page,
			ComputerColumnSorter sorter) {
		StringBuilder sb = new StringBuilder(BASE_SQL).append(WHERE_FILTER)
				.append(getOrderBy(sorter)).append(LIMIT);
		return jdbcTemplate
				.query(sb.toString(),
						new Object[] { String.format("%%%s%%", filtre),
								page * NB_PER_PAGE, NB_PER_PAGE },
						new ComputerMapper());
	}

	@Override
	public boolean saveOrUpdate(Computer computer) {
		if (computer.getId() <= 0) {
			save(computer);
			return true;
		}

		update(computer);
		return false;
	}

	private SqlParameterSource getParameterSource(Computer computer,
			boolean withId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("name", computer.getName());
		namedParams.addValue("introduced", computer.getIntroduced());
		namedParams.addValue("discontinued", computer.getDiscontinued());
		namedParams.addValue("company", computer.getCompany() != null ? (Integer)computer.getCompany().getId() : null);
		if (withId)
			namedParams.addValue("id", computer.getId());
		return namedParams;
//		stmt.setString(1, computer.getName());
//		if (computer.getIntroduced() != null)
//			stmt.setDate(2, new java.sql.Date(computer.getIntroduced()
//					.getTime()));
//		else
//			stmt.setNull(2, Types.DATE);
//		if (computer.getDiscontinued() != null)
//			stmt.setDate(3, new java.sql.Date(computer.getDiscontinued()
//					.getTime()));
//		else
//			stmt.setNull(3, Types.DATE);
//		if (computer.getCompany() == null)
//			stmt.setNull(4, Types.INTEGER);
//		else
//			stmt.setInt(4, computer.getCompany().getId());
//		if (withId)
//			stmt.setInt(5, computer.getId());
	}

	private void save(Computer computer) {
		new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update(INSERT_SQL, getParameterSource(computer, false));
	}

	private void update(Computer computer) {
		new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update(UPDATE_SQL, getParameterSource(computer, true));
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update(DELETE_SQL, new Object[] { id });
	}

}
