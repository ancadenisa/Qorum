'use strict';

angular.module('qorumApp')
    .controller('DepartmentDetailController', function ($scope, $rootScope, $stateParams, entity, Department, User, Project, Issue) {
        $scope.department = entity;
        $scope.load = function (id) {
            Department.get({id: id}, function(result) {
                $scope.department = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:departmentUpdate', function(event, result) {
            $scope.department = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
