package org.openfuxml.renderer.markdown.content.structure;


import org.junit.Before;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.markdown.structure.MdTitleRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestMdTitleRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdTitleRenderer.class);

	private MdTitleRenderer renderer;
	private Title title;


//	@Before
	void init()
	{
		renderer = new MdTitleRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
		title = XmlTitleFactory.build("Test");
	}

	void test()
	{
		List<String> header = new ArrayList<String>();

		for(int i = 1; i < 7; i++) {renderer.render(title, i);}

		header.addAll(renderer.getContent());
		for(String s : header) {System.out.println(s);}
	}

	public static void main(String[] args)
	{
		TestMdTitleRenderer test = new TestMdTitleRenderer();
		test.init();

		test.test();
	}
}
