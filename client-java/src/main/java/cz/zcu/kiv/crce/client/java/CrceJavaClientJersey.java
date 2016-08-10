/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.java;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cz.zcu.kiv.jacc.javatypes.JPackage;

import cz.zcu.kiv.crce.client.base.Constants;
import cz.zcu.kiv.crce.client.base.CrceClient;
import cz.zcu.kiv.crce.client.base.CrceClientJersey;
import cz.zcu.kiv.crce.client.base.metadata.AttributeVO;
import cz.zcu.kiv.crce.client.base.metadata.GenericRequirementVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;

/**
 *
 * @author Josef Zeman
 */
public class CrceJavaClientJersey implements CrceJavaClient {

    private CrceClient crceClient;
    private JavaMetadataParser metadataParser;
    
    /**
     * This method creates new instance of CoreLibraryImpl
     * @param apiURI (String)
     */
    public CrceJavaClientJersey(String apiURI) {
        crceClient = new CrceClientJersey(apiURI);
        this.metadataParser = new RecursiveJavaMetadataParser();
    }
    
    /**
     * set the url of server for coreLibrary
     * @param apiURI (String)
     */
    @Override
    public void setServerURI(String apiURI){
        crceClient.setServerURI(apiURI);
    }
    
    /**
     * get url of the server
     * @return url
     */
    @Override
    public String getServerURI(){
        return crceClient.getServerURI();
    }
    
    /**
     * This method connect to server at uri and downloads requirements
     * @param packages (Set<JClass>)
     * @return
     * @throws IOException
     */
    @Override
    public Resources makeRequest(Set<JPackage> packages) throws IOException {
        Set<GenericRequirementVO> requirements = metadataParser.parse(packages);

        return crceClient.filteredListMetadata(requirements);
    }

    @Override
    public Resources makeRequest(String groupId, String artifactId, String version) throws IOException {
        String externalId = buildCrceExternalId(groupId, artifactId);
        return crceClient.filteredListMetadata(externalId, version);
    }

    @Override
    public Resources makeRequest(String groupId, String artifactId, Set<JPackage> packages) throws IOException {
        String externalId = buildCrceExternalId(groupId, artifactId);
        GenericRequirementVO identityReq = new GenericRequirementVO();
        identityReq.setNamespace(Constants.NAMESPACE__CRCE_IDENTITY);
        AttributeVO at = new AttributeVO(Constants.ATTRIBUTE__EXTERNAL_ID, externalId);
        identityReq.getAttributes().add(at);

        Set<GenericRequirementVO> reqs = metadataParser.parse(packages);
        reqs.add(identityReq);

        return crceClient.filteredListMetadata(reqs);
    }

    /**
     * This method check the input value for library content and uploads it on server
     * @param filePath (String)
     * @throws IOException
     */
    @Override
    public void uploadLibrary(String filePath) throws IOException {
        File f = new File(filePath);  
        if (f.exists()) {
            String suff = filePath.substring(filePath.length()-4);
            switch(suff) {
                case ".jar":
                    crceClient.uploadResource(filePath);
                    break;
                case ".war":
                    crceClient.uploadResource(filePath);
                    break;
                case ".ear":
                    crceClient.uploadResource(filePath);
                    break;
                case "rar":
                    crceClient.uploadResource(filePath);
                    break;
                default:
                    throw new IllegalArgumentException("The input file is not s java library.");
            }
        } else {
            throw new IOException("The input file doesn't exist.");
        }
    }

    private String buildCrceExternalId(String groupId, String artifactId) {
        if(groupId == null) {
            return artifactId;
        } else if (artifactId == null) {
            return groupId;
        } else {
            return StringUtils.join(groupId, ".", artifactId);
        }
    }
    
}
