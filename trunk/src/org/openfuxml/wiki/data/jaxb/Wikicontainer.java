//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-34 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.03 at 12:19:07 AM OESZ 
//


package org.openfuxml.wiki.data.jaxb;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}wikireplace" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}wikiinjection" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wikireplace",
    "wikiinjection"
})
@XmlRootElement(name = "wikicontainer")
public class Wikicontainer {

    @XmlElement(required = true)
    protected List<Wikireplace> wikireplace;
    @XmlElement(required = true)
    protected List<Wikiinjection> wikiinjection;

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

}
