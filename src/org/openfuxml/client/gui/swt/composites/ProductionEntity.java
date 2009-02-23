package org.openfuxml.client.gui.swt.composites;

import java.io.Serializable;

/**
 * @author frank
 */
public class ProductionEntity implements Serializable
{
	public static final long serialVersionUID=1; 

	private boolean checked;
	
	private String Description;
	private String Directory;
	private String Filename;
		
	public ProductionEntity(boolean checked, String Description, String Directory, String Filename)
	{
		super();

		this.checked 		= checked;
		this.Description 	= Description;
		this.Directory 		= Directory;
		this.Filename		= Filename;
	}

	public ProductionEntity()
	{
		this(false, "", "", "");	
	}
	
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	public void setStrings(String Description, String Directory, String Filename)
	{
		this.Description 	= Description;
		this.Directory 		= Directory;
		this.Filename		= Filename;
	}
	
	public void set(boolean checked, String Description, String Directory, String Filename)
	{
		this.checked 		= checked;
		this.Description 	= Description;
		this.Directory 		= Directory;
		this.Filename		= Filename;
	}

	public boolean getChecked()
	{
		return checked;		
	}
	
	public String getDescription()
	{
		return Description;
	}

	public String getDirectory()
	{
		return Directory;
	}
	
	public String getFilename()
	{
		return Filename;
	}
	
	public String toString()
	{
		return ""+checked+" "+Description+" "+Directory+" "+Filename; 
	}
}
