'use strict';

angular.module('qorumApp')
    .controller('TagDetailController', function ($scope, $rootScope, $stateParams, entity, Tag, Issue) {
        $scope.tag = entity;
        $scope.load = function (id) {
            Tag.get({id: id}, function(result) {
                $scope.tag = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:tagUpdate', function(event, result) {
            $scope.tag = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
