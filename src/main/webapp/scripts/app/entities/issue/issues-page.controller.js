/**
 * Created by Marian on 11/8/2015.
 */
'use strict';

angular.module('qorumApp')
    .controller('IssuesPageController', function ($scope, Issue, ParseLinks,Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.issues = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Issue.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.issues = result;
            });
        };

        $scope.$on('filterIssuesEvent', function(event, data) {
            Issue.getByNameAndTags({page: $scope.page, size: 1,issueName: data['issueToSearch']},data['selectedTags'],
                function (result,headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.issues = result;
                });
        });

        $scope.timeago = function(date) {
            if (date != null) {
                return jQuery.timeago(date);
            }
            else {
                return "Created date not available!";
            }
        }

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
                rating: null,
                is_public: null,
                id: null,
                views: null
            };
        };
    });
