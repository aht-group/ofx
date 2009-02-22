package org.openfuxml.client.control;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.client.control.documents.DocumentFactory;
import org.openfuxml.client.control.documents.DocumentFactoryDirect;
import org.openfuxml.client.control.projects.ProjectFactory;
import org.openfuxml.client.control.projects.ProjectFactoryDirect;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.factory.OfxRequestFactory;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.model.jaxb.Sessionpreferences.Productionentities;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.handler.DirectProducer;
import org.openfuxml.producer.handler.SocketProducer;
import org.openfuxml.server.DummyServer;

public class OpenFuxmlClientControl
{
	static Logger logger = Logger.getLogger(OpenFuxmlClientControl.class);
	 
	private Configuration config;
	private ProjectFactory ofxProjectFactory;
	private DocumentFactory ofxDocumentFactory;
	private Producer producer;
	private ClientGuiCallback guiCallback;

	public OpenFuxmlClientControl(Configuration config, ClientGuiCallback guiCallback)
	{
		this.config=config;
		this.guiCallback=guiCallback;
		ofxProjectFactory = new ProjectFactoryDirect(config);
		ofxDocumentFactory = new DocumentFactoryDirect(config);
		
		DummyServer server = new DummyServer(config);
		producer = new DirectProducer(config,server.getEnvParameter());
	}
	
	public void initProducer()
	{	
		if(config.getString("server").equals("direct"))
		{
			logger.info("Using "+DirectProducer.class.getSimpleName());
			DummyServer server = new DummyServer(config);
			producer = new DirectProducer(config,server.getEnvParameter());
		}
		else
		{
			try
			{
				logger.info("Using "+SocketProducer.class.getSimpleName());
				producer = new SocketProducer(config);
				logger.info("[OK] ProducerThread");
			}
			catch (Exception e)	{logger.fatal("Exception", e);}
		}
	}
	
	public void getProducibleEntities(OfxApplication ofxA, OfxProject ofxP, OfxDocument ofxD)
	{
		OfxRequestFactory ofxReqF = new OfxRequestFactory();
			ofxReqF.setOfxA(ofxA);
			ofxReqF.setOfxP(ofxP);
			ofxReqF.setOfxD(ofxD);
		Sessionpreferences spref = ofxReqF.create();
//			ofxReqF.writeJaxb(System.out, spref);
	}
	
	public void produce(OfxApplication ofxA, OfxProject ofxP, OfxDocument ofxD, OfxFormat ofxF, Productionentities pe)
	{
		OfxRequestFactory orf = new OfxRequestFactory();
			orf.setOfxA(ofxA);
			orf.setOfxP(ofxP);
			orf.setOfxD(ofxD);
			orf.setOfxF(ofxF);
		Sessionpreferences spref = orf.create();
		spref.setProductionentities(pe);
		ProducerThread pt = new ProducerThread(guiCallback,producer);
		pt.produce(spref);
	}
	
	public ProjectFactory getOfxProjectFactory() {return ofxProjectFactory;}
	public DocumentFactory getOfxDocumentFactory() {return ofxDocumentFactory;}
	public Producer getProducer() {return producer;}
}
