'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('org-create', {
                parent: 'admin',
                url: '/organizations-create/{orgId}',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-crud/organizations-create.html',
                        controller: 'OrganizationsCrudController'
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
            .state('createDep', {
                parent: 'org-create',
                url: '{orgId}/new',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-crud/department-add-on-org.html',
                        controller: 'OrganizationsDepartmentsCrudController',
                        size: 'lg',
                        resolve: {
                            organizationSaved: ['Organization', function(Organization) {
                                return Organization.get({id : $stateParams.orgId});
                            }],
                            departmentToBeEdited: function(){
                                return {
                                    id: null
                                };
                            }
                          }
                    }).result.then(function(result) {
                        $state.go('org-create', null, { reload: false });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('editDep', {
                parent: 'org-create',
                url: '{orgId}/{depId}/new',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-crud/department-add-on-org.html',
                        controller: 'OrganizationsDepartmentsCrudController',
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
                        $state.go('org-create', null, { reload: false });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('createProj', {
                parent: 'org-create',
                url: '/newProj',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-crud/projects-add-edit-on-deps.html',
                        controller: 'OrganizationsDepartmentsProjectsAddEditController',
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
                        $state.go('org-create', null, { reload: false});
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('editProj', {
                parent: 'org-create',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/organizations-crud/projects-add-edit-on-deps.html',
                        controller: 'OrganizationsDepartmentsProjectsAddEditController',
                        size: 'lg',
                        resolve: {
                            entity: ['Project', function(Project) {
                                return Project.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('org-create', null, { reload: false });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('orgs-admin', {
                parent: 'admin',
                url: '/orgs-admin',
                data: {
                    authorities: [],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-crud/organizations-list.html',
                        controller: 'OrganizationsListController'
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
