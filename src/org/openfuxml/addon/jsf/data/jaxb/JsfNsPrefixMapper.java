package org.openfuxml.addon.jsf.data.jaxb;

import net.sf.exlp.util.xml.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class JsfNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
        if( "http://www.openfuxml.org/jsf".equals(namespaceUri) )
            return "";
         
        if( "http://www.openfuxml.org/ofx".equals(namespaceUri) )
            return "ofx";
            
        return suggestion;
    }
    
    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[2];
    	result[0] = "http://www.openfuxml.org/jsf";
    	result[1] = "http://www.openfuxml.org";
        return result;
    }
}
