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
	</div>
	<div class="row">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<p class="text-center">Add Project Issue</p>
				</div>
				<div class="panel-body">
					<form id="formAssignProject" method="post"
						action="projectissue/create">
						<fieldset>

							<div class="form-group">
								<label for="user">Project</label> <select id='project'
									name='project' class="form-control" required>

									<option></option>

								</select>
							</div>

							<div>
								<textarea id="issue" name="issue" rows="4" cols="55"
									style="resize: none" required></textarea>
							</div>
							<div>
								<br> <input type="submit" class="btn btn-info btn-sm"
									id="btnSubmit" value="Submit">
							</div>

						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>

	<script>
		$(document).ready(
				function() {
					var data = $.ajax({
						url : 'projectissue/search',
						type : 'GET',
						dataType : 'json',
						success : function(data) {
							$.each(data, function(i, value) {
								$('#project').append(
										$('<option>').text(
												data[i].project.title).attr(
												'value', data[i].project.id));
							});
						}
					});
				});

		/* 		$(document).ready(function() {
		 $('#btnSubmit').on('click', function() {
		 var form = $("#formAssignProject");
		 form.validate()
		 if (!form.valid()) {
		 return;
		 }
		 });
		 });  */

		/* 		$(document).ready(function() {

		 $("#btnSubmit").click(function() {
		 call();
		 });
		 }); */

		/*	function createJson() {
				var e = document.getElementById("project");
				varStrUser = e.options[e.selectedIndex].value;

				jsonData = {};
				jsonData.user = varStrUser;

				jsonData.detail = [];

				$("#tblDetail tbody tr").each(function() {
					jsonDetail(this);
					jsonData.detail.push(projectDetail);
				});
			}

			function jsonDetail(obj) {
				projectDetail = {};
				projectDetail.projectId = $(obj).find('.project').attr("data-id");
			}

			function create() {
				alert(JSON.stringify(jsonData));
				$.ajax({
					type : "POST",
					url : "assignprojects/create",
					contentType : "application/json",
					data : JSON.stringify(jsonData),
					complete : function(response) {
						if (response.status === 201) {
							alert("Data Saved Successfully");
							window.location.reload();
						}
					}
				});
			}

			function call() {
				createJson();
				create();

			} */
	</script>
</body>
</html>