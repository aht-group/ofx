
package org.openfuxml.model.xml.addon.wiki;

import java.io.Serializable;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="wikicontent"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchart" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxgallery" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="article" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="wikitag" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ofxtag" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wikicontent",
    "ofxchart",
    "ofxgallery"
})
@XmlRootElement(name = "wikiinjection")
public class Wikiinjection
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "", required = true)
    protected Wikiinjection.Wikicontent wikicontent;
    @XmlElement(required = true)
    protected List<Ofxchart> ofxchart;
    @XmlElement(required = true)
    protected List<Ofxgallery> ofxgallery;
    @XmlAttribute(name = "format")
    protected String format;
    @XmlAttribute(name = "article")
    protected String article;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "wikitag")
    protected String wikitag;
    @XmlAttribute(name = "ofxtag")
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
     * Gets the value of the ofxgallery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofxgallery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfxgallery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxgallery }
     * 
     * 
     */
    public List<Ofxgallery> getOfxgallery() {
        if (ofxgallery == null) {
            ofxgallery = new ArrayList<Ofxgallery>();
        }
        return this.ofxgallery;
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
     * Gets the value of the article property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticle() {
        return article;
    }

    /**
     * Sets the value of the article property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticle(String value) {
        this.article = value;
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
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Wikicontent
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
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
