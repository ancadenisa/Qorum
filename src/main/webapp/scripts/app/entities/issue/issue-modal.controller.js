/**
 * Created by Marian on 11/8/2015.
 */
'use strict';

angular.module('qorumApp').controller('IssueModalController',
    ['$scope', '$modalInstance', 'entity', 'Issue', 'User', 'Project', 'Department', 'Tag','Principal',
        function($scope, $modalInstance, entity, Issue, User, Project, Department, Tag, Principal) {

            $scope.isAuthenticated = Principal.isAuthenticated;
            $scope.issue = entity;
            $scope.users = User.query();
            $scope.projects = Project.query();
            $scope.departments = [];
            $scope.tags = Tag.query();

            $scope.getProjectDepartments = function() {
                Department.getForProject({projectId: $scope.issue.project.id},
                    function (result) {
                        $scope.departments = result
                    });
            }

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
                $scope.issue.created_date = new Date();
                $scope.issue.last_updated = new Date();
                User.get({login: "get_current_user"},
                    function(result){
                        $scope.issue.user =  result;
                        if ($scope.issue.id != null) {
                            Issue.update($scope.issue, onSaveFinished);
                        } else {
                            Issue.save($scope.issue, onSaveFinished);
                        }
                    });


            };

            $scope.clear = function() {
                $modalInstance.dismiss('cancel');
            };
        }]);
