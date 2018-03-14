myApp.controller("JobController", function($scope, $http,$location) {
	$scope.showEdit = true;
	$scope.master = {}; 
	$scope.loadData = function() {
		$http({
			method : "get",
			url : "../job/loadData"
		}).then(function(data) {
			console.log(data.data.result);
			$scope.joblist = data.data.result;
			console.log("success!");
		}).catch(function(data) {
			console.log("error");
		});
	};
	
	$scope.exec = function(job) {
		$http({
			method : "post",
			url : "../job/startNow",
			data : JSON.stringify(job),
			cache : true
		}).then(function(data) {
			console.log(data);
			console.log("success!");
			alert("已经提交成功");
			$scope.loadData();
		}).catch(function(data) {
			console.log(data);
			console.log("error");
			alert("提交失败");
		});
	};
	$scope.start = function(job) {
		var url = "../job/start";
		$http({
			method : "post",
			url : url,
			data : JSON.stringify(job)
		}).then(function(data) {
			console.log(data);
			console.log("success!");
			alert("已经提交成功");
			$scope.loadData();
		}).catch(function(data) {
			console.log(data);
			console.log("error");
			alert("提交失败");
		});
	};
	$scope.stop = function(job) {
		var url = "../job/stop";
		$http({
			method : "post",
			url : url,
			data : JSON.stringify(job)
		}).then(function(data) {
			console.log(data);
			console.log("success!");
			alert("已经提交成功");
			$scope.loadData();
		}).catch(function(data) {
			console.log(data);
			console.log("error");
			alert("提交失败");
		});
	};
	$scope.jobupdate = function(job) {
		var url = "../job/updatecron";
		var data = {
				"jobId" : job.jobId,
				"cronExpression" : job.cronExpression
		}
	
		$http({
   			method : "post",
   			url : url,
   			data : JSON.stringify(data)
   		}).then(function(data) {
   			console.log("success!");
   			$scope.loadData();
   		}).catch(function(data) {
   			console.log("error");
   		});
	};
	
//	$scope.joblist =[{"jobId":"1","jobName":"saptask_1","jobGroup":"masterdata","jobStatus":"1","cronExpression":"0 */1 * * * ?","className":"com.dusto.mobile.biz.scheduler.job.CustomScheduledJob","jobDesc":"主数据导入"}]
	$scope.loadData();

});

myApp.directive("editjob",function(){
	  return{
	    restrict: 'AE',
	    require: 'ngModel',
	    link: function(scope,element,attrs,ngModel){
	       element.bind("click",function(){
		       scope.$apply(function(){
		         angular.copy(ngModel.$modelValue,scope.master);
		         console.log(scope.master);
		         scope.showEdit = false;
		       });
	      });
	    }
	  }
	});

myApp.directive("jobupdate",function(){
	  return{
	    restrict: 'AE',
	    require: 'ngModel',
	    link: function(scope,element,attrs,ngModel){
	       element.bind("click",function(){
	    	   var job = ngModel.$modelValue;
	    	   console.log(job);
	    	   scope.jobupdate(job);
	      });
	    }
	  }
	});

	myApp.directive("jobcancel",function(){
	  return{
	    restrict: 'AE',
	    require: 'ngModel',
	    link: function(scope,element,attrs,ngModel){
	       element.bind("click",function(){
		       scope.$apply(function(){
		         angular.copy(scope.master,ngModel.$modelValue);
		         console.log(ngModel.$modelValue);
		         scope.showEdit = true;
		       });
	      });
	    }
	  }
	});
