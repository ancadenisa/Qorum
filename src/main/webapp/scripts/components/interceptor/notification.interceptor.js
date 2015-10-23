 'use strict';

angular.module('qorumApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-qorumApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-qorumApp-params')});
                }
                return response;
            }
        };
    });
