(function () {
	var OK = 0,
	    HelloWorld = function () {};

	function makeCall(toFunction, functionArgs) {
		var request = new blackberry.transport.RemoteFunctionCall('blackberry/helloworld/' + toFunction), argName,
		    retVal;

		if (functionArgs) {
			for (argName in functionArgs) {
				if (functionArgs.hasOwnProperty(argName)) {
					request.addParam(argName, functionArgs[argName]);
				}
			}
		}

		retVal = request.makeSyncCall();

		if(retVal.code < OK) {
		    throw new Error(retVal.msg);   
		}

		return retVal.data;
	}

	HelloWorld.prototype.sayHello = function (name) {

	//	return makeCall('sayHello', { 'name' : name.toString() }).sayHello;
	};

	blackberry.Loader.loadApi('blackberry.helloworld', HelloWorld);
}());
