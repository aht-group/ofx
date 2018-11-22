package org.openfuxml.renderer.word.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.Row;

public class NodeRunReplace 
{
	final static Logger logger = LoggerFactory.getLogger(NodeRunReplace.class);
	
	Document doc;	
	
	public NodeRunReplace(Document doc) {super();this.doc = doc;}

	public Document replace(Map<String, String> replacementTags) throws Exception
	{		
		
		for (int i = 10; i < replacementTags.size()+10; i++) 
		{
			String tagString = "CNHINRTXT" + i;
			doc.getRange().replace(tagString.toString(), replacementTags.get(tagString).toString(),
					new FindReplaceOptions(FindReplaceDirection.FORWARD));
			
			System.out.println("replace: " + tagString + "\t with: " + replacementTags.get(tagString).toString());			
		}
		return doc;
	}
}