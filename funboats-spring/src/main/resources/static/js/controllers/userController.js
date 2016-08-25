'use strict'

funBoatsApp.controller('UserController', function($scope, $http, $location, Message) {

	$scope.$parent.mainbgstyle = 'rgba(0,0,0,0.0)';
	
	$scope.checkEmail = function ($event) {
		var re =  /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
		if(re.test($event.target.value))
			$scope.emailstyle = "correctInput"
		else
			$scope.emailstyle = "wrongInput"  
	};
	
	$scope.checkFirstName = function ($event) {
		var re = /^[A-Za-z]{3,20}$/;
		if(re.test($event.target.value))
			$scope.firstNameStyle = "correctInput"
		else
			$scope.firstNameStyle = "wrongInput"
	};
	
	$scope.checkLastName = function ($event) {
		var re = /^[A-Za-z]{3,20}$/;
		if(re.test($event.target.value))
			$scope.lastNameStyle = "correctInput"
		else
			$scope.lastNameStyle = "wrongInput"
	};
	
	$scope.checkPhoneNumber = function($event){
		var re = /^([(][0-9]{3}[)]){0,1}[0-9]{3}[-\.][0-9]{4}|(\s*)?$/;
		if(re.test($event.target.value))
			$scope.phoneNumberStyle = "correctInput"
		else
			$scope.phoneNumberStyle = "wrongInput"	
	}
	
	$scope.checkMobileNumber = function($event){
		var re = /^([(][0-9]{3}[)]){0,1}[0-9]{3}[-\.][0-9]{4}|(\s*)?$/;
		if(re.test($event.target.value) || $event.target.value == "")
			$scope.mobileNumberStyle = "correctInput"
		else
			$scope.mobileNumberStyle = "wrongInput"	
	}
	
	$scope.checkPassword = function ($event) {
		
		var pass1 = null;
		var pass2 = null;
		
		if($event.target.id == "password")
		{
			var pass1 = $scope.event.passwordVerify;
			var pass2 = $event.target.value;
		}
		else
		{
			var pass1 = $scope.event.password;
			var pass2 = $event.target.value;
		}
		
		if(pass1 == pass2 )
		{
			setPassFieldBackground( checkPasswordStrength(pass1) );
			$scope.passwordstyle = "correctInput";
		} 
		else
			$scope.passwordstyle = "wrongInput";
	}
	
	function checkPasswordStrength(password)
	{
		var strongRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%\^&\*]).{8,}$/;
		var mediumRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
	    
	 	if( strongRegex.test(password) )
			return 0;
		else if ( mediumRegex.test(password) )
			return 1;
		else 
			return 2;
	}

	function setPassFieldBackground(index)
	{
		switch (index)
		{
			case 0:  $scope.passwordMeter = "password-strong";
					 $('#passwordType').text('Strong');
					 break;
			case 1:  $scope.passwordMeter = "password-medium";
					 $('#passwordType').text('Medium');
					 break;
			case 2:  $scope.passwordMeter = "password-weak";
					 $('#passwordType').text('Weak');
					 break;
		}
	}
	
	$scope.addUserEvent = function(response){
		var obj = {
			userName: $scope.event.userName,
			password: $scope.event.password,
			role: "",
			profile: {
				firstName: $scope.event.firstName,
				lastName: $scope.event.lastName,
				phoneNumber: $scope.event.phoneNumber,
				mobileNumber: $scope.event.mobileNumber
			}
		}
		
		$http.put('/api/users/new/').then
		.then(function(response) {
			Message.setMessage("Registration Succesful", true);
			reset();
			$location.path( "/login" );
		},
		function(response){
			Message.setMessage("Sorry...Registration Unsuccesful");
		});	
	};

	function reset(){
		$scope.event.email = "";
		$scope.event.password = "";
		$scope.event.firstname = "";
		$scope.event.lastName = "";
		$scope.event.phoneNumber = "";
		$scope.event.mobileNumber = "";
	}
});


