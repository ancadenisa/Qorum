/**
 * Created by Marian on 11/3/2015.
 */
'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('firstpage', {
                parent: 'site',
                url: '/',
                redirectTo : 'firstpage.issues',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/firstPage/firstPage.html',
                        controller: 'FirstPageController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('issue');
                        return $translate.refresh();
                    }]
                }
            })
            .state('firstpage.issues', {
                parent: 'firstpage',
                views : {
                   'issues': {
                       templateUrl: 'scripts/app/entities/issue/issues-page.html',
                       controller: 'IssuesPageController'
                   }
                },
                data: {
                    authorities: []
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('issue');
                        return $translate.refresh();
                    }]
                }
            })
            .state('firstpage.issues.new', {
                parent: 'firstpage.issues',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/issue/issue-modal.html',
                        controller: 'IssueModalController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    content: null,
                                    last_updated: null,
                                    created_date: null,
                                    rating: null,
                                    is_public: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {

                        }, function() {
                            $state.go('^');
                        })
                }]
            });
    });
