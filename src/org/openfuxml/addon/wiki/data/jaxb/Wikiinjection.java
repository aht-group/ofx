//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-34 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.13 at 05:05:39 PM MESZ 
//


package org.openfuxml.addon.wiki.data.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="wikicontent">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchart" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="wikitag" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ofxtag" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wikicontent",
    "ofxchart"
})
@XmlRootElement(name = "wikiinjection")
public class Wikiinjection {

    @XmlElement(namespace = "", required = true)
    protected Wikiinjection.Wikicontent wikicontent;
    @XmlElement(required = true)
    protected List<Ofxchart> ofxchart;
    @XmlAttribute
    protected String format;
    @XmlAttribute
    protected String id;
    @XmlAttribute
    protected String wikitag;
    @XmlAttribute
    protected String ofxtag;

    /**
     * Gets the value of the wikicontent property.
     * 
     * @return
     *     possible object is
     *     {@link Wikiinjection.Wikicontent }
     *     
     */
    public Wikiinjection.Wikicontent getWikicontent() {
        return wikicontent;
    }

    /**
     * Sets the value of the wikicontent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wikiinjection.Wikicontent }
     *     
     */
    public void setWikicontent(Wikiinjection.Wikicontent value) {
        this.wikicontent = value;
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

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the wikitag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWikitag() {
        return wikitag;
    }

    /**
     * Sets the value of the wikitag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWikitag(String value) {
        this.wikitag = value;
    }

    /**
     * Gets the value of the ofxtag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfxtag() {
        return ofxtag;
    }

    /**
     * Sets the value of the ofxtag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfxtag(String value) {
        this.ofxtag = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Wikicontent {

        @XmlValue
        protected String value;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

    }

}
