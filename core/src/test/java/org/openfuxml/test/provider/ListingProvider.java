package org.openfuxml.test.provider;


import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.content.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListingProvider  extends AbstractElementProvider {

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
		r.setValue(li.getWords(words) + "\n" + li.getWords(words,words) + "\n" + li.getWords(words*2));
		return r;
	}
}
