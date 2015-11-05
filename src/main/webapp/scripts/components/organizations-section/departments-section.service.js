angular.module('qorumApp')
    .factory('DepsSectionService', function ($resource, DateUtils) {
        return $resource('api/organizations-section/getDepartments/:orgId/:userId', {}, {
            'queryDepts': { method: 'GET', isArray: true}
        });
    }
);
