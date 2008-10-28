// Created on 28.04.2004
package org.openfuxml.server.cluster;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Date;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.communication.cluster.facade.OpenFuxmlFacade;
import org.openfuxml.communication.cluster.facade.OpenFuxmlFacadeBean;
import org.openfuxml.communication.cluster.facade.ProducerFacade;
import org.openfuxml.communication.cluster.facade.ProducerFacadeBean;
import org.openfuxml.producer.ejb.Application;
import org.openfuxml.producer.ejb.AvailableApplications;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.Producer;
import org.openfuxml.producer.handler.SyncProducer;
import org.openfuxml.server.AbstractServer;
import org.openfuxml.server.simple.SimpleServerThread;
import org.openfuxml.server.simple.SimpleShutdownThread;
import org.openfuxml.util.FuXmlLogger;

import de.kisner.util.Connector;
import de.kisner.util.xml.XmlConfig;

/**
 * Server oeffnet den ServerSocket und wartet dann auf Clientverbindungen.
 * Fur jede Clientverbindung wird ein neuer ServerThread der Klasse.
 * {@link org.openfuxml.communication.server.simple.SimpleServerThread.class SimpleServerThread}
 * erzeugt. <br>
 * @author Thorsten
 * @version 0.3
 */
public class EnterpriseServer extends AbstractServer
{
	static Logger logger = Logger.getLogger(EnterpriseServer.class);
	
	Host host;
	
	public EnterpriseServer(XmlConfig xCnf)
	{
		logger.info("Applikation wird gestartet");
		host = new Host();
		host.setHostIP(xCnf.getHostIp());
		host.setHostName(xCnf.getHostName());
		host.setRecord(new Date());
		logger.debug("Host "+host.getHostName()+"("+host.getHostIP()+") id="+host.getId());
		
		int serverPort =xCnf.getIntAttribute("net/server[@typ=\"socket\"]","port");
	
		setSystemProperties(xCnf);
		checkSystemProperties();
		
		
		String jndiHost = xCnf.getAttribute("net/server[@typ=\"jndi\"]","host")+":"+xCnf.getAttribute("net/server[@typ=\"jndi\"]","port");
		logger.info("Verbinden nach: "+jndiHost);
		InitialContext ctx=null;
		try {ctx = Connector.getContext(jndiHost);}
		catch (CommunicationException e) {exit("No Connection to JNDI-Server",e);}
		try
		{
			ProducerFacade fP = (ProducerFacade) ctx.lookup(ProducerFacadeBean.class.getSimpleName()+"/remote");
			OpenFuxmlFacade fO = (OpenFuxmlFacade) ctx.lookup(OpenFuxmlFacadeBean.class.getSimpleName()+"/remote");
			
			host=fO.updateHost(host);
			Producer p = new SyncProducer(xCnf,host);
			AvailableApplications aas = p.getAvailableApplications();
			
			for(Application a : aas.getApplications())
			{
				fP.newEjbObject(p.getAvailableFormats(a.getName()));
			}
			logger.debug("getAvailableApplications:"+aas.getHost().getHostName());
			fP.newEjbObject(aas);
			fP.close();
		}
		catch (javax.naming.NameNotFoundException e){exit("Alle EJB-Beans deployed?",e);}
		catch (NamingException e) {e.printStackTrace();}
		catch (ProductionSystemException e) {e.printStackTrace();}
		catch (ProductionHandlerException e) {e.printStackTrace();}
			
		logger.debug("ServerSocket erstellen: "+serverPort);
		ServerSocket serverSocket=null;
		try {serverSocket = new ServerSocket(serverPort);}
		catch (IOException e)
		{
			logger.fatal(e.getMessage());
			logger.fatal("Could not listen on port: "+serverPort);
			logger.fatal("Applikation wird beendet");
            	System.exit(1);
        }
		logger.debug("Server lauscht auf Port "+serverSocket.getLocalPort());
		
		ThreadGroup clientTg = new ThreadGroup("Alle Clients");
		SimpleShutdownThread myShutdownThread = new SimpleShutdownThread(clientTg,serverSocket);
		(Runtime.getRuntime()).addShutdownHook(myShutdownThread);
		try
		{
			while (myShutdownThread.getAppActive())
			{
				SimpleServerThread sst = new SimpleServerThread(clientTg, serverSocket.accept(),new SyncProducer(xCnf,host)); 
				sst.start();
			}
		}
		catch (SocketException e)
		{
			if (myShutdownThread.getAppActive())
			{
				logger.error(e.getMessage());
			}
		}
		catch (IOException e){logger.error(e.getMessage());}

		logger.debug(this.getClass().getSimpleName()+" ist beendet");
	}

	public static void exit(String message, Exception e)
	{
		if(e!=null){logger.fatal(e.getMessage());}
		logger.fatal(message);
		logger.fatal("Applikation wird beendet");
		System.exit(-1);
	}
	
	public static void main(String[] args) throws IOException
	{
		FuXmlLogger.init();
		logger.info("**************************************************************");
		XmlConfig xCnf = new XmlConfig("openFuXML-config.xml", "openFuXML-1.x.xsd");

		new EnterpriseServer(xCnf);
	}
}