angular.module("confsays.services", ["ngResource"]).
    factory('Conference', function ($resource) {
        var Conference = $resource('/resources/conferences/:conferenceId', {conferenceId: '@id'});
        console.log("Confernce: "+Conference);
        Conference.prototype.isNew = function(){
            return (typeof(this.id) === 'undefined');
        };
        return Conference;
    });

angular.module("confsays", ["confsays.services"]).
    config(function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: 'views/conferences/list.html', controller: ConferenceListController})
            .when('/conferences/new', {templateUrl: 'views/conferences/create.html', controller: ConferenceCreateController})
            .when('/conferences/:conferenceId', {templateUrl: 'views/conferences/detail.html', controller: ConferenceDetailController})
        	.when('/conferences/edit/:conferenceId', {templateUrl: 'views/conferences/create.html', controller: ConferenceDetailController});
    });

function ConferenceListController($scope, Conference) {
    $scope.conferences = Conference.query();
    
}

function ConferenceCreateController($scope, $routeParams, $location, Conference) {

    $scope.conference = new Conference();

    $scope.save = function () {
    	console.log($scope.conference);
    	$scope.conference.hashtags = $scope.conference.hashtags.split(",");
    	$scope.conference.speakers = $scope.conference.speakers.split(",");
    	$scope.conference.$save(function (conference, headers) {
    		toastr.success("Created New Conference");
            $location.path('/');
        });
    };
}


function ConferenceDetailController($scope, $routeParams, $location, Conference) {
    var conferenceId = $routeParams.conferenceId;
    
    $scope.conference = Conference.get({conferenceId: conferenceId});
    
    $scope.save = function () {
    	console.log($scope.conference);
    	if(typeof $scope.conference.hashtags ==  "string"){
    		var hashtags = $scope.conference.hashtags;
    		$scope.conference.hashtags = hashtags.split(",");;
    	}
    	if(typeof $scope.conference.speakers == "string"){
    		var speakers = $scope.conference.speakers;
    		$scope.conference.speakers = speakers.split(",");
    	}
    	
    	
    	$scope.conference.$save(function (conference, headers) {
    		toastr.success("Updated New conferences");
            $location.path('/');
        });
    };
}
