package org.openfuxml.producer.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.Format;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.util.config.OfxPathHelper;

import de.kisner.util.architecture.EnvironmentParameter;
import de.kisner.util.io.spawn.Spawn;
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
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	public static enum ProductionCode {Ok, InternalError, BuildError};
	
	private Host host;
	private String dirOutput;
	
	public DirectProducer(Configuration config,EnvironmentParameter envP){this(config,null,envP);}
	public DirectProducer(Configuration config,Host host,EnvironmentParameter envP)
	{
		super(config,envP);
		this.host=host;
		String baseDir = config.getString("dirs/dir[@type='basedir']");
		//TODO Relative PATH
		dirOutput = baseDir+fs+config.getString("dirs/dir[@type='output']");
	}
	
	public List<OfxApplication> getAvailableApplications() throws ProductionSystemException,ProductionHandlerException
	{
		List<OfxApplication> result = new ArrayList<OfxApplication>();
		
		File dirApp = new File(config.getString("dirs/dir[@type='basedir']")+fs+"applications");
		logger.debug("Suche in "+dirApp);
		if (dirApp.exists() && dirApp.isDirectory())
		{
			for(File dirEntry : dirApp.listFiles())
			{
				boolean isDir = dirEntry.isDirectory();
				boolean isSvn = dirEntry.getAbsolutePath().endsWith(".svn");
				if(isDir && !isSvn)
				{
					OfxApplication ofxA = new OfxApplication();
					ofxA.setName(dirEntry.getName());
					File dirSessionpreferences = new File(OfxPathHelper.getDir(config, "output")+fs+dirEntry.getName()+fs+"sessionpreferences");
					if(!dirSessionpreferences.exists())
					{
						logger.debug("Creating "+dirSessionpreferences.getAbsolutePath());
						dirSessionpreferences.mkdirs();
					}
					result.add(ofxA);
				}
			}
		}
		return result;
	}
	
	/**
	 * Gibt verfügbare Formate zurück
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
		
		String buildfile = sysprops.getProperty("ilona.home") + fs + "applications" + fs 
				+ request.getApplication() + fs + "formats" + fs
				+ request.getFormat() + fs + "build.xml";
		
		StringBuffer sb = new StringBuffer();
			sb.append(dirOutput);
			sb.append(fs+request.getApplication());
			sb.append(fs+"sessionpreferences");
			sb.append(fs+request.getUsername()+"-"+request.getProject()+"-"+"request.xml");
			
		File fRequest = new File(sb.toString());
		XmlObject xmlRequest = new XmlObject(request.toXmlDoc());
		if(!xmlRequest.save(fRequest))
		{
			//TODO Fehlercode hinzufügen
			logger.error("Error writing request: " + fRequest.getAbsolutePath());
			logger.debug("\tdirOutput="+dirOutput);
			logger.debug("\tapplication="+request.getApplication());
			return producedEntities;
		}
		//Constructing parameters for spawned Java process
		StringBuffer sbParameters = new StringBuffer();
		
		boolean writeLogFile = false;
		if(writeLogFile)
		{
			switch (invokeType)
			{
				case PRODUCE: 	String logfile= sysprops.getProperty("logger.path") + fs + request.getProject() + "_" + request.getDocument() + ".log";
								sbParameters.append(" -logfile " + logfile);
								break;
			}
		}
		sbParameters.append(" -Dilona.home="+ sysprops.getProperty("ilona.home"));
		sbParameters.append(" -Dilona.contentstore="+ sysprops.getProperty("ilona.contentstore")+File.separator+request.getApplication());
		sbParameters.append(" -Dilona.output="+dirOutput+File.separator+request.getApplication());
		sbParameters.append(" -Dapplication="+ request.getApplication());
		sbParameters.append(" -Dcoursename="+ request.getProject());
		sbParameters.append(" -Dmasterfile="+ request.getDocument());
			//TODO Thorsten productionDir Methode erstmal nicht berücksichtigt
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

//		Unter Windows müssen die Backslashes ersetzt werden
		pc=spawn(sbCmd.toString().replace('\\','/'));
		
		// Get stop time of production process
		Calendar endTime = Calendar.getInstance();
					
//		Create result message
		
		
		String proDir = 	request.getProject() + File.separator
			+	request.getFormat() + File.separator
			//TODO Methode überprüfen
		//	+	request.getProductionDir()
			+	request.toDocumentName();
		
		String path =null;
		switch(request.getTyp())
		{
			case PRODUCE: 	path = dirOutput+fs+request.getApplication()+ fs + proDir + fs + "result.xml";break;
			case ENTITIES:	path = dirOutput+fs+request.getApplication()+ fs + proDir + fs + "producableEntities.xml";break;
		}
				
		File xmlFile = new File(path);
		logger.debug("lade XML: " +xmlFile.getAbsolutePath());
		try {producedEntities.loadXML(xmlFile);}
		catch (XmlElementNotFoundException e){throw  new ProductionSystemException(e.getMessage());}
			
//		FuXmlLogger.productionLog( "ProducibleEntities()", request.getApplication(),  request.getProject(), request.getDocument(), request.getFormat(), request.getUsername(), startTime, endTime, pc);
		logger.info("Invoke "+request.getProject()+"-"+request.getDocument()+": "+pc);
		
		return producedEntities;
	}
	
	private ProductionCode spawn(String cmd) throws ProductionSystemException
	{
		ProductionCode pc = ProductionCode.Ok;
		
		Spawn spawn = new Spawn(cmd);
			spawn.setEnvParameter(envP);
			spawn.cmd();
		int exitValue = spawn.getExitValue();
		if (exitValue != 0) 
			pc = ProductionCode.BuildError;
		else 
			pc = ProductionCode.Ok;
		
		return pc;
	}
}