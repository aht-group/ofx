package org.openfuxml.renderer.word.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;

public class NodeRunReplace 
{
	final static Logger logger = LoggerFactory.getLogger(NodeRunReplace.class);
	
	Document doc;	
	
	public NodeRunReplace(Document doc) {super();this.doc = doc;}

	public Document replace(Map<String, String> replacementTags) throws Exception
	{		
		for (int i = 0; i < replacementTags.size(); i++) 
		{
			String tagString = "cNHinRtXt" + i;
			doc.getRange().replace(tagString.toString(), replacementTags.get(tagString).toString(),
					new FindReplaceOptions(FindReplaceDirection.FORWARD));
			System.out.println("replace: " + tagString + "\t with: " + replacementTags.get(tagString).toString());			
		}
		return doc;
	}
}
