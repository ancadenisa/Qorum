'use strict';

angular.module('qorumApp').controller('ProjectDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Project', 'Department', 'User', 'Issue',
        function($scope, $stateParams, $modalInstance, entity, Project, Department, User, Issue) {

        $scope.project = entity;
        $scope.departments = Department.query();
        $scope.users = User.query();
        $scope.issues = Issue.query();
        $scope.load = function(id) {
            Project.get({id : id}, function(result) {
                $scope.project = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:projectUpdate', result);
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
}]);
