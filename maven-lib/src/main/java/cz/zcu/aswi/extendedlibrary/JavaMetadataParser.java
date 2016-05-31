package cz.zcu.aswi.extendedlibrary;

import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import cz.zcu.kiv.jacc.javatypes.JClass;

import cz.zcu.aswi.corelibrary.metadata.impl.GenericRequirementVO;

/**
 * Service interface of the metadata parser - allows transforming
 * JaCC representations into capabilities and vice versa.
 *
 * Date: 24.11.15
 *
 * @author Jakub Danek
 * 
 */
@ParametersAreNonnullByDefault
public interface JavaMetadataParser {
    /**
     * Transform JaCC #classes into set of Capabilities.
     * @param classes set of classes to be parsed
     * @return respective requirements
     */
    Set<GenericRequirementVO> parse(Set<JClass> classes);

    /**s
     * .The mirror operation to the #parse method.
     * @param requirements set of capabilities representing classes to be mapped
     * @return respective JClass instances
     */
    Set<JClass> map(Set<GenericRequirementVO> requirements);

}
