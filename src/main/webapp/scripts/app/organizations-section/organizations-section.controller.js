angular.module('qorumApp')
    .controller('OrgsSectionController', function ($scope, OrgSectionService, DepsSectionService,ProjectSectionService) {
        $scope.userOrganizations = [];
        $scope.userDepartments = [];
        $scope.projects = [];
        //TODO ANCA - elimna hardcodare
        $scope.loaded =  false;
        $scope.loadAll = function() {
            OrgSectionService.queryOrgs({userId : 3}, function(result) {
                $scope.userOrganizations = result;
                for(var i = 0; i <  $scope.userOrganizations.length; i++){
                    $scope.userDepartments[($scope.userOrganizations[i]).id] = DepsSectionService.queryDepts({orgId: $scope.userOrganizations[i].id, userId : 3}, function(result){
                        for(dep in result){
                            $scope.projects[(result[dep]).id] = ProjectSectionService.queryProjects({depId: (result[dep]).id, userId : 3});
                        };
                    });
                }
            });
         };
         $scope.loadPostsFromOrganisation = function(id){
         }

        $scope.loadAll();
        $scope.fireEvent = function(){
        // This will only run after the ng-repeat has rendered its things to the DOM
            $('#side-menu').metisMenu();
        };

    }

);
