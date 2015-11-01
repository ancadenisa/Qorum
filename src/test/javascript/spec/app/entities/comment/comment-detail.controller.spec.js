'use strict';

describe('Comment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockComment, MockUser, MockIssue;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockComment = jasmine.createSpy('MockComment');
        MockUser = jasmine.createSpy('MockUser');
        MockIssue = jasmine.createSpy('MockIssue');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Comment': MockComment,
            'User': MockUser,
            'Issue': MockIssue
        };
        createController = function() {
            $injector.get('$controller')("CommentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'qorumApp:commentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
