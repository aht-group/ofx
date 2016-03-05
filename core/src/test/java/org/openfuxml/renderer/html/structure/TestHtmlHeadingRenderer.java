package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlHeadingRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlHeadingRenderer.class);

	private enum Key {lvl1,lvl2}
	
	private HtmlBody renderer;

	/*Initialisierung des Zielordners und des Renderers.
	* @Before JUnit Test Annotation, sorgt dafür dass diese Methode immer zuerst aufgerufen wird.
	* Wichtig wenn man dies als JUnit Test ausführt und nicht als Applikation über die
	* main() Methode.*/
	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	/*initFile(arg) initalisiert die Ausgabedatei @para arg: Dateiname
	* Konvertierung der Dummy “Section”, enthält die zu testende Überschrift.
	* renterTest(arg) speichert Ausgabedatei und vergleicht String-output mit Dateiinhalt als
	* JUnit Test
	* @Test: JUnit Annotation, ählich der @Before Annotation für JUnit Tests.
	* Deklariert die eigentlichen Tests.
	*/
	@Test public void lvl1() throws IOException
	{
		initFile(Key.lvl1);
        renderer.render(SectionProvider.build());
    	renderTest(renderer);
	}

	//Ähnlich lvl1(), hier wird nur zusätzlich eine untergeordnete Überschrift erzeugt.
	@Test public void lvl2() throws IOException
	{
		initFile(Key.lvl2);
		Section section = new Section(); section.getContent().add(SectionProvider.build());
		renderer.render(section);
    	renderTest(renderer);
	}

	//main() zum Ausführen als Applikation, wichtig vor jedem einzelnen Test init() aufzurufen.
	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlHeadingRenderer test = new TestHtmlHeadingRenderer();
        test.setEnvironment(true);
		
        test.init();test.lvl1();
        test.init();test.lvl2();
	}
}