/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.aswi.corelibrary.metadata.impl;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Josef Zeman
 */
@XmlRootElement(name = "requirements")
@XmlAccessorType (XmlAccessType.FIELD)
public class Requirements {
    
    @XmlElement(name="requirement")
    private List<GenericRequirementVO> requirements;

    /**
     * @return the resources
     */
    //@Nonnull
    //@XmlElementRef
    public List<GenericRequirementVO> getRequirements() {
        return requirements;
    }

    /**
     * @param resources the resources to set
     */
    public void setRequirements(List<GenericRequirementVO> requirements) {
        this.requirements = requirements;
    }
    
    
    
}
