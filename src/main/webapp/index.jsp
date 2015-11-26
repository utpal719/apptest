<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>App Test</title>
 <link href="resource/css/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="resource/css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resource/css/bootstrap.min.css"/>


<script src="resource/js/jquery-1.8.2.js" type="text/javascript"></script> 
<script src="resource/js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="resource/js/jquery.jtable.min.js" type="text/javascript"></script>
<script src="resource/js/jquery.tablesorter.min.js" type="text/javascript"></script> 
<script src="resource/js/bootstrap.min.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
	
		$('#TableContainer').jtable({
			title : 'USER LIST',
			paging: false,
	        sorting: true, 
	      
			selecting: true, //Enable selecting
            multiselect: false, //Allow multiple selecting
            selectingCheckboxes: true, //Show checkboxes on first column
			actions : {
				listAction : 'users/service/listusers',
				updateAction : 'users/service/updateuser',
				deleteAction : 'users/service/deleteuser'
			},
			fields : {
				id : {
					key : true,
					list : false,
					edit : false,
					create : false
				},
				name : {
					title : 'Name',
					width : '30%',
					
					list : true,
					edit : true,
					create : true
				},
				email : {
					title : 'Email',
					width : '30%',				
					list : true,
					edit : true,
					create : true
				},
				ph : {
					title : 'Ph Number',
					width : '15%',
					
					list : true,
					edit : true,
					create : true
				},
				comment : {
					title : 'Comment',
					width : '30%',
					
					list : true,
					edit : true,
					create : true
				},
			
			}
		});
		
		//Re-load records when user click 'load records' button.
        $('#reloadRecord').click(function (e) {
            e.preventDefault();
            
         //Pass the search and column name while invoking the service
         
            $('#TableContainer').jtable('load', {
                search: $('#search').val(),
                columnsearch: $('#columnSearch').val()
            });
        });
		
		$('#TableContainer').jtable('load');		
		
	});
</script>
</head>

<body>

<!-- container -->
<div id="container">
<br><br><br>

<!-- Form Start -->

<form id="test" class="form-horizontal" role="form" >
  <div class="form-group">
    <label class="control-label col-sm-2" for="name">Name : <span style="color:#E02222;font-size: 12px;">*</span></label>
   <div class="col-sm-10"> 
      <input type="text" class="form-control" id="name" data-required="1" name="name" placeholder="Enter Name">
    </div>
  </div>
 <div class="form-group">
    <label class="control-label col-sm-2" for="email">Email : <span style="color:#E02222;font-size: 12px;">*</span></label>
    <div class="col-sm-10">
      <input type="email" class="form-control" id="email" name="email" placeholder="Enter Email">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="PhNumber">Ph Number :</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="phNumber" name="phNumber" placeholder="Enter Phone Number">
    </div>
  </div>
  
    <div class="form-group">
    <label class="control-label col-sm-2" for="comment">Comment :</label>
    <div class="col-sm-10">
     <textarea class="form-control" rows="4" id="comment" name="comment" placeholder="Enter Comment"></textarea>
    </div>
 </div>  
 
  <input type="submit" class="btn btn-default center-block" value="submit"></input>
  
</form>
<!-- /Form END -->

<br><br><br>

<!-- Search box start -->
<div style="width: 75%; margin-right: 0%; margin-left: 17%; text-align: center;">

<div class="form-inline">  
<label class="control-label">Search</label>
  
  <input type="text" class="form-control"  id="search" name="search" placeholder="Enter Character">
   
   <select class="form-control" id="columnSearch">
    <option value="name">Name</option>
    <option value="email">Email</option>
    <option value="ph">Ph Number</option>
    <option value="comment">Comment</option>
  </select>
  
   <input type="button" class="btn btn-default" id="reloadRecord" value="Reload"></input>
   
</div>

<!-- /Search box end -->
<br>

<div id="TableContainer"></div>

</div>
<br><br><br>

</div>

<!-- /container -->

<script type="text/javascript"> 

$("#test").submit(function(e){
	
	e.preventDefault();
	var name = $("#name").val();
	var email=$("#email").val();
	var phNum =$("#phNumber").val();
	var numbers = /^[-+]?[0-9]+$/;
	
	if(name == null || name == ""){
		
		alert("Please enter name");
		return false;
	}
	
	if(email == null || email == ""){
		alert("Please enter email");
		return false;
	}

	if(!phNum.match(numbers)){
		
		alert("Please enter a valid ph number");
		return false;
	}
	

	var data = {};
    data['name'] = $('[name="name"]').val(); 
    data['email'] = $('[name="email"]').val();
    data['ph'] = $('[name="phNumber"]').val();
    data['comment'] = $('[name="comment"]').val();
   
    
    $.ajax({

    	  	type: "POST",
    	    url: "users/service/adduser",
    	    data: JSON.stringify(data), 
    	    
    	    contentType: "application/json; charset=utf-8",
    	   	dataType: "json",
    	   	
    	   	success: function(data){ 
               $('#TableContainer').jtable('load');
           	},
    	    error: function(e){
    	    	
    	    	alert("error");
    	    }
    	 
    });
		
});
</script> 
</body>
</html>
