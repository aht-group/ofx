package org.openfuxml.factory.xml.text;

import org.openfuxml.content.text.Text;

public class OfxTextFactory
{
	public static Text build(String text){return build(null,text);}
	public static Text build(String lang, String text)
	{
		Text xml = new Text();
		xml.setValue(text);
		xml.setLang(lang);
		return xml;
	}
}
