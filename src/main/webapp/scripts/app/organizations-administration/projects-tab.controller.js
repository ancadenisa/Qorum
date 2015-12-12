'use strict';
angular.module('qorumApp')
    .controller('ProjectsTabController',function ($scope, $modalInstance, $rootScope, $window, $stateParams, entity, Department, Project, User, OrganizationsCrud) {
        $scope.project = entity;
        $scope.departments = angular.element(document.getElementById('wrapperOrganizations')).scope().departments;
        $scope.organization = angular.element(document.getElementById('wrapperOrganizations')).scope().organization;
        $scope.users = [];

        var onSaveFinished = function (result) {
            OrganizationsCrud.getProjectsByOrganization($scope.organization.id).then(
                function(response){
                    angular.element(document.getElementById('wrapperOrganizations')).scope().projects = response.data
                }
            );
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.project.id != null) {
                Project.update($scope.project, onSaveFinished);
            } else {
                Project.save($scope.project, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.$watch('project.departments', function(newValue, oldValue) {
            $scope.users = [];
            angular.forEach($scope.project.departments, function(department, value){
                angular.forEach(department.users, function(user, value){
                    var existsUser = false;
                    angular.forEach($scope.users, function(userOnScope, value){
                        if(userOnScope.id == user.id){
                            existsUser = true;
                        }
                     });
                    if(existsUser == false){
                       $scope.users.push(user);
                    }
                });
            });
        });


   }
);
