package com.excilys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STATISTIC")
public class Statistic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="DATE_MODIF", nullable = false)
	private Date date;
	@Column(name = "COMPUTER_ID", nullable = false)
	private int computerId;
	@Column(name = "IP_ADDRESS", nullable = false)
	private String ip;
	@Column(name = "operation", nullable = false)
	private String operation;

	public enum DBOperation {
		CREATE(1),
		UPDATE(2),
		DELETE(3);
		
		private int num;
		
		DBOperation(int num) {
			this.num = num;
		}
		
		@Override
		public String toString() {
			return String.valueOf(num);
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public void setOperation(DBOperation operation) {
		this.operation = operation.getLibelle();
	}
}
