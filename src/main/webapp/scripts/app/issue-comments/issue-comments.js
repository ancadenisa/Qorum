'use strict';

angular.module('qorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('issue-comments', {
                parent: 'entity',
                url: '/issue-comments/{issueId}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'qorumApp.issue.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/issue-comments/issue-comments.html',
                        controller: 'IssueCommentsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('issue');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Issue', function($stateParams, Issue) {
                        return Issue.get({id : $stateParams.issueId});
                    }]
                }
            })
    });
