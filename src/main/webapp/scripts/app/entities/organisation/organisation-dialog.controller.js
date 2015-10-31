'use strict';

angular.module('qorumApp').controller('OrganisationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Organisation', 'User',
        function($scope, $stateParams, $modalInstance, entity, Organisation, User) {

        $scope.organisation = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Organisation.get({id : id}, function(result) {
                $scope.organisation = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:organisationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.organisation.id != null) {
                Organisation.update($scope.organisation, onSaveFinished);
            } else {
                Organisation.save($scope.organisation, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
