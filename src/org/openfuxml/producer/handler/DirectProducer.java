package org.openfuxml.producer.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.producer.ejb.Application;
import org.openfuxml.producer.ejb.AvailableApplications;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.Format;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.server.AbstractServer;
import org.openfuxml.util.FuXmlLogger;

import de.kisner.util.xml.XmlConfig;
import de.kisner.util.xml.XmlElementNotFoundException;
import de.kisner.util.xml.XmlObject;

/**
 * This class encapsulates the production server which produces output documents 
 * for a specified XML document. For every service request an Ant build process is started.
 * After finishing the build process a {@link SSIMessage} is returned to the client which
 * contains the result of the service.
 * 
 */
public class DirectProducer extends AbstractProducer implements Producer
{
	static Logger logger = Logger.getLogger(DirectProducer.class);
	
	private static Properties sysprops = System.getProperties();
	private static String fSep = sysprops.getProperty("file.separator");
	
	public static enum ProductionCode {Ok, InternalError, BuildError};
	
	private Host host;
	private String dirOutput;
	
	public DirectProducer(XmlConfig xCnf,Host host)
	{
		this.host=host;
		String baseDir;
		try {baseDir = xCnf.getTextException("dirs/dir[@typ=\"basedir\"]");}
		catch (XmlElementNotFoundException e)
		{
			baseDir = xCnf.getWorkingDir();
			logger.warn("No \"baseDir\" defined in xmlConfig. Using WorkingDir: "+baseDir);
		}
		dirOutput=xCnf.getPath("dirs/dir[@typ=\"output\"]",baseDir);
	}
	
	/**
	 * Gibt verf�gbare Applikationen zur�ck
	 * @author Thorsten
	 */
	public AvailableApplications getAvailableApplications() throws ProductionSystemException
	{
		AvailableApplications aas= new AvailableApplications();
		aas.setHost(host);
		aas.setRecord(new Date());
		File dirApplications = new File(sysprops.getProperty("ilona.home") + java.io.File.separator + "applications");
		if (dirApplications.isDirectory() && dirApplications.exists())
		{
			for(File dirEntry : dirApplications.listFiles())
			{
				if(dirEntry.isDirectory())
				{
					Application a = new Application();
					a.setAvailableApplications(aas);
					a.setName(dirEntry.getName());
					a.setAnzCores(1);
					aas.addApplication(a);
					File sessionpreferences = new File(dirOutput+fSep+dirEntry.getName()+fSep+"sessionpreferences");
					if(!sessionpreferences.exists()){sessionpreferences.mkdirs();}
				}
			}
		}
		else
		{
			logger.fatal("Invalid Application-Directoriy: "+dirApplications.getAbsolutePath());
			throw new ProductionSystemException("Invalid Application-Directoriy: "+dirApplications.getAbsolutePath());
		}
		return aas;
	}
	
	/**
	 * Gibt verf�gbare Formate zur�ck
	 * @author Thorsten
	 */
	public AvailableFormats getAvailableFormats(String application) throws ProductionSystemException
	{
		AvailableFormats availableFormats = new AvailableFormats();
		availableFormats.setHost(host);
		availableFormats.setRecord(new Date());
		File dirFormats = new File(sysprops.getProperty("ilona.home") + File.separator + "applications"+ File.separator + application + File.separator + "formats");
		logger.debug("getAvailableFormats("+application+"): "+dirFormats.getAbsoluteFile()+" exists:"+dirFormats.exists()+" isDirectory:"+dirFormats.isDirectory());
		if (dirFormats.exists() && dirFormats.isDirectory())
		{
			for(File dirEntry : dirFormats.listFiles())
			{
				File fFormats = new File(dirEntry.getAbsoluteFile() + File.separator + "format.xml");
				if (fFormats.exists())
				{
					Format f = new Format();
					f.setAvailableFormats(availableFormats);
					try{f.loadXML(fFormats);}
					catch (XmlElementNotFoundException e) {logger.error(e);}
					availableFormats.addFormat(f);
				}
			}
		}
		return availableFormats;
	}
	
