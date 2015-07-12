package org.openfuxml.factory.xml.text;

import org.openfuxml.content.text.Text;

public class OfxTextFactory
{
	public static Text build(String text)
	{
		Text xml = new Text();
		xml.setValue(text);
		return xml;
	}
}
