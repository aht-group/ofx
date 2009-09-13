package org.openfuxml.addon.wiki.emitter.injection;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.util.JdomXmlStreamer;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.content.AbsatzOhne;
import org.openfuxml.content.Grafik;
import org.openfuxml.content.Medienobjekt;
import org.openfuxml.content.Medienobjekt.Objekttitel;

public class OfxChartEmitter
{
	private static Logger logger = Logger.getLogger(OfxChartEmitter.class);

	private Wikiinjection injection;
	
	public OfxChartEmitter(Wikiinjection injection)
	{
		this.injection=injection;
	}
	
	private Element createOfxContent()
	{
		AbsatzOhne absatz = new AbsatzOhne();
		absatz.setValue("TestTitel");
		
		Objekttitel objektitel = new Objekttitel();
		objektitel.getAbsatzOhne().add(absatz);
		
		Grafik grafik = new Grafik();
		grafik.setAlign("left");
		grafik.setFliessen("nicht");
		grafik.setWidth(480);
		grafik.setDepth(320);
		grafik.setScale(1.0);
		grafik.setFileref("../bilder/web/"+injection.getId()+"-"+injection.getOfxtag()+".png");
		
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
		WikiContentIO.debugElement(e);
		try
		{
			jdomStreamer.write(e);
		}
		catch (XMLStreamException e1) {logger.error(e);}
	}
}