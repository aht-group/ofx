package org.openfuxml.model.factory;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.util.config.factory.ClientConfFactory;

import de.kisner.util.LoggerInit;

public class OfxRequestFactory extends AbstractJaxbFactory
{
	private OfxApplication ofxA;
	private OfxProject ofxP;
	private OfxDocument ofxD;
	
	public OfxRequestFactory()
	{
		
	}
	
	public Sessionpreferences create()
	{
		Sessionpreferences spref = new Sessionpreferences();
			spref.setVersion("TODO");
			spref.setReqType("hmmm");
			spref.setApplication(ofxA.getName());
			spref.setProject(ofxP.getName());
			spref.setDocument(ofxD.getName());
//		writeJaxb(System.out, spref);
//		System.out.close();
		return spref;
	}
	
	public void setOfxA(OfxApplication ofxA) {this.ofxA = ofxA;}
	public void setOfxP(OfxProject ofxP) {this.ofxP = ofxP;}
	public void setOfxD(OfxDocument ofxD) {this.ofxD = ofxD;}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		OfxRequestFactory orf = new OfxRequestFactory();
		orf.create();
	}
}
