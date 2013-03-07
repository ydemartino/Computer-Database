package com.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COMPANY")
public class Company {
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "COMPANY_SEQ")   
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")  
	private int id;
	@Column(nullable = false)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
}
