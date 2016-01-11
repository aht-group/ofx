package org.openfuxml.factory.xml.editorial;

import org.openfuxml.content.editorial.Term;
import org.openfuxml.content.text.Text;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;

public class XmlTermFactory
{	
	public static Term buildC(String code, XmlGlossaryFactory.Classifier classifier, String name, String description){return build(code,classifier.toString(),name,description);}
	public static Term build(String code, String name, String description) {return build(code,null,name,description);}
	public static Term build(String code, String classifier, String name, String description)
	{
		Term xml = new Term();
		xml.setCode(code);
		if(name!=null)
		{
			Text text = OfxTextFactory.build(name);
			if(classifier!=null){text.setClassifier(classifier);}
			xml.getText().add(text);
		}
		if(description!=null){xml.getParagraph().add(XmlParagraphFactory.text(description));}
		return xml;
	}
}