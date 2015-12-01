'use strict';
angular.module('qorumApp')
    .controller('OrganizationsCrudController',function ($scope, $rootScope, $window, $stateParams, User, Organization, OrganizationsCrud, Department, Project, orgToBeEdited) {
          $scope.tabs = [
            { title:'Organizatie'},
            { title:'Departamente', disabled: true },
            { title:'Proiecte', disabled: true }
          ];


          User.get({login: "get_current_user"}, function(result){$scope.loggedUser = result});
          $scope.organizationSaved = false;
          $scope.organization = {};
          $scope.alerts = [];
          $scope.departments = [];
          $scope.department = {};
          $scope.orgSaved = false;
          $scope.project = {};
          $scope.projects = [];

          if(orgToBeEdited.$promise != null){
                orgToBeEdited.$promise.then(function(result){
                    $scope.organization = result;
                    OrganizationsCrud.getDepartmentsByOrgId($scope.organization.id).then(
                        function(response){
                            $scope.departments = response.data
                            }
                        );
                    OrganizationsCrud.getProjectsByOrganization($scope.organization.id).then(
                        function(response){
                            $scope.projects = response.data
                        }
                    );
                }
          )
          }else{
              $scope.organization = orgToBeEdited;
          }

          $scope.isNameCompleted = function(){
            return $scope.organization.name != null && $scope.organization.name != " ";
          }

          $scope.submitOrganization  = function(){
                $scope.organization.orgAdmin = $scope.loggedUser;
                Organization.update($scope.organization).$promise.then(
                    function onSuccess(response){
                        $scope.alerts.push({type:"success", msg: 'Organizatia cu numele ' +  $scope.organization.name + ' a fost salvata! Continuati cu adaugarea altor informatii despre organizatia dvs.!'});
                        $scope.orgSaved  = true;
                        $scope.organization = response;
                        OrganizationsCrud.getDepartmentsByOrgId($scope.organization.id).then(
                            function(response){
                                $scope.departments = response.data
                                }
                            );
                        OrganizationsCrud.getProjectsByOrganization($scope.organization.id).then(
                            function(response){
                                $scope.projects = response.data
                            }
                        );

                    }
                )

          }


          $scope.closeAlert = function(index) {
              $scope.alerts.splice(index, 1);
          };

          $scope.confirmDelete = function (id) {
              Department.delete({id: id},
                  function () {
                     OrganizationsCrud.getDepartmentsByOrgId($scope.organization.id).then(
                        function(response){
                            $scope.departments = response.data
                            }
                        );
                      $('#deleteDepartmentIntention').modal('hide');
                      $scope.clear();
                  });
          };

          $scope.delete = function (id) {
              Department.get({id: id}, function(result) {
                  $scope.department = result;
                  $('#deleteDepartmentIntention').modal('show');
              });
          };


          $scope.deleteProject = function (id) {
              Project.get({id: id}, function(result) {
                  $scope.project = result;
                  $('#deleteProjectIntention').modal('show');
              });
          };

          $scope.confirmDeleteProject = function (id) {
              Project.delete({id: id},
                  function () {
                     OrganizationsCrud.getProjectsByOrganization($scope.organization.id).then(
                        function(response){
                            $scope.projects = response.data
                        }
                      );
                      $('#deleteProjectIntention').modal('hide');
                      $scope.clear();
                  });
          };


          $scope.clear = function() {
//              $modalInstance.dismiss('cancel');
          };


    }
);
