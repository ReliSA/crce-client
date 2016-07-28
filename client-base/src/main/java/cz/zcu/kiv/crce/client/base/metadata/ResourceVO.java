package cz.zcu.kiv.crce.client.base.metadata;

import cz.zcu.kiv.crce.client.base.Constants;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * VO containing full information about a resource.
 *
 * List of all its capabilities, requirements, properties, etc.
 *
 * Date: 5.5.15
 *
 * @author Jakub Danek
 */
@XmlRootElement(name = "resource")
public class ResourceVO extends ValueObject {

    /**
    * 
    */
    private static final long serialVersionUID = 2934197103935521158L;
    private List<CapabilityVO> capabilities = new ArrayList<>();
    private IdentityCapabilityVO identityCapability = null;
    private List<PropertyVO> properties = new ArrayList<>();
    private List<GenericRequirementVO> requirements = new ArrayList<>();

    public ResourceVO() {
    }

    public ResourceVO(String id) {
        super(id);
    }

    
    
    @Nonnull
    @XmlElementRef
    public List<CapabilityVO> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<CapabilityVO> capabilities) {
        this.capabilities = capabilities;
    }

    @Nonnull
    @XmlElementRef
    public List<GenericRequirementVO> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<GenericRequirementVO> requirements) {
        this.requirements = requirements;
    }

    @Nonnull
    @XmlElementRef
    public List<PropertyVO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyVO> properties) {
        this.properties = properties;
    }

    /**
     * @return the identityCapability
     */
    @XmlTransient
    public IdentityCapabilityVO getIdentityCapability() {
        if(identityCapability == null){
            for(CapabilityVO cap : capabilities){
                if (cap.getNamespace().equals(Constants.NAMESPACE__CRCE_IDENTITY)) {
                    identityCapability = new IdentityCapabilityVO(cap);
                    break;
                }
            }
        }
        return identityCapability;
    }

    /**
     * @param identityCapability the identityCapability to set
     */
    public void setIdentityCapability(IdentityCapabilityVO identityCapability) {
        this.identityCapability = identityCapability;
    }
}
