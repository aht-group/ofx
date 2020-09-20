package org.openfuxml.doc.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractXmlExampleProvider
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlExampleProvider.class);
	
	protected final String fileBase = "../doc/src/main/resources/ofx/doc/provider/xml";
	protected final String resourceBase = "/ofx/doc/provider/xml";
	protected String filePath;
	protected String resourcePath;
	
	public final List<String> codes; public List<String> getCodes() {return codes;}
	public final Map<String,String> mapResources; public Map<String, String> getMapResources() {return mapResources;}
	
	public AbstractXmlExampleProvider()
	{
		codes = new ArrayList<>();
		mapResources = new HashMap<String,String>();
	}
	
	protected <E extends Enum<E>> void save(E code, Object o)
	{
		JaxbUtil.info(o);
		JaxbUtil.save(new File(fileBase+filePath+code+".xml"),o,true);
		Document doc = JaxbUtil.toDocument(o);
		JDomUtil.debug(doc);
		
		
	}
}