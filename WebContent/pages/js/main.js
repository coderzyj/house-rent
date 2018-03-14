var myApp = angular.module('myapp', ['ngRoute', 'ngAnimate']);
myApp.config(function($routeProvider) {
    $routeProvider
        // home page
        .when('/', {
            templateUrl: 'html/page-user.html',
            controller: 'UserController'
        })

        // evn page
        .when('/job', {
            templateUrl: 'html/page-job.html',
            controller: 'JobController'
        })

        // download page
        .when('/dl', {
            templateUrl: 'html/page-download.html',
            controller: 'DownloadController'
        })
        
        // table page
        .when('/tb', {
            templateUrl: 'html/page-table.html',
            controller: 'MainCtrl'
        })
        .otherwise({
            redirectTo : '/'
        });

});

myApp.controller("MenuController",  ["$scope", "$location", function ($scope, $location) {
	$scope.classId="1";
	$scope.goToHomePage = function () {
		 $scope.classId="1";
	  $location.path("/")
	 };
	$scope.goToImportData = function () {
		 $scope.classId="2";
	  $location.path("/job")
	 };
	 
	 $scope.goToDownloadPage = function () {
		 $scope.classId="3";
		  $location.path("/dl")
		 }
	}]);

myApp.controller("DownloadController",  ["$scope", "$location", function ($scope, $location) {
	 
	}]);
