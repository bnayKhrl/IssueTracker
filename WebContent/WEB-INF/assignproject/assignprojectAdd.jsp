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
					<p class="text-center">Assign Project</p>
				</div>
				<div class="panel-body">
					<form id="formAssignProject" method="post" action="assignprojects/create">
						<fieldset>
							<div class="form-group">
								<label for="user">User</label> <select id='user' name='user'
									class="form-control">
									<c:forEach items="${users}" var="user">
											<option value='${user.user.id}'>${user.user.username}</option>
									</c:forEach>
								</select>
							</div>

							<div>
								<table id="tblDetail" class="table">
				
									<thead>
										<tr>
											<th class="text-center">S.N.</th>
											<th class="text-center">Project</th>
											<th class="text-center">&nbsp;</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div id="myDynamicTable">
							<input type="button" class="btn btn-info btn-sm" id="btnAdd" value="Add">
							
							<input type="button" class="btn btn-info btn-sm" id="btnSubmit" value="Submit"
									onclick="call()">
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
		$(document).ready(function() {
			addRow();
			$("#btnAdd").click(function() {
				addRow();
				$(".project").focus();
			});
		});
		$(document).ready(function () {
			$('#btnSubmit').on('click', function() {
			    var form = $("#formAssignProject");
			    form.validate();
			    if (!form.valid()) {
			    	return;
		        }
			});
		});
	
		function checkProject(ui){
			$('#tblDetail tbody tr').each(function(){
				var existingUsers = $(this).find('input.project').attr('data-id');
				if(existingUsers == ui.item.id){
					alert("Project Already Exists");
				//	$(this).clear();
					$(this).remove();
					reIndexing();
					addRow();
					
				//$(this).find('input.project').attr("data-id","");
				}
			});
		}

		function addRow() {
			var counter = $("#tblDetail tbody tr").length + 1;
			var tr = $("<tr>");

			var td0 = $("<td class='col-md-1 sn'>");
			var divFormT0 = $("<div class='form-group'>");
			var divInputT0 = $("<div class='input-group'>");
			td0.innerHTML = counter;

			divInputT0.append(counter);
			divFormT0.append(divInputT0);
			td0.append(divFormT0);

			var td1 = $("<td class='col-md-4'>");
			var divForm1 = $("<div class='form-group'>");
			var divInputT1 = $("<div class='input-group'>");
			var span = $("<span class='project'>").addClass("model");
			var project = $("<input type='text' class='form-control' required>")
					.attr("name", "project").val("");
			project.addClass("project");
			
					divInputT1.append(project);
					//divInputT1.append(projectId);
					divInputT1.append(span);
					divForm1.append(divInputT1);
					td1.append(divForm1);
			
			var td2 = $("<td class='col-md-6'>");
			var divFormT2 = $("<div class='form-group'>");
			var divInputT2 = $("<div class='input-group'>");
			var button = $("<button id='btnDelete' type='button' class='removebutton' required> Delete </button>")
					.attr("name", "project").val("");
			
			divInputT2.append(button);
			divFormT2.append(divInputT2);
			td2.append(divFormT2);
			
			tr.append(td0);
			tr.append(td1);
			tr.append(td2);

			$("#tblDetail tbody").append(tr);
			projectAutoComplete('project');

		}

 				$('#tblDetail tbody').on('click', '.removebutton',function() {
				  $(this).closest('tr').remove();
				  if ($('#tblDetail tbody tr').length<2) {
						$('#btnDelete').attr("disabled", true);
				  }
				  reIndexing();
				});

		
		function reIndexing(){
				var sn = 0;
				$('.sn').each(function(k,v){
					$(this).html(++sn);		
					
				});
		}  
		
		function projectAutoComplete(classname) {
			$("." + classname).autocomplete(
					{
						source : function(request, response) {
							$.ajax({
								type : "GET",
								url : 'assignprojects/search',
								dataType : "json",
								data : {
									title : request.term,
								},
								success : function(data) {
									response($.map(data, function(v) {
										return {
											id : v.id,
											label : v.title,
											value : v.title,
										};
									}));
								}
							});
						},
						select : function(event, ui) {
							checkProject(ui);
							$(this).closest('tr').find('input.project').attr(
									"data-id", ui.item.id);
						}
					});
		}

		function createJson() {
			 var e = document.getElementById("user");
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
					if(response.status===201){
						alert("Data Saved Successfully");
						window.location.reload(); 
					}
				}
			});
		}

		function call() {
			createJson();
			create();

		}
	</script>
</body>
</html>