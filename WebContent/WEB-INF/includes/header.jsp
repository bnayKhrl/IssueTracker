<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="tracker.user.entity.User"%>
<%@ page import="java.util.Collection"%>
<%@ page import="tracker.rolemenudetail.entity.RoleMenuDetail"%>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#sidebar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><span>Guru</span>Admin</a>
			<!-- <img src="assets/image/logo_GURU.jpg"> -->
			<ul class="user-menu">
				<li class="dropdown pull-right"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"><svg
							class="glyph stroked male-user">
							<use xlink:href="#stroked-male-user"></use></svg> <%
 	User userdto = (User) session.getAttribute("user");
 %> <!-- <%=userdto.getUsername()%>  --> <c:out
							value="${sessionScope.user.username }" /> <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#"><svg class="glyph stroked male-user">
									<use xlink:href="#stroked-male-user"></use></svg> Profile</a></li>
						<li><a href="#"><svg class="glyph stroked gear">
									<use xlink:href="#stroked-gear"></use></svg> Settings</a></li>
						<li><a href="logout"><svg class="glyph stroked cancel">
									<use xlink:href="#stroked-cancel"></use></svg> Logout</a></li>
						</form>
					</ul></li>
			</ul>
		</div>

	</div>
	<!-- /.container-fluid -->
</nav>


<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
	<ul class="nav menu">
		<li class="active"><a href="index"><svg
					class="glyph stroked home">
					<use xlink:href="#stroked-home"></use></svg> Dashboard</a></li>

		<%
			HttpSession sessions = request.getSession();
			Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
			String menus = null;
			String actions = null;
			boolean flag = false;
			for (RoleMenuDetail roleMenuDetail : sessionScope) {
				menus = roleMenuDetail.getMenuAction().getMenu().getItem();
				actions = roleMenuDetail.getMenuAction().getAction().getPermission();
		%>

		<li>
			<%
				if (menus.equalsIgnoreCase("Project") && actions.equalsIgnoreCase("details")) {
			%><a href="projects"><svg class="glyph stroked chevron-right">
			<use xlink:href="#stroked-chevron-right"></use></svg> Project(s)</a> <%
 	}
 %>
		</li>

		<li>
			<%
				if (menus.equalsIgnoreCase("User") && actions.equalsIgnoreCase("details")) {
			%><a href="users"><svg class="glyph stroked line-graph">
			<use xlink:href="#stroked-line-graph"></use></svg> User(s)</a> <%
 	}
 %>
		</li>

		<li>
			<%
				if (menus.equalsIgnoreCase("Role") && actions.equalsIgnoreCase("details")) {
			%> <a href="roles"><svg class="glyph stroked calendar">
			<use xlink:href="#stroked-calendar"></use></svg> Role(s)</a> <%
 	}
 %>
		</li>

		<li>
			<%
				if (menus.equalsIgnoreCase("Assign Project") && actions.equalsIgnoreCase("details")) {
			%><a href="assignprojects"><svg class="glyph stroked plus-sign">
			<use xlink:href="#stroked-plus-sign"></use></svg> Assign Projects </a> <%
 	}
 %>
		</li>


		<li>
			<%
				if (menus.equalsIgnoreCase("Role Menu") && actions.equalsIgnoreCase("details")) {
			%><a href="rolemenudetails"><svg class="glyph stroked table">
			<use xlink:href="#stroked-table" /></svg> Role Menu </a> <%
 	}
 	}
 %>
		</li>

		<li><a href="projectissue"><svg class="glyph stroked table">
		<use xlink:href="#stroked-table" /></svg> Project Issue </a></li>

		<li><a href="task"><svg
					class="glyph stroked app-window">
					<use xlink:href="#stroked-app-window"></use></svg> Alerts &amp; Panels</a></li>

		<li role="presentation" class="divider"></li>
		<li><a href="login.html"><svg class="glyph stroked male-user">
					<use xlink:href="#stroked-male-user"></use></svg> Login Page</a></li>
	</ul>
	
<!-- 			<div id="myModal" class="modal fade" role="dialog">
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
		</div>  -->
		
	</div>
<!--/.sidebar-->
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="index"><svg class="glyph stroked home">
						<use xlink:href="#stroked-home"></use></svg></a></li>
		</ol>
	</div>
	<!--/.row-->

	<div class="row" style="margin-top: 15px;">
		<div class="col-xs-12 col-md-6 col-lg-3">
			<div class="panel panel-blue panel-widget ">
				<div class="row no-padding">
					<div class="col-sm-3 col-lg-5 widget-left">
						<svg class="glyph stroked bag">
							<use xlink:href="#stroked-bag"></use></svg>
					</div>
					<div class="col-sm-9 col-lg-7 widget-right">
						<div class="large">120</div>
						<div class="text-muted">New Orders</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-md-6 col-lg-3">
			<div class="panel panel-orange panel-widget">
				<div class="row no-padding">
					<div class="col-sm-3 col-lg-5 widget-left">
						<svg class="glyph stroked empty-message">
							<use xlink:href="#stroked-empty-message"></use></svg>
					</div>
					<div class="col-sm-9 col-lg-7 widget-right">
						<div class="large">52</div>
						<div class="text-muted">Comments</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-md-6 col-lg-3">
			<div class="panel panel-teal panel-widget">
				<div class="row no-padding">
					<div class="col-sm-3 col-lg-5 widget-left">
						<svg class="glyph stroked male-user">
							<use xlink:href="#stroked-male-user"></use></svg>
					</div>
					<div class="col-sm-9 col-lg-7 widget-right">
						<div class="large">24</div>
						<div class="text-muted">New Users</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-md-6 col-lg-3">
			<div class="panel panel-red panel-widget">
				<div class="row no-padding">
					<div class="col-sm-3 col-lg-5 widget-left">
						<svg class="glyph stroked app-window-with-content">
							<use xlink:href="#stroked-app-window-with-content"></use></svg>
					</div>
					<div class="col-sm-9 col-lg-7 widget-right">
						<div class="large">25.2k</div>
						<div class="text-muted">Page Views</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<!--/.row-->