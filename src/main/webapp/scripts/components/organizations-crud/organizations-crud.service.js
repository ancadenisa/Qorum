angular.module('qorumApp')
    .factory('OrganizationsCrud', function ($http) {
            var factory = {};

            factory.getDepartmentsByOrgId = function(orgId) {
                return $http.get('api/getDepartmentsByOrgId/' + orgId)
                    .success(function(response){
                    return response;
                    })
                    .error(function(error){
                        return error;
                    })
            }
            factory.getProjectsByOrganization = function(orgId) {
                return $http.get('api/getProjectsByOrgId/' + orgId)
                    .success(function(response){
                        return response;
                    })
                    .error(function(error){
                        return error;
                    })
            }
            return factory;
    }
)

