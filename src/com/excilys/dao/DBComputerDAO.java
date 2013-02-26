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

public class DBComputerDAO implements ComputerDAO {

	private static final String BASE_SELECT = "c.id c_id, c.name c_name, introduced, discontinued, cy.id cy_id, cy.name cy_name ";
	private static final String BASE_FROM = "FROM computer c LEFT JOIN company cy ON c.company_id = cy.id ";
	private static final String BASE_SQL = "SELECT " + BASE_SELECT + BASE_FROM;
	private static final String BASE_COUNT = "SELECT COUNT(*) " + BASE_FROM;
	private static final String WHERE_ID = "WHERE c.id = ? ";
	private static final String WHERE_FILTER = "WHERE c.name LIKE ? ";
	private static final String LIMIT = "LIMIT ?, ? ";
	private static final String INSERT_SQL = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (computer_seq.nextval, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

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
	public Computer getComputer(int id) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return null;
		try {
			StringBuilder sb = new StringBuilder(BASE_SQL);
			sb.append(WHERE_ID);
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			Computer c = null;
			if (res.next()) {
				c = extractComputer(res);
			}
			res.close();
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int getComputersCount() {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(BASE_COUNT);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<Computer> getComputers() {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return null;
		try {
			PreparedStatement stmt = conn.prepareStatement(BASE_SQL);
			ResultSet res = stmt.executeQuery();
			List<Computer> list = new ArrayList<Computer>();
			while (res.next()) {
				list.add(extractComputer(res));
			}
			res.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Computer> getComputers(int page) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return null;
		try {
			StringBuilder sb = new StringBuilder(BASE_SQL);
			sb.append(LIMIT);
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setInt(1, page * NB_PER_PAGE);
			stmt.setInt(2, NB_PER_PAGE);
			ResultSet res = stmt.executeQuery();
			List<Computer> list = new ArrayList<Computer>();
			while (res.next()) {
				list.add(extractComputer(res));
			}
			res.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public int getComputersCount(String filtre) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return 0;
		try {
			StringBuilder sb = new StringBuilder(BASE_COUNT);
			sb.append(WHERE_FILTER);
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, String.format("%%%s%%", filtre));
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public List<Computer> getComputers(String filtre) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return null;
		try {
			StringBuilder sb = new StringBuilder(BASE_SQL);
			sb.append(WHERE_FILTER);
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, String.format("%%%s%%", filtre));
			ResultSet res = stmt.executeQuery();
			List<Computer> list = new ArrayList<Computer>();
			while (res.next()) {
				list.add(extractComputer(res));
			}
			res.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Computer> getComputers(String filtre, int page) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return null;
		try {
			StringBuilder sb = new StringBuilder(BASE_SQL);
			sb.append(WHERE_FILTER);
			sb.append(LIMIT);
			PreparedStatement stmt = conn.prepareStatement(sb.toString());
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void saveOrUpdate(Computer computer) {
		if (computer.getId() <= 0) {
			save(computer);
		}
		else {
			update(computer);
		}
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
	
	private void save(Computer computer) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return ;
		try {
			PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);
			bindParameters(computer, stmt, false);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update(Computer computer) {
		Connection conn = DataBaseUtil.getConnection();
		if (conn == null)
			return ;
		try {
			PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL);
			bindParameters(computer, stmt, true);
			conn.setAutoCommit(false);
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
