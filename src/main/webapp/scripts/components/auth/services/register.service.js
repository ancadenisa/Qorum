'use strict';

angular.module('qorumApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


