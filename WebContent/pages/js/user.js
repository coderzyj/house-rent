myApp.controller("UserController", function($scope, $http,$location) {
//	$scope.users =[{id:1, userId:"000304343234", userName:'John', password:'555-1276',warehouse:"中央仓库",authority:"1111111"},
//		{id:1, userId:"00030984384202", userName:'Kevin', password:'555-1236',warehouse:"武汉仓库",authority:"1111111"},
//		{id:1, userId:"00083916477280", userName:'lan', password:'555-1272',warehouse:"中央仓库",authority:"1111111"},
//		{id:1, userId:"00039381763309", userName:'Mike', password:'555-1274',warehouse:"中央仓库",authority:"1111111"},
//		{id:1, userId:"000319376482232", userName:'Steven', password:'555-1203',warehouse:"广州仓库",authority:"1111111"},
//		{id:1, userId:"00030984389361", userName:'Smith', password:'555-1888',warehouse:"中央仓库",authority:"1111111"}
//		];
	$scope.showedit = true;
	$scope.master = {}; 
	$scope.loadData = function() {
		var req = {
				"offset" : 0,
				"rows" : 10000
		}
		$http({
			method : "post",
			url : "../user/getUserList",
			data : JSON.stringify(req)
		}).then(function(data) {
			console.log(data.data.result);
			$scope.users = data.data.result;
			console.log("success!");
		}).catch(function(data) {
			console.log(data.data.errorMsg);
			console.log("error");
		});
	};
	$scope.addUser = function(){
		if($scope.newId == undefined || $scope.newId == ""){
			alert("请输入登录名!");
			return;
		}
		if($scope.newName == undefined || $scope.newName == ""){
			alert("请输入姓名!");
			return;
		}
		if($scope.newPasswd == undefined || $scope.newPasswd == ""){
			alert("请输入密码!");
			return;
		}
			var url = "../user/insert";
			var user = {
					userId:$scope.newId,
					userName:$scope.newName,
					password:$scope.newPasswd,
					authority:$scope.selAuthority.toString()
			}
//			console.log(user);
			$http({
				method : "post",
				url : url,
				data : JSON.stringify(user)
			}).then(function(data) {
				console.log(data);
				console.log("success!");
				alert("已经提交成功");
				$scope.loadData();
				$scope.newId="";
				$scope.newName="";
				$scope.newPasswd="";
				$scope.selAuthority = [] ; 
			}).catch(function(data) {
				console.log(data);
				console.log("error");
				alert("提交失败");
			});
	};
	$scope.userupdate = function(user) {
		user.authority=$scope.selAuthority.toString();
		$http({
   			method : "post",
   			url : "../user/update",
   			data:JSON.stringify(user)
   		}).then(function(data) {
   			console.log("success!");
   			$scope.selAuthority = [] ; 
   			$scope.loadData();
   		}).catch(function(data) {
   			console.log("error");
   		});
	};
	$scope.userdelete = function(id) {
		$http({
   			method : "get",
   			url : "../user/delete/"+id
   		}).then(function(data) {
   			console.log("success!");
   			for(var i=0; i<$scope.users.length; i++){
	        	 if($scope.users[i].userId == id){
//	        		 console.log($scope.users[i]); 
	                 $scope.users.splice(i,1);  
	        	 }
	         }
   		}).catch(function(data) {
   			console.log(data);
   			console.log("error");
   		});
	};
	$scope.loadData();
	
	$scope.authorityList = [  
        {id : "1", name : '查询'},  
        {id : "2", name : '收货'},  
        {id : "3", name : '发货'},  
        {id : "4", name : '库内'}
    ] ;  
      
    $scope.selAuthority = [] ;  
      
    $scope.isChecked = function(authority, id){
        if(authority != null){
        	var ss = authority.split(",");
        	return ss.indexOf(id) >= 0 ; 
        }else{
        	return false;
        }
    } ;  
      
    $scope.updateSelection = function($event,id){  
        var checkbox = $event.target;  
        var checked = checkbox.checked; 
        if(checked){  
            $scope.selAuthority.push(id) ;  
        }else{  
            var idx = $scope.selAuthority.indexOf(id);  
            $scope.selAuthority.splice(idx,1); 
        }  
    };  
    
    $scope.updateSelAuthority = function(str) {
    	if(str!=null){
    		$scope.selAuthority = str.split(",");	
    	}
    }
});

myApp.directive("edituser",function(){
  return{
    restrict: 'AE',
    require: 'ngModel',
    link: function(scope,element,attrs,ngModel){
       element.bind("click",function(){
	       scope.$apply(function(){
	         angular.copy(ngModel.$modelValue,scope.master);
	         console.log(scope.master);
	         scope.showedit = false;
	         scope.updateSelAuthority(ngModel.$modelValue.authority);
	       });
      });
    }
  }
});

myApp.directive("userupdate",function(){
  return{
    restrict: 'AE',
    require: 'ngModel',
    link: function(scope,element,attrs,ngModel){
       element.bind("click",function(){
    	   var user = ngModel.$modelValue;
    	   scope.userupdate(user);
      });
    }
  }
});

myApp.directive("usercancel",function(){
  return{
    restrict: 'AE',
    require: 'ngModel',
    link: function(scope,element,attrs,ngModel){
       element.bind("click",function(){
	       scope.$apply(function(){
	         angular.copy(scope.master,ngModel.$modelValue);
	         console.log(ngModel.$modelValue);
	         scope.showedit = true;
	       });
      });
    }
  }
});

myApp.directive("userdelete",function(){
  return{
    restrict: 'AE',
    require: 'ngModel',
    link: function(scope,element,attrs,ngModel,http){
       element.bind("click",function(){
    	   var id = ngModel.$modelValue.userId;
    	   scope.userdelete(id);
       console.log(scope.users);
      });
    }
  }

});
