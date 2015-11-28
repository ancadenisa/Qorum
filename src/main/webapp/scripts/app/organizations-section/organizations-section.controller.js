angular.module('qorumApp')
    .controller('OrgsSectionController', function ($scope, OrgSectionService, Issue, IssuesSection, DepsSectionService,ProjectSectionService, ParseLinks ) {
        $scope.userOrganizations = [];
        $scope.userDepartments = [];
        $scope.projects = [];
        //TODO ANCA - elimna hardcodare
        $scope.loaded =  false;
        $scope.issues = []; $scope.loadAll = function() {
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

        $scope.loadAll();
        $scope.fireEvent = function(){
        // This will only run after the ng-repeat has rendered its things to the DOM
            $('#side-menu').metisMenu();
        };

         $scope.loadPostsFromOrganisation = function(id){
           IssuesSection.getIssuesByOrg(id).then(function(response){
                $scope.issues = response.data;
           })
         }

          $scope.loadPostsFromDep = function(id){
            IssuesSection.getIssuesByDep(id).then(function(response){
                 $scope.issues = response.data;
            })
          }

         $scope.loadPostsFromProject = function(id){
            IssuesSection.getIssuesByProj(id).then(function(response){
                $scope.issues = response.data;
           })
         }
        $scope.delete = function (id) {
            Issue.get({id: id}, function(result) {
                $scope.issue = result;
                $('#deleteIssueConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Issue.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteIssueConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.issue = {
                name: null,
                content: null,
                last_updated: null,
                created_date: null,
                rating: null,
                is_public: null,
                id: null
            };
        };

    }

);