	public ProducedEntities invoke(org.openfuxml.producer.ejb.ProductionRequest request) throws ProductionSystemException
	{
		org.openfuxml.producer.ejb.ProductionRequest.Typ invokeType = request.getTyp();
		logger.debug("Invoke aufgerufen mit "+invokeType);
		ProducedEntities producedEntities = new ProducedEntities();
		producedEntities.setTyp(invokeType);
		
		Calendar startTime = Calendar.getInstance();
		ProductionCode pc=ProductionCode.Ok;
		
		String logfile="";
		switch (invokeType)
		{
			case PRODUCE: 	logfile= sysprops.getProperty("logger.path") + fSep + 
							request.getProject() + "_" + request.getDocument() + ".log";break;
		}
		
		String buildfile = sysprops.getProperty("ilona.home") + fSep + "applications" + fSep 
				+ request.getApplication() + fSep + "formats" + fSep
				+ request.getFormat() + fSep + "build.xml";
		
		StringBuffer sb = new StringBuffer();
			sb.append(dirOutput);
			sb.append(fSep+request.getApplication());
			sb.append(fSep+"sessionpreferences");
			sb.append(fSep+request.getUsername()+"-"+request.getProject()+"-"+"request.xml");
			
		File fRequest = new File(sb.toString());
		XmlObject xmlRequest = new XmlObject(request.toXmlDoc());
		if(!xmlRequest.save(fRequest))
		{
			//TODO Fehlercode hinzuf�gen
			logger.error("Error writing request: " + fRequest.getAbsolutePath());
			logger.debug("\tdirOutput="+dirOutput);
			logger.debug("\tapplication="+request.getApplication());
			return producedEntities;
		}
		//Constructing parameters for spawned Java process
		StringBuffer sbParameters = new StringBuffer();
		switch (invokeType)
		{
			case PRODUCE: 	sbParameters.append(" -logfile " + logfile);break;
		}
		sbParameters.append(" -Dilona.home="+ sysprops.getProperty("ilona.home"));
		sbParameters.append(" -Dilona.contentstore="+ sysprops.getProperty("ilona.contentstore")+File.separator+request.getApplication());
		sbParameters.append(" -Dilona.output="+dirOutput+File.separator+request.getApplication());
		sbParameters.append(" -Dapplication="+ request.getApplication());
		sbParameters.append(" -Dcoursename="+ request.getProject());
		sbParameters.append(" -Dmasterfile="+ request.getDocument());
			//TODO Thorsten productionDir Methode erstmal nicht ber�cksichtigt
			//+ " -Ddocumentdir="	+ request.getProductionDir()
		sbParameters.append(" -Ddocumentdir= ");
		sbParameters.append(" -Dformat="+ request.getFormat());
		sbParameters.append(" -Dusername="+ request.getUsername());
				
		logger.debug("Parameters: " + sbParameters);
		logger.debug("Buildfile:" +buildfile);
		logger.debug("Ant Home:" + sysprops.getProperty("ant.home"));
		
		StringBuffer sbCmd = new StringBuffer(); 
		sbCmd.append("java ");
		sbCmd.append(" -Dant.home="+sysprops.getProperty("ant.home"));
		sbCmd.append(" org.apache.tools.ant.Main ");
		sbCmd.append("-buildfile "	+ buildfile);
		sbCmd.append(" "+ sbParameters.toString()+ " ");
		switch (invokeType)
		{
			case ENTITIES: 	sbCmd.append(" producableEntities ");break;
		}
		logger.debug("Spawn: "+sbCmd.toString());

//		Unter Windows m�ssen die Backslashes ersetzt werden
		pc=spawn(sbCmd.toString().replace('\\','/'));

		
		
		// Get stop time of production process
		Calendar endTime = Calendar.getInstance();
					
//		Create result message
		
		
		String proDir = 	request.getProject() + File.separator
			+	request.getFormat() + File.separator
			//TODO Methode �berpr�fen
		//	+	request.getProductionDir()
			+	request.toDocumentName();
		
		String path =null;
		switch(request.getTyp())
		{
			case PRODUCE: 	path = dirOutput+fSep+request.getApplication()+ fSep + proDir + fSep + "result.xml";break;
			case ENTITIES:	path = dirOutput+fSep+request.getApplication()+ fSep + proDir + fSep + "producableEntities.xml";break;
		}
				
		File xmlFile = new File(path);
		logger.debug("lade XML: " +xmlFile.getAbsolutePath());
		try {producedEntities.loadXML(xmlFile);}
		catch (XmlElementNotFoundException e){throw  new ProductionSystemException(e.getMessage());}
			
		FuXmlLogger.productionLog( "ProducibleEntities()", request.getApplication(),  request.getProject(), request.getDocument(), request.getFormat(), request.getUsername(), startTime, endTime, pc);
		
		return producedEntities;
	}
	
	private ProductionCode spawn(String cmd) throws ProductionSystemException
	{
		
		ProductionCode pc = ProductionCode.Ok;
		String s;
		String stdlog = "";
		String errlog = "";
		try
		{
			logger.debug("Ant Call:" + cmd);
			Process p = Runtime.getRuntime().exec(cmd,AbstractServer.environmentParameters);
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			FuXmlLogger.spawn.debug("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null)
			{
				FuXmlLogger.spawn.debug(s);
				stdlog = stdlog + s + "\n";
			}
			FuXmlLogger.spawn.debug("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null)
			{ 
				FuXmlLogger.spawn.error(s);;
				errlog = errlog + s + "\n";
			}
			
			int buildresult = 1;
			try {buildresult = p.waitFor();}
			catch(InterruptedException e)
			{ 
				logger.error("Interrupted while waiting for end of build process!\n" 
				  + e.toString());
			}
			
			if (buildresult != 0) 
				pc = ProductionCode.BuildError;
			else 
				pc = ProductionCode.Ok;
			
			FuXmlLogger.spawn.info("Process Exit Value="+ buildresult);
		}
		
		catch(IOException e)
		{	//Process cannot be started
			logger.error("Error during ant call!\n" + e.toString());
			pc = ProductionCode.InternalError;
			
			//This is not due to an error in the build file
			//Hence, we throw a ProductionSystemException to signal a system error
			throw new ProductionSystemException();
		}
		return pc;
	}
}