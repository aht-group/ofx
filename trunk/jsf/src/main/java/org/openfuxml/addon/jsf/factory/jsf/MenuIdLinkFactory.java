package org.openfuxml.addon.jsf.factory.jsf;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsfapp.data.jaxb.Menu;
import org.openfuxml.addon.jsfapp.data.jaxb.Menuitem;

public class MenuIdLinkFactory
{
	static Log logger = LogFactory.getLog(MenuIdLinkFactory.class);
	
	Map<String,String> resultLinkId;
	
	public MenuIdLinkFactory()
	{
		
	}
	
	public Map<String,String> getLinkId(Menu menu)
	{
		resultLinkId = new Hashtable<String,String>();
		for(Menuitem item : menu.getMenuitem())
		{
			getLinkId(item);
		}
		return resultLinkId;
	}
	
	private void getLinkId(Menuitem item)
	{
		resultLinkId.put(item.getLink(), item.getId());
		for(Menuitem subItem : item.getMenuitem())
		{
			getLinkId(subItem);
		}
		
	}
	
}