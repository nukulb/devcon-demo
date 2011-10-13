package blackberry;

import java.util.Random;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.util.StringUtilities;

public class HelloWorldImpl {

    private static Runnable _randomEchoEvent;
    private static Object _randomEchoEventLock = new Object();
    private static boolean _isListening = false;

    {
        _randomEchoEvent = new Runnable() {
            public void run() {
                HelloWorldImpl.onRandomEchoEvent();
            }
        };
        UiApplication.getUiApplication().invokeLater(_randomEchoEvent, (new Random()).nextInt(), true);

    }

    static void onRandomEchoEvent(){
        if(_isListening){
            synchronized (_randomEchoEventLock) {
                _randomEchoEventLock.notify();
            }
        }
    }

    public static void listenToRandomEcho(boolean enable) throws Exception{
        if(enable && !_isListening){
            _isListening = true;

            //Blocking wait until the event actually happens
            //Keeps the request open until the event
            synchronized (_randomEchoEventLock) {
                _randomEchoEventLock.wait();
            }

            //Checks whether we were notified by the event or the removal of a monitor
            if(!_isListening){
                throw new Exception( "Random Echo Event no longer monitored" );
            }

        }else if(!enable && _isListening){
            _isListening= false;
            synchronized (_randomEchoEventLock) {
                _randomEchoEventLock.notify();
            }
        }
    }

    public static String echo(int count){
        StringBuffer resultBuffer = new StringBuffer("Hello World");
        StringBuffer helloWorldBuffer = new StringBuffer("Hello World");

        for(int i=1;i<count;i++){
            resultBuffer.append(' ');
            StringUtilities.append(resultBuffer, helloWorldBuffer);
        }

        return resultBuffer.toString();
    }
}