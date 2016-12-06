<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	</div>
	<div class="row">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<p class="text-center">Edit Role Menu Action</p>
				</div>
				<div class="panel-body">

					<form id="formRoleMenuDetail">
						<fieldset>
							<div class="form-group">
								<label for="menu">Menu</label><br /> <select id="selectMenu"
									onchange="menuFunction()" class="chzn-menu"
									multiple="multiple" style="width: 300px;">
									<c:forEach items="${menus}" var="menu">
										<option value="${menu.id}">${menu.item}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="action">Action</label><br /> <select
									id="selectAction" onchange="actionFunction()"
									class="chzn-action" multiple="multiple" style="width: 300px;">
									<c:forEach items="${actions}" var="action">
										<option value="${action.id}">${action.permission}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="role">Role</label> <select id='role' name='role'
									class="form-control">
									<c:forEach items="${roles}" var="role">
										<option value="${role.id}" disabled="disabled"
											${role.id == editRoleMenuDetail.roleMenu.role.id ? 'selected' : ''}>${role.role}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<table id="tblRoleMenuDetail" class="table">
									<thead>
										<tr>
											<th class="text-center">SN</th>
											<th class="text-center">&nbsp;</th>
											<th class="text-center">Menu</th>
											<th class="text-center">Action</th>
											<th class="text-center">Access</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="form-group">
								<input type=submit class="btn btn-info btn-md"
									class="form-control" id="btnSubmit" name="adduser"
									value="Submit" onclick="call()">
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
		$(document).ready(function() {
			loadMenuByRoleMenuId();
			loadActionByRoleMenuId();
			getInitialData();
			$('.chzn-menu').chosen();
			$('.chzn-action').chosen();
			$('.chzn-menu').trigger("chosen:updated");
			$('.chzn-action').trigger("chosen:updated");
		});
				
		function loadMenuByRoleMenuId(){
			var url = window.location.href;
			var id = url.split('/').slice(-2)[0];
			$.ajax({
				url: 'rolemenudetails/'+ id +'/menus',
				method: "GET",
				dataType: "JSON",
				data:{
					roleMenuId: $('.menu-action-id').val(),
				},
				success: function(data){
					pushMenuInChosen(data);
				},
				error : function(x) {
					console.log(x);
				}
			});
		}
		
		function loadActionByRoleMenuId(){
			var url = window.location.href;
			var id = url.split('/').slice(-2)[0];
			$.ajax({
				url: 'rolemenudetails/'+ id +'/actions',
				method: "GET",
				dataType: "JSON",
				data:{
					roleMenuId: $('.menu-action-id').val(),
				},
				success: function(data){
					pushActionInChosen(data);
				},
				error : function(x) {
					console.log(x);
				}
			});
		}
		
		function pushActionInChosen(action){
			var actionArray = [];
			for(var i=0; i<action.length;i++ ){
				actionArray.push(action[i]['menuAction'].action.id);
			}
			$("#selectAction").val(actionArray);
			$(".chzn-action").chosen();
			$(".chzn-action").trigger("chosen:updated");
		}
		
		function pushMenuInChosen(menu){
			var menuArray = [];
			for(var i=0;i<menu.length;i++){
				menuArray.push(menu[i]['menuAction'].menu.id);
			}
			$("#selectMenu").val(menuArray);
			$(".chzn-menu").chosen();
			$(".chzn-menu").trigger("chosen:updated");
		}

		function menuFunction() {
			if ($('#selectMenu').val() != null
					&& $('#selectAction').val() != null)
				getData();
			if ($('#selectMenu').val() == null
					|| $('#selectAction').val() == null) {
				$('#tblRoleMenuDetail tbody').remove();
			}
		}

		function actionFunction() {
			if ($('#selectMenu').val() != null
					&& $('#selectAction').val() != null)
				getData();
			if ($('#selectMenu').val() == null
					|| $('#selectAction').val() == null) {
				$('#tblRoleMenuDetail tbody').remove();
			}
		}

		function convertToJsonData() {
			var jsonData = {};
			jsonData.menuId = $("#selectMenu").val();
			jsonData.actionId = $("#selectAction").val();
			return JSON.stringify(jsonData);
		}

		function getInitialData() {
			var url = window.location.href;
			var id = url.split('/').slice(-2)[0];
			var jsonData = convertToJsonData();
			var data = $.ajax({
				type : "GET",
				url : 'rolemenudetails/' + id + '/getdata',
				dataType : "json",
				data : {
					jsonData : jsonData
				},
				success : function(data) {
					loadRoleMenuForEdit(data);
				},
				error : function(x) {
					console.log(x);
				}
			});
		}
		
		function getData() {
			var jsonData = convertToJsonData();
			var data = $
					.ajax({
						type : "GET",
						url : 'rolemenudetails/editdata',
						dataType : "json",
						data : {
							jsonData : jsonData
						},
						success : function(data) {
							var trHtml = '';
							$('#tblRoleMenuDetail tbody').remove();
							for (var i = 0; i < data.length; i++) {
								trHtml = '<tr><td>'
										+ (i + 1)
										+ '</td><td><input type="hidden" class="menu-action-id" value="'+data[i].id+'">'
										+ '</td><td>'
										+ data[i]['menu'].item
										+ '</td><td>'
										+ data[i]['action'].permission
										+ '</td><td>'
										+ '<input type="checkbox" name="rolemenudetail" checked>'
										+ '</td></tr>';
								$('#tblRoleMenuDetail').append(trHtml);
							}
						},
						error : function(x) {
							console.log(x);
						}
					});
		}

		function loadRoleMenuForEdit(data) {
			$('#tblRoleMenuDetail tbody').remove();
			var trHtml = '';
			for (var i = 0; i < data.length; i++) {
				trHtml = '<tr><td>'
						+ (i + 1)
						+ '</td><td><input type="hidden" class="menu-action-id" value="'+data[i].id+'">'
						+ '</td><td>'
						+ data[i]['menuAction']['menu'].item
						+ '</td><td>'
						+ data[i]['menuAction']['action'].permission
						+ '</td><td>'
						+ '<input type="checkbox" name="checkbox" checked>'
						+ '</td></tr>';
				$('#tblRoleMenuDetail').append(trHtml);
			}
		}		

		function createJson() {
			var e = document.getElementById("role");
			varStrRole = e.options[e.selectedIndex].value;

			jsonData = {};
			jsonData.role = varStrRole;
			jsonData.roleMenuActionDetail = [];

			$("#tblRoleMenuDetail tbody tr").filter(':has(:checkbox:checked)').each(function() {
				jsonDetail(this);
				jsonData.roleMenuActionDetail.push(roleMenuDetail);
			});
		}

		function jsonDetail(obj) {
			roleMenuDetail = {};
			roleMenuDetail.menuActionId = $(obj).find('input.menu-action-id')
					.val();
		}

		function update() {
			var url = window.location.href;
			var id = url.split('/').slice(-2)[0];
			alert(JSON.stringify(jsonData));
			$.ajax({
				type : "POST",
				url : 'rolemenudetails/' + id + '/edit',
				contentType : "application/json",
				data : JSON.stringify(jsonData),
				complete : function(response) {
					if (response.status === 201) {
						alert("Data Updated Successfully");
						window.location.reload();
					}
				}
			});
		}

		function call() {
			createJson();
			update();

		}
	</script>
</body>
</html>