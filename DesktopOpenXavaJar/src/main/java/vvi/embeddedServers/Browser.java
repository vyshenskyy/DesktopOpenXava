package vvi.embeddedServers;


import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class Browser {

    public static void browser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(URI.create(url));
                }
            }
        } catch (IOException | InternalError e) {
            e.printStackTrace();
            System.out.println("Cannot open " + url + " in the default browser");
        }
    }
}