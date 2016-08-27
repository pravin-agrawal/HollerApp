package com.holler.util;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class ServerLauncher {
    public static void main(final String[] args) throws ServletException,
            LifecycleException {
        String webappDirLocation = "src/main/webapp/";
        File webAppDir = new File(webappDirLocation);
        if (!webAppDir.exists()) {
            webAppDir = new File("holler-web/src/main/webapp/");
        }
        Tomcat tomcat = new Tomcat();
        String userHome = System.getProperty("user.home");


        // The port that we should run on can be set into an environment
        // variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8084";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", webAppDir.getAbsolutePath());
        System.out.println("configuring app with basedir: "
                + webAppDir.getAbsolutePath());

        Connector httpsConnector = new Connector();
        httpsConnector.setPort(8443);
        //httpsConnector.setSecure(true);
        //httpsConnector.setScheme("https");
        //httpsConnector.setAttribute("keystorePass", "qwer1234");
        //httpsConnector.setAttribute("keystoreFile", keyStorePath);
        httpsConnector.setAttribute("clientAuth", "false");
        //httpsConnector.setAttribute("sslProtocol", "TLS");
        //httpsConnector.setAttribute("SSLEnabled", true);
        Service service = tomcat.getService();
        service.addConnector(httpsConnector);

        tomcat.start();
        tomcat.getServer().await();
    }
}