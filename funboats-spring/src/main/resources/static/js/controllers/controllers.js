/*
 * controller for index page 
 */
funBoatsApp.controller('MainController', function($rootScope, $scope, $location, ItemService, StorageService) {
	
	// sets copyright year
	$scope.copywrite = new Date().getFullYear();
	
	$scope.$parent.mainbgstyle = 'rgba(0,0,0,0.0)';

	// json object for Brand select box
	$scope.brands = [           
	    { id: 1, brand: 'Honda' },
	    { id: 2, brand: 'Kawasaki' },
	    { id: 3, brand: 'Polaris' },
	    { id: 4, brand: 'Sea-Doo' },
	    { id: 5, brand: 'Tigershark' },
	    { id: 6, brand: 'Yamaha' }
	];
	
	// json object for Category select box
	$scope.categories = [
	    { id: 1, category: 'Recreation' },
	    { id: 2, category: 'High Performance' },
	    { id: 3, category: 'Luxory' },
	    { id: 4, category: 'Sport' }
	];
	
	$scope.search = {};
	$scope.search.year = 2000;	
	var year = new Date().getFullYear();
	
	/*
	 * calculates and display the years in the range slider
	 */
	$scope.rangeYear = function (event) {
		$scope.search.year = (event.year > year) ? year : $scope.search.year
	};

	/*
	 * calculates and display the cost in the range slider
	 */
	$scope.search.cost = 1000;	
	$scope.rangeCost = function (event) {
		$scope.search.cost = Math.floor(event.cost / 1000) *1000; 
	};
	
	/*
	 * search for contents and store the results
	 * note: listener is attached to view data on the relocated page viewJetSki
	 */
	$scope.searchEvent = function(event){
		ItemService.search(event.searchContent).then(function(data){
			StorageService.store(data)
			$rootScope.$broadcast("searchForJetSki", "");
			$location.path('/viewJetSki');
		});
	}
	
	/*
	 * search for contents and store the results
	 * note: listener is attached to view data on the relocated page viewJetSki
	 */
	$scope.searchFilterEvent = function(){
		console.log("MainController :: search  0");
		var obj = {
			brand: $scope.event.brand.brand,
			year: $scope.event.year,
			cost: $scope.event.cost,
			"item":{
				category: $scope.event.category.category
			}
		}
		
		console.log("MainController :: search  1  " + obj.category);
		
		ItemService.searchByFilter(obj).then(function(data){
			StorageService.store(data)
			$rootScope.$broadcast("searchForJetSki", "");
			$location.path('/viewJetSki');
		});
	}
});

/*
 * controller for viewing jetski
 */
funBoatsApp.controller('ItemViewController', function($scope, $window, ItemService, StorageService) {
	$scope.$parent.mainbgstyle = "white";
	
	$scope.items = [];
	
	// if there is no record stored then retrieve all jetskis
	if( StorageService.isEmpty() ){
		ItemService.view().then(function(data){
			$scope.items = data;
		});
	}
	else{
		StorageService.retrieve().then(function(data){
			$scope.items.push(data);
		});
	}
	
	/*
	 * listener for viewing searched jetskis
	 * note: calls getList() to retrieve stared data
	 */
	$scope.$on('searchForJetSki', function(e) { $scope.getList();});
	
	$scope.getList = function(){
		StorageService.retrieve().then(function(data){
			$scope.items = data;
		});
	}
	
	/*
	 * goes back a page
	 */
	$scope.event.back = function(){
		$window.history.back();
	}

});	
	