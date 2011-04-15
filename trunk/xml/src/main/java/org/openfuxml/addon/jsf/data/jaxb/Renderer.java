//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.15 at 10:14:57 PM MESZ 
//


package org.openfuxml.addon.jsf.data.jaxb;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="component-family" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="renderer-type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="renderer-class" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "componentFamily",
    "rendererType",
    "rendererClass"
})
@XmlRootElement(name = "renderer")
public class Renderer
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "component-family", required = true)
    protected String componentFamily;
    @XmlElement(name = "renderer-type", required = true)
    protected String rendererType;
    @XmlElement(name = "renderer-class", required = true)
    protected String rendererClass;

    /**
     * Gets the value of the componentFamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentFamily() {
        return componentFamily;
    }

    /**
     * Sets the value of the componentFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentFamily(String value) {
        this.componentFamily = value;
    }

    public boolean isSetComponentFamily() {
        return (this.componentFamily!= null);
    }

    /**
     * Gets the value of the rendererType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRendererType() {
        return rendererType;
    }

    /**
     * Sets the value of the rendererType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRendererType(String value) {
        this.rendererType = value;
    }

    public boolean isSetRendererType() {
        return (this.rendererType!= null);
    }

    /**
     * Gets the value of the rendererClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRendererClass() {
        return rendererClass;
    }

    /**
     * Sets the value of the rendererClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRendererClass(String value) {
        this.rendererClass = value;
    }

    public boolean isSetRendererClass() {
        return (this.rendererClass!= null);
    }

}
