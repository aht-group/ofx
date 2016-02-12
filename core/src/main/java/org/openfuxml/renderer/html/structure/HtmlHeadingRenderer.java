package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;

/*Klasse für HTML Überschriften, e.g <h1>...</h1>*/
public class HtmlHeadingRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	public HtmlHeadingRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	/*
	* Methode zum Erzeugen von Überschriften
	* @para title: Title Object, heraus gelesen aus dem Quelldokument
	* @para lvl: Ebene des Titels
	*/
	public void render(HtmlElement parent, Title title, int lvl)
	{
		String h$ = "h" + String.valueOf(lvl);
		HtmlElement heading = new HtmlElement(h$);
		heading.addContent(TxtTitleFactory.build(title));

		parent.addContent(heading);
	}
}