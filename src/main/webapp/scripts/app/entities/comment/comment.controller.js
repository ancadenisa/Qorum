'use strict';

angular.module('qorumApp')
    .controller('CommentController', function ($scope, Comment, ParseLinks) {
        $scope.comments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Comment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.comments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
                $('#deleteCommentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Comment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCommentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.comment = {
                content: null,
                is_solution: null,
                id: null
            };
        };
    });
