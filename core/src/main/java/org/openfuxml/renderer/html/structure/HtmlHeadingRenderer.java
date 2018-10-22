package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;

/*Klasse für HTML Überschriften, z.B <h1>...</h1>*/
public class HtmlHeadingRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{

	public HtmlHeadingRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}

	/*Methode zum Erzeugen von Überschriften
	* @para title: Title Object, heraus gelesen aus dem Quelldokument
	* @para lvl: Ebene des Titels*/
	public void render(HtmlElement parent, Title title, int lvl)
	{
		if(lvl > 6)
			lvl = 6;
		HtmlElement heading = new HtmlElement(("h" + String.valueOf(lvl)));
		heading.addContent(TxtTitleFactory.build(title));

		parent.addContent(heading);
	}
}