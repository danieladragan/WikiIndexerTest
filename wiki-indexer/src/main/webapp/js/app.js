(function() {
	'use strict';

	angular.module('wikiIndexerApp', [
		'ngRoute',
		'wikiIndexerApp.services',
		'wikiIndexerApp.controllers',
		'wikiIndexerApp.directives'
	])
		.config(
			['$routeProvider', function ($routeProvider) {
				$routeProvider.when("/", {
					templateUrl: 'views/wordIndex.html',
					controller: 'IndexController'
				}).otherwise({redirectTo: '/'});
			}]
		).run(function ($rootScope) {
		$rootScope.initialized = true;
	});
})();

