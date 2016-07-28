/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.base.metadata;

import java.util.ArrayList;	
import java.util.Arrays;
import java.util.List;	
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang3.StringUtils;

import cz.zcu.kiv.crce.client.base.Constants;


@ParametersAreNonnullByDefault
@XmlRootElement(name = "capability")
public class IdentityCapabilityVO extends ValueObject {

    private static final long serialVersionUID = 2809281317607315849L;

    /**
     * CRCE external id - identificator from the external system.	
     *
     * E.g. osgi symbolic name.
     */    
    private AttributeVO externalId;
    /**
     * CRCE Readable name. E.g. osgi presentation name.
     */
    private AttributeVO name;
    /**
     * Resource version string representation
     */
    private AttributeVO version;

    /**
     * Original version of the resource (if CRCE calculates
     * the version itself).
     */
    private AttributeVO originalVersion;
    /**
     * Size of the resource binary.
     */
    private AttributeVO size;
    /**
     * List of resource Type tags
     */
    private AttributeVO types;
    /**
     * List of resource Category tags.
     */
    private AttributeVO categories;

    public IdentityCapabilityVO() {
        this("");
    }

    public IdentityCapabilityVO(String id) {
        super(id, Constants.NAMESPACE__CRCE_IDENTITY);
    }

    public IdentityCapabilityVO(String id, String externalId, String name, String version) {
        this(id);
        this.externalId = new AttributeVO(Constants.ATTRIBUTE__EXTERNAL_ID, externalId);
        this.name = new AttributeVO(Constants.ATTRIBUTE__NAME, name);
        this.version = new AttributeVO(Constants.ATTRIBUTE__VERSION, version);
    }
    
    public IdentityCapabilityVO(CapabilityVO cap) {
    	super(cap.getId(), cap.getNamespace());
    	
    	List<AttributeVO> attrs = cap.getAttributes();
    	for (AttributeVO attr : attrs) {
    		switch (attr.getName()) {
    			case Constants.ATTRIBUTE__CATEGORIES:
    				this.categories = attr;
    				break;
    			case Constants.ATTRIBUTE__EXTERNAL_ID:
    				this.externalId = attr;
    				break;
    			case Constants.ATTRIBUTE__NAME:
    				this.name = attr;
    				break;
    			case Constants.ATTRIBUTE__SIZE:
    				this.size = attr;
    				break;
    			case Constants.ATTRIBUTE__VERSION:
    				this.version = attr;
    				break;
    			case Constants.ATTRIBUTE__ORIGINAL_VERSION:
    				this.originalVersion = attr;
    				break;
    			case Constants.ATTRIBUTE__TYPES:
    				this.types = attr;
    				break;
    		}
    		
    	}

    }

    /*
    ################MAPPING INTERFACE #########################
     */

    @XmlElementRef
    AttributeVO getExternalIdAt() {
        return externalId;
    }

    void setExternalIdAt(AttributeVO externalId) {
        this.externalId = externalId;
    }

    @XmlElementRef
    AttributeVO getNameAt() {
        return name;
    }

    void setNameAt(AttributeVO nameAt) {
        this.name = nameAt;
    }

    @XmlElementRef
    AttributeVO getVersionAt() {
        return version;
    }

    void setVersionAt(AttributeVO versionAt) {
        this.version = versionAt;
    }

    @XmlElementRef
    AttributeVO getOriginalVersionAt() {
        return originalVersion;
    }

    void setOriginalVersionAt(AttributeVO originalVersionAt) {
        this.originalVersion = originalVersionAt;
    }

    @XmlElementRef
    AttributeVO getSizeAt() {
        return size;
    }

    void setSizeAt(AttributeVO sizeAt) {
        this.size = sizeAt;
    }

    @XmlElementRef
    AttributeVO getTypesAt() {
        return types;
    }

    void setTypesAt(AttributeVO typesAt) {
        this.types = typesAt;
    }

    @XmlElementRef
    AttributeVO getCategoriesAt() {
        return categories;
    }

    void setCategoriesAt(AttributeVO categoriesAt) {	
        this.categories = categoriesAt;	
    }
    
    /*
     ################### PUBLIC INTERFACE #########################
     */

    @Nonnull
    public String getExternalId() {
        return externalId.getValue();
    }

    protected void setExternalId(String externalId) {
        this.externalId.setValue(externalId);
    }

    @Nonnull
    public String getName() {
        return name.getValue();	
    }
    
    protected void setName(String name) {
        this.name.setValue(name);
    }

    @Nonnull
    public String getVersion() {
        return version.getValue();
    }

    protected void setVersion(String version) {
        this.version.setValue(version);	
    }
    
    @Nullable
    public String getOriginalVersion() {
        if(originalVersion == null) {
            return null;
        }
        return originalVersion.getValue();
    }

    public void setOriginalVersion(String originalVersion) {
        if(this.originalVersion == null) {
            this.originalVersion = new AttributeVO(Constants.ATTRIBUTE__VERSION, originalVersion);
        } else {
            this.originalVersion.setValue(originalVersion);	
        }	
    }
    
    @Nullable
    @XmlTransient
    public Long getSize() {
        if(size == null) {
            return null;
        }
        return Long.parseLong(size.getValue());	
    }
    
    public void setSize(Long size) {
        if(this.size == null) {
            this.size = new AttributeVO(Constants.ATTRIBUTE__SIZE, size.toString());
        } else {
            this.size.setValue(size.toString());
        }
    }

    @Nonnull
    public List getTypes() {
        if(this.types == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(types.getValue().split(","));
    }

    protected void setTypes(@Nonnull List types) {
        String value = StringUtils.join(types, ",");
        if(this.types == null) {
            this.types = new AttributeVO(Constants.ATTRIBUTE__TYPES, value);
        } else {
            this.types.setValue(value);
        }
    }

    @Nonnull
    public List<String> getCategories() {
        if(categories == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(categories.getValue().split(","));
    }

    public void addCategory(String cat) {
        if(categories == null) {
            this.categories = new AttributeVO(Constants.ATTRIBUTE__CATEGORIES, cat);
        } else {
            String oldVal = this.categories.getValue();
            oldVal = oldVal.concat("," + cat);
            this.categories.setValue(oldVal);
        }
    }

    protected void setCategories(@Nonnull List<String> categories) {
        String value = StringUtils.join(categories, ",");
        if(this.categories == null) {
            this.categories = new AttributeVO(Constants.ATTRIBUTE__CATEGORIES, value);
        } else {
            this.categories.setValue(value);
        }	
    }
}
