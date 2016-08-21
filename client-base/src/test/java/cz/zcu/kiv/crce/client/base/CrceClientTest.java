/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.base;

import cz.zcu.kiv.crce.client.base.metadata.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Josef Kazak
 */
@Ignore("Ignored by J. Danek, these are not unit tests, these are IT, need some work to pass")
public class CrceClientTest {

	private static final String RELISA_URI = "http://147.228.127.152:8080/rest/v2";
	private static final String MOCK_URI = "http://private-anon-f2858d7f0-crceapi.apiary-mock.com";

	private CrceClient cl;

	@Before
	public void prepare() {
		cl = new CrceClientJersey(RELISA_URI);
	}


	@Test
	public void testListAllBundles() throws IOException {
		final String ATTR_NAME = "categories";
		final String ATTR_VALUE = "zip,osgi,metrics,compared";
		final String ATTR_TYPE = "java.lang.String";

		cl.setServerURI(RELISA_URI);
		Resources listAllBundles = cl.listAllBundles();
		List<ResourceVO> resources = listAllBundles.getResources();
		assert (resources.size() > 0);
		List<AttributeVO> attributes = resources.get(0).getCapabilities().get(0).getAttributes();
		assert (attributes.size() > 0);
		AttributeVO attribute = attributes.get(0);
		assert (attribute.getName().equals(ATTR_NAME));
		assert (attribute.getValue().equals(ATTR_VALUE));
		assert (attribute.getType().equals(ATTR_TYPE));
	}

	@Test
	public void testGetServerUri() {
		String serverURI = cl.getServerURI();
		assert !serverURI.isEmpty();
	}

	@Test
	public void testSetBinaryFilePath() {
		final String HASH = "RXhpZgAATU0AKgAAAAgACgEPAAIAAAAGAAAAhgEQAAIAAAAMAAAAjAESAAMAAAABAAEA";
		String before = cl.getBinaryFilePath();
		cl.setBinaryFilePath(HASH);
		assert cl.getBinaryFilePath().equals(HASH);
		cl.setBinaryFilePath(before);
	}

	@Test
	public void testUploadResource() throws IOException, InterruptedException {
		final String FILE_PATH = "test.jar";
		final String FILE_NAME = "test.jar";
		final int SLEEP_TIME = 5000;

		cl.setServerURI(RELISA_URI);
		File f = new File(FILE_PATH);
		assert f.exists();
		cl.uploadResource(FILE_PATH);
		Thread.sleep(SLEEP_TIME);
		Resources metadata = cl.listAllMetadata();
		boolean found = false;
		for (ResourceVO res : metadata.getResources()) {
			for (CapabilityVO cap : res.getCapabilities()) {
				for (AttributeVO attr : cap.getAttributes()) {
					if (attr.getName().equals(Constants.ATTRIBUTE__NAME) && attr.getValue().equals(FILE_NAME)) {
						found = true;
					}
				}
			}
		}
		assert found == true;

	}

	@Test
	public void testSetServerURI() {
		String before = cl.getServerURI();

		cl.setServerURI(MOCK_URI);
		assert (cl.getServerURI().equals(MOCK_URI));

		cl.setServerURI(RELISA_URI);
		assert (cl.getServerURI().equals(RELISA_URI));

		cl.setServerURI(before);
	}

	@Test
	public void testFilteredListMetadata() throws IOException {
		final String EXTERNAL_ID = "org.obcc.parking.gate";
		final String VERSION = "1.0.0-SNAPSHOT";
		final String ORIG_VERSION = "1.0.0";

		cl.setServerURI(MOCK_URI);
		Resources filteredListMetadata = cl.filteredListMetadata(EXTERNAL_ID, VERSION);
		ResourceVO resource = filteredListMetadata.getResources().get(0);
		IdentityCapabilityVO ic = resource.getIdentityCapability();
		assert (ic.getExternalId().equals(EXTERNAL_ID));
		assert (ic.getOriginalVersion().equals(ORIG_VERSION));
	}

	@Test
	public void testGetBinaryFilePath() {
		String binaryFilePath = cl.getBinaryFilePath();
		assert !binaryFilePath.isEmpty();
	}

	@Test
	public void testListAllMetadata() throws IOException {
		final String ATTR_NAME = "categories";
		final String ATTR_VALUE = "zip,osgi,metrics,compared";
		final String ATTR_TYPE = "java.lang.String";

		cl.setServerURI(RELISA_URI);
		Resources listAllMetadata = cl.listAllMetadata();
		List<ResourceVO> resources = listAllMetadata.getResources();
		assert (resources.size() > 0);
		List<AttributeVO> attributes = resources.get(0).getCapabilities().get(0).getAttributes();
		assert (attributes.size() > 0);
		AttributeVO attribute = attributes.get(0);
		assert (attribute.getName().equals(ATTR_NAME));
		assert (attribute.getValue().equals(ATTR_VALUE));
		assert (attribute.getType().equals(ATTR_TYPE));
	}

	@Test
	public void testGetMetadataForBundle() throws IOException {
		final String ATTR_NAME = "symbolic-name";
		final String ATTR_VALUE = "obcc-parking-example.statsbase";
		final String ATTR_TYPE = "class java.lang.String";
		final String IDENT_NAME = "obcc-parking-example.statsbase";
		final String BUNDLE_ID = "b8ac84e9-c2a9-4f67-a43a-803216eb997e";

		cl.setServerURI(RELISA_URI);
		ResourceVO metadataForBundle = cl.getMetadataForBundle(BUNDLE_ID);
		IdentityCapabilityVO identityCapability = metadataForBundle.getIdentityCapability();
		CapabilityVO cap = metadataForBundle.getCapabilities().get(1);
		assert (identityCapability.getName().equals(IDENT_NAME));
		AttributeVO attr = cap.getAttributes().get(0);
		assert (attr.getName().equals(ATTR_NAME));
		assert (attr.getType().equals(ATTR_TYPE));
		assert (attr.getValue().equals(ATTR_VALUE));
	}

	@Test
	public void testGetResourceBinary() throws IOException {
		final String BUNDLE_ID = "6be68f5b-2af8-46c5-ac0c-8f7f0b59d8bf";
		final String LOCAL_DIR = ".";
		final OutputStream STREAM = new FileOutputStream(LOCAL_DIR + File.separator + "test.jar");

		cl.setServerURI(RELISA_URI);
		cl.getResourceBinary(BUNDLE_ID, STREAM);
		File f = new File(LOCAL_DIR + File.separator + "test.jar");
		assert (f.exists());
		f.delete();
	}

}
