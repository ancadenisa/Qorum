'use strict';

angular.module('qorumApp')
    .controller('OrganisationDetailController', function ($scope, $rootScope, $stateParams, entity, Organisation, User) {
        $scope.organisation = entity;
        $scope.load = function (id) {
            Organisation.get({id: id}, function(result) {
                $scope.organisation = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:organisationUpdate', function(event, result) {
            $scope.organisation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
