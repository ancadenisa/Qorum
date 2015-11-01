'use strict';

describe('Organization Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockOrganization, MockUser, MockDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockOrganization = jasmine.createSpy('MockOrganization');
        MockUser = jasmine.createSpy('MockUser');
        MockDepartment = jasmine.createSpy('MockDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Organization': MockOrganization,
            'User': MockUser,
            'Department': MockDepartment
        };
        createController = function() {
            $injector.get('$controller')("OrganizationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'qorumApp:organizationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
