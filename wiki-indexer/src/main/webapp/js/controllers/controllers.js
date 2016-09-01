(function () {
    'use strict';

    /* Controllers */

    var controllers = angular.module('wikiIndexerApp.controllers', ['zingchart-angularjs']);

    controllers.controller('IndexController', ['$scope', 'WordIndexFactory', 'FileUploadFactory', 'SearchWordFactory', function ($scope, WordIndexFactory, FileUploadFactory, SearchWordFactory) {
        $scope.searchArticle = function (title) {
            if (title.length != 0) {
                $scope.showWord = false;
                var labels = [];
                var dataSeries = [];
                $scope.response = WordIndexFactory.query({title: title});
                $scope.response.$promise.then(function (data) {
                    $scope.titles = data.titles.join(",");
                    for (var key in data.wordsList) {
                        labels.push(data.wordsList[key].word);
                        dataSeries.push(data.wordsList[key].occurrences);
                    }
                    $scope.listSize = dataSeries.length;
                    if( $scope.listSize > 0){
                        $scope.showContent = true;
                        $scope.showError = false;
                    }else{
                        $scope.showContent = false;
                        $scope.showError = true;
                    }
                });
            }

            $scope.topWordsChartJson = {
                type: "bar",
                "title": {
                    "text": "Top 10 Words"
                },
                backgroundColor: "white",
                "scale-x": {
                    "values": labels,
                    "itemsOverlap": 1
                },
                series: [
                    {
                        values: dataSeries,
                        backgroundColor: "#6bbf19"
                    }
                ]
            };
        };

        //FileUpload

        $scope.uploadFile = function () {
            var file = $scope.myFile;
            if (file.length != 0) {
                var uploadUrl = "wiki-indexer/file", //Url of web service
                    promise = FileUploadFactory.uploadFileToUrl(file, uploadUrl);
                promise.then(function (response) {
                    $scope.response = response;
                    var labels = [];
                    var dataSeries = [];
                    $scope.titles = response.titles.join(",");
                    for (var key in response.wordsList) {
                        labels.push(response.wordsList[key].word);
                        dataSeries.push(response.wordsList[key].occurrences);
                    }

                    $scope.topWordsChartJson = {
                        type: "bar",
                        backgroundColor: "white",
                        "scale-x": {
                            "values": labels
                        },
                        series: [
                            {
                                values: dataSeries,
                                backgroundColor: "#6bbf19"
                            }
                        ]
                    };
                }, function () {
                    $scope.serverResponse = 'An error has occurred';
                });
                $scope.showContent = true;
            }
        };

        //Search word
        $scope.searchWord = function (title, word) {
            if (title.length != 0 && word.length != 0) {
                $scope.response = SearchWordFactory.query({title: title, word: word});
                $scope.response.$promise.then(function (data) {
                    $scope.word = data.wordDTO.word;
                    $scope.occurrences = data.wordDTO.occurrences;
                    $scope.showWord = true;
                });
            }
        };

    }]);

})();

