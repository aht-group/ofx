
package org.openfuxml.content.ofx;

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
import org.openfuxml.model.xml.core.layout.Box;
import org.openfuxml.model.xml.core.layout.Column;
import org.openfuxml.model.xml.core.layout.Container;
import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.model.xml.core.media.Image;


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
 *         &lt;element ref="{http://www.openfuxml.org/layout}box"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}container" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}width" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/layout}column" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}paragraph" maxOccurs="unbounded"/&gt;
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
@XmlRootElement(name = "marginalia")
public class Marginalia implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElementRefs({
        @XmlElementRef(name = "box", namespace = "http://www.openfuxml.org/layout", type = Box.class),
        @XmlElementRef(name = "container", namespace = "http://www.openfuxml.org/layout", type = Container.class),
        @XmlElementRef(name = "width", namespace = "http://www.openfuxml.org/layout", type = Width.class),
        @XmlElementRef(name = "column", namespace = "http://www.openfuxml.org/layout", type = Column.class),
        @XmlElementRef(name = "paragraph", namespace = "http://www.openfuxml.org", type = Paragraph.class),
        @XmlElementRef(name = "image", namespace = "http://www.openfuxml.org/media", type = Image.class)
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
     * {@link String }
     * {@link Paragraph }
     * {@link Box }
     * {@link Column }
     * {@link Container }
     * {@link Width }
     * {@link Image }
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

}
