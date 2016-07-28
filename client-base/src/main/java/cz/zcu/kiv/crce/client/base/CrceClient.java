/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.base;

import java.io.IOException;
import cz.zcu.kiv.crce.client.base.metadata.ResourceVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;

/**
 *
 * @author Josef Zeman, Zdeněk Šmucr
 */
public interface CrceClient {
    
    /**
     * set server URI to connect
     * @param apiURI (url)
     */
    void setServerURI(String apiURI);
    
    /**
     * get server URI that is used to connect
     * @return connected server url
     */
    String getServerURI();
    
    /**
     * set absolute path for storing downloaded binary files
     * @param filePath (String)
     */
    void setBinaryFilePath(String filePath);
    
    /**
     * get absolute path for storing downloaded binary files
     * @return directory path
     */
    String getBinaryFilePath();
    
    /**
     * This method connect to the server and return list of all bundles
     * @return list of resources
     * @throws java.io.IOException 
     **/
    Resources listAllBundles() throws IOException;
    
    /**
     * this method upload the given file to the server
     * @param filename (String path)
     * @throws java.io.IOException
     */
    void uploadResource(String filename) throws IOException;
    
    /**
     * * This method connect to the server and return list of bundles filtered by externalId and version
     * @param externalId
     * @param version
     * @return list of resources
     * @throws java.io.IOException 
     */
    Resources filteredListBundles(String externalId, String version) throws IOException;
    
    /**
     * * This method connect to the server and return list of metadata filtered by externalId and version
     * @param externalId
     * @param version
     * @return list of metadatas
     * @throws java.io.IOException 
     */
    Resources filteredListMetadata(String externalId, String version) throws IOException;
    
    /**
     * This method connect to the server and return list of all metadata
     * @return list of metadatas
     * @throws java.io.IOException 
     */
    Resources listAllMetadata() throws IOException;
    
    /**
     * This method connect to the server and return metadata with id
     * @param id
     * @return single metadata
     * @throws java.io.IOException 
     */
    ResourceVO getMetadataForBundle(String id) throws IOException;
    
    /**
     * this method download binary file by given id from server
     *      // TODO fileName may change to targetDir
     * @param id
     * @return filename (String)
     * @throws java.io.IOException
     */
    String getResourceBinary(String id) throws IOException;
    
    
}
