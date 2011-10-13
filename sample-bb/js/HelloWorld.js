(function () {
    var OK = 0,
    
    HelloWorld = function () {};

    function makeCall(toFunction, functionArgs) {
        var request = new blackberry.transport.RemoteFunctionCall('blackberry/helloworld/' + toFunction), argName;

        if (functionArgs) {
            for (argName in functionArgs) {
                if (functionArgs.hasOwnProperty(argName)) {
                    request.addParam(argName, functionArgs[argName]);
                }
            }
        }

        var retVal = request.makeSyncCall();
        
        if (retVal.code < OK) {
            throw new Error(retVal.msg);   
        }
        
        return retVal.data;
    }

    HelloWorld.prototype.__defineGetter__('text', function () {
        return makeCall('text').text;
    });

    HelloWorld.prototype.echo = function (count) {
        if (!count || count < 1) {
            count = 1;
        }
        
        return makeCall('echo', { 'count' : count }).echo;
    };

    HelloWorld.prototype.onRandomEcho = function (onRandomEcho) {
        
        if (typeof onRandomEcho !== 'function') {
            throw new Error('Argument to onRandomEcho must be a callback function.');
        }
        
        var args = {'monitor' : true };
        
        blackberry.transport.poll('blackberry/helloworld/onRandomEcho/',
            { 'get' : args },
            
            function (response) {
                //Only continue polling is response was OK
                if (response.code === OK) {
                    onRandomEcho();
                    return true;
                }
                
                return false;
            }
        );
    };
    
    HelloWorld.prototype.__defineGetter__('CONSTANT', function () { return 42; });
    
    blackberry.Loader.loadApi('blackberry.helloworld', HelloWorld);
}());