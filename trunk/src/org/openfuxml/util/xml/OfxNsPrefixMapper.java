package org.openfuxml.util.xml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OfxNsPrefixMapper extends NamespacePrefixMapper
{

    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
        if("http://www.openfuxml.org/chart".equals(namespaceUri) ){return "chart";}
  
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
