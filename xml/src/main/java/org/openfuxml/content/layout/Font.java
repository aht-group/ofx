
package org.openfuxml.content.layout;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="relativeSize" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "font")
public class Font implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "relativeSize")
    protected Integer relativeSize;

    /**
     * Gets the value of the relativeSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRelativeSize() {
        return relativeSize;
    }

    /**
     * Sets the value of the relativeSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRelativeSize(int value) {
        this.relativeSize = value;
    }

    public boolean isSetRelativeSize() {
        return (this.relativeSize!= null);
    }

    public void unsetRelativeSize() {
        this.relativeSize = null;
    }

}
