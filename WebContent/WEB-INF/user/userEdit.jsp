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
					<p class="text-center">Edit User</p>
				</div>
				<div class="panel-body">

					<form id="formUser" method=post action="users/${editUser.id}/edit">
						<fieldset>
							<div class="form-group">
								<label for="username">Username</label> <input type="text"
									class="form-control" id="username" name="username"
									placeholder="Username" value="${editUser.username}">
							</div>
							<div class="form-group">
								<label for="password">Password</label> <input type="password"
									class="form-control" id="password" name="password"
									placeholder="Password" value="${editUser.password}">
							</div>
							<div class="form-group">
								<label for="role">Role</label> <select id='role' name='role'
									class="form-control">
									<%-- 											<option value="${selected}" selected>${selected}</option> --%>
									<c:forEach items="${roles}" var="role">
										<%-- 												<c:if test="${role != selected}"> --%>
										<option value="${role.id}"
											${role.id == editUser.role.id ? 'selected' : ''}>${role.role}</option>
										<%-- 												</c:if> --%>
									</c:forEach>
								</select>
							</div>

							<div class="form-group">
								<input type=submit class="btn btn-info" class="form-control"
									id="btnSubmit" name="edituser" value="Submit" onclick="validation()">
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
		    var form = $("#formUser");
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
		var user = document.getElementById('username').value;
		var password = document.getElementById('password').value;
		if(user == null || password == null){
			alert("Please enter username and password");
			document.getElementById('username').focus();
		}
	} */
	</script>
</body>
</html>