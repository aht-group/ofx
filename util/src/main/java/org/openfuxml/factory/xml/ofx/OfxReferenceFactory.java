package org.openfuxml.factory.xml.ofx;

import org.openfuxml.model.xml.core.ofx.Reference;

public class OfxReferenceFactory
{
	
	public static Reference build(String target)
	{
		Reference xml = new Reference();
		xml.setTarget(target);

		return xml;
	}
}
