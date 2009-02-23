package org.openfuxml.client.gui.swt;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.openfuxml.client.gui.simple.Client;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

public class OpenFuxmlTray
{
	static Logger logger = Logger.getLogger(OpenFuxmlTray.class);
	public final static String IMG_FUXICON_KLEIN	= "/swt/images/FuXML-Icon-klein.gif";
	
	private Composite parent;
	private OpenFuxmlClient of;
	
	public OpenFuxmlTray(Composite parent, OpenFuxmlClient of)
	{
		this.parent=parent;
		final Tray tray = Display.getDefault().getSystemTray ();
		if (tray == null) {
			System.out.println ("The system tray is not available");
		} else {
			final TrayItem item = new TrayItem (tray, SWT.NONE);
			item.setToolTipText("SWT TrayItem");
			item.addListener (SWT.Show, new Listener () {
				public void handleEvent (Event event) {
					System.out.println("show");
				}
			});
			item.addListener (SWT.Hide, new Listener () {
				public void handleEvent (Event event) {
					System.out.println("hide");
				}
			});
			item.addListener (SWT.Selection, new Listener () {
				public void handleEvent (Event event) {
					System.out.println("selection");
					toggleMinTray();
				}
			});
			item.addListener (SWT.DefaultSelection, new Listener () {
				public void handleEvent (Event event) {
					System.out.println("default selection");
				}
			});
			final Menu menu = new Menu (parent.getShell(), SWT.POP_UP);
			for (int i = 0; i < 2; i++) {
				MenuItem mi = new MenuItem (menu, SWT.PUSH);
				mi.setText ("Item" + i);
			}
			item.addListener (SWT.MenuDetect, new Listener () {
				public void handleEvent (Event event) {
					menu.setVisible (true);
				}
			});
			try
			{
				Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_FUXICON_KLEIN, of.getDisplay());
				item.setImage(img);
			}
			catch (FileNotFoundException e) {logger.error(e);}
		}
	}
	
	private void toggleMinTray()
	{
		if(parent.getShell().getMinimized())
		{	//Windows soll maximiert werden
			parent.getShell().setVisible(true); 
			parent.getShell().setMinimized(false);
		}
		else
		{	//Windows soll minimiert werden
			parent.getShell().setVisible(false); 
			parent.getShell().setMinimized(true);
		}
	}
}
