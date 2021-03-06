package cz.zcu.kiv.crce.client.base.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Generic VO for {@link cz.zcu.kiv.crce.metadata.Requirement}
 *
 * Date: 5.5.15
 *
 * @author Jakub Danek
 */
@XmlRootElement(name = "requirement")
public class GenericRequirementVO extends ValueObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8785440571943517681L;
	/**
     * List of requirement attributes.
     */
    private List<AttributeVO> attributes = new ArrayList<>();
    /**
     * List of requirement's sub-requirements.
     */
    private List<GenericRequirementVO> children = new ArrayList<>();

    @Nonnull
    @XmlElementRef
    public List<AttributeVO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeVO> attributes) {
        this.attributes = attributes;
    }

    @Nonnull
    @XmlElementRef
    public List<GenericRequirementVO> getChildren() {
        return children;
    }

    public void setChildren(List<GenericRequirementVO> children) {
        this.children = children;
    }
}