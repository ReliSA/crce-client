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
@XmlRootElement(name = "resources")
@XmlAccessorType (XmlAccessType.FIELD)
public class Resources {
    
    @XmlElement(name="resource")
    private List<ResourceVO> resources;

    /**
     * @return the resources
     */
    //@Nonnull
    //@XmlElementRef
    public List<ResourceVO> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(List<ResourceVO> resources) {
        this.resources = resources;
    }
    
    
    
}
