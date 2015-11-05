angular.module('qorumApp')
    .factory('OrgSectionService', function ($resource, DateUtils) {
        return $resource('api/organizations-section/getOrganizations/:userId', {}, {
            'queryOrgs': { method: 'GET', isArray: true}
        });
    }
);
