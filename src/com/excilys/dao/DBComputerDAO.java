package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public class DBComputerDAO implements ComputerDAO {

	private static final String BASE_SELECT = "c.id c_id, c.name c_name, introduced, discontinued, cy.id cy_id, cy.name cy_name ";
	private static final String BASE_FROM = "FROM computer c LEFT JOIN company cy ON c.company_id = cy.id ";
	private static final String BASE_SQL = "SELECT " + BASE_SELECT + BASE_FROM;
	private static final String BASE_COUNT = "SELECT COUNT(*) " + BASE_FROM;
	private static final String WHERE_ID = "WHERE c.id = ? ";
	private static final String WHERE_FILTER = "WHERE UPPER(c.name) LIKE UPPER(?) ";
	private static final String LIMIT = "LIMIT ?, ? ";
	private static final String INSERT_SQL = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (computer_seq.nextval, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_SQL = "DELETE FROM computer WHERE id = ?";
	private static final String ORDER = "ORDER BY UPPER(%1$s) %2$s ";
	
	private Connection connection;
	
	public DBComputerDAO(Connection connection) {
		this.connection = connection;
	}

	private String getOrderBy(ComputerColumnSorter sorter) {
		return String.format(ORDER, sorter.getColumnName(), sorter.getOrderBy());
	}
	
	private Computer extractComputer(ResultSet res) throws SQLException {
		Computer c = new Computer();
		c.setId(res.getInt("c_id"));
		c.setName(res.getString("c_name"));
		c.setIntroduced(res.getDate("introduced"));
		c.setDiscontinued(res.getDate("discontinued"));

		String companyName = res.getString("cy_name");
		if (companyName != null) {
			Company company = new Company();
			company.setId(res.getInt("cy_id"));
			company.setName(companyName);
			c.setCompany(company);
		}

		return c;
	}

	@Override
	public Computer getComputer(int id) throws SQLException {
		StringBuilder sb = new StringBuilder(BASE_SQL);
		sb.append(WHERE_ID);
		PreparedStatement stmt = connection.prepareStatement(sb.toString());
		stmt.setInt(1, id);
		ResultSet res = stmt.executeQuery();
		Computer c = null;
		if (res.next()) {
			c = extractComputer(res);
		}
		res.close();
		return c;
	}

	@Override
	public int getComputersCount() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(BASE_COUNT);
		ResultSet res = stmt.executeQuery();
		res.next();
		return res.getInt(1);
	}

	@Override
	public List<Computer> getComputers() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(BASE_SQL);
		ResultSet res = stmt.executeQuery();
		List<Computer> list = new ArrayList<Computer>();
		while (res.next()) {
			list.add(extractComputer(res));
		}
		res.close();
		return list;
	}

	@Override
	public List<Computer> getComputers(int page, ComputerColumnSorter sorter) throws SQLException {
		StringBuilder sb = new StringBuilder(BASE_SQL);
		sb.append(getOrderBy(sorter));
		sb.append(LIMIT);
		PreparedStatement stmt = connection.prepareStatement(sb.toString());
		stmt.setInt(1, page * NB_PER_PAGE);
		stmt.setInt(2, NB_PER_PAGE);
		ResultSet res = stmt.executeQuery();
		List<Computer> list = new ArrayList<Computer>();
		while (res.next()) {
			list.add(extractComputer(res));
		}
		res.close();
		return list;
	}

	@Override
	public int getComputersCount(String filtre) throws SQLException {
		StringBuilder sb = new StringBuilder(BASE_COUNT);
		sb.append(WHERE_FILTER);
		PreparedStatement stmt = connection.prepareStatement(sb.toString());
		stmt.setString(1, String.format("%%%s%%", filtre));
		ResultSet res = stmt.executeQuery();
		res.next();
		return res.getInt(1);
	}

	@Override
	public List<Computer> getComputers(String filtre, ComputerColumnSorter sorter) throws SQLException {
		StringBuilder sb = new StringBuilder(BASE_SQL);
		sb.append(WHERE_FILTER);
		sb.append(getOrderBy(sorter));
		PreparedStatement stmt = connection.prepareStatement(sb.toString());
		stmt.setString(1, String.format("%%%s%%", filtre));
		ResultSet res = stmt.executeQuery();
		List<Computer> list = new ArrayList<Computer>();
		while (res.next()) {
			list.add(extractComputer(res));
		}
		res.close();
		return list;
	}

	@Override
	public List<Computer> getComputers(String filtre, int page, ComputerColumnSorter sorter) throws SQLException {
		StringBuilder sb = new StringBuilder(BASE_SQL);
		sb.append(WHERE_FILTER);
		sb.append(getOrderBy(sorter));
		sb.append(LIMIT);
		PreparedStatement stmt = connection.prepareStatement(sb.toString());
		stmt.setString(1, String.format("%%%s%%", filtre));
		stmt.setInt(2, page * NB_PER_PAGE);
		stmt.setInt(3, NB_PER_PAGE);
		ResultSet res = stmt.executeQuery();
		List<Computer> list = new ArrayList<Computer>();
		while (res.next()) {
			list.add(extractComputer(res));
		}
		res.close();
		return list;
	}

	@Override
	public boolean saveOrUpdate(Computer computer) throws SQLException {
		if (computer.getId() <= 0) {
			save(computer);
			return true;
		}
		
		update(computer);
		return false;
	}
	
	private void bindParameters(Computer computer, PreparedStatement stmt, boolean withId) throws SQLException {
		stmt.setString(1, computer.getName());
		if (computer.getIntroduced() != null)
			stmt.setDate(2, new java.sql.Date(computer.getIntroduced().getTime()));
		else
			stmt.setNull(2, Types.DATE);
		if (computer.getDiscontinued() != null)
			stmt.setDate(3, new java.sql.Date(computer.getDiscontinued().getTime()));
		else
			stmt.setNull(3, Types.DATE);
		if (computer.getCompany() == null) 
			stmt.setNull(4, Types.INTEGER);
		else
			stmt.setInt(4, computer.getCompany().getId());
		if (withId)
			stmt.setInt(5, computer.getId());
	}
	
	private void save(Computer computer) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(INSERT_SQL);
		bindParameters(computer, stmt, false);
		stmt.executeUpdate();
	}
	
	private void update(Computer computer) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL);
		bindParameters(computer, stmt, true);
		stmt.executeUpdate();
	}

	@Override
	public void delete(int id) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(DELETE_SQL);
		stmt.setInt(1, id);
		stmt.executeUpdate();
	}

}
