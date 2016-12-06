<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="tracker.user.entity.Role" %>

<html>
<head>
<%@ include file="/WEB-INF/includes/head.jsp"%>
</head>
<body>
	<div>
		<%@ include file="/WEB-INF/includes/header.jsp"%>

		<h2>Role List</h2>
		<div>
			<a href="roles/create"><button type="button"
					class="btn btn-info">
					<span class="glyphicon glyphicon-plus"></span>Add New
				</button> </a>
		</div>
		<div>
			<table class="table table-striped">
				<tr class="success">
					<th>SN</th>
					<th>Role</th>
					<th>Action</th>

				</tr>
				<c:set var="count" value="0" />
				<c:forEach items="${roles}" var="role">
					<tr>
						<td>${count+1}</td>
						<c:set var="count" value="${count+1}" />
						<td><a href="roles/details/${role.id}">${role.role}</a></td>
						<td><a href="roles/${role.id}/edit" data-toggle="tooltip" title="Edit"><span class="glyphicon glyphicon-edit"></span></a>
						<a href="roles/${role.id}/delete" data-toggle="tooltip" title="Delete"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>
</body>
</html>