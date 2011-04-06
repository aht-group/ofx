//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-34 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.06 at 08:44:11 PM MESZ 
//


package org.openfuxml.content.ofx;

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
 *         &lt;element ref="{http://www.openfuxml.org}tableColSpec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org}tableHead" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org}tableBody"/>
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
    "tableColSpec",
    "tableHead",
    "tableBody"
})
@XmlRootElement(name = "tableGroup")
public class TableGroup
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<TableColSpec> tableColSpec;
    protected TableHead tableHead;
    @XmlElement(required = true)
    protected TableBody tableBody;

    /**
     * Gets the value of the tableColSpec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tableColSpec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTableColSpec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TableColSpec }
     * 
     * 
     */
    public List<TableColSpec> getTableColSpec() {
        if (tableColSpec == null) {
            tableColSpec = new ArrayList<TableColSpec>();
        }
        return this.tableColSpec;
    }

    public boolean isSetTableColSpec() {
        return ((this.tableColSpec!= null)&&(!this.tableColSpec.isEmpty()));
    }

    public void unsetTableColSpec() {
        this.tableColSpec = null;
    }

    /**
     * Gets the value of the tableHead property.
     * 
     * @return
     *     possible object is
     *     {@link TableHead }
     *     
     */
    public TableHead getTableHead() {
        return tableHead;
    }

    /**
     * Sets the value of the tableHead property.
     * 
     * @param value
     *     allowed object is
     *     {@link TableHead }
     *     
     */
    public void setTableHead(TableHead value) {
        this.tableHead = value;
    }

    public boolean isSetTableHead() {
        return (this.tableHead!= null);
    }

    /**
     * Gets the value of the tableBody property.
     * 
     * @return
     *     possible object is
     *     {@link TableBody }
     *     
     */
    public TableBody getTableBody() {
        return tableBody;
    }

    /**
     * Sets the value of the tableBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link TableBody }
     *     
     */
    public void setTableBody(TableBody value) {
        this.tableBody = value;
    }

    public boolean isSetTableBody() {
        return (this.tableBody!= null);
    }

}
