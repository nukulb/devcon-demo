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
package blackberry;

import org.w3c.dom.Document;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.ScriptEngine;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.util.SimpleSortingVector;
import net.rim.device.api.web.WidgetConfig;
import net.rim.device.api.web.WidgetException;

import blackberry.common.util.json4j.JSONObject;

import blackberry.common.util.JSUtilities;

import blackberry.core.IJSExtension;
import blackberry.core.JSExtensionRequest;
import blackberry.core.JSExtensionResponse;
import blackberry.core.JSExtensionReturnValue;

public class HelloWorldExtension implements IJSExtension  {
    
	private static final String FEATURE_ID = "blackberry.helloworld";
    
    private static String[] JS_FILES = { "HelloWorld.js" };
    
	private static final String FUNCTION_SAYHELLO = "sayHello";
	private static final String FUNCTION_SAYHELLO_ARG_NAME = "name";

    public String[] getFeatureList() {
        return new String[] { FEATURE_ID };
    }
    
    public void loadFeature( String feature, String version, Document document, ScriptEngine scriptEngine,
            SimpleSortingVector jsInjectionPaths ) {
        JSUtilities.loadJS( scriptEngine, JS_FILES, jsInjectionPaths );
    }

    /**
     * Implements invoke() of interface IJSExtension. Methods of extension will be called here.
     * @throws WidgetException if specified method cannot be recognized
     */
    public void invoke( JSExtensionRequest request, JSExtensionResponse response ) throws WidgetException {
        String method = request.getMethodName();
        Object[] args = request.getArgs();
        String msg = "";
        int code = JSExtensionReturnValue.SUCCESS;
        JSONObject data = new JSONObject();
        JSONObject returnValue = null;

        try {
            if( method.equals( FUNCTION_SAYHELLO ) ) {
                String name = (String) args[ 0 ];
                data.put( FUNCTION_SAYHELLO_ARG_NAME, name );
                data.put( FUNCTION_SAYHELLO, sayHello(name) );
            } 
        } catch( Exception e ) {
            msg = e.getMessage();
            code = JSExtensionReturnValue.FAIL;
        }

        returnValue = new JSExtensionReturnValue( msg, code, data ).getReturnValue();

        response.setPostData( returnValue.toString().getBytes() );
    }
    
    public static String sayHello(String name){
        return "Hello " + name + "! I'm " +  DeviceInfo.getDeviceName();
    }

    public void register( WidgetConfig widgetConfig, BrowserField browserField ) {
    }
    
    public void unloadFeatures() {
    }
}
