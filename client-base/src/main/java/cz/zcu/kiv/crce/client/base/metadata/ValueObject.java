package cz.zcu.kiv.crce.client.base.metadata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Parent object for all CRCE value objects.
 *
 * Marked as Serializable and contains id and namespace properties.
 *
 * Date: 18.5.15
 *
 * @author Jakub Danek
 */
public class ValueObject implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6128720967772972230L;
	private String id;
    private String namespace;

    public ValueObject() {
    }

    public ValueObject(String id) {
        this.id = id;
    }

    public ValueObject(String id, String namespace) {
        this.id = id;
        this.namespace = namespace;
    }

    @XmlAttribute(name = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "namespace")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}