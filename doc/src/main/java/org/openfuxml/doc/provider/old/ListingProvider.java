package org.openfuxml.doc.provider.old;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.controller.provider.text.SectionProvider;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListingProvider
{

	final static Logger logger = LoggerFactory.getLogger(ListingProvider.class);

	public static Section build()
	{
		Section s = SectionProvider.build();
		Listing l = new Listing();
		l.setRaw(buildRaw(5));
		s.getContent().add(l);
		return s;
	}

	public static Section buildEx()
	{
		Section s = SectionProvider.build();
		Listing l = new Listing();
		l.setExternal("src/test/resources/data/externalListing.sh");
		s.getContent().add(l);
		return s;
	}

	private static Raw buildRaw(int words)
	{
		Raw r = new Raw();
		r.setValue(DemoContentProvider.li.getWords(words) + "\n" + DemoContentProvider.li.getWords(words,words) + "\n" + DemoContentProvider.li.getWords(words*2));
		return r;
	}
}
