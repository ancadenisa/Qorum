/**
 * Created by Marian on 11/3/2015.
 */
'use strict';

angular.module('qorumApp')
    .controller('FirstPageController', ['$scope', '$modal', 'Principal', '$state', 'Issue',
        function ($scope, $modal, Principal, $state, Issue) {
        $('#side-menu').metisMenu();
        $scope.isAuthenticated = Principal.isAuthenticated;

        $state.transitionTo('firstpage.issues');

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
                                id: null
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
