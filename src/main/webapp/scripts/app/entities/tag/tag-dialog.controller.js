'use strict';

angular.module('qorumApp').controller('TagDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Tag', 'Issue',
        function($scope, $stateParams, $modalInstance, entity, Tag, Issue) {

        $scope.tag = entity;
        $scope.issues = Issue.query();
        $scope.load = function(id) {
            Tag.get({id : id}, function(result) {
                $scope.tag = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('qorumApp:tagUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.tag.id != null) {
                Tag.update($scope.tag, onSaveFinished);
            } else {
                Tag.save($scope.tag, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
