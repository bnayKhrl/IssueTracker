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

		<h2>Task Lists</h2>

		<div>
			<a href="task/create"><button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-plus"></span>Add New
				</button> </a>
		</div>

		<div>
			<div id="tablediv">
				<table class="table table-striped" id="tbldetail">
					<tr class="success">
						<th>SN</th>
						<th style="width: 50%">Task</th>
						<th>Startdate</th>
						<!-- <th style = "width:10%">Action</th> -->

					</tr>
				</table>
			</div>
			<%-- 				<c:set var="count" value="0" />
				
				<c:forEach items="${task}" var="task">
					<tr>
						<td>${count+1}</td>
						<c:set var="count" value="${count+1}" />
						<td>${task.name}</td>
						<td>${task.deadline}  </td>
						 <td><a href="task/${task.id}/edit"><span class="glyphicon glyphicon-edit"></span></a>
						<a href="task/delete/${task.id}"><span class="glyphicon glyphicon-trash"></span></a></td> 
					</tr>
				</c:forEach> --%>


			<!-- 	</table> -->
		</div>

 		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Reminder</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>

	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>

	<script>
		$(document).ready(
				function() {
					$.ajax({
						method : "GET",
						url : 'api/task',
						dataType : "json",
						success : function(data) {
							for (var i = 0; i < data.length; i++) {
								var count = $('table tr').length;
								$('#tbldetail').append(
										'<tr><td>' + count + '</td><td>'
												+ data[i].name + '</td><td>'
												+ data[i].startdate
												+ '</td></tr>');

							}
						},
						complete : function() {
							setTimeout(modalAlert, 40000);
						}
					});
				});

	</script>

</body>
</html>