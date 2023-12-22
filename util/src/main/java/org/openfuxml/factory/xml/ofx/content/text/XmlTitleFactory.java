package org.openfuxml.factory.xml.ofx.content.text;

import org.openfuxml.model.xml.core.ofx.Title;

public class XmlTitleFactory
{
	public static Title build(String title){return build(null,title);}
	public static Title build(String lang, String title)
	{
		Title xml = build();
		xml.setLang(lang);
		xml.getContent().add(title);
		return xml;
	}
	
	public static Title build()
	{
		Title xml = new Title();

		return xml;
	}
}
