'use strict'; 

var funBoatsApp = angular.module('funBoatsApp', ['ngResource', 'ngRoute', 'ngMessages', 'ngSanitize', 'ngAnimate', 'http-auth-interceptor']);

/*
 * roles allowed to use app
 */
funBoatsApp.constant('USER_ROLES',{
	all: '*',
	user: 'ROLE_USER'
});

/*
 * configures routes and their controllers along with user and public access rights
 */
funBoatsApp.config(function($routeProvider, $locationProvider, $httpProvider, USER_ROLES){
		$locationProvider.html5Mode(true);
	
		$routeProvider
		.when('/', {
			templateUrl: '/fragments/public/Home.html',
			controller: 'MainController'
		})
		.when('/viewJetSki', {
			templateUrl: '/fragments/public/ViewJetSki.html',
			controller: 'ItemViewController',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/aboutContent', {
			templateUrl: '/fragments/public/AboutContent.html',
			controller: '',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/signUp', {
			templateUrl: '/fragments/public/SignUp.html',
			controller: 'RegistrationController',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/login', {
			templateUrl: '/fragments/public/Login.html',
			controller: 'LoginController',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/forgot', {
			templateUrl: '/fragments/public/Forgot.html',
			controller: 'LoginController',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/loading',{
			template: '/fragments/public/Loading.html',
			controller: 'LogoutController',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/logout',{
			template: '/fragments/public/Login.html',
			controller: 'LogoutController',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.when('/welcome', {
			templateUrl: '/fragments/members/Welcome.html',
			controller: 'MemberMainController',
			access:{
				loginRequired: true,
				authorizedRoles: [USER_ROLES.user]
			}
		})
		.when('/view', {
			templateUrl: '/fragments/members/View.html',
			controller: 'ViewItemsController',
			access:{
				loginRequired: true,
				authorizedRoles: [USER_ROLES.user]
			}
		})
		.when('/add', {
			templateUrl: '/fragments/members/Add.html',
			controller: 'AddItemController',
			access:{
				loginRequired: true,
				authorizedRoles: [USER_ROLES.user]
			}
		})
		.when('/edit', {
			templateUrl: '/fragments/members/Edit.html',
			controller: 'AddItemController',
			access:{
				loginRequired: true,
				authorizedRoles: [USER_ROLES.user]
			}
		})
		.when('/error/:code',{
			templateUrl: '/fragments/public/Error.html',
			controller: "ErrorController",
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		})
		.otherwise({
			redirectTo: '/fragments/error/404',
			access:{
				loginRequired: false,
				authorizedRoles: [USER_ROLES.all]
			}
		});
		
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});



funBoatsApp.run(function ($rootScope, $location, $http, AuthenticateService, SessionService, USER_ROLES, $q, $timeout){

	/*
	 *  listens for a route that is about to be changed and handles it
	 *  
	 *  note:	if user is logged in do nothing
	 *   		else if user is not logged in then broadcast to other pages  
	 *  		else : forbid all other navigations 
	 */     
	$rootScope.$on('$routeChangeStart', function(event, next){
		if (next.originalPath === "/login" && $rootScope.authenticated){
			event.preventDefault();
		}
		else if(next.access && next.access.loginRequired && !$rootScope.authenticated) {
			event.preventDefault();
			$rootScope.$broadcast("event:auth-forbidden", {});
		}
		else if(next.access && !AuthenticateService.isAuthorized(next.access.authorizedRoles)) {
			event.preventDefault();
			$rootScope.$broadcast("event:auth-forbidden", {});
		}
	});
	
	/*
	 *  Executes init immediately when the route has successfully changed within the current view
	 */ 
	$rootScope.$on('$routeChangeSuccess', function(scope, next, current){
		$rootScope.$evalAsync(function(){
			$.material.init();
		});
	});
	
	
	    
	/*
	 *  Executes after login is confirmed and then creates a login session
	 *  
	 *  note: redirects to /welcome
	 */ 
	$rootScope.$on('event:auth-loginConfirmed', function(event, data){
		$rootScope.loadingAccount = false;
		var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestUrl : "/welcome");
		var delay = ($location.path() === "/loading" ? 1500 : 0);
	
		$timeout(function (){
			SessionService.create(data);
			$rootScope.account = SessionService;
			$rootScope.authenticated = true;
			$location.path(nextLocation).replace();
		}, delay);
	});
	
	/*
	 *  Executes when 401 is returned by the server
	 */ 
	$rootScope.$on('event:auth-loginRequired', function(event, data){
		if ($rootScope.loadingAccount && data.status != 401){
			$rootScope.requestedUrl = $location.path();
			$location.path('/loading');
		} else {
			SessionService.invalidate();
			$rootScope.authenticated = false;
			$rootScope.loadingAccount = false;
			$location.path('/login');
		}
	});
	
	/*
	 *  Executes when 403 is returned by the server
	 *  
	 *  note: redirects to error page
	 */ 
	$rootScope.$on('event:auth-forbidden', function(rejection){
		$rootScope.$evalAsync(function(){
			$rootScope.authenticated = false;
			$location.path('/login').replace();
		});
		
	});
	
	/***
	 *  note: redirects to login page
	 */ 
	$rootScope.$on('event:auth-loginCancelled', function(){
		$location.path('/login').replace();
	});
	
	AuthenticateService.getAccount();
});
		

