package org.openfuxml.xml;

import java.util.ArrayList;
import java.util.List;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.jdom2.Namespace;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OfxNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
	public enum NS {text}
	
	public static Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	public static Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
	
	public static List<Namespace> toOfxNamespaces()
	{
		List<Namespace> list = new ArrayList<>();
		list.add(OfxNsPrefixMapper.nsOfx);
		list.add(OfxNsPrefixMapper.nsWiki);
		return list;
	}
	
	public static String prefix(NS ns)
	{
		switch(ns)
		{
			case text:	return "text";
			default:		return "xxxxxxxxxxxxxx";
		}
	}
	
	public static String namespace(NS ns)
	{
		switch(ns)
		{
			case text:	return "http://www.openfuxml.org/text";
			default:	return "xxxxxxxxxxxxxx";
		}
	}

    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if("http://www.openfuxml.org".equals(namespaceUri) ){return "ofx";}
    	if("http://www.openfuxml.org/editorial".equals(namespaceUri) ){return "ed";}
    	if("http://www.openfuxml.org/wiki".equals(namespaceUri) ){return "wiki";}
        if("http://www.openfuxml.org/chart".equals(namespaceUri) ){return "chart";}
        if("http://www.openfuxml.org/table".equals(namespaceUri) ){return "table";}
        if("http://www.openfuxml.org/layout".equals(namespaceUri) ){return "layout";}
        if("http://www.openfuxml.org/list".equals(namespaceUri) ){return "l";}
        if("http://www.openfuxml.org/text".equals(namespaceUri) ){return "text";}
        if("http://www.openfuxml.org/media".equals(namespaceUri) ){return "media";}
        
        if("http://www.openfuxml.org/jsf".equals(namespaceUri) ){return "jsf";}
        
        if("http://exlp.sf.net/io".equals(namespaceUri) ){return "io";}
  
        return suggestion;
    }
    
    public String getOfxPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if("http://www.openfuxml.org".equals(namespaceUri) ){return "ofx";}
    	if("http://www.openfuxml.org/editorial".equals(namespaceUri) ){return "ed";}
    	if("http://www.openfuxml.org/wiki".equals(namespaceUri) ){return "wiki";}
        if("http://www.openfuxml.org/chart".equals(namespaceUri) ){return "chart";}
        if("http://www.openfuxml.org/table".equals(namespaceUri) ){return "ofxT";}
        if("http://www.openfuxml.org/layout".equals(namespaceUri) ){return "layout";}
        if("http://www.openfuxml.org/list".equals(namespaceUri) ){return "l";}
        if("http://www.openfuxml.org/text".equals(namespaceUri) ){return "text";}
        if("http://www.openfuxml.org/media".equals(namespaceUri) ){return "media";}
        
        if("http://www.openfuxml.org/jsf".equals(namespaceUri) ){return "jsf";}
        
        if("http://exlp.sf.net/io".equals(namespaceUri) ){return "io";}
  
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://www.openfuxml.org/chart";
    	result = new String[0];
        return result;
    }
}