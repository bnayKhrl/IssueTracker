<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="tracker.user.entity.Role"%>

<html>
<head>
<%@ include file="/WEB-INF/includes/head.jsp"%>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.4.2/chosen.jquery.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.4.2/chosen.css">
</head>
<body>

	<div>
		<%@ include file="/WEB-INF/includes/header.jsp"%>
		<div class="row">
			<div
				class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">

				<div class="login-panel panel panel-large">
					<div class="panel-heading">
						<p class="text-center">Assign Task</p>
					</div>
					<div class="panel-body">
						<form id="formtask">
							<fieldset>
								<div class="panel-body">
									<table class="table table-bordered" align="center">
										<div class="form-group">

											<label for="task">Task</label> <input type="text"
												class="form-control" id="task" size="80" name="task"
												placeholder="Task Name" required>

										</div>

										<!-- 		<div class="form-group">
											<label for="startdate">Date </label><br /> <input
												type="date" step="1" min="00:00:00" max="20:00:00"
												id="startdate">
										</div> -->

										<div class="form-group">
											<label for="startdate">Time </label><br /> <input
												type="time" step="1" min="00:00:00" max="20:00:00"
												id="starttime">
										</div>

										<div class="form-group">
											<table id="tbltask" class="table">
												<thead>
													<tr>
														<th class="text-center">SN</th>
														<th class="text-center" id="tbltsk">Task</th>
														<th class="text-center" id="tbldead">Startdate</th>
														<th class="text-center">Action</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<div class="form-group">
											<input type="button" class="btn btn-info btn-sm" id="btnAdd"
												value="Add"> <input type="button"
												class="btn btn-info btn-sm" class="form-control"
												id="btnSubmit" name="adduser" value="Submit">
										</div>
									</table>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>



		</div>
	</div>



	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>

	<p id="demo"></p>

	<script type="text/javascript">
		$("#btnAdd")
				.click(
						function() {
							var task = $("#task").val();

							var time = $("#starttime").val();
							var count = $('table tr').length;

							$('#tbltask')
									.append(
											'<tr><td class="sn" >'
													+ count
													+ '</td><td>'
													+ task
													+ '</td><td>'
													+ time
													+ '</td><td>'
													+ '<button type="button" class="btn btn-info btn-sm remove">Del</button>'
													+ '</td></tr>');

						});

		$('#tbltask').on('click', '.remove', function() {
			$(this).closest('tr').remove();
			reIndexing();
		});

		function reIndexing() {
			var sn = 0;
			$('.sn').each(function(k, v) {
				$(this).html(++sn);

			});
		}

		$('#btnSubmit').click(function() {
			create();
		});

		function create() {

			var myRows = [];
			var $headers = $("th");
			var $rows = $("tbody tr").each(
					function(index) {
						$cells = $(this).find("td");
						myRows[index] = {};
						$cells.each(function(cellIndex) {
							myRows[index][$($headers[cellIndex]).html()] = $(
									this).html();
						});
					});

			var myObj = {};
			myObj.myrows = myRows;
			alert(JSON.stringify(myObj));

			$.ajax({
				type : "POST",
				url : "api/task/create",
				contentType : "application/json",
				data : JSON.stringify(myObj),
				complete : function(response) {
					if (response.status === 201) {
						alert("Data Saved Successfully");
						window.location.reload();
					}
				}
			});
		}
	</script>
</body>
</html>