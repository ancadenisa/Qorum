'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organisation', {
                parent: 'entity',
                url: '/organisations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.organisation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organisation/organisations.html',
                        controller: 'OrganisationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organisation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('organisation.detail', {
                parent: 'entity',
                url: '/organisation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.organisation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organisation/organisation-detail.html',
                        controller: 'OrganisationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organisation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Organisation', function($stateParams, Organisation) {
                        return Organisation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organisation.new', {
                parent: 'organisation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organisation/organisation-dialog.html',
                        controller: 'OrganisationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    address: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('organisation', null, { reload: true });
                    }, function() {
                        $state.go('organisation');
                    })
                }]
            })
            .state('organisation.edit', {
                parent: 'organisation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organisation/organisation-dialog.html',
                        controller: 'OrganisationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Organisation', function(Organisation) {
                                return Organisation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organisation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
