package org.openfuxml.producer.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.openfuxml.client.control.formats.FormatFactory;
import org.openfuxml.client.control.formats.FormatFactoryDirect;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProductionRequest;
import org.openfuxml.model.ejb.OfxProductionResult;
import org.openfuxml.model.factory.OfxProductionResultFactory;
import org.openfuxml.model.factory.OfxRequestFactory;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.Producer;
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
	private String baseDir;
	
	public DirectProducer(Configuration config,EnvironmentParameter envP){this(config,null,envP);}
	public DirectProducer(Configuration config,Host host,EnvironmentParameter envP)
	{
		super(config,envP);
		this.host=host;
		baseDir = config.getString("dirs/dir[@type='basedir']");
		dirOutput = OfxPathHelper.getDir(config, "output");
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
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException
	{
		List<OfxApplication> result = new ArrayList<OfxApplication>();
		FormatFactory ff = new FormatFactoryDirect(config);
		return ff.getFormat(ofxA);
	}
	
	public OfxProductionResult produce(OfxProductionRequest ofxR) throws ProductionSystemException
	{
		Sessionpreferences spref = ofxR.getSessionpreferences();
		int suffixindex = spref.getDocument().indexOf(".xml");
		String docName = spref.getDocument().substring(0,suffixindex);
		
		invoke(spref,ofxR.getTyp());
		String proDir = spref.getProject()+fs+spref.getFormat()+fs+docName;
		File fResult = new File(dirOutput+fs+spref.getApplication()+fs+proDir+fs+"result.xml");
		OfxProductionResultFactory oprf = new OfxProductionResultFactory();
		OfxProductionResult ofxResult = oprf.get(fResult);
		return ofxResult;
	}
	
	private void invoke(Sessionpreferences spref, OfxProductionRequest.Typ invokeType) throws ProductionSystemException
	{
		logger.debug("Invoke aufgerufen mit "+invokeType);
		ProducedEntities producedEntities = new ProducedEntities();
		
		Calendar startTime = Calendar.getInstance();
		ProductionCode pc=ProductionCode.Ok;
		
		String buildfile = baseDir+fs+"applications"+fs+spref.getApplication()+fs+"formats"+fs+spref.getFormat()+fs+"build.xml";
		
		StringBuffer sb = new StringBuffer();
			sb.append(dirOutput);
			sb.append(fs+spref.getApplication());
			sb.append(fs+"sessionpreferences");
			sb.append(fs+spref.getUsername()+"-"+spref.getProject()+"-"+"request.xml");
			
		File fRequest = new File(sb.toString());
		
		OfxRequestFactory orf = new OfxRequestFactory();
		orf.write(spref, fRequest);
		
		//Constructing parameters for spawned Java process
		StringBuffer sbParameters = new StringBuffer();
		
		boolean writeLogFile = false;
		if(writeLogFile)
		{
			switch (invokeType)
			{
				case PRODUCE: 	String logfile= OfxPathHelper.getDir(config, "log")+fs+spref.getProject()+"_"+spref.getDocument() + ".log";
								sbParameters.append(" -logfile " + logfile);
								break;
			}
		}
		sbParameters.append(" -Dilona.home="+ sysprops.getProperty("ilona.home"));
		sbParameters.append(" -Dilona.contentstore="+ OfxPathHelper.getDir(config, "repositry")+fs+spref.getApplication());
		sbParameters.append(" -Dilona.output="+dirOutput+fs+spref.getApplication());
		sbParameters.append(" -Dapplication="+ spref.getApplication());
		sbParameters.append(" -Dcoursename="+ spref.getProject());
		sbParameters.append(" -Dmasterfile="+ spref.getDocument());
			//TODO Thorsten productionDir Methode erstmal nicht berücksichtigt
			//+ " -Ddocumentdir="	+ request.getProductionDir()
		sbParameters.append(" -Ddocumentdir= ");
		sbParameters.append(" -Dformat="+ spref.getFormat());
		sbParameters.append(" -Dusername="+ spref.getUsername());
				
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
			case ENTIITES: 	sbCmd.append(" producableEntities ");break;
		}
		logger.debug("Spawn: "+sbCmd.toString());

//		Unter Windows müssen die Backslashes ersetzt werden
		pc=spawn(sbCmd.toString().replace('\\','/'));
		
		// Get stop time of production process
		Calendar endTime = Calendar.getInstance();
					
//		Create result message
		
		int suffixindex = spref.getDocument().indexOf(".xml");
		String docName = spref.getDocument().substring(0,suffixindex);
		
		String proDir = spref.getProject()+fs +spref.getFormat()+fs
			//TODO Methode überprüfen
		//	+	request.getProductionDir()
			+	docName;
		
		String path =null;
		switch(invokeType)
		{
			case PRODUCE: 	path = dirOutput+fs+spref.getApplication()+ fs + proDir + fs + "result.xml";break;
			case ENTIITES:	path = dirOutput+fs+spref.getApplication()+ fs + proDir + fs + "producableEntities.xml";break;
		}
				
		File xmlFile = new File(path);
		logger.debug("lade XML: " +xmlFile.getAbsolutePath());
		try {producedEntities.loadXML(xmlFile);}
		catch (XmlElementNotFoundException e){throw  new ProductionSystemException(e.getMessage());}
			
//		FuXmlLogger.productionLog( "ProducibleEntities()", request.getApplication(),  request.getProject(), request.getDocument(), request.getFormat(), request.getUsername(), startTime, endTime, pc);
		logger.info("Invoke "+spref.getProject()+"-"+spref.getDocument()+": "+pc);
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
		
		
		String proDir = 	request.getProject() + fs +	request.getFormat() + fs
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