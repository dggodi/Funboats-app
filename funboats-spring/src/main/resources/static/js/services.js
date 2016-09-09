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

funBoatsApp.service("SessionService", function(){	
	this.create = function (data){
		if(data.data == null)
			this.invalidate();
		else{
			console.log("SessionService :: create  " + data.data.details.sessionId);
			this.id = data.data.details.sessionId;
			this.login = data.data.principal.username;
			this.userRoles = [];
			angular.forEach(data.data.authorities, function(value, key){
				this.push(value.authority);
			}, this.userRoles);
		}
	};
	this.invalidate = function(){
		this.id = null;
		this.login = null;
		this.userRoles = null;
	};
	this.getLogin = function(){
		return this.login;
	}
	return this;
});

funBoatsApp.factory("AuthenticateService", function($rootScope, $http, $resource, authService, SessionService){	
	return {
		login: function (username, password){
			console.log("AuthenticateService :: login  " + username + "  " + password);
			var config = {
				obj:{
					username: username,
					password: password, 
				},
				ignoreAuthModule: 'ignoreAuthModule'
			};
			
			$http.post('/authenticate/', '', config).then(function(response){
				console.log("AuthenticateService :: login succes " + response.status );
				authService.loginConfirmed(response);
			},
			
			function(response){
				$rootScope.authenticationError = true;
				console.log("AuthenticateService :: login fail " + response.status );
				SessionService.invalidate();
			});		
		},
		
		getAccount: function(){
			$rootScope.loadingAccount = true;
			$http.get('/security/account/').then(function(response){
				authService.loginConfirmed(response.data);	
			});
		},
		
		isAuthorized: function (authorizedRoles){
			if (!angular.isArray(authorizedRoles)){
				if(authroizedRoles == '*')
					return true
				authorizedRoles = [authorizedRols];
			}
			var isAuthorized = false;
			angular.forEach(authorizedRoles, function(authorizedRole){
				var authorized = (!!SessionService.login && SessionService.userRoles.indexOf(authorizedRole) !== -1);
				
				if (authorized || authorizedRole == '*'){
					isAuthorized = true;
				}
			});
			return isAuthorized;
		},
		
		logout: function(){
			$rootScope.authenticationError = false;
			$rootScope.authenticated = false;
			$rootScope.account = null;
			$http.post('/logout');
			SessionService.invalidate();
			authService.loginCancelled();
		}
	}
});

funBoatsApp.factory("RegistrationService", function($http, $location, $q, Message){
	return{
		register: function(obj){
			$http.post('/api/users/new/', obj)
			.then(function(response) {
				Message.setMessage("Registration Succesful", true);
				$location.path( "/login" );
			},
			function(response){
				Message.setMessage("Sorry...Registration Unsuccesful");
			});	
		},
	
		unique: function(username){
			var deferred = $q.defer();
			$http.post('/api/users/username', {username:username}).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		}
	}
});

funBoatsApp.factory("StorageService", function($q){
	var rec = [];
	
	return {
		store: function(obj){
			rec = obj;
		},
		
		retrieve: function(){
			var deferred = $q.defer();
		    deferred.resolve(rec);
		    return deferred.promise;
		},

		isEmpty: function(){
			return rec.length == 0;
		}
	}
});

funBoatsApp.factory("ItemService", function($http, $q, StorageService){
	return {
		view: function(){
			var deferred = $q.defer();
			$http.get('/api/jetskis/list/').then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		viewById: function(id){
			var deferred = $q.defer();	
			$http.post('/api/jetskis/list/id', {id: id}).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		viewByLocation: function(search){
			var deferred = $q.defer();	
			$http.post('/api/jetskis/location/search', {search: search}).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		save: function(data){
			var deferred = $q.defer();
			$http.post('/api/jetskis/add/', data).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		update: function(data){
			var deferred = $q.defer();
			$http.post('api/jetskis/update/', data).then(function(response){
				deferred.resolve(response.data);
			})
			return deferred.promise;
		},
		remove: function(id){
			var deferred = $q.defer();	
			$http.post('/api/jetskis/delete/', {id: id}).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		search: function(data){
			var deferred = $q.defer();	
			$http.post('/api/jetskis/search/like/', {obj: data}).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		searchByFilter: function(data){
			console.log("MainController :: searchByFilter  2  " + data.brand);
			var deferred = $q.defer();
			$http.post('/api/jetskis/search/filter/', data).then(function(response){
				deferred.resolve(response.data);
			});
			return deferred.promise;
		},
		getLocations: function(){
			var deferred = $q.defer();	
			$http.get('/api/locations/list/').then(function(response){
				console.log("ViewItemsController locations http ");
				deferred.resolve(response.data);
			});
			return deferred.promise;
		}
	}
});

funBoatsApp.factory("DropDownListService", function($http){	
	return {
		getColors: function(key, data){
			var colors = []
			for(var i = 0; i < data.length; i++)
				colors.push({"id":i, [key]:data[i]});
			return colors;
		},
		getYears: function(limit, key){
			var years = [];
			var year = limit;
			for(var i = 1; i <= new Date().getFullYear() - limit + 1; i++, year++)
				years.push({"id":i, [key]:year})
			
			return years;
		},
		getByFactor: function(limit, key, factor){
			var costs = [];
			for(var i = 1; i <= limit ; i++)
				costs.push({"id":i, [key]:+i*factor})
			
			return costs;
		},
		get: function(limit, value){
			var rec = [];
			for(var i = 0; i <= limit ; i++)
				rec.push({"id":i, [value]:i})
	
			return rec;
		},
		getSelectedValue: function(rec, searchKey, searchValue){		
			for(var i = 0; i < rec.length; i++){
				if(rec[i][searchKey] == searchValue)
					return rec[i];
			}		
		}
	}
});

funBoatsApp.service('ImageService', function($q) {
	return{
		convertToBytes: function(file){
			var deferral = $q.defer(); 
			var reader = new FileReader();
            reader.readAsDataURL(file);
            deferral.resolve(reader);
            return deferral.promise;
		}
	}
});

funBoatsApp.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
       var fd = new FormData();
       fd.append('file', file);
    
       $http.post(uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
       })
    
       .success(function(){
       })
    
       .error(function(){
       });
    }
 }]);
