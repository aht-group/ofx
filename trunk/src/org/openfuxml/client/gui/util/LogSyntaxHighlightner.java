/*
 * Created on 23.03.2005
 */
package org.openfuxml.client.gui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

/**
 * @author kisner
 */
public class LogSyntaxHighlightner
{
	private StyledText textLog;
	private int aktuellerIndex;
	private Display display;
	
	public LogSyntaxHighlightner(StyledText textLog,Display display)
	{
		this.textLog=textLog;
		this.display=display;
		aktuellerIndex=0;
	}
	
	public void highlight(String highlightline)
	{
		String s="Element abschnitt";
        int index = highlightline.indexOf(s);
        if(index>0)
        {
        	StyleRange styleRange = new StyleRange();
        	styleRange.start = aktuellerIndex+index;
        	styleRange.length = s.length();
        	styleRange.fontStyle = SWT.BOLD;
        	styleRange.foreground = display.getSystemColor(SWT.COLOR_RED);
        	textLog.setStyleRange(styleRange);
        }
        
		s="Unzulässiges XML-Zeichen";
        index = highlightline.indexOf(s);
        if(index>0)
        {
        	StyleRange styleRange = new StyleRange();
        	styleRange.start = aktuellerIndex+index;
        	styleRange.length = s.length();
        	styleRange.fontStyle = SWT.BOLD;
        	styleRange.foreground = display.getSystemColor(SWT.COLOR_RED);
        	textLog.setStyleRange(styleRange);
        }
        
        aktuellerIndex=aktuellerIndex+highlightline.length();
	}
}
