
package org.openfuxml.model.xml.core.text;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openfuxml.model.xml.core.text package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openfuxml.model.xml.core.text
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Text }
     * 
     */
    public Text createText() {
        return new Text();
    }

    /**
     * Create an instance of {@link Emphasis }
     * 
     */
    public Emphasis createEmphasis() {
        return new Emphasis();
    }

    /**
     * Create an instance of {@link Symbol }
     * 
     */
    public Symbol createSymbol() {
        return new Symbol();
    }

    /**
     * Create an instance of {@link Raw }
     * 
     */
    public Raw createRaw() {
        return new Raw();
    }

}
