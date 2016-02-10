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
public class HtmlTitleRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	public HtmlTitleRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	/*
	* Methode zum Erzeugen von Überschriften
	* @para title: Title Object, heraus gelesen aus dem Quelldokument
	* @para lvl: Ebene des Titels
	*/
	public void render(Title title, int lvl)
	{
//		StringBuffer sb = new StringBuffer();
//		sb.append(new HtmlElement().createPre("h" + String.valueOf(lvl)));
//		sb.append(TxtTitleFactory.build(title));
//		sb.append(new HtmlElement().createPost("h" + String.valueOf(lvl)));
//
//		txt.add(sb.toString());
	}
}