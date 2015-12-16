'use strict';
angular.module('qorumApp')
    .controller('IssueCommentsController',function ($scope, $rootScope, $window, $stateParams, entity, Issue, IssueComments, User, Comment, AlertService,  Auth, Principal, ENV) {
        entity.$promise.then(function(result){
            $scope.issue = result;
            loadComments(result.id);
            }
        )

        function loadComments(issueId){
            IssueComments.getCommentsByIssue({issueId : issueId}).$promise.then(function(result){
                $scope.comments = result;
                $scope.nrAnswers = $scope.comments.length;
            })
        }
        $scope.existsSolution = false;
        $scope.onEditComm = false;
        $scope.onEditIssue= false;
        $scope.newComment = {};
        $scope.loggedUser = {};
        $scope.alerts = [];
        $scope.nrAnswers = 0;

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
                Comment.update($scope.newComment, function () {
                    loadComments($scope.issue.id);
                });
            } else {
                $scope.newComment.is_solution = 0;
                $scope.newComment.user =  $scope.loggedUser;
                $scope.newComment.issue = entity;
                $scope.newComment.votes = 0;
                Comment.save($scope.newComment, function () {
                    loadComments($scope.issue.id);
                });
            }
            $scope.nrAnswers++;
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
                comment = Comment.update(comment);
            }
        }

        $("#increaseButton").click(function () {
            if($scope.issue.user.id == $scope.loggedUser.id){
                $scope.alerts.push({type:"danger", msg: 'Nu puteti creste rating-ul propriei dvs. postari!'});
                $scope.$apply()
            }else{
                var newValue = 1 + parseInt($("#rating").text());
                $("#rating").text(newValue);
                updateRatingForIssue(newValue);
            }
        });
        $("#decreaseButton").click(function () {
            if($scope.issue.user.id == $scope.loggedUser.id){
                $scope.alerts.push({type:"danger", msg: 'Nu puteti descreste rating-ul propriei dvs. postari!'});
                $scope.$apply()
            }else{
                var newValue = parseInt($("#rating").text()) - 1;
                $("#rating").text(newValue);
                updateRatingForIssue(newValue);
            }
        });


        $scope.deleteCommentIntention = function(comment){
            $('#deleteComment').modal('show');
            $scope.commentToBeDeleted = comment;
        }
        $scope.deleteComment = function(){
                        Comment.delete({id: $scope.commentToBeDeleted.id},
                            function () {
                                loadComments($scope.issue.id);
                                $('#deleteComment').modal('hide');
                            });
        }

        $scope.editCommentIntention =  function(comment){
            $scope.onEditComm = true;
            $scope.commentToBeEdited = comment;
        }
        $scope.editComment = function(comment){
            Comment.update(comment);
            $scope.onEditComm = false;
        }

        $scope.cancelEdit =  function(){
            $scope.onEditComm = false;
            $scope.onEditIssue = false;
        }

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.editIssueIntention = function(){
            $scope.onEditIssue = true;
        }
        $scope.editIssue = function(){
            Issue.update($scope.issue);
            $scope.onEditIssue = false;
        }
        $scope.deleteIssueIntention = function(){
            $('#deleteIssue').modal('show');
        }
        $scope.deleteIssue = function(){
            Issue.delete({id: $scope.issue.id},
                function () {
                    $('#deleteIssue').modal('hide');
                    $window.location = "/"
                }
            );
        }

        $scope.isUserOrgAdmin = function(issue){
            var existsAdmin = false;
            angular.forEach($scope.issue.departments, function(department, value){
                if($scope.loggedUser.id == department.organization.orgAdmin.id ){
                    existsAdmin =  true;
                }
             });
            return existsAdmin;
        }

        $scope.voteUp = function(comment){
            if(comment.votes == null){
                comment.votes = 1;
            }else{
                comment.votes++;
            }
            Comment.update(comment);
        }

        $scope.voteDown = function(comment){
            if(comment.votes == null){
                comment.votes = -1;
            }else{
                comment.votes--;
            }
            Comment.update(comment);
        }
        $scope.isAuthenticated = Principal.isAuthenticated;


    }
);
