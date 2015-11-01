'use strict';

describe('Issue Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockIssue, MockUser, MockProject, MockDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockIssue = jasmine.createSpy('MockIssue');
        MockUser = jasmine.createSpy('MockUser');
        MockProject = jasmine.createSpy('MockProject');
        MockDepartment = jasmine.createSpy('MockDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Issue': MockIssue,
            'User': MockUser,
            'Project': MockProject,
            'Department': MockDepartment
        };
        createController = function() {
            $injector.get('$controller')("IssueDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'qorumApp:issueUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
