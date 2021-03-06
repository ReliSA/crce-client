package cz.zcu.kiv.crce.client.base;


import cz.zcu.kiv.crce.client.base.metadata.GenericRequirementVO;
import cz.zcu.kiv.crce.client.base.metadata.Requirements;
import cz.zcu.kiv.crce.client.base.metadata.ResourceVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;


/**
 * Jersey implementation of the {@link CrceClient} API.
 *
 * @author Josef Zeman, Zdeněk Šmucr
 */
public class CrceClientJersey implements CrceClient {

	private String apiURI;
	private WebTarget webTarget;
	private String BinaryFilePath = ".";

	/**
	 * Creates new Object of CoreLibraryImpl and sets server URI to connect
	 *
	 * @param apiURI (String url)
	 */
	public CrceClientJersey(String apiURI) {
		setServerURI(apiURI);
	}

	/**
	 * set server URI to connect
	 *
	 * @param apiURI (url)
	 */
	@Override
	public void setServerURI(String apiURI) {
		this.apiURI = apiURI;
		webTarget = null;
	}

	/**
	 * get server URI that is used to connect
	 *
	 * @return connected server url
	 */
	@Override
	public String getServerURI() {
		return this.apiURI;
	}

	/**
	 * set absolute path for storing downloaded binary files
	 *
	 * @param filePath (String)
	 */
	@Override
	public void setBinaryFilePath(String filePath) {
		if (filePath == null) {
			BinaryFilePath = ".";
		} else {
			BinaryFilePath = filePath;
		}
	}

	/**
	 * get absolute path for storing downloaded binary files
	 *
	 * @return directory path
	 */
	@Override
	public String getBinaryFilePath() {
		return BinaryFilePath;
	}

	/**
	 * This method connect to the server at URI and return list of all bundles
	 *
	 * @return list of resources
	 * @throws IOException
	 */
	@Override
	public Resources listAllBundles() throws IOException {
		Client c = ClientBuilder.newClient();
		WebTarget t = c.target(apiURI);
		t = t.path(Constants.RESOURCES_DIR);
		Invocation.Builder ib = t.request(MediaType.APPLICATION_XML_TYPE);
		Response response = ib.get();

		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

		return response.readEntity(Resources.class);
	}

	/**
	 * This method uploads the given file to the server
	 *
	 * @param filename (String path)
	 * @throws java.io.IOException
	 */
	@Override
	public void uploadResource(String filename) throws IOException {
		Client c = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

		File file = new File(filename);
		final FileDataBodyPart filePart = new FileDataBodyPart("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);

		@SuppressWarnings("resource") final MultiPart multipart = new FormDataMultiPart().bodyPart(filePart);

		WebTarget t = c.target(apiURI);
		t = t.path(Constants.RESOURCES_DIR);
		Invocation.Builder ib = t.request();
		Response response = ib.accept("*/*").post(Entity.entity(multipart, MediaType.MULTIPART_FORM_DATA_TYPE));
		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

	}

	/**
	 * This method connect to the server at URI and return list of metadata filtered by externalId and version
	 * externalId = groupId.artefactId
	 *
	 * @param externalId
	 * @param version
	 * @return list of metadatas
	 * @throws IOException
	 */
	@Override
	public Resources filteredListMetadata(String externalId, String version) throws IOException {
		WebTarget t = connect();
		t = t.path(Constants.METADATA_DIR).path(Constants.CATALOGUE_DIR).path(externalId).path(version);
		Invocation.Builder ib = t.request(MediaType.APPLICATION_XML_TYPE);
		Response response = ib.get();

		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

		return response.readEntity(Resources.class);
	}

	@Override
	public Resources filteredListMetadata(Collection<GenericRequirementVO> requirements) throws IOException {
		WebTarget t = connect();
		t = t.path(Constants.METADATA_DIR).path(Constants.CATALOGUE_DIR);
		Invocation.Builder ib = t.request(MediaType.APPLICATION_XML_TYPE);
		Response response = ib.post(Entity.xml(new Requirements(requirements)));

		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

		return response.readEntity(Resources.class);
	}

	/**
	 * This method connect to the server at URI and return list of all metadata
	 *
	 * @return list of metadatas
	 * @throws IOException
	 */
	@Override
	public Resources listAllMetadata() throws IOException {
		WebTarget t = connect();
		t = t.path(Constants.METADATA_DIR);
		Invocation.Builder ib = t.request(MediaType.APPLICATION_XML_TYPE);
		Response response = ib.get();

		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

		return response.readEntity(Resources.class);
	}

	/**
	 * This method connect to the server at URI and return metadata with id
	 *
	 * @param id
	 * @return single metadata
	 * @throws IOException
	 */
	@Override
	public ResourceVO getMetadataForBundle(String id) throws IOException {
		WebTarget t = connect();
		t = t.path(Constants.METADATA_DIR).path(id);
		Invocation.Builder ib = t.request(MediaType.APPLICATION_XML_TYPE);
		Response response = ib.get();

		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

		return response.readEntity(ResourceVO.class);
	}

	/**
	 * This method connect to the server at URI and downloads a binary file
	 *
	 * @param id
	 * @throws IOException
	 */
	@Override
	public void getResourceBinary(String id, OutputStream stream) throws IOException {
		WebTarget t = connect();
		t = t.path(Constants.RESOURCES_DIR).path(id);
		Invocation.Builder ib = t.request(MediaType.APPLICATION_XML);
		Response response = ib.get();

		int status = response.getStatus();
		if (status != 200) {
			throw new IOException("server returned status: " + status);
		}

		createBinaryFile(t.getUri().toURL(), stream);
	}

	/**
	 * This method downloads a file and creates a copy of downloaded file
	 *
	 * @param url
	 * @param stream
	 */
	private void createBinaryFile(URL url, OutputStream stream) throws IOException {

		//Connection and deserialization classes
		URLConnection connection = url.openConnection();
		try (InputStream in = connection.getInputStream()) {
			//Use 4096-byte byte array as buffer and
			//write binary stream into it
			byte binaryChunk[] = new byte[4096];
			while ((in.read(binaryChunk)) != -1) {
				stream.write(binaryChunk);
			}
		}
		stream.close();
	}

	/**
	 * This method connects to url in apiURI and creates ClientBuilder
	 *
	 * @return WebTarget (new or used)
	 */
	private WebTarget connect() {
		if (webTarget != null) {
			return webTarget;
		} else {
			Client c = ClientBuilder.newClient();
			webTarget = c.target(apiURI);

			return webTarget;
		}
	}
}

