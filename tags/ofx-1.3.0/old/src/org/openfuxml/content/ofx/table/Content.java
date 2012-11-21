//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-34 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.07 at 06:37:53 PM MESZ 
//


package org.openfuxml.content.ofx.table;

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
 *         &lt;element ref="{http://www.openfuxml.org/table}head" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/table}body" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/table}foot" minOccurs="0"/>
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
    "head",
    "body",
    "foot"
})
@XmlRootElement(name = "content")
public class Content
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Head head;
    @XmlElement(required = true)
    protected List<Body> body;
    protected Foot foot;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link Head }
     *     
     */
    public Head getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link Head }
     *     
     */
    public void setHead(Head value) {
        this.head = value;
    }

    public boolean isSetHead() {
        return (this.head!= null);
    }

    /**
     * Gets the value of the body property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the body property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBody().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Body }
     * 
     * 
     */
    public List<Body> getBody() {
        if (body == null) {
            body = new ArrayList<Body>();
        }
        return this.body;
    }

    public boolean isSetBody() {
        return ((this.body!= null)&&(!this.body.isEmpty()));
    }

    public void unsetBody() {
        this.body = null;
    }

    /**
     * Gets the value of the foot property.
     * 
     * @return
     *     possible object is
     *     {@link Foot }
     *     
     */
    public Foot getFoot() {
        return foot;
    }

    /**
     * Sets the value of the foot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Foot }
     *     
     */
    public void setFoot(Foot value) {
        this.foot = value;
    }

    public boolean isSetFoot() {
        return (this.foot!= null);
    }

}