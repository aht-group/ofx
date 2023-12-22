
package org.openfuxml.model.xml.core.ofx;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.openfuxml.org}title"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}raw"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="external" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="codeLang" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="classifier" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="setting" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="numbering" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="linebreak" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
    @XmlAttribute(name = "classifier")
    protected String classifier;
    @XmlAttribute(name = "setting")
    protected String setting;
    @XmlAttribute(name = "numbering")
    protected Boolean numbering;
    @XmlAttribute(name = "linebreak")
    protected Boolean linebreak;

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

    /**
     * Gets the value of the classifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassifier() {
        return classifier;
    }

    /**
     * Sets the value of the classifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassifier(String value) {
        this.classifier = value;
    }

    /**
     * Gets the value of the setting property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetting() {
        return setting;
    }

    /**
     * Sets the value of the setting property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetting(String value) {
        this.setting = value;
    }

    /**
     * Gets the value of the numbering property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNumbering() {
        return numbering;
    }

    /**
     * Sets the value of the numbering property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNumbering(Boolean value) {
        this.numbering = value;
    }

    /**
     * Gets the value of the linebreak property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLinebreak() {
        return linebreak;
    }

    /**
     * Sets the value of the linebreak property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLinebreak(Boolean value) {
        this.linebreak = value;
    }

}
