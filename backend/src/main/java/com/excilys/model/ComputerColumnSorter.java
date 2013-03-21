package com.excilys.model;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;


public class ComputerColumnSorter {
	
	private static final ComparableExpressionBase<?>[] QIndexes = { null, QComputer.computer.name, QComputer.computer.introduced,
		QComputer.computer.discontinued, QCompany.company.name } ;
	
	private int sort;
	
	public ComputerColumnSorter(int sort) {
		this.sort = sort;
		if (QIndexes.length < Math.abs(sort) || Math.abs(sort) <= 0)
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
	
	public Object getQColumn() {
		return QIndexes[Math.abs(sort)];
	}
	
	public OrderSpecifier<?> getOrderSpecifier() {
		ComparableExpressionBase<?> path = QIndexes[Math.abs(sort)];
		return isAsc() ? path.asc() : path.desc();
	}
	
	public boolean isAsc() {
		return sort >= 0;
	}

}
