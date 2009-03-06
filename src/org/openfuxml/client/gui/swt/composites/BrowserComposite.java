package org.openfuxml.client.gui.swt.composites;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.openfuxml.client.util.ImgCanvas;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

public class BrowserComposite extends Composite
{
	static Logger logger = Logger.getLogger(ProjektComposite.class);
	
	final static String ICON = "/swt/images/browser/fuxicon.gif";
	final static String ICON_ROTATION = "/swt/images/browser/fuxicon-rotation.gif";
	
	final static String IMG_BTN_BACK	= "/swt/images/browser/back.gif";
	final static String IMG_BTN_FORWARD	= "/swt/images/browser/forward.gif";
	final static String IMG_BTN_REFRESH	= "/swt/images/browser/refresh.gif";
	final static String IMG_BTN_STOP	= "/swt/images/browser/stop.gif";
	
	private Image imgBtnBack;		
	private Image imgBtnForward;		
	private Image imgBtnRefresh;		
	private Image imgBtnStop;		
	
	private Button btnBack;
	private Button btnForward;
	private Button btnRefresh;
	private Button btnStop;
	
	private Text textURL;
	
	private ImgCanvas imgIconRotation;
	
	private Browser browser;
	
	private Label labelStatus;
	
	private ProgressBar progressBar;

	public BrowserComposite(Composite parent, Configuration config)
	{
		super(parent, SWT.NONE);
	
		try
		{
			String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='produce']");
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			imgBtnBack = new Image(null, img.getImageData(), img.getImageData().getTransparencyMask());
			imgBtnForward	= new Image(null, img.getImageData(), img.getImageData().getTransparencyMask());
			imgBtnRefresh	= new Image(null, img.getImageData(), img.getImageData().getTransparencyMask());
			imgBtnStop		= new Image(null, img.getImageData(), img.getImageData().getTransparencyMask()); 
			
		}
		catch (FileNotFoundException e) {logger.error(e);}
			
		{
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.verticalSpacing = 10;
			layout.horizontalSpacing = 10;
			layout.numColumns = 6;
			this.setLayout(layout);
		}
		
		{
			btnBack = new Button(this, SWT.NONE);
			btnBack.setToolTipText("Back");
			btnBack.setImage(imgBtnBack);
			btnBack.setBackground(this.getBackground());

			btnBack.setEnabled(false);
			
			btnBack.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.back();
					}
				});
		}
		{
			btnForward = new Button(this, SWT.NONE);
			btnForward.setToolTipText("Forward");
			btnForward.setImage(imgBtnForward);
			btnForward.setBackground(this.getBackground());

			btnForward.setEnabled(false);

			btnForward.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.forward();
					}
				});
		}
		{
			btnRefresh = new Button(this, SWT.NONE);
			btnRefresh.setToolTipText("Refresh");
			btnRefresh.setImage(imgBtnRefresh);
			btnRefresh.setBackground(this.getBackground());
			
			btnRefresh.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.refresh();
					}
				});
		}
		{
			btnStop = new Button(this, SWT.NONE);
			btnStop.setToolTipText("Stop");
			btnStop.setImage(imgBtnStop);
			btnStop.setBackground(this.getBackground());

			btnStop.setEnabled(false);
			
			btnStop.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.stop();
					}
				});
		}
		{
			textURL = new Text(this, SWT.SINGLE | SWT.BORDER);
			textURL.setText("URL");
			
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				textURL.setLayoutData(data);
			}
			
			textURL.addSelectionListener(new SelectionAdapter()
				{
					public void widgetDefaultSelected(SelectionEvent event)
					{
						System.out.println("ENTER");
						browser.setUrl(textURL.getText());
					}
				});
		}
		{
			imgIconRotation = new ImgCanvas(this, ICON);
			GridData data = new GridData();
			data.widthHint = 40;
			data.heightHint = 40;
			imgIconRotation.setLayoutData(data);
			imgIconRotation.setBackground(this.getBackground());
		}
		{
			browser = new Browser(this, SWT.NONE);
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.grabExcessVerticalSpace   = true;
				data.horizontalAlignment = GridData.FILL;
				data.verticalAlignment   = GridData.FILL;
				data.horizontalSpan = 6;
				browser.setLayoutData(data);
			}
						
			browser.addLocationListener(new LocationListener(){
				public void changing(LocationEvent event)
				{
System.out.println("LocationListener---changing: "+ event.location);
					if (event.location.startsWith("xmetal://"))
					{
						System.out.println("TREFFER: xmetal://");
						System.out.println("starte Applikation mit "+event.location);

						try
						{
							Runtime.getRuntime().exec("C:/Programme/SoftQuad/XMetaL 3/xmetal31.exe");
						}
						catch (Exception e) {e.printStackTrace();}
						
						
						
						System.out.println("hartesBack aufrufen");
						hartesBack();
					}
					else
					{
						textURL.setText(browser.getUrl());
						
						imgIconRotation.setImage(ICON_ROTATION);
						
						btnBack.setEnabled(browser.isBackEnabled());
						btnForward.setEnabled(browser.isForwardEnabled());
					}
				}
				public void changed(LocationEvent event)
				{
System.out.println("LocationListener---changed: "+ event.location);
					textURL.setText(browser.getUrl());
					
					imgIconRotation.setImage(ICON);
					
					btnBack.setEnabled(browser.isBackEnabled());
					btnForward.setEnabled(browser.isForwardEnabled());
				}
			});

			browser.addProgressListener(new ProgressListener(){
				public void completed(ProgressEvent event)
				{
System.out.println("ProgressListener---completed: "+ event.current + " von " + event.total + " --- "+ event.toString());
					progressBar.setMaximum(event.total);
					progressBar.setSelection(event.current);
				}
				public void changed(ProgressEvent event)
				{
System.out.println("ProgressListener---changed: "+ event.current + " von " + event.total + " --- "+ event.toString());					
					progressBar.setMaximum(event.total);
					progressBar.setSelection(event.current);
				}
			});
			
			browser.addStatusTextListener(new StatusTextListener(){
				public void changed(StatusTextEvent event)
				{
					labelStatus.setText(event.text);		
				}
			});
			
			browser.addOpenWindowListener(new OpenWindowListener(){
				public void open(WindowEvent event) {
					// Der folgende Code ist notwendig, damit ein neues Browser-Fenster
					// geöffnet wird.
					// Allerdings wird die Debug-Meldung nicht ausgegeben.
					// WARUM???
					System.out.println("EventBrowser: " +  event.browser.getUrl());					
				}
			});
		}

		{
			labelStatus = new Label(this, SWT.BORDER);
			labelStatus.setBackground(this.getBackground());
			labelStatus.setText("Status");		
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				data.horizontalSpan = 5;
				labelStatus.setLayoutData(data);
			}
		}
		{
			progressBar = new ProgressBar(this, SWT.BORDER);
			progressBar.setMinimum(0);
			progressBar.setMaximum(1000);
			progressBar.setSelection(300);
			
		}
	}

	public void setUrl(String URL)
	{
		browser.setUrl(URL);
	}
	
	public void setText(String text)
	{
		browser.setText(text);
	}
	
	public void hartesBack()
	{
		
	}
}
