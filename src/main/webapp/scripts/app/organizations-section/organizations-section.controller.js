'use strict';

angular.module('qorumApp')
    .controller('OrgsSectionController', function ($scope, OrgSectionService, DepsSectionService) {
        $scope.userOrganizations = [];
        $scope.userDepartments = [];
        //TODO ANCA - elimna hardcodare
        $scope.loadAll = function() {
            OrgSectionService.queryOrgs({userId : 3}, function(result) {
                $scope.userOrganizations = result;
                for(var i = 0; i <  $scope.userOrganizations.length; i++){
                    $scope.userDepartments[($scope.userOrganizations[i]).id] = DepsSectionService.queryDepts({orgId: $scope.userOrganizations[i].id, userId : 3});
                        /*for(dep in $scope.userDepartments){
                            DepsSectionService.queryProjects({orgId: orgId, userId : 3}, function(result) {
                                $scope.userDepartments = result;
                            });
                        }*/
                    };
                });
           };
        $scope.loadAll();

        $scope.showDeps = function(orgId){
            $("#deps"+orgId).toggle('collapse');
        }

    }

);
