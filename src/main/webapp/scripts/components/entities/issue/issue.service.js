'use strict';

angular.module('qorumApp')
    .factory('Issue', function ($resource, DateUtils) {
        return $resource('api/issues/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.last_updated = DateUtils.convertDateTimeFromServer(data.last_updated);
                    data.created_date = DateUtils.convertDateTimeFromServer(data.created_date);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'getCount' : {
                url : 'api/issues/getCount',
                method : 'GET',
                transformResponse: function (data) {
                    return {issuesCount : data};
                }
            },
            'getByNameAndTags': {
                url : 'api/issues/filtered',
                method: 'POST',
                isArray: true,
                params: {
                  issueName: '@issueName'
                },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.last_updated = DateUtils.convertDateTimeFromServer(data.last_updated);
                    data.created_date = DateUtils.convertDateTimeFromServer(data.created_date);
                    return data;
                }
            },
            'getForCurrentUser': {
                url : 'api/issues/currentUser',
                method: 'GET',
                isArray: true}
        });
    });
