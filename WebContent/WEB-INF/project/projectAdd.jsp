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

					<form id="formProject" method="post" action="projects/create">
						<fieldset>
							<div class="form-group">
								<label for="projectTitle">Project Title</label> <input
									type="text" class="form-control" id="projectTitle" name="projectTitle"
									placeholder="Project Title" required>
							</div>
							<div class="form-group">
								<label for="startDate">Start Date</label><br />
								<input type="date" value="${datetoday}" class="datepicker" id="startDate" name="startDate"
									onblur="dateCheck()" placeholder="Start Date">
							</div>
							
							<div class="form-group">
								<label for="deadlineDate">Deadline Date</label><br />
								<input
									type="date" value="${datetoday}" class="datepicker" id="deadlineDate" name="deadlineDate"
									onblur="dateCheck()" placeholder="Deadline Date">
							</div>
							
							<div class="form-group">
								<input type=submit class="btn btn-info btn-lg"
									class="form-control" id="btnSubmit" name="addproject" value="Submit">
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
		    var form = $("#formProject");
		    form.validate();
		    if (!form.valid()) {
		    	return;
	        }
		});
	});
	
/* 	function validation(){
		titleCheck();
		dateCheck();
	}
	function titleCheck(){
		var title = document.getElementById('projectTitle').value;
		if(title==null){
			alert("Please enter project title");
			document.getElementById('projectTitle').focus();
		}
	} */
	function dateCheck()
	{
	  var startDate= document.getElementById('startDate').value;
	  var endDate= document.getElementById('deadlineDate').value;
	  var eDate = new Date(endDate);
	  var sDate = new Date(startDate);
	  if(startDate!= '' && endDate!= '' && sDate> eDate)
	    {
	    alert("Please Enter the valid start and end date");
	   }
	 
	} 
	</script>
</body>
</html>