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
    
    import flash.utils.Timer;
    import flash.events.TimerEvent;
	
	public class HelloWorld extends DefaultExtension
	{
        
        private var hwTimer : Timer;
        
		public function HelloWorld() {
		}
		
		public override function getFeatureList() : Array {
			return new Array("blackberry.helloworld");
		}
		
		public function text() : String {
			return "Hello World!";		
		}
        
        public function echo(numTimes : Number) : String {
            var echoedGreeting : String = "";
            
            numTimes = Math.max(1, numTimes); //echo at least once even if numTimes is less than 1
            
            for(var i : Number = 0; i < numTimes; i++) {
                echoedGreeting += "Hello World";
            }
			
			return echoedGreeting;
        }
        
        public function onRandomEcho(handlerId : Number) : void {
            var interval : Number = Math.random() * 10000; //want something between 1 and 10 sec
            
            //Create new timer or stop existing one if active 
            if(hwTimer == null) {
				hwTimer = new Timer(interval, 0);
			} else {
				hwTimer.stop();
			} 
            
            hwTimer.addEventListener("timer", function() : void {
                this.evalJavaScriptEvent(handlerId, new Array( "Hello World Event!" ));
            });
            
            hwTimer.start();
        }
	}
}