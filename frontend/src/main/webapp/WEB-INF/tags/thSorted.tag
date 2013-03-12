<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="colId" required="true" type="java.lang.Integer"%>
<%@ attribute name="sorter" required="true" type="com.excilys.model.ComputerColumnSorter"%>
<%@ attribute name="thLabel" required="true" type="java.lang.String"%>
<%@ attribute name="extraParamFilter" required="true" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
					<th class="col${colId + 1} header<c:if test="${sorter.isCurrent(colId)}"> headerSort<c:choose><c:when test="${sorter.isReverse(colId)}">Down</c:when><c:otherwise>Up</c:otherwise></c:choose></c:if>"><a href="<c:url value="computers.do?sort=${sorter.getSort(colId)}${extraParamFilter}"/>">${thLabel}</a></th>