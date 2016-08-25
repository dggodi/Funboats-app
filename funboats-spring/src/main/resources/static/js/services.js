'use strict'

funBoatsApp.factory("Message", function($rootScope){
	var currentMessage = {};
	
	initService();
	
	return {
		setMessage: function(mes, status){
			currentMessage = {
				mes: mes,
				status: status,
				locationChange: status
			} 
		},
		
		getMessage: function(){
			return currentMessage;
		}
	}
	
	function initService(){
		$rootScope.$on('$locationChangeStart', function(){
			return clearMessage();
		});
		
		function clearMessage(){
			console.log("currentMessage.status =  " + currentMessage.status);
			if(currentMessage.status){
				if(!currentMessage.locationChange)
					currentMessage = {};
				else
					currentMessage.locationChange = false;
			}
		}
	}
});


funBoatsApp.factory("LoginService", function($http, $location, Message){
	
	return{
		login: function(obj){
			authenticate: authenticate(obj)
		}
	}

	function authenticate(obj){
		
		$http.post('/api/users/authenticate/', obj)
		.then(function(response) {
			if(response.status == "200")
				$location.path( "/welcome" );
			else
				$location.path( "/login" );
			},
			function(response){
				Message.setMessage("Sorry...Login Unsuccesful");
				$location.path( "/login" );
			});
	}
});


