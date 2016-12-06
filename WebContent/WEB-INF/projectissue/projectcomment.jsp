<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<%@ include file="/WEB-INF/includes/head.jsp"%>
</head>

<style type="text/css">
.issuetd {
	max-width: 200px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.issuetd:hover {
	overflow: visible;
	white-space: normal;
}
</style>


<body>
	<div>
		<%@ include file="/WEB-INF/includes/header.jsp"%>
	</div>
	<div class="row">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<p class="text-center">Issue Comments</p>
				</div>
				<div class="panel-body">
					<form id="formAssignProject" method="post"
						action="projectissue/comment">
						<fieldset>

							<div class="form-group">
								<table style="border: hidden;">
									<c:forEach items="${issuecomment}" var="issuecomment">
									<tr>
										<th class="issuetd">Issue Title:</th>
										<td width="60%"> ${issuecomment.issue.issue}  </td>
										<th>By: </th>
										<td> ${ issuecomment.user.username }  </td>
									</tr>
									</c:forEach>
								</table>
							</div>

							<div id="commentdiv">
							
							</div>
							
							<div class="panel-footer">
								<div class="input-group">
									<input id="btn-input" type="text" class="form-control input-md"
										placeholder="Type your comment here..."> <span
										class="input-group-btn">
										<button class="btn btn-input btn-md" id="btnCmt">Send</button>
									</span>
								</div>
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
	</script>
</body>
</html>