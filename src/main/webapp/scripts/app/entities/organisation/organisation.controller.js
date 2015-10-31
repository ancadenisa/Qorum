'use strict';

angular.module('qorumApp')
    .controller('OrganisationController', function ($scope, Organisation, ParseLinks) {
        $scope.organisations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Organisation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.organisations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Organisation.get({id: id}, function(result) {
                $scope.organisation = result;
                $('#deleteOrganisationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Organisation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrganisationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.organisation = {
                name: null,
                address: null,
                id: null
            };
        };
    });
