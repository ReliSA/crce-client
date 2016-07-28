/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.java;

import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.jacc.javatypes.JClass;
import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Josef Zeman
 */
public interface CrceJavaClient {

    /**
     * set the url of server for coreLibrary
     * @param apiURI (String)
     */
    void setServerURI(String apiURI);
    
    /**
     * get url of the server
     * @return url
     */
    String getServerURI();
    
    /**
     * This method connect to server at uri and downloads requirements
     * @param classes (Set<JClass>)
     * @return
     * @throws IOException
     */
    Resources makeRequest(Set<JClass> classes) throws IOException;
 
    /**
     * This method check the input value for library content and uploads it on server
     * @param filePath (String)
     * @throws IOException
     */
    void uploadLibrary(String filePath) throws IOException;
    
}
