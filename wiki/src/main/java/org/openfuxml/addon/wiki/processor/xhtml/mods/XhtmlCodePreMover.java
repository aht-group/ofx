package org.openfuxml.addon.wiki.processor.xhtml.mods;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class XhtmlCodePreMover
{
	final static Logger logger = LoggerFactory.getLogger(XhtmlCodePreMover.class);
	
	private XPathExpression<Element> xpeCode;
	private XPathExpression<Element> xpeParent;
	private XPathExpression<Element> xpePre;
	
	private Element rootElement;
	
	public XhtmlCodePreMover()
	{
		XPathFactory xf = XPathFactory.instance();
		xpeCode = xf.compile("//code",Filters.element());
		xpeParent = xf.compile("..",Filters.element());
		xpePre = xf.compile("following-sibling::pre[position()=1]",Filters.element());
	}
	
	public String move(String xHtmlText) throws JDOMException
	{
		Document doc = JDomUtil.txtToDoc(xHtmlText);
		rootElement = doc.getRootElement();
		logger.debug(JDomUtil.docToTxt(doc));
		
		process();
		
		xHtmlText=JDomUtil.docToTxt(doc);
//		JDomUtil.debug(doc);
		return xHtmlText;
	}

	private void process() throws JDOMException
	{
		org.jdom2.Document doc = JaxbUtil.toDocument(rootElement);
		
		List<Element> list2 = xpeCode.evaluate(doc.getRootElement());
		logger.debug(list2.size()+" <code> elements found in "+rootElement.getName());
		
		for (Element eCode : list2)
		{

			
			if(eCode.getChildren().size()==0)
			{
				
				Element eP = xpeParent.evaluateFirst(eCode);
				if(eP!=null)
				{
					logger.trace("eP="+eP);
	
					Element ePre = xpePre.evaluateFirst(eP);
					if(ePre!=null)
					{
						logger.trace("ePre="+ePre);
		
						int iP = eP.getParent().indexOf(eP);
						int iPre = ePre.getParent().indexOf(ePre);				
						logger.trace(iP+" "+iPre);
						if(iPre==(iP+1))
						{
							eCode.setText(ePre.getText());
							ePre.detach();
							
							Element eCodeGrandParent = eCode.getParentElement().getParentElement();
							int iCodeParent = eCodeGrandParent.indexOf(eCode.getParentElement());
							logger.debug(iCodeParent+"");
							eCode.detach();
							eCodeGrandParent.removeContent(iCodeParent);
							eCodeGrandParent.addContent(iCodeParent, eCode);
							
						}
					}
				}
			}
		}
	}
}