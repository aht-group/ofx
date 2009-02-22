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
import org.openfuxml.model.ejb.OfxProductionRequest;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.factory.OfxRequestFactory;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
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

	public OpenFuxmlClientControl(Configuration config)
	{
		this.config=config;
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
	
	public void produce(OfxApplication ofxA, OfxProject ofxP, OfxDocument ofxD, OfxFormat ofxF)
	{
		OfxRequestFactory orf = new OfxRequestFactory();
			orf.setOfxA(ofxA);
			orf.setOfxP(ofxP);
			orf.setOfxD(ofxD);
			orf.setOfxF(ofxF);
	
		OfxProductionRequest ofxReq = new OfxProductionRequest();
			ofxReq.setSessionpreferences(orf.create());
			ofxReq.setTyp(OfxProductionRequest.Typ.PRODUCE);
	
	try {
		producer.produce(ofxReq);
	} catch (ProductionSystemException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ProductionHandlerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public ProjectFactory getOfxProjectFactory() {return ofxProjectFactory;}
	public DocumentFactory getOfxDocumentFactory() {return ofxDocumentFactory;}
	public Producer getProducer() {return producer;}
}
