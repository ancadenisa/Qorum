'use strict';
angular.module('qorumApp')
    .controller('OrganizationsDepartmentsCrudController',function ($scope, $modalInstance, $rootScope, $window, $stateParams, User, Department, organizationSaved, OrganizationsCrud, departmentToBeEdited) {

        $scope.users = User.query();
        $scope.department = {};
        if(departmentToBeEdited.$promise != null){
            departmentToBeEdited.$promise.then(function(result){
                $scope.department = result;
                }
            )
        }else{
            $scope.department = departmentToBeEdited;
        }


        organizationSaved.$promise.then(function(result){
            $scope.department.organization = result;
            }
        )

        var onSaveFinished = function (result) {
            $modalInstance.close(result);
            OrganizationsCrud.getDepartmentsByOrgId($scope.department.organization.id).then(
                function(response){
                    angular.element(document.getElementById('wrapperOrganizations')).scope().departments = response.data
                    }
                )
        };

        $scope.save = function () {
            if ($scope.department.id != null) {
                Department.update($scope.department, onSaveFinished);
            } else {
                Department.save($scope.department, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
   }
);
