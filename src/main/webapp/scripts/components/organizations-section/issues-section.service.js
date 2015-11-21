angular.module('qorumApp')
    .factory('IssuesSection', function ($http) {
            var factory = {};

            factory.getIssuesByOrg = function(orgId) {
                return $http.get('api/issuesByOrg/' + orgId)
                    .success(function(response){
                    return response;
                    })
                    .error(function(error){
                        return error;
                    })
            }

             factory.getIssuesByDep = function(depId) {
                return $http.get('api/issuesByDep/' + depId)
                    .success(function(response){
                        return response;
                    })
                    .error(function(error){
                        return error;
                    })
            }

             factory.getIssuesByProj = function(projId) {
                return $http.get('api/issuesByProj/' + projId)
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

