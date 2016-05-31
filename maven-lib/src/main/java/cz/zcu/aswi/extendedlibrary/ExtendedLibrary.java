/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.aswi.extendedlibrary;

import cz.zcu.aswi.corelibrary.metadata.impl.Resources;
import cz.zcu.kiv.jacc.javatypes.JClass;
import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Josef Zeman
 */
public interface ExtendedLibrary {

    /**
     * set the url of server for coreLibrary
     * @param apiURI (String)
     */
    public void setServerURI(String apiURI);
    
    /**
     * get url of the server
     * @return url
     */
    public String getServerURI();
    
    /**
     * This method connect to server at uri and downloads requirements
     * @param classes (Set<JClass>)
     * @return
     * @throws IOException
     */
    public Resources makeRequest(Set<JClass> classes) throws IOException;
 
    /**
     * This method check the input value for library content and uploads it on server
     * @param filePath (String)
     * @throws IOException
     */
    public void uploadLibrary(String filePath) throws IOException;
    
}
