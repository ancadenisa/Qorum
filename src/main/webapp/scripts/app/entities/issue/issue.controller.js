'use strict';

angular.module('qorumApp')
    .controller('IssueController', function ($scope, Issue, ParseLinks,Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.issues = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Issue.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.issues = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Issue.get({id: id}, function(result) {
                $scope.issue = result;
                $('#deleteIssueConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Issue.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteIssueConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.issue = {
                name: null,
                content: null,
                last_updated: null,
                created_date: null,
                rating: 0,
                is_public: false,
                id: null,
                views: 0,
                hasSolution: false
            };
        };
    });
