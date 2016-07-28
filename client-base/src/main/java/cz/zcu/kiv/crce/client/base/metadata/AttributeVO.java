package cz.zcu.kiv.crce.client.base.metadata;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Value object for {@link cz.zcu.kiv.crce.metadata.Attribute}
 *
 * Date: 5.5.15
 *
 * @author Jakub Danek
 */
@XmlRootElement(name = "attribute")
public class AttributeVO extends ValueObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2137861259588372713L;
	/**
     * Attribute name.
     */
    private String name;
    /**
     * Attribute value.
     */
    private String value;
    /**
     * Attribute type.
     */
    private String type;

    public AttributeVO() {
    }

    public AttributeVO(String name, String value) {
        this.name = name;
        this.value = value;
        this.type = value.getClass().getName();
    }

    public AttributeVO(String name, String value, Class<?> type) {
        this.name = name;
        this.value = value;
        this.type = type.getName();
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}