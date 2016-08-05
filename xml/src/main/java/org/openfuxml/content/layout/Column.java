
package org.openfuxml.content.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openfuxml.content.media.Image;


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
 *         &lt;element ref="{http://www.openfuxml.org/layout}width" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}height" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}container" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/media}image" maxOccurs="unbounded"/&gt;
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
    "content"
})
@XmlRootElement(name = "column")
public class Column implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElementRefs({
        @XmlElementRef(name = "width", namespace = "http://www.openfuxml.org/layout", type = Width.class),
        @XmlElementRef(name = "image", namespace = "http://www.openfuxml.org/media", type = Image.class),
        @XmlElementRef(name = "height", namespace = "http://www.openfuxml.org/layout", type = Height.class),
        @XmlElementRef(name = "container", namespace = "http://www.openfuxml.org/layout", type = Container.class)
    })
    @XmlMixed
    protected List<Serializable> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Width }
     * {@link Container }
     * {@link String }
     * {@link Image }
     * {@link Height }
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    public boolean isSetContent() {
        return ((this.content!= null)&&(!this.content.isEmpty()));
    }

    public void unsetContent() {
        this.content = null;
    }

}
