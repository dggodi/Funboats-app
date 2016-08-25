'use strict'

funBoatsApp.directive("funboatsHeader", function(){
	return{
		restrict: 'E',
		controller: 'HeaderLoginController',
		templateUrl: '/header.html'
	}
});	

funBoatsApp.directive("funboatsNavbar", function(){
	return{
		restrict: 'E',
		templateUrl: '/navbar.html'
	};
});

funBoatsApp.directive("funboatsFooter", function(){
	return{
		restrict: 'E',
		scope:{},
		templateUrl: '/footer.html',
		link: function(scope){
			scope.copywrite = new Date().getFullYear();
		}
	}	
});
	