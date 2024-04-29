/**
 * 
 */
package vvi.embeddedServers;

import java.io.File;
import java.nio.file.Paths;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * @author vvi
 */
public class TomcatEmbedded {
	private static final int DEFAULT_TOMCAT_PORT = 8080;
	private static final String WEBAPPS_FOLDER = "webapps";

	public static void runWebAppOnEmbeddedTomcat(String appName, int tomcatPort) throws LifecycleException {
		runTomcatWithWar(appName, tomcatPort);
	}

	public static void runWebAppOnEmbeddedTomcat(String appName) throws LifecycleException {
		runTomcatWithWar(appName, DEFAULT_TOMCAT_PORT);
	}

	private static void runTomcatWithWar(String appName, int tomcatPort) throws LifecycleException {
		String currentPath = Paths.get("").toAbsolutePath().toString();
		String warFileName = appName + ".war";
		File warFileToRun = new File(currentPath, warFileName);
		String warFilePath = warFileToRun.getAbsolutePath();

		createWebappFolder(currentPath); // Tomcat needs this folder

		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(currentPath);
		tomcat.setPort(tomcatPort);
		String contextPath = "/" + appName;
		Context context = tomcat.addWebapp(contextPath, warFilePath);
		context.setParentClassLoader(context.getClass().getClassLoader());
		tomcat.getConnector();
		tomcat.enableNaming();
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
			throw e;
		}
		int localPort = tomcat.getConnector().getLocalPort();
		String msg = "Cannot start Tomcat on port " + tomcatPort;
		if (localPort != tomcatPort) {
			System.out.println(msg);
			throw new LifecycleException(msg);
		}
		System.out.println("Application started on " + "http://localhost:" + localPort + contextPath);
		tomcat.getServer().await();
	}

	private static void createWebappFolder(String currentPath) {
		File webappsDirectory = new File(currentPath, WEBAPPS_FOLDER);
		if (webappsDirectory.exists()) {
			System.out.println("webapps folder already exists");
		} else {
			webappsDirectory.mkdir();
		}
	}

}
