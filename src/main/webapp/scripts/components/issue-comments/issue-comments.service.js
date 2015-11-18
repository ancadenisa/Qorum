angular.module('qorumApp')
    .factory('IssueComments', function ($resource, DateUtils) {
        return $resource('api/comments/byIssue/:issueId', {}, {
            'getCommentsByIssue': { method: 'GET', isArray: true}
        });
    }
);
