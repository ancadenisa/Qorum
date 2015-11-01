'use strict';

angular.module('qorumApp').controller('OrganizationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Organization', 'User', 'Department',
        function($scope, $stateParams, $modalInstance, entity, Organization, User, Department) {

        $scope.organization = entity;
        $scope.users = User.query();
        $scope.departments = Department.query();
        $scope.load = function(id) {
            Organization.get({id : id}, function(result) {
                $scope.organization = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:organizationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.organization.id != null) {
                Organization.update($scope.organization, onSaveFinished);
            } else {
                Organization.save($scope.organization, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
