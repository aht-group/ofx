//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-34 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.08 at 09:23:36 AM MEZ 
//


package org.openfuxml.addon.jsfapp.data.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://www.openfuxml.org/jsfapp}ofxinjection" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/jsfapp}genericinjection"/>
 *       &lt;/sequence>
 *       &lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ofxinjection",
    "genericinjection"
})
@XmlRootElement(name = "ofxinjections")
public class Ofxinjections
    implements Serializable
{

    private final static long serialVersionUID = 14L;
    @XmlElement(required = true)
    protected List<Ofxinjection> ofxinjection;
    @XmlElement(required = true)
    protected Genericinjection genericinjection;
    @XmlAttribute
    protected String locale;

    /**
     * Gets the value of the ofxinjection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofxinjection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfxinjection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxinjection }
     * 
     * 
     */
    public List<Ofxinjection> getOfxinjection() {
        if (ofxinjection == null) {
            ofxinjection = new ArrayList<Ofxinjection>();
        }
        return this.ofxinjection;
    }

    public boolean isSetOfxinjection() {
        return ((this.ofxinjection!= null)&&(!this.ofxinjection.isEmpty()));
    }

    public void unsetOfxinjection() {
        this.ofxinjection = null;
    }

    /**
     * Gets the value of the genericinjection property.
     * 
     * @return
     *     possible object is
     *     {@link Genericinjection }
     *     
     */
    public Genericinjection getGenericinjection() {
        return genericinjection;
    }

    /**
     * Sets the value of the genericinjection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Genericinjection }
     *     
     */
    public void setGenericinjection(Genericinjection value) {
        this.genericinjection = value;
    }

    public boolean isSetGenericinjection() {
        return (this.genericinjection!= null);
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    public boolean isSetLocale() {
        return (this.locale!= null);
    }

}