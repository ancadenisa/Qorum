'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('issue', {
                parent: 'entity',
                url: '/issues',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.issue.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/issue/issues.html',
                        controller: 'IssueController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('issue');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('issue.detail', {
                parent: 'entity',
                url: '/issue/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/issue/issue-detail.html',
                        controller: 'IssueDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('issue');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Issue', function($stateParams, Issue) {
                        return Issue.get({id : $stateParams.id});
                    }]
                }
            })
            .state('issue.new', {
                parent: 'issue',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/issue/issue-dialog.html',
                        controller: 'IssueDialogController',
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
                                    id: null,
                                    views: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('issue', null, { reload: true });
                    }, function() {
                        $state.go('issue');
                    })
                }]
            })
            .state('issue.edit', {
                parent: 'issue',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/issue/issue-dialog.html',
                        controller: 'IssueDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Issue', function(Issue) {
                                return Issue.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('issue', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
