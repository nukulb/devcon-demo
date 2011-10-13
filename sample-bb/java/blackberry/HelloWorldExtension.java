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
    
	private static final String PROPERTY_TEXT = "text";
    
	private static final String FUNCTION_ECHO ="echo";
	private static final String FUNCTION_ECHO_ARG_COUNT ="count";
	
	private static final String EVENT_RANDOM_ECHO = "onRandomEcho";
	private static final String EVENT_RANDOM_ECHO_ARG_MONITOR = "monitor";

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
            if( method.equals( PROPERTY_TEXT ) ) {
                data.put( PROPERTY_TEXT, "Hello World" );
            }else if( method.equals( FUNCTION_ECHO ) ) {
                int echoCount = parseInt((String) args[ 0 ]);
                data.put( FUNCTION_ECHO_ARG_COUNT, echoCount );
                data.put( FUNCTION_ECHO, HelloWorldImpl.echo(echoCount) );
            } else if( method.equals( EVENT_RANDOM_ECHO ) ) {
            	 String monitor = (String) request.getArgumentByName( EVENT_RANDOM_ECHO_ARG_MONITOR );
                 data.put( EVENT_RANDOM_ECHO_ARG_MONITOR, monitor );

                 HelloWorldImpl.listenToRandomEcho(parseBoolean(monitor));                 
            } 
        } catch( Exception e ) {
            msg = e.getMessage();
            code = JSExtensionReturnValue.FAIL;
        }

        returnValue = new JSExtensionReturnValue( msg, code, data ).getReturnValue();

        response.setPostData( returnValue.toString().getBytes() );
    }

    private static boolean parseBoolean( String str ) {
        return ( str != null && //undefined
        		 str.length() > 0 && //""
        		!str.toLowerCase().equals( Boolean.FALSE.toString().toLowerCase() ) && //FALSE and false
        		!str.equals("0") && //0
        		!str.equals("null")); //null, NaN
    }
    
    private static int parseInt( String str ) {
        if (str != null){
        	return Integer.parseInt(str);
        }
        return 0;
    }
    
    public void register( WidgetConfig widgetConfig, BrowserField browserField ) {
    }
    
    public void unloadFeatures() {
    }
}
