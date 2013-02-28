package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Company;

public enum DBCompanyDAO implements CompanyDAO {
	
	INSTANCE;

	private Company extractCompany(ResultSet res) throws SQLException {
		Company c = new Company();
		c.setId(res.getInt("id"));
		c.setName(res.getString("name"));
		return c;
	}
	
	@Override
	public Company getCompany(int id) throws SQLException {
		Connection connection = DataSourceFactory.INSTANCE.getConnectionThread();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM company WHERE id = ?");
		stmt.setInt(1, id);
		ResultSet res = stmt.executeQuery();
		Company c = null;
		if (res.next()) {
			c = extractCompany(res);
		}
		res.close();
		return c;
	}

	@Override
	public List<Company> getCompanies() throws SQLException {
		Connection connection = DataSourceFactory.INSTANCE.getConnectionThread();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM company ORDER BY name");
		ResultSet res = stmt.executeQuery();
		List<Company> list = new ArrayList<Company>();
		while (res.next()) {
			list.add(extractCompany(res));
		}
		res.close();
		return list;
	}

}
