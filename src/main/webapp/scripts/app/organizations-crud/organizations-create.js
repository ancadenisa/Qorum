'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('org-create', {
                parent: 'admin',
                url: '/organizations-create',
                data: {
                    pageTitle: 'Adauga organizatie'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-crud/organizations-create.html',
                        controller: 'OrganizationsCrudController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('logs');
                        return $translate.refresh();
                    }]
                }
            })
            .state('createDep', {
                parent: 'org-create',
                url: '{orgId}/new',
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
            });


    });
