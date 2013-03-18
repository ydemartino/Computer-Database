package com.excilys.model;

import org.joda.time.LocalDate;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.StringPath;


public class ComputerColumnSorter {
	
	private static final String[] indexes = { "", "name", "introduced", "discontinued", "company.name" } ;
	private static final Object[] QIndexes = { null, QComputer.computer.name, QComputer.computer.introduced,
		QComputer.computer.discontinued, QCompany.company.name } ;
	
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
	
	public Object getQColumn() {
		return QIndexes[Math.abs(sort)];
	}
	
	public OrderSpecifier<? extends Comparable<?>> getOrderSpecifier() {
		Object object = QIndexes[Math.abs(sort)];
		if (object instanceof StringPath) {
			StringPath path = (StringPath) object;
			return isAsc() ? path.asc() : path.desc(); 
		}
		if (object instanceof DatePath<?>) {
			DatePath<LocalDate> path = (DatePath<LocalDate>) object;
			return isAsc() ? path.asc() : path.desc();
		}
		
		return null;
	}
	
	public String getOrderBy() {
		return sort < 0 ? "DESC" : "ASC";
	}
	
	public boolean isAsc() {
		return sort >= 0;
	}

}
