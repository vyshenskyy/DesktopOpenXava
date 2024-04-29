package vvi.runOpenXava;

import java.io.IOException;

import org.apache.catalina.LifecycleException;

import vvi.embeddedServers.*;

/**
 * @author vvi
 */
public class RunWithServers {

	private static final int TOMCAT_PORT = 8080;
	private static final boolean SKIP_EXISTING_FILE = true;

	/**
	 * The name of Web application that will be run on the embedded Tomcat server.
	 */
	private static final String APP_NAME = "OpenXavaSample";

	/**
	 * 
	 * Deploys application {@code .war} file from .jar resources
	 * to current working folder.
	 * <p>
	 * Runs embedded hsql server.
	 * <p>
	 * Runs embedded Tomcat server and use application .war
	 * 
	 * @throws Exception
	 * 
	 */
	public static void main(String[] args) {
		String version = System.getProperty("java.version");
		System.out.println("java version= " + version);

		// Copy .war file to working folder
		DeployToTomcat deployToTomcat = new DeployToTomcat();
		try {
			deployToTomcat.deployToWorkingFolder(APP_NAME, SKIP_EXISTING_FILE);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Start embedded HSQLDB database
		new Thread(() -> {
			try {
				DBServer.start(APP_NAME);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}).start();
		// Run embedded Tomcat and use application .war file
		new Thread(() -> {
			try {
				TomcatEmbedded.runWebAppOnEmbeddedTomcat(APP_NAME, TOMCAT_PORT);
			} catch (LifecycleException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}).start();

		// Launch application in the default browser.
		try {
			Thread.sleep(2000); // Wait a little - Tomcat will start soon in its thread
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String myUrl = "http://localhost:" + TOMCAT_PORT + "/" + APP_NAME;
		Browser.browser(myUrl);

	}

}
