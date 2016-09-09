'use strict'

funBoatsApp.directive("funboatsHeader", function(){
	return{
		restrict: 'E',
		transclude: true,
		controller: 'LoginController',
		templateUrl: '/header.html'
	}
});	

funBoatsApp.directive("funboatsNavbar", function(){
	return{
		restrict: 'E',
		transclude: true,
		templateUrl: '/navbar.html'
	};
});

funBoatsApp.directive("funboatsFooter", function(){
	return{
		restrict: 'E',
		transclude: true,
		scope:{},
		templateUrl: '/footer.html',
		link: function(scope){
			scope.copywrite = new Date().getFullYear();
		}
	}	
});

funBoatsApp.directive("funboatsMember", function(){
	return{
		restrict: 'E',
		transclude: true,
		templateUrl: '/member-navbar.html'
	}	
});


funBoatsApp.directive("accessMessage", ['AuthenticateService', 
    function(AuthenticateService){
		return{
			restrict: 'A',
			link: function(scope, element, attrs){
				var roles = attrs.access.split(',');
				if(roles.length > 0){
					if(AuthenticateService.isAuthorized(roles))
						element.removeClass('hide');
					else
						element.addClass('hide');
				}
			}
		}
	}	
]);

funBoatsApp.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);


