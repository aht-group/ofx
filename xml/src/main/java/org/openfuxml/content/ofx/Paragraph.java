
package org.openfuxml.content.ofx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openfuxml.content.editorial.Acronym;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.editorial.Index;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.content.text.Symbol;
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
 *         &lt;element ref="{http://www.openfuxml.org/layout}font"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/text}emphasis" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/text}symbol" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}marginalia" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/editorial}glossary" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/editorial}acronym" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/editorial}index" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}reference" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/media}image"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="top" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="normal"/&gt;
 *             &lt;enumeration value="small"/&gt;
 *             &lt;enumeration value="mini"/&gt;
 *             &lt;enumeration value="zero"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
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
@XmlRootElement(name = "paragraph")
public class Paragraph implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElementRefs({
        @XmlElementRef(name = "font", namespace = "http://www.openfuxml.org/layout", type = Font.class),
        @XmlElementRef(name = "emphasis", namespace = "http://www.openfuxml.org/text", type = Emphasis.class),
        @XmlElementRef(name = "symbol", namespace = "http://www.openfuxml.org/text", type = Symbol.class),
        @XmlElementRef(name = "marginalia", namespace = "http://www.openfuxml.org", type = Marginalia.class),
        @XmlElementRef(name = "glossary", namespace = "http://www.openfuxml.org/editorial", type = Glossary.class),
        @XmlElementRef(name = "acronym", namespace = "http://www.openfuxml.org/editorial", type = Acronym.class),
        @XmlElementRef(name = "index", namespace = "http://www.openfuxml.org/editorial", type = Index.class),
        @XmlElementRef(name = "reference", namespace = "http://www.openfuxml.org", type = Reference.class),
        @XmlElementRef(name = "image", namespace = "http://www.openfuxml.org/media", type = Image.class)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "lang")
    protected String lang;
    @XmlAttribute(name = "top", required = true)
    protected String top;

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
     * {@link Acronym }
     * {@link Glossary }
     * {@link Index }
     * {@link Font }
     * {@link Marginalia }
     * {@link Reference }
     * {@link Emphasis }
     * {@link Symbol }
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

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the top property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTop() {
        return top;
    }

    /**
     * Sets the value of the top property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTop(String value) {
        this.top = value;
    }

}
