package org.openfuxml.model.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.Sessionpreferences;

import de.kisner.util.LoggerInit;

public class OfxRequestFactory extends AbstractJaxbFactory
{
	private OfxApplication ofxA;
	private OfxProject ofxP;
	private OfxDocument ofxD;
	private OfxFormat ofxF;

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
			if(ofxF!=null){spref.setFormat(ofxF.getFormat().getId());}
			spref.setUsername("changeme");
//		writeJaxb(System.out, spref);
//		System.out.close();
		return spref;
	}
	
	public void setOfxA(OfxApplication ofxA) {this.ofxA = ofxA;}
	public void setOfxP(OfxProject ofxP) {this.ofxP = ofxP;}
	public void setOfxD(OfxDocument ofxD) {this.ofxD = ofxD;}
	public void setOfxF(OfxFormat ofxF) {this.ofxF = ofxF;}
	
	public void write(Sessionpreferences spref, File f)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
			writeJaxb(fos,spref);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		OfxRequestFactory orf = new OfxRequestFactory();
		orf.create();
	}
}
