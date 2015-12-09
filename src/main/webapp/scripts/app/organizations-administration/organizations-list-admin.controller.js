'use strict';

angular.module('qorumApp')
    .controller('OrganizationsListAdminController', function ($scope, Organization, ParseLinks) {
        $scope.organizations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Organization.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.organizations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
                $('#deleteOrganizationIntention').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Organization.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrganizationIntention').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.organization = {
                name: null,
                id: null
            };
        };
    });
