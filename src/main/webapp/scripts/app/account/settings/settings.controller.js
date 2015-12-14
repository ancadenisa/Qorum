'use strict';

angular.module('qorumApp')
    .controller('SettingsController', function ($scope, Principal, Auth, Language, $translate, Issue) {
        $scope.success = null;
        $scope.error = null;
        Principal.identity(true).then(function(account) {
            $scope.settingsAccount = account;
        });

        Issue.getForCurrentUser({page: 0, size: 10, sort: 'rating,DESC'},
            function (result) {
                $scope.issuesTopRated = result;
            });

        $scope.timeago = function(date) {
            if (date != null) {
                return jQuery.timeago(date);
            }
            else {
                return "Created date not available!";
            }
        }

        $scope.save = function () {
            Auth.updateAccount($scope.settingsAccount).then(function() {
                $scope.error = null;
                $scope.success = 'OK';
                Principal.identity().then(function(account) {
                    $scope.settingsAccount = account;
                });
                Language.getCurrent().then(function(current) {
                    if ($scope.settingsAccount.langKey !== current) {
                        $translate.use($scope.settingsAccount.langKey);
                    }
                });
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
            });
        };
    });
