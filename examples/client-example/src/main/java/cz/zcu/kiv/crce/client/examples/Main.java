package cz.zcu.kiv.crce.client.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.zcu.kiv.jacc.javatypes.JClass;
import cz.zcu.kiv.jacc.javatypes.impl.JClassImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JMethodImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JPackageImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JTypeVariableImpl;

import cz.zcu.kiv.crce.client.base.Constants;
import cz.zcu.kiv.crce.client.base.CrceClient;
import cz.zcu.kiv.crce.client.base.CrceClientJersey;
import cz.zcu.kiv.crce.client.base.metadata.CapabilityVO;
import cz.zcu.kiv.crce.client.base.metadata.IdentityCapabilityVO;
import cz.zcu.kiv.crce.client.base.metadata.ResourceVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.crce.client.java.CrceJavaClient;
import cz.zcu.kiv.crce.client.java.CrceJavaClientJersey;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Josef Zeman
 */
public class Main {
    
    private static final String RELISA_URI = "http://danekja.org:8086/rest/v2";
    private static final String MOCK_URI = "http://private-anon-f2858d7f0-crceapi.apiary-mock.com";
    
    private static void myAssert(boolean condition){
        if(!condition){
            throw new AssertionError();
        }
    }
    
    public static void coreLibrarySmokeTest1() throws IOException, InterruptedException{
        final String LOCAL_DIR = ".";
        final String EXTERNAL_ID = "org.obcc.parking.gate";
        final String VERSION = "1.0.0-SNAPSHOT";
        
        CrceClient cl = new CrceClientJersey(RELISA_URI);
        Resources listAllBundles = cl.listAllBundles();
        Resources listAllMetadata = cl.listAllMetadata();
        
        String id = listAllBundles.getResources().get(0).getId();
        myAssert(id.equals(listAllMetadata.getResources().get(0).getId()));
        
        cl.setServerURI(MOCK_URI);

        cl.setServerURI(RELISA_URI);
        
        ResourceVO metadataForBundle = cl.getMetadataForBundle(id);
        myAssert(metadataForBundle.getCapabilities().size() > 0);
        
        
        CapabilityVO cap;
        cap = listAllBundles.getResources().get(0).getCapabilities().get(0);
        if (cap.getNamespace().equals(Constants.NAMESPACE__CRCE_IDENTITY)) {
        	IdentityCapabilityVO idCap = new IdentityCapabilityVO(cap);
        	idCap.getVersion();
        }
        
        cl.setBinaryFilePath(LOCAL_DIR);
        
        cl.setServerURI(RELISA_URI);
        String fileName = cl.getResourceBinary(id);
        File f = new File(cl.getBinaryFilePath() + File.separator + fileName);
        myAssert(f.exists());        
                
        cl.uploadResource(f.getAbsolutePath());
        
        f.delete();
        
    }
    
    public static void extendedLibrarySmokeTest1() throws IOException{
        final String EXTERNAL_ID = "org.obcc.parking.gate";
        final String VERSION = "1.0.0";
        
        CrceJavaClient el = new CrceJavaClientJersey(RELISA_URI);
        el.setServerURI(RELISA_URI);
        final String FILE_PATH = "test.jar";
        File f = new File(FILE_PATH);
        myAssert(f.exists());
        el.uploadLibrary(FILE_PATH);
        el.setServerURI(MOCK_URI);
        
        List<JClass> classes = new ArrayList<JClass>();
        JClassImpl clazz = new JClassImpl("MojeTrida");
        classes.add(clazz);
        JPackageImpl pack = new JPackageImpl("cz.zcu.aswi.my.package", classes);
        clazz.setPackage(pack);
        
        JMethodImpl method = new JMethodImpl("nejakaMetoda");
        method.setReturnType(new JTypeVariableImpl("void", null));
        clazz.getDeclaredMethods().add(method);

        Resources resources = el.makeRequest(Collections.singleton(pack));
        IdentityCapabilityVO iden = resources.getResources().get(0).getIdentityCapability();
        
        assert iden.getExternalId().equals(EXTERNAL_ID);
        assert iden.getOriginalVersion().equals(VERSION);
    }
    
    public static void main(String[] args) throws IOException, InterruptedException
    {
        
       coreLibrarySmokeTest1();
       extendedLibrarySmokeTest1();
        
    }
    
}
