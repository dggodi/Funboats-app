'use struct'

funBoatsApp.controller('LoginController', function($scope, $http, $location, Message) {

	var statusMessage = Message.getMessage();
	$scope.message = statusMessage.mes;
		
	$scope.loginEvent = function(event, loginForm){ 
		console.log("LoginService");
		//LoginService.authenticate();
	}
});

funBoatsApp.controller('HeaderLoginController', function($scope, $http, $location, LoginService){
	console.log("hu");
	$scope.loginEvent = function(){
			
		var obj = {
				userName: $scope.main.username,
				password: $scope.main.password
			}
		
		console.log($scope.main.username);
		LoginService.test(obj);
	}
});

