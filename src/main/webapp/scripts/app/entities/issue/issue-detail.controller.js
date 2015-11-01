'use strict';

angular.module('qorumApp')
    .controller('IssueDetailController', function ($scope, $rootScope, $stateParams, entity, Issue, User, Project, Department, Tag) {
        $scope.issue = entity;
        $scope.load = function (id) {
            Issue.get({id: id}, function(result) {
                $scope.issue = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:issueUpdate', function(event, result) {
            $scope.issue = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
