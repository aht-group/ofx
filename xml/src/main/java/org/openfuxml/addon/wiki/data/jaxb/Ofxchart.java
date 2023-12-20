
package org.openfuxml.addon.wiki.data.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="title"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="x-axis"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="dateformat" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="y-axis"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="colors"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{http://www.openfuxml.org/wiki}color" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="size"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *                 &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="grid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="range" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="domain" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchartcontainer" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="legend" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "xAxis",
    "yAxis",
    "colors",
    "size",
    "grid",
    "ofxchartcontainer"
})
@XmlRootElement(name = "ofxchart")
public class Ofxchart
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Title title;
    @XmlElement(name = "x-axis", namespace = "", required = true)
    protected Ofxchart.XAxis xAxis;
    @XmlElement(name = "y-axis", namespace = "", required = true)
    protected Ofxchart.YAxis yAxis;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Colors colors;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Size size;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Grid grid;
    @XmlElement(required = true)
    protected List<Ofxchartcontainer> ofxchartcontainer;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "legend")
    protected Boolean legend;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Title }
     *     
     */
    public Ofxchart.Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Title }
     *     
     */
    public void setTitle(Ofxchart.Title value) {
        this.title = value;
    }

    /**
     * Gets the value of the xAxis property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.XAxis }
     *     
     */
    public Ofxchart.XAxis getXAxis() {
        return xAxis;
    }

    /**
     * Sets the value of the xAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.XAxis }
     *     
     */
    public void setXAxis(Ofxchart.XAxis value) {
        this.xAxis = value;
    }

    /**
     * Gets the value of the yAxis property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.YAxis }
     *     
     */
    public Ofxchart.YAxis getYAxis() {
        return yAxis;
    }

    /**
     * Sets the value of the yAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.YAxis }
     *     
     */
    public void setYAxis(Ofxchart.YAxis value) {
        this.yAxis = value;
    }

    /**
     * Gets the value of the colors property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Colors }
     *     
     */
    public Ofxchart.Colors getColors() {
        return colors;
    }

    /**
     * Sets the value of the colors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Colors }
     *     
     */
    public void setColors(Ofxchart.Colors value) {
        this.colors = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Size }
     *     
     */
    public Ofxchart.Size getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Size }
     *     
     */
    public void setSize(Ofxchart.Size value) {
        this.size = value;
    }

    /**
     * Gets the value of the grid property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Grid }
     *     
     */
    public Ofxchart.Grid getGrid() {
        return grid;
    }

    /**
     * Sets the value of the grid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Grid }
     *     
     */
    public void setGrid(Ofxchart.Grid value) {
        this.grid = value;
    }

    /**
     * Gets the value of the ofxchartcontainer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofxchartcontainer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfxchartcontainer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxchartcontainer }
     * 
     * 
     */
    public List<Ofxchartcontainer> getOfxchartcontainer() {
        if (ofxchartcontainer == null) {
            ofxchartcontainer = new ArrayList<Ofxchartcontainer>();
        }
        return this.ofxchartcontainer;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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

    /**
     * Gets the value of the legend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLegend() {
        return legend;
    }

    /**
     * Sets the value of the legend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLegend(Boolean value) {
        this.legend = value;
    }


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
     *         &lt;element ref="{http://www.openfuxml.org/wiki}color" maxOccurs="unbounded"/&gt;
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
        "color"
    })
    public static class Colors
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(required = true)
        protected List<Color> color;

        /**
         * Gets the value of the color property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the color property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getColor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Color }
         * 
         * 
         */
        public List<Color> getColor() {
            if (color == null) {
                color = new ArrayList<Color>();
            }
            return this.color;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="range" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="domain" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Grid
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "range")
        protected Boolean range;
        @XmlAttribute(name = "domain")
        protected Boolean domain;

        /**
         * Gets the value of the range property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRange() {
            return range;
        }

        /**
         * Sets the value of the range property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRange(Boolean value) {
            this.range = value;
        }

        /**
         * Gets the value of the domain property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDomain() {
            return domain;
        }

        /**
         * Sets the value of the domain property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDomain(Boolean value) {
            this.domain = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Size
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "height")
        protected Integer height;
        @XmlAttribute(name = "width")
        protected Integer width;

        /**
         * Gets the value of the height property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getHeight() {
            return height;
        }

        /**
         * Sets the value of the height property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setHeight(Integer value) {
            this.height = value;
        }

        /**
         * Gets the value of the width property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getWidth() {
            return width;
        }

        /**
         * Sets the value of the width property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setWidth(Integer value) {
            this.width = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Title
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "label")
        protected String label;
        @XmlAttribute(name = "key")
        protected String key;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="dateformat" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class XAxis
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "label")
        protected String label;
        @XmlAttribute(name = "key")
        protected String key;
        @XmlAttribute(name = "dateformat")
        protected String dateformat;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

        /**
         * Gets the value of the dateformat property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateformat() {
            return dateformat;
        }

        /**
         * Sets the value of the dateformat property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateformat(String value) {
            this.dateformat = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class YAxis
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "label")
        protected String label;
        @XmlAttribute(name = "key")
        protected String key;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

    }

}
