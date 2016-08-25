'use strict'
//funBoatsApp.controller('MainController', function($scope, $routeParams, LoginService) {
funBoatsApp.controller('MainController', function($scope, $routeParams) {

	$scope.copywrite = new Date().getFullYear();
	
	$scope.loginEvent = function(event, mainHeaderLogin){
		//console.log("MainController");
		//LoginService.authenticate;
		/*var user = {
				name: $scope.main.username,
				password: {name: $scope.main.password}
			}
		LoginService.getByUserName(user)
		.then(function (user){
			console.log("Login ");
		});*/
	}
	
	var testMain = "";
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
	
	/*
	$http.get('api/users/home').then(function(response){
		$scope.greeting = response.data;
	});
	
	$http.get('api/users/list').then(function(response){
		$scope.users = response.data;
	});
	*/

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

/*
MainController.controller('CrudController', function($scope, $http, $routeParams, colorService) {
	
	$scope.getFilter = function(event){
		 $scope.mainbgstyle = colorService.getColor();
	}
	
});

MainController.service('colorService', function){
	this.getColor = function(){
		return "white";
	}
});
/*
// Default values for the request.
		  var config = {
		    params : {
		      'callback' : 'JSON_CALLBACK',
		      'brand' : $scope.brand,
		      'capacity' : $scope.capacity,
		      'year' : $scope.year,
		      'cost' : $scope.cost
		    },
		  };

		  // Perform JSONP request.
		  var $promise = $http.jsonp('response.json', config)
		    .success(function(data, status, headers, config) {
		      if (data.status == 'OK') {
		        $scope.brand = null;
		        $scope.capacity = null;
		        $scope.year = null;
		        $scope.cost = null;
		        $scope.messages = 'Your form has been sent!';
		        $scope.submitted = false;
		      } else {
		        $scope.messages = 'Oops, we received your request, but there was an error processing it.';
		        $log.error(data);
		      }
		    })
		    .error(function(data, status, headers, config) {
		      $scope.progress = data;
		      $scope.messages = 'There was a network error. Try again later.';
		      $log.error(data);
		    })
		    .finally(function() {
		      // Hide status messages after three seconds.
		      $timeout(function() {
		        $scope.messages = null;
		      }, 3000);
		    });
	};
	
*/


