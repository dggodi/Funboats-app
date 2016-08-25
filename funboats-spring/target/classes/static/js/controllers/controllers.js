'use strict'
//funBoatsApp.controller('MainController', function($scope, $routeParams, LoginService) {
funBoatsApp.controller('MainController', function($scope, $routeParams) {

	$scope.copywrite = new Date().getFullYear();
	
});



funBoatsApp.controller('IndexController', function($scope, $http, $routeParams) {

	$scope.$parent.mainbgstyle = 'rgba(0,0,0,0.0)';
	
	$scope.searchEvent = function(event, searchEventForm){
		console.log('event ' + event.brand + 'saved');
	};
	
	$scope.searchEvent = function(event, searchOneEventForm){
		if(searchOneEventForm.$valid){
			console.log('event ' + event.searchContent + ' saved');
		}
	};
	
	
});



funBoatsApp.controller('CrudController', function($scope, $http, $routeParams) {
	/*
	$scope.$parent.mainbgstyle = 'white';
	console.log("child");
	
	$http.get('api/jetskis/list').then(function(response){
		$scope.item = response
	});
	*/
});





