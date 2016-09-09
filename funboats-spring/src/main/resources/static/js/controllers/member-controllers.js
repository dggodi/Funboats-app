'use struct'

/*
 * controller for member navbar
 * note: controls member links and views by location and search
 */
funBoatsApp.controller('MemberMainController', function($rootScope, $scope, $location, SessionService, StorageService, ItemService) {
	console.log("MemberMainController");
	$scope.$parent.mainbgstyle = 'white';
	
	$scope.event.user = SessionService.getLogin();
	
	/*
	 * gets all locations available for dropdown menu
	 */
	ItemService.getLocations().then(function(data){
		rec = [];
		angular.forEach(data, function(value, key){
			rec.push({ "name": value.name });
		});
		rec.push({ "name": "All" });
		$scope.event.locations = rec;
		$scope.currentLocation = "All";	
	});
	
	/*
	 * gets the jetskis stored by location
	 */
	$scope.viewByLocation = function(search){
		//return all jetskis if "All" is selected
		if(search === "All"){	
			ItemService.view().then(function(data){	
				$scope.storeAndRelocate(data);
			});
		}
		else{
			ItemService.viewByLocation(search).then(function(data){
				$scope.storeAndRelocate(data);
			});
		}
		$scope.currentLocation = search;
	}
	
	/*
	 * return a list of jetskis by search content
	 */
	$scope.viewBySearch = function(event){
		ItemService.search(event.searchContent).then(function(data){
			$scope.storeAndRelocate(data)
		});
		$scope.event.searchContent = "";
	}
	
	/*
	 * gets the stored jetskis from DB using StorageService
	 * note: listener is attached for redisplaying search results
	 */
	$scope.storeAndRelocate = function(data){
		StorageService.store(data);
			
		$rootScope.$broadcast("newSearch", "");
		$location.path('/view');
	}
})

/*
 * controller for displaying search results, also for editing, viewing and deleting
 */
funBoatsApp.controller('ViewItemsController', function($rootScope, $scope, $location, ItemService, SessionService, StorageService){
	$scope.$parent.mainbgstyle = 'white';
	$scope.items = [];
	$scope.session = SessionService.getLogin();	
	
	// return full list of stored record is empty
	if( StorageService.isEmpty() ){
		ItemService.view().then(function(data){
			$scope.items = data;
		});
	}
	else
		getList();
	
	/*
	 * listen for scope change 
	 * note: gets new searched list 
	 */
	$scope.$on('newSearch', function(e) { getList();});	

	/*
	 * gets the stored jetskis from DB using StorageService
	 */
	function getList(){
		StorageService.retrieve().then(function(data){
			$scope.items = data;
		});
	}

	/*
	 * click event called to view jetski
	 * note: dispatches an event for viewing jetskis
	 */
	$scope.viewItem = function(id){
	    ItemService.viewById(id).then(function(data){
	    	StorageService.store(data);
	    	$rootScope.$broadcast("searchForJetSki", "");
	    	$location.path('/viewJetSki');
	    });
	}

	/*
	 * click event called to edit jetski
	 * note: dispatches an event for editing jetskis
	 */
	$scope.editItem = function(id){
		ItemService.viewById(id).then(function(data){
			StorageService.store(data);
		    $rootScope.$broadcast("searchForJetSki", "");
		    $location.path('/edit');
		});
	}

	/*
	 * click event called to delete jetski
	 * note: dispatches an event for deleting jetskis
	 */
	$scope.deleteItem = function(id){
		ItemService.remove(id).then(function(data){
			$scope.items = ItemService.view().then(function(data){
				$scope.items = data;
			});
		});
	}
});

/*
 * controller for editing jetskis
 */
