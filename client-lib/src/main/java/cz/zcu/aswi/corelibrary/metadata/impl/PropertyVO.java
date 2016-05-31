package cz.zcu.aswi.corelibrary.metadata.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Value object for {@link cz.zcu.kiv.crce.metadata.Property}
 *
 * Date: 1.6.15
 *
 * @author Jakub Danek
 */
@XmlRootElement(name = "property")
public class PropertyVO extends ValueObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8674006690610431045L;
	private List<AttributeVO> attributes = new ArrayList<>();

    public PropertyVO() {
    }

    @Nonnull
    @XmlElementRef
    public List<AttributeVO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeVO> attributes) {
        this.attributes = attributes;
    }
}