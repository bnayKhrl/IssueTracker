$(document).ready(function() {
	modalAlert();
	
});
function modalAlert(){
			
			var infoModal = $('#myModal');
			$.ajax({
				method : "GET",
				url : 'api/task/search',
				dataType : "json",
				success : function(data) {
					if (data != '') {
						var htmlData = '';
						for (var i = 0; i < data.length; i++) {
						htmlData = data[i].name;
						}
						infoModal.find('.modal-body').html(
								'<p>' + htmlData + '</p>');
						$('#myModal').modal('show');
					}
				},
				complete : function() {
					setTimeout(modalAlert, 40000);
				}

			});
		}	

