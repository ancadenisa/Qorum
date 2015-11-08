angular.module('qorumApp')
    .factory('ProjectSectionService', function ($resource, DateUtils) {
        return $resource('api/projects-section/getProjects/:depId/:userId', {}, {
            'queryProjects': { method: 'GET', isArray: true}
        });
    }
);
