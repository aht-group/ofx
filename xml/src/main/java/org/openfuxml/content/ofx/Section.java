
package org.openfuxml.content.ofx;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.table.Table;


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
 *         &lt;element ref="{http://www.openfuxml.org}comment" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}title" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}content" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}marginalia" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}highlight" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}paragraph" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}section" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}listing" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}sections" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org}include" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/table}table" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/media}image" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/list}list" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}template" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="classifier" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="external" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="include" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="container" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
@XmlRootElement(name = "section")
public class Section implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElementRefs({
        @XmlElementRef(name = "listing", namespace = "http://www.openfuxml.org", type = Listing.class),
        @XmlElementRef(name = "comment", namespace = "http://www.openfuxml.org", type = Comment.class),
        @XmlElementRef(name = "section", namespace = "http://www.openfuxml.org", type = Section.class),
        @XmlElementRef(name = "include", namespace = "http://www.openfuxml.org", type = Include.class),
        @XmlElementRef(name = "sections", namespace = "http://www.openfuxml.org", type = Sections.class),
        @XmlElementRef(name = "marginalia", namespace = "http://www.openfuxml.org", type = Marginalia.class),
        @XmlElementRef(name = "paragraph", namespace = "http://www.openfuxml.org", type = Paragraph.class),
        @XmlElementRef(name = "highlight", namespace = "http://www.openfuxml.org", type = Highlight.class),
        @XmlElementRef(name = "image", namespace = "http://www.openfuxml.org/media", type = Image.class),
        @XmlElementRef(name = "list", namespace = "http://www.openfuxml.org/list", type = org.openfuxml.content.list.List.class),
        @XmlElementRef(name = "table", namespace = "http://www.openfuxml.org/table", type = Table.class),
        @XmlElementRef(name = "content", namespace = "http://www.openfuxml.org/wiki", type = Content.class),
        @XmlElementRef(name = "title", namespace = "http://www.openfuxml.org", type = Title.class),
        @XmlElementRef(name = "template", namespace = "http://www.openfuxml.org/wiki", type = Template.class)
    })
    @XmlMixed
    protected java.util.List<Serializable> content;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "classifier")
    protected String classifier;
    @XmlAttribute(name = "lang")
    protected String lang;
    @XmlAttribute(name = "external")
    protected Boolean external;
    @XmlAttribute(name = "source")
    protected String source;
    @XmlAttribute(name = "include")
    protected String include;
    @XmlAttribute(name = "container")
    protected Boolean container;

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
     * {@link Listing }
     * {@link Comment }
     * {@link Section }
     * {@link Include }
     * {@link Sections }
     * {@link Marginalia }
     * {@link Paragraph }
     * {@link Highlight }
     * {@link Image }
     * {@link String }
     * {@link org.openfuxml.content.list.List }
     * {@link Table }
     * {@link Content }
     * {@link Title }
     * {@link Template }
     * 
     * 
     */
    public java.util.List<Serializable> getContent() {
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

    public boolean isSetId() {
        return (this.id!= null);
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

    public boolean isSetClassifier() {
        return (this.classifier!= null);
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

    public boolean isSetLang() {
        return (this.lang!= null);
    }

    /**
     * Gets the value of the external property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExternal() {
        return external;
    }

    /**
     * Sets the value of the external property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternal(boolean value) {
        this.external = value;
    }

    public boolean isSetExternal() {
        return (this.external!= null);
    }

    public void unsetExternal() {
        this.external = null;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    public boolean isSetSource() {
        return (this.source!= null);
    }

    /**
     * Gets the value of the include property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInclude() {
        return include;
    }

    /**
     * Sets the value of the include property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInclude(String value) {
        this.include = value;
    }

    public boolean isSetInclude() {
        return (this.include!= null);
    }

    /**
     * Gets the value of the container property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isContainer() {
        return container;
    }

    /**
     * Sets the value of the container property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContainer(boolean value) {
        this.container = value;
    }

    public boolean isSetContainer() {
        return (this.container!= null);
    }

    public void unsetContainer() {
        this.container = null;
    }

}
