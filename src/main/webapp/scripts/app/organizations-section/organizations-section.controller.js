'use strict';

angular.module('qorumApp')
    .controller('OrgsSectionController', function ($scope, OrgSectionService, DepsSectionService) {
        $scope.userOrganizations = [];
        $scope.userDepartments = [];
        //TODO ANCA - elimna hardcodare
        $scope.loadAll = function() {
            OrgSectionService.queryOrgs({userId : 3}, function(result) {0
                $scope.userOrganizations = result;
            });
        };
        $scope.loadAll();

        $scope.loadDepartments =  function(orgId){
          DepsSectionService.queryDepts({orgId: orgId, userId : 3}, function(result) {
                $scope.userDepartments = result;
                $("#deps" + orgId).collapse('toggle');
            });
        }

    }
);
