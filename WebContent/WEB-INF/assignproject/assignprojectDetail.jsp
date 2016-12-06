<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<%@ include file="/WEB-INF/includes/head.jsp"%>
</head>
<body>
	<div>
		<%@ include file="/WEB-INF/includes/header.jsp"%>

		<h2> Projects Lists </h2>

		<div>
			<table class="table table-striped">
				<tr class="success">
					<th>SN</th>
					<th>Project Title</th>
				<!-- <th>Edit</th> -->
				<th>Delete</th>
					
				</tr>
				<c:set var="count" value="0" />
				<c:forEach items="${assignprojects}" var="userProjectDetails">
					<tr>
						<td>${count+1}</td>
						<c:set var="count" value="${count+1}" />
						
						<td>${userProjectDetails.userProjectDetail.project.title}</td>
						<!-- <td><a href="assignprojects/${userProjectDetails.id}/edit"><span class="glyphicon glyphicon-edit"></span></a></td> -->
						<td><a href="assignprojects/delete/${userProjectDetails.id}"><span class="glyphicon glyphicon-trash"></span></a></td> 
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>
</body>
</html>