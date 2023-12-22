
package org.openfuxml.model.xml.addon.wiki;

import java.io.Serializable;
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
 *         &lt;element ref="{http://www.openfuxml.org/wiki}replacements"/&gt;
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
    "replacements"
})
@XmlRootElement(name = "xhtmlProcessor")
public class XhtmlProcessor
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Replacements replacements;

    /**
     * Gets the value of the replacements property.
     * 
     * @return
     *     possible object is
     *     {@link Replacements }
     *     
     */
    public Replacements getReplacements() {
        return replacements;
    }

    /**
     * Sets the value of the replacements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Replacements }
     *     
     */
    public void setReplacements(Replacements value) {
        this.replacements = value;
    }

}
