'use strict';

angular.module('qorumApp')
    .controller('IssueCommentsController', function ($scope, $rootScope, $stateParams, entity, Issue, IssueComments, User, Comment) {
        $scope.issue = entity;
        $scope.comments = IssueComments.getCommentsByIssue({issueId : entity.id});
        $scope.newComment = {};
        $scope.loggedUser = {};
        User.get({login: "get_current_user"}, function(result){$scope.loggedUser = result});
        $scope.save = function () {
            if ($scope.newComment != null && $scope.newComment.id != null) {
                Comment.update($scope.newComment, onSaveFinished);
            } else {
                $scope.newComment.isSolution = 0;
                $scope.newComment.user =  $scope.loggedUser;
                $scope.newComment.issue = entity;
                Comment.save($scope.newComment);
            }
        }


        //TODO ANCA - update issue rating on every click
        // click handler for increment button
        $("#increaseButton").click(function () {
            var newValue = 1 + parseInt($("#rating").text());
            $("#rating").text(newValue);
        });
        $("#decreaseButton").click(function () {
            var newValue = parseInt($("#rating").text()) - 1;
            $("#rating").text(newValue);
        });
    }
);
