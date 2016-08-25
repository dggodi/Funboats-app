'use strict'; 

var funBoatsApp = angular.module('funBoatsApp', ['ngResource', 'ngRoute', 'ngSanitize']);

funBoatsApp.config(function($routeProvider, $locationProvider){
		$routeProvider.when('/', {
			templateUrl: '/fragments/public/Home.html',
			controller: 'IndexController'
		});
		$routeProvider.when('/viewJetSki', {
			templateUrl: '/fragments/public/ViewJetSki.html',
			controller: 'CrudController'
		});
		$routeProvider.when('/aboutContent', {
			templateUrl: '/fragments/public/AboutContent.html',
			controller: 'CrudController'
		});
		$routeProvider.when('/findJetSki', {
			templateUrl: '/fragments/public/FindJetSki.html',
			controller: 'CrudController'
		});
		$routeProvider.when('/signUp', {
			templateUrl: '/fragments/public/SignUp.html',
			controller: 'UserController'
		});
		$routeProvider.when('/login', {
			templateUrl: '/fragments/public/Login.html',
			controller: 'LoginController'
		});
		$routeProvider.when('/welcome', {
			templateUrl: '/fragments/members/Welcome.html',
			controller: 'MemberMainController'
		});
		$routeProvider.otherwise({
			redirectTo: '/'
		});
		$locationProvider.html5Mode(true);
	});
