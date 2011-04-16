//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-34 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.08 at 11:26:20 AM MEZ 
//


package org.openfuxml.addon.jsf.data.jaxb;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.openfuxml.org/jsf}tag"/>
 *         &lt;element ref="{http://www.openfuxml.org/jsf}component" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/jsf}renderer" minOccurs="0"/>
 *         &lt;element name="examples" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.openfuxml.org/jsf}example" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "tag",
    "component",
    "renderer",
    "examples"
})
@XmlRootElement(name = "metatag")
public class Metatag
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Tag tag;
    protected Component component;
    protected Renderer renderer;
    protected Metatag.Examples examples;

    /**
     * Gets the value of the tag property.
     * 
     * @return
     *     possible object is
     *     {@link Tag }
     *     
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tag }
     *     
     */
    public void setTag(Tag value) {
        this.tag = value;
    }

    public boolean isSetTag() {
        return (this.tag!= null);
    }

    /**
     * Gets the value of the component property.
     * 
     * @return
     *     possible object is
     *     {@link Component }
     *     
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Sets the value of the component property.
     * 
     * @param value
     *     allowed object is
     *     {@link Component }
     *     
     */
    public void setComponent(Component value) {
        this.component = value;
    }

    public boolean isSetComponent() {
        return (this.component!= null);
    }

    /**
     * Gets the value of the renderer property.
     * 
     * @return
     *     possible object is
     *     {@link Renderer }
     *     
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the value of the renderer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Renderer }
     *     
     */
    public void setRenderer(Renderer value) {
        this.renderer = value;
    }

    public boolean isSetRenderer() {
        return (this.renderer!= null);
    }

    /**
     * Gets the value of the examples property.
     * 
     * @return
     *     possible object is
     *     {@link Metatag.Examples }
     *     
     */
    public Metatag.Examples getExamples() {
        return examples;
    }

    /**
     * Sets the value of the examples property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metatag.Examples }
     *     
     */
    public void setExamples(Metatag.Examples value) {
        this.examples = value;
    }

    public boolean isSetExamples() {
        return (this.examples!= null);
    }


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
     *         &lt;element ref="{http://www.openfuxml.org/jsf}example" maxOccurs="unbounded"/>
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
        "example"
    })
    public static class Examples
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(required = true)
        protected List<Example> example;

        /**
         * Gets the value of the example property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the example property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExample().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Example }
         * 
         * 
         */
        public List<Example> getExample() {
            if (example == null) {
                example = new ArrayList<Example>();
            }
            return this.example;
        }

        public boolean isSetExample() {
            return ((this.example!= null)&&(!this.example.isEmpty()));
        }

        public void unsetExample() {
            this.example = null;
        }

    }

}
