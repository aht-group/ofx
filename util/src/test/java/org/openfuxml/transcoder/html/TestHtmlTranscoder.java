package org.openfuxml.transcoder.html;

import org.exlp.util.jx.JaxbUtil;
import org.junit.Test;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.openfuxml.trancoder.html.HtmlTranscoder;
import org.openfuxml.util.filter.TestOfxClassifierFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlTranscoder extends AbstractOfxUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxClassifierFilter.class);
	
	@Test
	public void test()
	{
		String html = "<ol><li><font face=\"Arial, Verdana\" size=\"2\">test</font></li><li><font face=\"Arial, Verdana\" size=\"2\">xx</font></li></ol><ol><li><font face=\"Arial, Verdana\" size=\"2\">test</font></li><li><font face=\"Arial, Verdana\" size=\"2\">xx</font></li></ol>";
		
		HtmlTranscoder htmlTranscoder = new HtmlTranscoder();
		Section section = htmlTranscoder.transcode(html);
		
		JaxbUtil.info(section);
		

	}
}
