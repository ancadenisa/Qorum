/**
 * Created by Marian on 11/3/2015.
 */
'use strict';

angular.module('qorumApp')
    .controller('FirstPageController', ['$scope', '$modal', 'Principal', '$state', 'Issue', 'Tag', 'Organization',
        function ($scope, $modal, Principal, $state, Issue, Tag, Organization) {
            $('#side-menu').metisMenu();

            $scope.organizations = [];
            $scope.issuesCount = 0;

            $scope.allObj = {name : "All"};
            $scope.selected = {
                tags: [],
                organizations: []
            };
            $scope.issueToSearch = '';

            $scope.isAuthenticated = Principal.isAuthenticated;

            $state.transitionTo('firstpage.issues');

            Organization.query(function(result) {
                $scope.organizations = result;
                $scope.organizations.unshift($scope.allObj);
            });

            Issue.getCount(
                function (result) {
                    $scope.issuesCount = result.issuesCount;
                });

            $scope.tags = [];

            Tag.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.tags = result;
                $scope.tags.unshift($scope.allObj);
            });

            $scope.getByNameAndTags = function() {
                if ($.inArray($scope.allObj,$scope.selected.tags) != -1) {
                    $scope.selected.tags = [];
                }
                if ($.inArray($scope.allObj,$scope.selected.organizations) != -1) {
                    $scope.selected.organizations = [];
                }

                $scope.$broadcast('filterIssuesEvent', {selectedOrganizations: $scope.selected.organizations,
                    selectedTags : $scope.selected.tags,  issueToSearch : $scope.issueToSearch});
            }

            $scope.createNewIssue = function () {
                if ($scope.isAuthenticated() == true) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/issue/issue-modal.html',
                        controller: 'IssueModalController',
                        size: 'lg',
                        resolve: {
                            entity: ['Issue', function (Issue) {
                                return {
                                    name: null,
                                    content: null,
                                    last_updated: null,
                                    created_date: null,
                                    rating: 0,
                                    is_public: null,
                                    id: null,
                                    views: 0,
                                    hasSolution: false
                                };
                            }]
                        }
                    }).result.then(function (result) {
                            //When clicking ok
                        }, function () {
                            //When clicking cancel
                        })
                }

            }
        }]);
