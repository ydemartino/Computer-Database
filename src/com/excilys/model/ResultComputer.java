package com.excilys.model;

import java.util.List;

public class ResultComputer {
	
	private List<Computer> computers;
	
	private int total;

	public ResultComputer(List<Computer> computers, int total) {
		super();
		this.computers = computers;
		this.total = total;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
