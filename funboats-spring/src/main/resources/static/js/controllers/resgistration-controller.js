'use strict'

/*
 * controller for new user
 */
funBoatsApp.controller('RegistrationController', function($scope, RegistrationService) {
	var valid = false;
	$scope.unique = true;
	$scope.$parent.mainbgstyle = 'white';

	/*
	 * click event to verify phone number
	 */
	$scope.checkPhoneNumber = function($event){
		var re = /^([(][0-9]{3}[)]){0,1}[0-9]{3}[-\.][0-9]{4}|(\s*)?$/;
		if(re.test($event.target.value))
			$scope.phoneNumberStyle = "correctInput"
		else
			$scope.phoneNumberStyle = "wrongInput"	
	}
	
	/*
	 * click event to verify mobile number
	 */
	$scope.checkMobileNumber = function($event){
		var re = /^([(][0-9]{3}[)]){0,1}[0-9]{3}[-\.][0-9]{4}|(\s*)?$/;
		if(re.test($event.target.value) || $event.target.value == "")
			$scope.mobileNumberStyle = "correctInput"
		else
			$scope.mobileNumberStyle = "wrongInput"	
	}
	
	/*
	 * click event to verify unique user name
	 * note: calls RegistrationService to search DB
	 */
	$scope.checkUsername = function ($event){
		var obj = {username: $scope.event.username};
		$scope.unique = RegistrationService.unique(obj).then(function(valid){
			$scope.unique = valid;
		});
		$scope.unique = true;
	}
	
	/*
	 * checks for valid password
	 */
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
			valid = true;
		} 
		else{
			$scope.passwordstyle = "wrongInput";
			valid = false;
		}
			
	}
	
	/*
	 * checks for password strength
	 */
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

	/*
	 * calculates the meter representing password strength
	 */
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
	
	/*
	 * click event to add new user
	 * note: calls RegistrationService to add new user
	 */
	$scope.addNewUser = function(){
		
		if(valid){
		
			var obj = {
				username: $scope.event.username,
				password: $scope.event.password,
				enabled: true,
				authority: {role: "ROLE_USER"},
				profile: {
					firstName: $scope.event.firstName,
					lastName: $scope.event.lastName,
					email: $scope.event.email,
					phoneNumber: $scope.event.phoneNumber,
					mobileNumber: $scope.event.mobileNumber
				}
			}
		
			$scope.event = {};
			RegistrationService.register(obj);
		}
	};
	
	$scope.cancel = function(response){
		$scope.event = {};
	}

});


