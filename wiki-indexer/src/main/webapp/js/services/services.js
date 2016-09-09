(function () {
    'use strict';

    var services = angular.module('wikiIndexerApp.services', ['ngResource']);

    services.factory('WordIndexFactory', function ($resource) {
        return $resource('wiki-indexer/indexate?title=:title', {}, {
            query: {method: 'GET', params: {title: "@title"}, isArray: false}
        });
    }).factory('SearchWordFactory', function ($resource) {
        return $resource('wiki-indexer/search?word=:word&title=:title', {}, {
            query: {method: 'GET', params: {word: "@word", title: "@title"}, isArray: false}
        });
    }).factory('FileUploadFactory', function ($http, $q) {
        return {
            uploadFileToUrl: function (file, uploadUrl) {
                var fileFormData = new FormData();
                fileFormData.append('file', file);

                var deffered = $q.defer();
                $http.post(uploadUrl, fileFormData, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}

                }).success(function (response) {
                    deffered.resolve(response);

                }).error(function (response) {
                    deffered.reject(response);
                });
                return deffered.promise;
            }
        };
    });
})();