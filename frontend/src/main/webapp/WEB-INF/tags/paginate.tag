<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="pager" required="true" type="org.springframework.data.domain.Page"%>
<%@ attribute name="extraParam" required="true" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
			<ul>
				<li class="prev<c:out value="${pager.firstPage ? ' disabled' : '' }"/>"><a
					<c:if test="${!pager.firstPage}"> href="computers.do?page=${pager.number - 1}${extraParam}"</c:if>>&larr;
						Previous</a></li>
				<li class="current"><a>Displaying ${pager.number *
						pager.size + 1} to ${pager.number * pager.size +
						pager.numberOfElements} of ${pager.totalElements} </a></li>
				<li class="next<c:out value="${pager.lastPage ? ' disabled' : ''}"/>"><a
					<c:if test="${!pager.lastPage}"> href="computers.do?page=${pager.number + 1}${extraParam}"</c:if>>Next
						&rarr;</a></li>
			</ul>