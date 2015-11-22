'use strict';

angular.module('qorumApp')
    .controller('IssueCommentsController', function ($scope, $rootScope, $window, $stateParams, entity, Issue, IssueComments, User, Comment) {
        entity.$promise.then(function(result){
            $scope.comments = IssueComments.getCommentsByIssue({issueId : entity.id});
            $scope.issue = result;
            }
        )
        $scope.existsSolution = false;
        $scope.newComment = {};
        $scope.loggedUser = {};
        User.get({login: "get_current_user"}, function(result){$scope.loggedUser = result});
        $scope.checkSolution = function(){
            for(var index=0; index < $scope.comments.length ; index++){
                if($scope.comments[index].is_solution == 1){
                    $("#solution"+$scope.comments[index].id).css('color', 'green');
                    return $scope.comments[index].id;
                }
            }
            return -100;
        }
        $scope.save = function () {
            if ($scope.newComment != null && $scope.newComment.id != null) {
                Comment.update($scope.newComment, onSaveFinished);
            } else {
                $scope.newComment.is_solution = 0;
                $scope.newComment.user =  $scope.loggedUser;
                $scope.newComment.issue = entity;
                Comment.save($scope.newComment);
            }
            $window.location.reload();
        }

        var updateRatingForIssue =  function(newValue){
            $scope.issue.rating = newValue;
            Issue.update($scope.issue);
        }

        $scope.markOrUnmarkSolution =  function(comment){
            if($scope.loggedUser.id ==  $scope.issue.user.id){
                if(comment.is_solution == 1){
                    comment.is_solution = 0;
                    $("#solution"+comment.id).css('color', 'grey');
                }else{
                    comment.is_solution  = 1;
                    $("#solution"+comment.id).css('color', 'green');
                }
                Comment.update(comment);
            }
        }

        $("#increaseButton").click(function () {
            var newValue = 1 + parseInt($("#rating").text());
            $("#rating").text(newValue);
            updateRatingForIssue(newValue);
        });
        $("#decreaseButton").click(function () {
            var newValue = parseInt($("#rating").text()) - 1;
            $("#rating").text(newValue);
            updateRatingForIssue(newValue);
        });

    }
);
