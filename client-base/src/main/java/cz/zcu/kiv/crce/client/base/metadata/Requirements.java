/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.base.metadata;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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

    public Requirements() {
    }

    public Requirements(Collection<GenericRequirementVO> requirements) {
        this.requirements = new LinkedList<>(requirements);
    }
    public List<GenericRequirementVO> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<GenericRequirementVO> requirements) {
        this.requirements = requirements;
    }
    
    
    
}
