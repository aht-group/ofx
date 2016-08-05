
package org.openfuxml.content.layout;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}line" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}font" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}spacing" minOccurs="0"/&gt;
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
    "line",
    "font",
    "spacing"
})
@XmlRootElement(name = "layout")
public class Layout
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Line> line;
    protected Font font;
    protected Spacing spacing;

    /**
     * Gets the value of the line property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the line property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Line }
     * 
     * 
     */
    public List<Line> getLine() {
        if (line == null) {
            line = new ArrayList<Line>();
        }
        return this.line;
    }

    public boolean isSetLine() {
        return ((this.line!= null)&&(!this.line.isEmpty()));
    }

    public void unsetLine() {
        this.line = null;
    }

    /**
     * Gets the value of the font property.
     * 
     * @return
     *     possible object is
     *     {@link Font }
     *     
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the value of the font property.
     * 
     * @param value
     *     allowed object is
     *     {@link Font }
     *     
     */
    public void setFont(Font value) {
        this.font = value;
    }

    public boolean isSetFont() {
        return (this.font!= null);
    }

    /**
     * Gets the value of the spacing property.
     * 
     * @return
     *     possible object is
     *     {@link Spacing }
     *     
     */
    public Spacing getSpacing() {
        return spacing;
    }

    /**
     * Sets the value of the spacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Spacing }
     *     
     */
    public void setSpacing(Spacing value) {
        this.spacing = value;
    }

    public boolean isSetSpacing() {
        return (this.spacing!= null);
    }

}
