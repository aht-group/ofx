package org.openfuxml.util.configuration.settings.word;

public class OfxWordFont
{
	private boolean italic,italicDefault; public boolean isItalic() {return italic;}
	private boolean bold,boldDefault; public boolean isBold() {return bold;}
	// ... underline
	
	// ... size
	
	public static OfxWordFont instance() {return new OfxWordFont();}
	
	public OfxWordFont()
	{
		italic = false;
		bold = false;
		
		save();
	}
	
	public void save()
	{
		italicDefault = italic;
		boldDefault = bold;
	}
	
	public OfxWordFont reset()
	{
		italic = italicDefault;
		bold = boldDefault;
		return this;
	}
	
	public OfxWordFont italic() {italic(true);return this;}
	public OfxWordFont italic(boolean value) {italic=value;return this;}
	
	public OfxWordFont bold() {bold(true);return this;}
	public OfxWordFont bold(boolean value) {bold=value;return this;}
}