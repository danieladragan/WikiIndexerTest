function validate(string) {
    if (string.length == 0) {
        return false;
    }

    return true;
}

(function () {
    'use strict';

    /* Controllers */

    var controllers = angular.module('wikiIndexerApp.controllers', [ 'zingchart-angularjs']);

    controllers.controller('IndexController', ['$rootScope', '$scope', 'WordIndexFactory', 'FileUploadFactory', 'SearchWordFactory',
        function ($rootScope, $scope, WordIndexFactory, FileUploadFactory, SearchWordFactory) {

        $scope.searchArticle = function (title) {
            $rootScope.showContentBar = false;
            $rootScope.showContentPie = false;
            if (title.length != 0) {
                $scope.showWord = false;
                var labels = [];
                var dataSeries = [];
                $scope.response = WordIndexFactory.query({title: title});
                $scope.response.$promise.then(function (data) {
                    $scope.searchDate = data.date;
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

            console.log($scope.topWordsChartJson);
            $rootScope.showContentWord = true;
        };

        //FileUpload

        $scope.uploadFile = function () {
            $rootScope.showContentWord = false;

            var file = $scope.myFile;
            if (file.length != 0) {
                $scope.showWord = false;
                var uploadUrl = "wiki-indexer/file", //Url of web service
                    promise = FileUploadFactory.uploadFileToUrl(file, uploadUrl);
                promise.then(function (response) {
                    $scope.response = response;

                    var aux = {"values": [], text: ''};
                    $scope.seriesAux = [];

                    $scope.labels = [];
                    $scope.dataSeries = [];

                    $scope.dataSeriesAux = [];
                    $scope.labelsAux = [];

                    $scope.titleBar = "Top 10 Words";
                    $scope.titlePie = "Top Articles";

                    $scope.titles = response.titles.join(",");
                    for (var key in response.wordsList) {
                        $scope.labels.push(response.wordsList[key].word);
                        $scope.dataSeries.push(response.wordsList[key].occurrences);
                        $scope.labelsAux.push(response.wordsList[key].word);
                        $scope.dataSeriesAux.push(response.wordsList[key].occurrences);
                    }

                    for (var key in response.articles) {
                        var occ = 0;
                        for (var wordAgg in response.wordsList) {
                            for (var word in response.articles[key].wordsList) {
                                if (response.wordsList[wordAgg].word == response.articles[key].wordsList[word].word) {
                                    occ++;
                                }
                            }
                        }

                        if (occ != 0) {
                            aux.values.push(occ);
                            aux.text += response.articles[key].title;
                            $scope.seriesAux.push(aux);

                            aux = {"values": [], text: ''};
                        }
                    }

                    $scope.topWordsChartJson1 = {
                        type: "bar",
                        backgroundColor: "white",
                        title: {
                            text: $scope.titleBar,
                            fontFamily: 'Georgia'
                        },
                        plot: {
                            "animation": {
                                "on-legend-toggle": true, //set to true to show animation and false to turn off
                                "effect": 5,
                                "method": 1,
                                "sequence": 1,
                                "speed": 1
                            },
                            tooltip: {
                                visible: true
                            },
                            "border-width": 1,
                            "border-color": "#cccccc",
                            "line-style": "dotted"
                        },
                        scaleX: {
                            values: $scope.labels
                        },
                        series: [
                            {
                                values: $scope.dataSeries,
                                backgroundColor: "#6bbf19"
                            }
                        ]
                    };

                    $scope.topWordsChartJson2 = {
                        "type": "pie",
                        title: {
                            text: $scope.titlePie,
                            fontFamily: 'Georgia'
                        },
                        plot: {
                            detach: false,
                            animation: {
                                onLegendToggle: true, //set to true to show animation and false to turn off
                                "effect": 5,
                                "method": 1,
                                "sequence": 4,
                                "speed": 0.5
                            },
                            "value-box": {
                                "text": "%t",
                                "font-size": 12,
                                "font-family": "Georgia",
                                "font-weight": "normal",
                                "placement": "out",
                                "font-color": "gray",
                            },
                            tooltip: {
                                visible: true,
                                "text": "%v (%npv%)",
                                "font-color": "black",
                                "font-family": "Georgia",
                                "text-alpha": 1,
                                "background-color": "white",
                                "alpha": 0.7,
                                "border-width": 1,
                                "border-color": "#cccccc",
                                "line-style": "dotted",
                                "border-radius": "10px",
                                "padding": "10%",
                                "placement": "node:center"
                            },
                            "border-width": 1,
                            "border-color": "#cccccc",
                            "line-style": "dotted"
                        },
                        "series": $scope.seriesAux
                    };

                    $scope.topWordsChartJson1.values = [];
                }, function () {
                    $scope.serverResponse = 'An error has occurred';
                });
                $scope.showContent = true;
                $rootScope.showContentBar = true;
                $rootScope.showContentPie = true;
                $rootScope.showContentWhatever = false;
            }

        };

        zingchart.node_mouseout = function (e) {
            if (e.id === 'myChart') {
                $scope.$apply(function () {
                    $scope.topWordsChartJson1.plot.tooltip.visible = true;
                    $rootScope.showContentBar = false;
                    $scope.topWordsChartJson1.series[0].values = $scope.dataSeriesAux;
                    $scope.topWordsChartJson1.scaleX.values = $scope.labelsAux;
                    $scope.topWordsChartJson1.title.text = $scope.titleBar;
                    $rootScope.showContentBar = true;
                })
            }

            if (e.id === 'myNextChart'){
                $scope.$apply(function () {
                    $scope.topWordsChartJson2.plot.tooltip.visible = true;
                    $rootScope.showContentPie = false;
                    $scope.topWordsChartJson2.series = $scope.seriesAux;
                    $scope.topWordsChartJson2.title.text = $scope.titlePie;
                    $rootScope.showContentPie = true;
                })
            }
        }
        zingchart.node_mouseover = function(e) {
            if (e.id === 'myChart') {
                $scope.$apply(function(){
                    $scope.topWordsChartJson1.plot.tooltip.visible = false;
            //        $scope.topWordsChartJson2.plot.animation.OnLegendToggle = false;
                    $rootScope.showContentBar = false;

                    var title = "Top 10 Words ";

                    var applyLabels = [];
                    var applyDataSeries = [];
                    for (var key in $scope.response.articles) {
                        if ($scope.topWordsChartJson2.series[e.plotindex].text == $scope.response.articles[key].title){
                            for (var word in $scope.response.articles[key].wordsList){
                                applyLabels.push($scope.response.articles[key].wordsList[word].word);
                                applyDataSeries.push($scope.response.articles[key].wordsList[word].occurrences);
                            }
                        }
                    }

                    $scope.topWordsChartJson1.series[0].values = [];
                    $scope.topWordsChartJson1.scaleX.values = [];

                    $scope.topWordsChartJson1.series[0].values = applyDataSeries;
                    $scope.topWordsChartJson1.scaleX.values = applyLabels;
                    $scope.topWordsChartJson1.title.text = title + $scope.topWordsChartJson2.series[e.plotindex].text;

                    $rootScope.showContentBar = true;

                });
            }

            if (e.id === 'myNextChart') {
                $scope.$apply(function() {
                    $scope.topWordsChartJson2.plot.tooltip.visible = false;
                    $rootScope.showContentPie = false;

                    var seriesAux1 = [];
                    var aux = {"values":[], text:''};

                    var clickedWord = $scope.topWordsChartJson1.scaleX.values[e.nodeindex];
                    for (var key in $scope.response.articles){
                        for (var word in $scope.response.articles[key].wordsList){
                            if ($scope.response.articles[key].wordsList[word].word == clickedWord){
                                aux.values.push($scope.response.articles[key].wordsList[word].occurrences);
                                aux.text += $scope.response.articles[key].title;
                                seriesAux1.push(aux);

                                aux = {"values":[], text:''};
                            }
                        }
                    }

                    $scope.topWordsChartJson2.series = seriesAux1;
                    $rootScope.showContentPie = true;

                });
            }
        };

        //Search word
        $scope.searchWord = function (title, word) {
            $scope.showWord = true;
            $rootScope.showContentBar = false;
            $rootScope.showContentPie = false;

            if (title.length != 0 && word.length != 0) {

                var labels = [];
                var dataSeries = [];
                $scope.response = SearchWordFactory.query({title: title, word: word});
                $scope.response.$promise.then(function (data) {
                    $scope.word = data.wordDTO.word;
                    $scope.occurrences = data.wordDTO.occurrences;

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


                $rootScope.showContentWord = true;
            }
        };

    }]);
})();

