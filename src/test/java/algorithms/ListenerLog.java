package algorithms;

import logs.log_log.Listener;
import logs.log_log.LogLogData;

public class ListenerLog implements Listener{

    @Override
    public void message() {
	System.out.println("-->" + LogLogData.printLog());
    }
    
}
