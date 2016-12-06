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

		<h2> Role Menu Lists </h2>
		<div>
			<a href="rolemenudetails/create"><button type="button"
					class="btn btn-info">
					<span class="glyphicon glyphicon-plus"></span>Add New
				</button> </a>
		</div>

		<div>
			<table class="table table-striped">
				<tr class="success">
					<th>SN</th>
					<th>Role</th>
					<th>Action</th>

				</tr>
				<c:set var="count" value="0" />
				<c:forEach items="${roleMenu}" var="roleMenu">
					<tr>
						<td>${count+1}</td>
						<c:set var="count" value="${count+1}" />
						
						<td><a href="rolemenudetails/${roleMenu.id}/details">${roleMenu.role.role}</a></td>
					    <td><a id="detailModal" class="detailModal" data-toggle="modal" data-target="#myModal" data-id="${roleMenu.id}" data-toggle="tooltip" title="Details"><span class="glyphicon glyphicon-list"></span></a>	
						<a href="rolemenudetails/${roleMenu.id}/edit" data-toggle="tooltip" title="Edit"><span class="glyphicon glyphicon-edit"></span></a>
						<a href="rolemenudetails/${roleMenu.id}/delete" data-toggle="tooltip" title="Delete"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
	<div id="myModal" class="modal fade"  role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"> Menu Action Lists </h4>
        </div>
         <div class="modal-body">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
	<%@ include file="/WEB-INF/includes/footer.jsp"%>
	<%@ include file="/WEB-INF/includes/javascripts.jsp"%>
	<script type="text/javascript">
	$(document).ready(function(){
	    var infoModal = $('#myModal');
	    $('.detailModal').on('click', function(){
	    var id = $(this).attr("data-id");
		
	    $.ajax({
			type : "GET",
			url : 'rolemenudetails/' + id + '/details',
			dataType : 'json',
			
			success : function(data) {
				var htmlData = '';
				for(var i=0;i<data.length;i++){
				 htmlData += '<li>'+ data[i]["menuAction"].menu.item  +'&nbsp;'+'&nbsp;' + data[i]['menuAction'].action.permission+'</li>';
				}
		            infoModal.find('.modal-body').html('<ul>'+htmlData+'</ul>');
		            modal.modal('show'); 
					
		            
			}
		});
	    });
	});
	
	
	</script>
</body>
</html>