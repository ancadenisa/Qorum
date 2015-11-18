angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organizations-section', {
                parent: 'entity',
                url: '/organizations-section',
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
    });
