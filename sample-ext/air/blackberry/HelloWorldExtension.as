/*
* Copyright 2010-2011 Research In Motion Limited.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package blackberry
{
	import webworks.extension.DefaultExtension;
    
	import qnx.system.Device;
	
	import json.JSON;
	
	public class HelloWorldExtension extends DefaultExtension
	{
        private const FEATURE_ID:String = "blackberry.helloworld";
		
		public override function getFeatureList() : Array {
			return new Array(FEATURE_ID);
		}
		
		public function sayHello(name : String) : Object {
			var sayHello : Object = {
				'name' : name,
				'sayHello' : "Hello " + name + "! I'm a BlackBerry " + Device.device.os
			};
			
			var returnObject:Object = {
				"code" : 0,
				"msg" : null,
				"data" : sayHello
			};
			
			return returnObject;
		}
		
	}
}
