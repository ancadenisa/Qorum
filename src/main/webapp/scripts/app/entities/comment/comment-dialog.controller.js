'use strict';

angular.module('qorumApp').controller('CommentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Comment', 'User', 'Issue',
        function($scope, $stateParams, $modalInstance, entity, Comment, User, Issue) {

        $scope.comment = entity;
        $scope.users = User.query();
        $scope.issues = Issue.query();
        $scope.load = function(id) {
            Comment.get({id : id}, function(result) {
                $scope.comment = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:commentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.comment.id != null) {
                Comment.update($scope.comment, onSaveFinished);
            } else {
                Comment.save($scope.comment, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
