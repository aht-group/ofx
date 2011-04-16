package org.openfuxml.addon.wiki.emitter.injection;

import javax.xml.stream.XMLStreamException;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.Ofxgallery.Ofximage;
import org.openfuxml.addon.wiki.util.JdomXmlStreamer;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.content.fuxml.AbsatzOhne;
import org.openfuxml.content.fuxml.Grafik;
import org.openfuxml.content.fuxml.Medienobjekt;
import org.openfuxml.content.fuxml.Medienobjekt.Objekttitel;

public class OfxImageEmitter
{
	static Log logger = LogFactory.getLog(OfxImageEmitter.class);

	private Ofximage image;
	private String fileName;
	
	public OfxImageEmitter(Ofximage image, String fileName)
	{
		this.image=image;
		this.fileName=fileName;
	}
	
	private Element createOfxContent()
	{
		AbsatzOhne absatz = new AbsatzOhne();
		absatz.setValue(image.getValue());
		
		Objekttitel objektitel = new Objekttitel();
		objektitel.getAbsatzOhne().add(absatz);
		
		Grafik grafik = new Grafik();
		grafik.setAlign("left");
		grafik.setFliessen("nicht");
		grafik.setWidth(480);
		grafik.setDepth(320);
		grafik.setScale(1.0);
		grafik.setFileref("../bilder/web/"+fileName+".png");
		
		Medienobjekt medienobjekt = new Medienobjekt();
		medienobjekt.setGleiten("ja");
		medienobjekt.setId("testId");
		medienobjekt.setObjekttitel(objektitel);
		medienobjekt.getGrafik().add(grafik);
		
		Element result = WikiContentIO.toElement(medienobjekt, Medienobjekt.class);
		return result;
	}
	
	public void transform(JdomXmlStreamer jdomStreamer)
	{
		Element e = createOfxContent();
		JDomUtil.debugElement(e);
		try
		{
			jdomStreamer.write(e);
		}
		catch (XMLStreamException e1) {logger.error(e);}
	}
}