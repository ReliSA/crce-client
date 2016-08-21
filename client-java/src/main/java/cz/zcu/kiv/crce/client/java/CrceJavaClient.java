/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.java;

import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.jacc.javatypes.JPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * @author Josef Zeman
 */
public interface CrceJavaClient {

	/**
	 * set the url of server for coreLibrary
	 *
	 * @param apiURI (String)
	 */
	void setServerURI(String apiURI);

	/**
	 * get url of the server
	 *
	 * @return url
	 */
	String getServerURI();

	/**
	 * This method connects to the server at uri and downloads resources based on the given requirements
	 *
	 * @param packages set of JPackage instances representing the requirements which returned resources must provide
	 * @return
	 * @throws IOException
	 */
	Resources makeRequest(Set<JPackage> packages) throws IOException;

	/**
	 * This method connects to the server at uri and downloads resources based on the given requirements
	 *
	 * @param groupId    wanted groupId
	 * @param artifactId wanted artifactId
	 * @param @version   wanted version
	 * @return
	 * @throws IOException
	 */
	Resources makeRequest(String groupId, String artifactId, String version) throws IOException;

	/**
	 * This method connects to the server at uri and downloads resources based on the given requirements.
	 * <p>
	 * Searches only within the given groupId-artifactId space.
	 *
	 * @param groupId    wanted groupId
	 * @param artifactId wanted artifactId
	 * @param packages   set of JPackage instances representing the requirements which returned resources must provide
	 * @return
	 * @throws IOException
	 */
	Resources makeRequest(String groupId, String artifactId, Set<JPackage> packages) throws IOException;

	/**
	 * This method check the input value for library content and uploads it on server
	 *
	 * @param filePath (String)
	 * @throws IOException
	 */
	void uploadLibrary(String filePath) throws IOException;

	/**
	 * Download artifact from CRCE with the given ID.
	 *
	 * @param id     id of the wanted artifact in CRCE
	 * @param stream output stream to write the file data into
	 */
	void downloadArtifact(String id, OutputStream stream) throws IOException;
}
