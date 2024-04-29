package vvi.embeddedServers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hsqldb.server.ServerConstants;

/**
 * 
 * @author Javier Paniza
 * @author vvi
 */

public class DBServer {
	
	public static void start(String dbName) throws Exception {
		start(dbName, 1666); 
	}
	
	public static void start(String dbName, int port) throws Exception { 
			org.hsqldb.Server hsqlServer = new org.hsqldb.Server();
	        hsqlServer.setSilent(true);
	        hsqlServer.setDatabaseName(0, "");
	        hsqlServer.setDatabasePath(0, "file:data/" + dbName);
	        hsqlServer.setPort(port);      
	        hsqlServer.start();
	        Logger.getLogger("").setLevel(Level.INFO);
	        
	        int state = hsqlServer.getState();
	        if(state != ServerConstants.SERVER_STATE_ONLINE) {
	        	String msg = "Cannot start new HSQLDB server: state = " + hsqlServer.getStateDescriptor();
	        	throw new IllegalStateException(msg);
	        }
	}

}