package com.excilys.model;

import java.util.Date;

public class Statistic {
	
	private int computerId;
	private Date date;
	private String ip;
	private DBOperation operation;

	public enum DBOperation {
		CREATE(1),
		UPDATE(2),
		DELETE(3);
		
		private int num;
		
		DBOperation(int num) {
			this.num = num;
		}
		
		public int getNum() {
			return num;
		}
		
		public String getLibelle() {
			switch(this) {
				case CREATE: return "CREATE";
				case DELETE: return "DELETE";
				default: return "UPDATE";
			}
		}
	}
	
	public int getComputerId() {
		return computerId;
	}
	
	public void setComputerId(int computerId) {
		this.computerId = computerId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public DBOperation getOperation() {
		return operation;
	}
	
	public void setOperation(DBOperation operation) {
		this.operation = operation;
	}
}
