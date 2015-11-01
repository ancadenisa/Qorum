'use strict';

angular.module('qorumApp')
    .controller('OrganizationDetailController', function ($scope, $rootScope, $stateParams, entity, Organization, User, Department) {
        $scope.organization = entity;
        $scope.load = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:organizationUpdate', function(event, result) {
            $scope.organization = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
