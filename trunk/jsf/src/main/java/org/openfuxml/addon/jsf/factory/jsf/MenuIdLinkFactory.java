package org.openfuxml.addon.jsf.factory.jsf;

import java.util.Hashtable;
import java.util.Map;

import org.openfuxml.addon.jsfapp.data.jaxb.Menu;
import org.openfuxml.addon.jsfapp.data.jaxb.Menuitem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuIdLinkFactory
{
	final static Logger logger = LoggerFactory.getLogger(MenuIdLinkFactory.class);
	
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