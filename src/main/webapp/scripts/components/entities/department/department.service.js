'use strict';

angular.module('qorumApp')
    .factory('Department', function ($resource, DateUtils) {
        return $resource('api/departments/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'getForProject': {
                url: 'api/departments/getByProjectId/:projectId',
                method: 'GET',
                isArray: true
            }

        });
    });
