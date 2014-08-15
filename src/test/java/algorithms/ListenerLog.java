package algorithms;

import java.text.SimpleDateFormat;
import java.util.Date;

import logs.log_log.Listener;
import logs.log_log.LogLogData;

public class ListenerLog implements Listener{

    @Override
    public void message() {
	String now = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ").format(new Date());
	System.out.println(now + LogLogData.printLog());
    }
    
}