funBoatsApp.controller('AddItemController', function($rootScope, $scope,  $location, ItemService, SessionService, DropDownListService, StorageService){
	$scope.$parent.mainbgstyle = 'white';

	// construct drop downlists
	$scope.years 			= DropDownListService.getYears(2000, "year");
	$scope.costs 			= DropDownListService.getByFactor(20, "cost", 1000);
	$scope.colors			= DropDownListService.getColors("color", CSS_COLOR_NAMES);
	$scope.hours 			= DropDownListService.get(100, "hour");
	$scope.fuels 			= DropDownListService.get(50, "fuel");
	$scope.displacements 	= DropDownListService.get(50, "displacement");
	 
	$scope.brands = [           
	    { id: 1, brand: 'Honda' },
	    { id: 2, brand: 'Kawasaki' },
	    { id: 3, brand: 'Polaris' },
	    { id: 4, brand: 'Sea-Doo' },
	    { id: 5, brand: 'Tigershark' },
	    { id: 6, brand: 'Yamaha' }
	];
	
	 $scope.locations = [
	     { id: 1, location: 'Bay City' },
	     { id: 2, location: 'Midland' },
	     { id: 3, location: 'Saginaw' }
	 ];
	 
	 $scope.categories = [
         { id: 1, category: 'Recreation' },
         { id: 2, category: 'High Performance' },
         { id: 3, category: 'Luxory' },
         { id: 4, category: 'Sport' }
     ];
	 
	 $scope.seatings = [
	     { id: 1, seating: 'Stand up' },
	     { id: 2, seating: 'Seating Capcaity 1' },
	     { id: 3, seating: 'Seating Capcaity 2' },
	     { id: 4, seating: 'Seating Capcaity 3' }
	 ];
	 
	 // insert records from stored list into select box
	 $scope.items =[];
	 $scope.event = [];
	 $scope.event.itemOfferingId = 0;
	 if( !StorageService.isEmpty() ){
		 StorageService.retrieve().then(function(data){
			$scope.event = data;
			$scope.event.brand 				= DropDownListService.getSelectedValue($scope.brands, 		"brand", 	data.brand);
			$scope.event.year 				= DropDownListService.getSelectedValue($scope.years, 		"year", 	data.year);
			$scope.event.cost 				= DropDownListService.getSelectedValue($scope.costs, 		"cost", 	data.cost);
			$scope.event.location 			= DropDownListService.getSelectedValue($scope.locations, 	"location", data.location);
			$scope.event.item.color 		= DropDownListService.getSelectedValue($scope.colors, 		"color", 	data.item.color);
			$scope.event.item.category 		= DropDownListService.getSelectedValue($scope.categories, 	"category", data.item.category);
			$scope.event.item.seating 		= DropDownListService.getSelectedValue($scope.seatings, 	"seating", 	data.item.seating);
			$scope.event.item.hour 			= DropDownListService.getSelectedValue($scope.hours, 		"hour", 	data.item.hours);
			$scope.event.item.fuel 			= DropDownListService.getSelectedValue($scope.fuels, 		"fuel", 	data.item.fuelcapacity);
			$scope.event.item.displacement 	= DropDownListService.getSelectedValue($scope.displacements, "displacement", data.item.displacement);	
		 });
	 }
	
	/*
	 * click event to save new jetski
	 */ 
	$scope.addNewItem = function(){		
		var rec = createJson($scope.items);
		ItemService.save(rec).then(function(data){	
			$scope.success(rec);
		});
	}
	
	/*
	 * click event to edit jetski
	 */
	$scope.editItem = function(){	
		var rec = createJson($scope.items);
		ItemService.update(rec).then(function(data){
			$scope.success(rec);
		});		
	}
	
	/*
	 * click event to clear form
	 */
	$scope.cancel = function(response){
		cancel();
	}
	
	/*
	 * store new item to view 
	 */
	$scope.success = function(data){
		StorageService.store([data]);
		$location.path('/view');
	}
	
	/*
	 * clear form
	 */
	function cancel(){
		$scope.event = {};
	}
	
	/*
	 * create json object
	 */
	function createJson(data){
		return {
			itemOfferingId: $scope.event.itemOfferingId,
			brand: $scope.event.brand.brand,
			model: $scope.event.model,
			year: $scope.event.year.year,
			cost: $scope.event.cost.cost,
			descript: $scope.event.descript,
			location: $scope.event.location.location,
			username: SessionService.getLogin(),
			item:{
				hours: $scope.event.item.hours,
				displacement: $scope.event.item.displacement.displacement,
				color: $scope.event.item.color.color,
				category: $scope.event.item.category.category,
				seating: $scope.event.item.seating.seating,
				vinNo: $scope.event.item.vinNo,
				engine: $scope.event.item.engine,
				pumpType: $scope.event.item.pumpType,
				fuelcapacity: $scope.event.item.fuel.fuel
			}
		}
	}
});






                           