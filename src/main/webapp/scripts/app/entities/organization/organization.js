'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organization', {
                parent: 'entity',
                url: '/organizations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.organization.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organization/organizations.html',
                        controller: 'OrganizationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organization');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('organization.detail', {
                parent: 'entity',
                url: '/organization/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.organization.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organization/organization-detail.html',
                        controller: 'OrganizationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organization');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Organization', function($stateParams, Organization) {
                        return Organization.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organization.new', {
                parent: 'organization',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organization/organization-dialog.html',
                        controller: 'OrganizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('organization', null, { reload: true });
                    }, function() {
                        $state.go('organization');
                    })
                }]
            })
            .state('organization.edit', {
                parent: 'organization',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/organization/organization-dialog.html',
                        controller: 'OrganizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Organization', function(Organization) {
                                return Organization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
