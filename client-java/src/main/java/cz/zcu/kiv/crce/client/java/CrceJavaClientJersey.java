/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.java;

import cz.zcu.kiv.crce.client.base.Constants;
import cz.zcu.kiv.crce.client.base.CrceClient;
import cz.zcu.kiv.crce.client.base.CrceClientJersey;
import cz.zcu.kiv.crce.client.base.metadata.GenericRequirementVO;
import cz.zcu.kiv.crce.client.base.metadata.Requirements;
import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.jacc.javatypes.JClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Josef Zeman
 */
public class CrceJavaClientJersey implements CrceJavaClient {

    private CrceClient crceClient;
    
    /**
     * This method creates new instance of CoreLibraryImpl
     * @param apiURI (String)
     */
    public CrceJavaClientJersey(String apiURI) {
        crceClient = new CrceClientJersey(apiURI);
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
     * @param classes (Set<JClass>)
     * @return
     * @throws IOException
     */
    @Override
    public Resources makeRequest(Set<JClass> classes) throws IOException {
        JavaMetadataParser parser = new RecursiveJavaMetadataParser();
        Set<GenericRequirementVO> requirements = parser.parse(classes);
        Requirements reqs = new Requirements();
        reqs.setRequirements(new ArrayList<>(requirements));
        
        Client c = ClientBuilder.newClient();
        WebTarget t  = c.target(getServerURI());
        t = t.path(Constants.METADATA_DIR).path(Constants.CATALOGUE_DIR).path("/");
        Invocation.Builder ib = t.request();
        Response response = ib.post(Entity.xml(reqs.getRequirements().get(0)));
        
        int status = response.getStatus();
        if(status != 200){
            throw new IOException("server returned status: " + status);
        }
        
        return response.readEntity(Resources.class);
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
    
}
