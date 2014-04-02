package org.openfuxml.factory.content;

import org.openfuxml.content.ofx.Emphasis;

public class OfxEmphasisFactory
{
	private boolean bold;
	
	public OfxEmphasisFactory(Emphasis emphasis)
	{
		this(emphasis.isSetBold()&&emphasis.isBold());
	}
	
	public OfxEmphasisFactory(boolean bold)
	{
		this.bold=bold;
	}
	
	public Emphasis build(String text)
	{
		Emphasis emphasis = new Emphasis();
		emphasis.setBold(bold);
		emphasis.setValue(text);
		return emphasis;
	}
}
