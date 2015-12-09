/**
 * Created by Marian on 11/3/2015.
 */
'use strict';

angular.module('qorumApp')
    .controller('FirstPageController', ['$scope', '$modal', 'Principal', '$state', 'Issue', 'Tag',
        function ($scope, $modal, Principal, $state, Issue, Tag) {
            $('#side-menu').metisMenu();

            $scope.issuesCount = 0;
            $scope.selected = {
                tags: []
            };
            $scope.issueToSearch = '';

            $scope.isAuthenticated = Principal.isAuthenticated;

            $state.transitionTo('firstpage.issues');

            Issue.getCount(
                function (result) {
                    $scope.issuesCount = result.issuesCount;
                });

            $scope.tags = [];
            Tag.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.tags = result;
            });

            $scope.getByNameAndTags = function() {
                $scope.$broadcast('filterIssuesEvent', {selectedTags : $scope.selected.tags,  issueToSearch : $scope.issueToSearch});
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
                                    rating: null,
                                    is_public: null,
                                    id: null,
                                    views: null
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
