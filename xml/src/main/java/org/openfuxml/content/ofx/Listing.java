
package org.openfuxml.content.ofx;

import java.io.Serializable;
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
 *         &lt;element ref="{http://www.openfuxml.org}title"/>
 *         &lt;element ref="{http://www.openfuxml.org}raw"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="external" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codeLang" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "raw"
})
@XmlRootElement(name = "listing")
public class Listing implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Title title;
    @XmlElement(required = true)
    protected Raw raw;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "external")
    protected String external;
    @XmlAttribute(name = "codeLang")
    protected String codeLang;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    public boolean isSetTitle() {
        return (this.title!= null);
    }

    /**
     * Gets the value of the raw property.
     * 
     * @return
     *     possible object is
     *     {@link Raw }
     *     
     */
    public Raw getRaw() {
        return raw;
    }

    /**
     * Sets the value of the raw property.
     * 
     * @param value
     *     allowed object is
     *     {@link Raw }
     *     
     */
    public void setRaw(Raw value) {
        this.raw = value;
    }

    public boolean isSetRaw() {
        return (this.raw!= null);
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

    public boolean isSetId() {
        return (this.id!= null);
    }

    /**
     * Gets the value of the external property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternal() {
        return external;
    }

    /**
     * Sets the value of the external property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternal(String value) {
        this.external = value;
    }

    public boolean isSetExternal() {
        return (this.external!= null);
    }

    /**
     * Gets the value of the codeLang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeLang() {
        return codeLang;
    }

    /**
     * Sets the value of the codeLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeLang(String value) {
        this.codeLang = value;
    }

    public boolean isSetCodeLang() {
        return (this.codeLang!= null);
    }

}
