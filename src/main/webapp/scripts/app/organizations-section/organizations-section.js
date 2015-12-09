angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orgssection', {
                parent: 'entity',
                url: '/organizations-section',
                redirectTo : 'orgssection.issues',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Organizatii'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/organizations-section/organizations-section.html',
                        controller: 'OrgsSectionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('comment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('orgssection.issues', {
                parent: 'orgssection',
                views : {
                   'issuesz': {
                       templateUrl: 'scripts/app/entities/issue/issues-page.html',
                       //controller: 'OrgsSectionController'
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
            .state('orgssection.issues.new', {
                parent: 'orgssection.issues',
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
                                    id: null,
                                    views: null
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
