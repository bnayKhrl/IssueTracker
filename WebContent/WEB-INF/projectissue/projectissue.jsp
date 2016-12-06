
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<%@ include file="/WEB-INF/includes/head.jsp"%>
</head>

<style type="text/css">
.issuetd {
   max-width:200px;
   white-space:nowrap; 
   overflow:hidden;
   text-overflow:ellipsis;
   }
.issuetd:hover {
   overflow:visible;
    white-space: normal;
   }
</style>



<body>
	<div>
		<%@ include file="/WEB-INF/includes/header.jsp"%>

		<h2>Project Issue</h2>
		<div>
			<a href="projectissue/create"><button type="button"
					class="btn btn-info">
					<span class="glyphicon glyphicon-plus"></span>Add Issue
				</button> </a>
		</div>

		<div>
			<table class="table table-striped">
				<tr class="success">
					<th width="10%">SN</th>
					<th width="30%">Projects</th>
					<th width="30%">Issue</th>
					<th width= "20%"> Posted By </th>
					<th width="10%">Action</th>

				</tr>
				<c:set var="count" value="0" />
				<c:forEach items="${projectissue}" var="projectissue">
					<tr>
						<td>${count+1}</td>
						<c:set var="count" value="${count+1}" />
						
						<td ><a href="projectissue/${projectissue.project.id}/details">${projectissue.project.title}</a></td>
					
						<td class="issuetd"><a href="projectissue/${projectissue.id}/comment">${projectissue.issue}</a></td>
						<td> ${ projectissue.user.username } </td>
						<td><a href="projectissue/${projectissue.id}/edit"
							data-toggle="tooltip" title="Edit"><span
								class="glyphicon glyphicon-edit"></span></a> <a
							href="projectissue/${projectissue.id}/delete"
							data-toggle="tooltip" title="Delete" onclick="return confirm('Are You Sure You Want To Delete');"><span
								class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Project Issue</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>
	
</body>
</html>