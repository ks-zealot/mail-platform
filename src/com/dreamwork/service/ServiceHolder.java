/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamwork.service;
 
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
 

/**
 *
 * @author y.lybarskiy
 */
public class ServiceHolder {

  
    private static MailServiceMBean mbean;
    private static Integer port;
    private static String host;
   

    public static void setPort(Integer port) {
        ServiceHolder.port = port;
    }

    public static void setHost(String host) {
        ServiceHolder.host = host;
    }

    public static MailServiceMBean getService() {

        if (mbean == null) {
            try {
                String urlString
                        = System.getProperty("jmx.service.url", "service:jmx:remoting-jmx://" + host + ":" + port);
                JMXServiceURL serviceURL = new JMXServiceURL(urlString);
                HashMap<String, String[]> env = new HashMap<String, String[]>();

                String[] creds = new String[2];

                creds[0] = "jboss";

                creds[1] = "zsq21wax!@12";

                env.put(JMXConnector.CREDENTIALS, creds);
                ObjectName name = new ObjectName("alt1:service=USSD-SDPService");
                
                JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, env);
                MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
               
                mbean = JMX.newMBeanProxy(connection, name, MailServiceMBean.class);

                if (mbean == null) {
                    
                }
                
                return mbean;

            } catch (MalformedURLException ex) {
                 
            } catch (IOException ex) {
               
            } catch (MalformedObjectNameException ex) {
                
            } catch (Exception ex) {
                 
            }
        }
        return mbean;
    }
}
