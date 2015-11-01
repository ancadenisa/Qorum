'use strict';

angular.module('qorumApp').controller('DepartmentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Department', 'User', 'Project', 'Issue',
        function($scope, $stateParams, $modalInstance, entity, Department, User, Project, Issue) {

        $scope.department = entity;
        $scope.users = User.query();
        $scope.projects = Project.query();
        $scope.issues = Issue.query();
        $scope.load = function(id) {
            Department.get({id : id}, function(result) {
                $scope.department = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:departmentUpdate', result);
            $modalInstance.close(result);
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
}]);
