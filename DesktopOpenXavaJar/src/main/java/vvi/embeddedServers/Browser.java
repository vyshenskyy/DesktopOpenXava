package vvi.embeddedServers;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

/**
 * Allows to open url in a browser
 */
public class Browser {

	/**
	 * Tries to open {@code url} in the default browser using java awt.
	 * <p> 
	 * If {@code awt} fails, tries to open a browser depending on operating system.
	 * 
	 * @param url open url in a browser
	 */
	public static void browser(String url) {
		if (browserAwt(url)) {
			System.out.println("Default browser is opened using java awt");
		} else {
			browserForOs(url);
		}
	}

	public static boolean browserAwt(String url) {
		try {
			System.out.println("-- Trying java awt to run the default browser ");
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(URI.create(url));
					return true;
				} else {
					System.out.println("Desktop.Action.BROWSE) is not supported ");
				}

			} else {
				System.out.println("isDesktopSupported() returned false ");
			}
		} catch (IOException | InternalError e) {
			e.printStackTrace();
			System.out.println("Cannot open " + url + " in the default browser");
		}
		return false;
	}

	public static void browserForOs(String url) {
		System.out.println("--Trying to open a browser depending on Operating System");
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		try {

			if (os.indexOf("win") >= 0) {
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

			} else if (os.indexOf("mac") >= 0) {
				rt.exec("open " + url);

			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
				String[] browsers = { "google-chrome", "chromium", "epiphany", "firefox", "mozilla", "chromium",
						"konqueror", "netscape", "opera", "links", "lynx" };

				// Build a command string which looks like "browser1 "url" || browser2 "url"
				// ||..."
				StringBuffer cmd = new StringBuffer();
				for (int i = 0; i < browsers.length; i++)
					cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });
			} else {
				System.out.println("Cannot find a browser for this operatin system " + os);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}