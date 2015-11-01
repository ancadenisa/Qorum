'use strict';

angular.module('qorumApp')
    .controller('CommentDetailController', function ($scope, $rootScope, $stateParams, entity, Comment, User, Issue) {
        $scope.comment = entity;
        $scope.load = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
            });
        };
        var unsubscribe = $rootScope.$on('qorumApp:commentUpdate', function(event, result) {
            $scope.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
