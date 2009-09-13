package org.openfuxml.addon.wiki.emitter.injection;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.util.JdomXmlStreamer;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.content.AbsatzOhne;
import org.openfuxml.content.Medienobjekt;
import org.openfuxml.content.Medienobjekt.Objekttitel;

public class OfxChartEmitter
{
	private static Logger logger = Logger.getLogger(OfxChartEmitter.class);

	private Wikiinjection wikiinjection;
	
	public OfxChartEmitter(Wikiinjection wikiinjection)
	{
		this.wikiinjection=wikiinjection;
	}
	
	private Element createOfxContent()
	{
		AbsatzOhne absatz = new AbsatzOhne();
		absatz.setValue("TestTitel");
		
		Objekttitel objektitel = new Objekttitel();
		objektitel.getAbsatzOhne().add(absatz);
		
		Medienobjekt medienobjekt = new Medienobjekt();
		medienobjekt.setGleiten("ja");
		medienobjekt.setId("testId");
		medienobjekt.setObjekttitel(objektitel);
		
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