<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Collection"%>
<%@ page import="tracker.rolemenudetail.entity.RoleMenuDetail"%>
<html>
<head>
<%@ include file="/WEB-INF/includes/head.jsp"%>
</head>
<body>
	<%
		
	%>
	<div>
		<%@ include file="/WEB-INF/includes/header.jsp"%>

		<h2>Project List</h2>
		<div>
			<a href="projects/create">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-plus"></span>Add New
				</button>
			</a>
		</div>
		<div>
			<table class="table table-striped">
				<tr class="success">
					<th>SN</th>
					<th>Title</th>
					<th>Start Date</th>
					<th>Deadline Date</th>
					<th>Action</th>

				</tr>
				<c:set var="count" value="0" />
				<c:forEach items="${projects}" var="project">
					<tr>
						<td>${count+1}</td>
						<c:set var="count" value="${count+1}" />
						<td><a href="projects/details/${project.id}">${project.title}</a></td>
						<td>${project.startdate}</td>
						<td>${project.deadlinedate}</td>
						<td><a href="projects/${project.id}/edit"
							data-toggle="tooltip" title="Edit"><span
								class="glyphicon glyphicon-edit"></span></a> <a
							href="projects/${project.id}/delete" data-toggle="tooltip"
							title="Delete"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</table>

			<div>
				<form method="GET" action="projects/report" target="_blank">
					<table>
						<td style="width: 95%">&nbsp;</td>
					
						<td><br /> <input type="Submit" value="Print"
							class="btn btn-info" /></td>

					</table>

				</form>
			</div>


		</div>

	</div>

	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>
</body>
</html>