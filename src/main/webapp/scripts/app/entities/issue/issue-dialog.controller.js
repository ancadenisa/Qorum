'use strict';

angular.module('qorumApp').controller('IssueDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Issue', 'User', 'Project', 'Department',
        function($scope, $stateParams, $modalInstance, entity, Issue, User, Project, Department) {

        $scope.issue = entity;
        $scope.users = User.query();
        $scope.projects = Project.query();
        $scope.departments = Department.query();
        $scope.load = function(id) {
            Issue.get({id : id}, function(result) {
                $scope.issue = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:issueUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.issue.id != null) {
                Issue.update($scope.issue, onSaveFinished);
            } else {
                Issue.save($scope.issue, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
