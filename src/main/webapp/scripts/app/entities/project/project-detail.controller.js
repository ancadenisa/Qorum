'use strict';

angular.module('qorumApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, Department, User, Issue) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
