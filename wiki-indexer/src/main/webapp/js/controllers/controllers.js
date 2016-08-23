(function () {
    'use strict';

    /* Controllers */

    var controllers = angular.module('wikiIndexerApp.controllers', [ 'zingchart-angularjs']);

    controllers.controller('IndexController', ['$scope', 'WordIndexFactory', 'FileUploadFactory', function ($scope, WordIndexFactory, FileUploadFactory) {
        $scope.searchArticle = function (title) {
            var labels =[];
            var dataSeries = [];
            $scope.response = WordIndexFactory.query({title: title});
            $scope.response.$promise.then(function(data) {
                $scope.titles = data.titles.join(",");
                for (var key in data.wordsList) {
                    labels.push(data.wordsList[key].word);
                    dataSeries.push(data.wordsList[key].occurrences);
                }
            });
            
            $scope.topWordsChartJson = {
                type : "bar",
                "title": {
                    "text":"Top 10 Words"
                },
                backgroundColor : "white",
                "scale-x":{
                    "values":labels,
                    "itemsOverlap": 1
                },
                series : [
                    {
                        values : dataSeries,
                        backgroundColor : "#6bbf19"
                    }
                ]
            };
            $scope.showContent = true;
        };
        
        //FileUpload

        $scope.uploadFile = function () {
            var file = $scope.myFile;
            var uploadUrl = "wiki-indexer/file", //Url of web service
                promise = FileUploadFactory.uploadFileToUrl(file, uploadUrl);
            promise.then(function (response) {
                $scope.response = response;
                var labels =[];
                var dataSeries = [];
                $scope.titles = response.titles.join(",");
                for (var key in response.wordsList) {
                    labels.push(response.wordsList[key].word);
                    dataSeries.push(response.wordsList[key].occurrences);
                }

                $scope.topWordsChartJson = {
                    type : "bar",
                    backgroundColor : "white",
                    "scale-x":{
                        "values":labels
                    },
                    series : [
                        {
                            values : dataSeries,
                            backgroundColor : "#6bbf19"
                        }
                    ]
                };
            }, function () {
                $scope.serverResponse = 'An error has occurred';
            });
            $scope.showContent = true;
        };

    }]);

})();

