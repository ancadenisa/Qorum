'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('org-create-tabs', {
                parent: 'admin',
                url: '/org-create-tabs',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-administration/organization-create-tabs.html',
                        controller: 'OrganizationsCreateTabsController'
                    },
                },
                 resolve: {
                     orgToBeEdited: ['Organization', '$stateParams', function(Organization, $stateParams) {
                         if($stateParams.orgId == null || $stateParams.orgId == ""){
                             return {id: null}
                         }
                         return Organization.get({id : $stateParams.orgId});
                     }]
                 }
            })
            .state('org-edit-tabs', {
                parent: 'admin',
                url: '/org-edit-tabs/{orgId}',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-administration/organization-create-tabs.html',
                        controller: 'OrganizationsCreateTabsController'
                    },
                },
                 resolve: {
                     orgToBeEdited: ['Organization', '$stateParams', function(Organization, $stateParams) {
                         if($stateParams.orgId == null || $stateParams.orgId == ""){
                             return {id: null}
                         }
                         return Organization.get({id : $stateParams.orgId});
                     }]
                 }
            })
            .state('create-dep', {
                parent: 'org-create-tabs',
                url: '{orgId}/newDep',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-administration/departments-tab.html',
                        controller: 'DepartmentsTabController',
                        size: 'lg',
                        resolve: {
                            departmentToBeEdited: ['Department', function(Department) {
                                return {id: null};
                            }],
                            organizationSaved: ['Organization', function(Organization) {
                                return Organization.get({id : $stateParams.orgId});
                            }]
                          }
                    }).result.then(function(result) {
                        $state.go('org-create-tabs', null, { reload: false });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('edit-dep', {
                parent: 'org-create-tabs',
                url: '{orgId}/{depId}/new',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-administration/departments-tab.html',
                        controller: 'DepartmentsTabController',
                        size: 'lg',
                        resolve: {
                            departmentToBeEdited: ['Department', function(Department) {
                                return Department.get({id : $stateParams.depId});
                            }],
                            organizationSaved: ['Organization', function(Organization) {
                                return Organization.get({id : $stateParams.orgId});
                                }]
                          }
                    }).result.then(function(result) {
                        $state.go('org-create-tabs', null, { reload: false });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('create-proj', {
                parent: 'org-create-tabs',
                url: '/newProj',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-administration/projects-tab.html',
                        controller: 'ProjectsTabController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('org-create-tabs', null, { reload: false});
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('edit-proj', {
                parent: 'org-create-tabs',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-administration/projects-tab.html',
                        controller: 'ProjectsTabController',
                        size: 'lg',
                        resolve: {
                            entity: ['Project', function(Project) {
                                return Project.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('org-create-tabs', null, { reload: false });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('orgs-admin-list', {
                parent: 'admin',
                url: '/orgs-admin-list',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-administration/organizations-list.html',
                        controller: 'OrganizationsListAdminController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('logs');
                        return $translate.refresh();
                    }]
                }
            });
    });
