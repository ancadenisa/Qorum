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
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            });
    });
