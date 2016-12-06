<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
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
					<p class="text-center">New Project</p>
				</div>
				<div class="panel-body">

					<form id="formRole" method="post" action="roles/create">
						<fieldset>
							<div class="form-group">
								<label for="role"> Role </label> <input
									type="text" class="form-control" id="role" name="role"
									placeholder="Enter role" required>
							</div>
							<div class="form-group">
								<input type=submit class="btn btn-info btn-lg"
									class="form-control" id="btnSubmit" name="addrole" value="Submit">
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>
	<script type="text/javascript">
	$(document).ready(function () {
		$('#btnSubmit').on('click', function() {
		    var form = $("#formRole");
		    form.validate();
		    if (!form.valid()) {
		    	return;
	        }
		});
	});
	/* function validation(){
		inputCheck();
	}
	function inputCheck(){
		var role = document.getElementById('role').value;
		if(role==null){
			alert("Please enter role");
			document.getElementById('role').focus();
		}
	} */
	</script>
</body>
</html>