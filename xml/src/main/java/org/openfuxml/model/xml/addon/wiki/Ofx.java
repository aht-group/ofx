
package org.openfuxml.model.xml.addon.wiki;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}wikireplace" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}wikiinjection" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchart" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wikireplace",
    "wikiinjection",
    "ofxchart"
})
@XmlRootElement(name = "ofx")
public class Ofx
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Wikireplace> wikireplace;
    @XmlElement(required = true)
    protected List<Wikiinjection> wikiinjection;
    @XmlElement(required = true)
    protected List<Ofxchart> ofxchart;

    /**
     * Gets the value of the wikireplace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wikireplace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWikireplace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wikireplace }
     * 
     * 
     */
    public List<Wikireplace> getWikireplace() {
        if (wikireplace == null) {
            wikireplace = new ArrayList<Wikireplace>();
        }
        return this.wikireplace;
    }

    /**
     * Gets the value of the wikiinjection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wikiinjection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWikiinjection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wikiinjection }
     * 
     * 
     */
    public List<Wikiinjection> getWikiinjection() {
        if (wikiinjection == null) {
            wikiinjection = new ArrayList<Wikiinjection>();
        }
        return this.wikiinjection;
    }

    /**
     * Gets the value of the ofxchart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofxchart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfxchart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxchart }
     * 
     * 
     */
    public List<Ofxchart> getOfxchart() {
        if (ofxchart == null) {
            ofxchart = new ArrayList<Ofxchart>();
        }
        return this.ofxchart;
    }

}
