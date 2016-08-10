package cz.zcu.kiv.crce.client.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import cz.zcu.kiv.jacc.javatypes.JClass;
import cz.zcu.kiv.jacc.javatypes.JField;
import cz.zcu.kiv.jacc.javatypes.JMethod;
import cz.zcu.kiv.jacc.javatypes.JPackage;
import cz.zcu.kiv.jacc.javatypes.JType;

import cz.zcu.kiv.crce.client.base.Constants;
import cz.zcu.kiv.crce.client.base.metadata.AttributeVO;
import cz.zcu.kiv.crce.client.base.metadata.GenericRequirementVO;

/**
 * Recursive {@link JavaMetadataParser} implementation.
 *
 * Date: 24.11.15
 *
 * @author Jakub Danek
 * @author Josef Zeman
 * 
 */
@ParametersAreNonnullByDefault
public class RecursiveJavaMetadataParser implements JavaMetadataParser {

    @Override
    public Set<GenericRequirementVO> parse(Set<JPackage> toParse) {
        Map<String, GenericRequirementVO> packages = new HashMap<>();

        String pckgName;
        GenericRequirementVO pckg;
        for (JPackage jPackage : toParse) {
            pckgName = jPackage.getName();
            pckg = packages.get(pckgName);
            if(pckg == null) {
                pckg = createPackage(pckgName);
                packages.put(pckgName, pckg);
            }

            for (JClass jClass : jPackage.getJClasses()) {
                pckg.getChildren().add(parseClass(jClass));
            }
        }

        return new HashSet<>(packages.values());
    }

    @Override
    public Set<JPackage> map(Set<GenericRequirementVO> requirements) {
        throw new UnsupportedOperationException("Not supported yet!");
    }

    /**
     *
     * @param jClass class to be transformed into requirements
     * @return requirements representing the given class
     */
    private GenericRequirementVO parseClass(JClass jClass) {
        boolean firstLevel = jClass.getOuterClass() == null;
        GenericRequirementVO req;
        if(firstLevel) {
            req = new GenericRequirementVO();
            req.setNamespace(Constants.NAMESPACE__JAVA_CLASS);
        } else {
            req = new GenericRequirementVO();
            req.setNamespace(Constants.NAMESPACE__JAVA_INNER_CLASS);
        }

        List<AttributeVO> attributes = req.getAttributes();
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__NAME, jClass.getShortName()));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__ABSTRACT, jClass.getModifiers().isAbstract()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__ENUM, jClass.isEnum()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__INTERFACE, jClass.isInterface()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__ANNOTATION, jClass.isAnnotation()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__FINAL, jClass.getModifiers().isFinal()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__STATIC, jClass.getModifiers().isStatic()+"", Boolean.class));

        for (JMethod jMethod : jClass.getMethods()) {
            req.getChildren().add(parseMethod(jMethod));            
        }

        for (JField jField : jClass.getFields()) {
            req.getChildren().add(parseField(jField));
        }

        for (JClass aClass : jClass.getInnerClasses()) {
            req.getChildren().add(parseClass(aClass));
        }

        return req;
    }

    /**
     *
     * @param jField field to be transformed into requirements
     * @return requirements representing the given field
     */
    private GenericRequirementVO parseField(JField jField) {
        GenericRequirementVO req = new GenericRequirementVO();
        req.setNamespace(Constants.NAMESPACE__JAVA_FIELD);

        List<AttributeVO> attributes = req.getAttributes();
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__NAME, jField.getName()));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__FINAL, jField.getModifiers().isFinal()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__STATIC, jField.getModifiers().isStatic()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__VOLATILE, jField.getModifiers().isVolatile()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__TYPE, jField.getType().getName()));

        return req;
    }

    /**
     *
     * @param jMethod method to be transformed into requirements
     * @return requirements representing the given method
     */
    private GenericRequirementVO parseMethod(JMethod jMethod) {
        GenericRequirementVO req = new GenericRequirementVO();
        req.setNamespace(Constants.NAMESPACE__JAVA_METHOD);

        List<AttributeVO> attributes = req.getAttributes();
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__NAME, jMethod.getName()));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__CONSTRUCTOR, jMethod.isConstructor()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__ABSTRACT, jMethod.getModifiers().isAbstract()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__FINAL, jMethod.getModifiers().isFinal()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__STATIC, jMethod.getModifiers().isStatic()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__SYNCHRONIZED, jMethod.getModifiers().isSynchronized()+"", Boolean.class));
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__RETURN_TYPE, jMethod.getReturnType().getName()));
        List<String> tmp = new LinkedList<>();
        for (JType jType : jMethod.getParameterTypes()) {
            tmp.add(jType.getName());
        }
        if(tmp.size() > 0){
            attributes.add(new AttributeVO(Constants.ATTRIBUTE__PARAM_TYPES, tmp.toString(), List.class));
        }
        
        tmp = new LinkedList<>();
        for (JType jType : jMethod.getExceptionTypes()) {
            tmp.add(jType.getName());
        }
        if(tmp.size() > 0){
            attributes.add(new AttributeVO(Constants.ATTRIBUTE__EXCEPTIONS, tmp.toString(), List.class));
        }
        
        return req;
    }

    /**
     *
     * @param packageName name of the package for which the requirements is created
     * @return requirements representing the given package
     */
    private GenericRequirementVO createPackage(String packageName) {
        GenericRequirementVO req = new GenericRequirementVO();
        req.setNamespace(Constants.NAMESPACE__JAVA_PACKAGE);
        
        List<AttributeVO> attributes = req.getAttributes();
        attributes.add(new AttributeVO(Constants.ATTRIBUTE__NAME, packageName));

        return req;
    }
}
