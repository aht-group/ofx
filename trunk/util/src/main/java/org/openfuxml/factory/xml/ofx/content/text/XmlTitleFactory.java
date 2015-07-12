package org.openfuxml.factory.xml.ofx.content.text;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.Title2;

public class XmlTitleFactory
{
    public static Title build(String title){return build(null,title);}
	public static Title build(String lang, String title)
	{
		Title xml = new Title();
		xml.setLang(lang);
		xml.setValue(title);
		return xml;
	}
	
	public static Title2 build2(String title){return build2(null,title);}
	public static Title2 build2(String lang, String title)
	{
		Title2 xml = new Title2();
		xml.setLang(lang);
		xml.getContent().add(title);
		return xml;
	}
}
