'use struct'

/*
 * controller for login
 */
funBoatsApp.controller('LoginController', function($rootScope, $scope, Message, AuthenticateService) {
	$scope.event = {}

	// retrieves stored message
	$scope.message = Message.getMessage().mes;
	
	/*
	 * login function
	 */
	$scope.loginEvent = function(event, loginForm){
		$rootScope.authenticationError = false;
		
		AuthenticateService.login(
			$scope.event.username,
			$scope.event.password
		);
	}

	$scope.forgotEvent = function(){
		console.log("forgot password");
	}
});

/*
 * controller for logout
 */
funBoatsApp.controller('LogoutController', function($rootScope, AuthenticateService, Message) {
	Message.setMessage(" . . . Logout Successful", 200);
	AuthenticateService.logout();
});

/*
 * controller for error functions
 */
funBoatsApp.controller('ErrorController', function($scope, $routeParams) {
	$scope.$parent.mainbgstyle = 'white';
	$scope.code = $routeParams;
	
	switch($scope.code){
		case "403" :
			$scope.message = "Unauthorized access";
			break;
		case "404" :
			$scope.message = "Page not found";
		default:
			$scope.code = 500;
			$scope.message = "Unexpected Error"	
	}
});



