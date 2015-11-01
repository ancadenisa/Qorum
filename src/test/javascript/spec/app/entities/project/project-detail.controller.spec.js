'use strict';

describe('Project Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProject, MockDepartment, MockUser, MockIssue;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProject = jasmine.createSpy('MockProject');
        MockDepartment = jasmine.createSpy('MockDepartment');
        MockUser = jasmine.createSpy('MockUser');
        MockIssue = jasmine.createSpy('MockIssue');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Project': MockProject,
            'Department': MockDepartment,
            'User': MockUser,
            'Issue': MockIssue
        };
        createController = function() {
            $injector.get('$controller')("ProjectDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'qorumApp:projectUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
