package com.excilys.model;

public class ComputerColumnSorter {
	
	private static final String[] indexes = { "", "c.name", "introduced", "discontinued", "cy.name" } ;
	
	private int sort;
	
	public ComputerColumnSorter(int sort) {
		this.sort = sort;
		if (indexes.length < Math.abs(sort) || Math.abs(sort) <= 0)
			this.sort = 1;
	}
	
	public boolean isCurrent(int sort) {
		return sort == Math.abs(this.sort);
	}
	
	public int getSort(int sort) {
		if (isCurrent(sort))
			return -this.sort;
		
		return sort;
	}
	
	public boolean isReverse(int sort) {
		return isCurrent(sort) && this.sort < 0;
	}
	
	public String getColumnName() {
		return indexes[Math.abs(sort)];
	}
	
	public String getOrderBy() {
		return sort < 0 ? "DESC" : "ASC";
	}
	
	public boolean isAsc() {
		return sort >= 0;
	}

}
