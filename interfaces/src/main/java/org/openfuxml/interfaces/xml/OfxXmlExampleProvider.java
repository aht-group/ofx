package org.openfuxml.interfaces.xml;

import java.util.List;
import java.util.Map;

public interface OfxXmlExampleProvider 
{
	List<String> getCodes();
	Map<String,String> getMapResources();
	
	void updateXmlInResourceFolder();
}