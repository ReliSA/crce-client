/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cz.zcu.kiv.jacc.javatypes.JClass;
import cz.zcu.kiv.jacc.javatypes.JPackage;
import cz.zcu.kiv.jacc.javatypes.impl.JClassImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JMethodImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JPackageImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JTypeVariableImpl;

import cz.zcu.kiv.crce.client.base.metadata.IdentityCapabilityVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;

/**
 *
 * @author Josef Kazak
 */
@Ignore("Ignored by J. Danek, these are not unit tests, these are IT, need some work to pass")
public class CrceJavaClientTest {
    
    private static final String RELISA_URI = "http://147.228.127.152:8080/rest/v2F";
    private static final String MOCK_URI = "http://private-anon-f2858d7f0-crceapi.apiary-mock.com";
    
    private CrceJavaClient el;
    
    @Before
    public void prepare(){
         el = new CrceJavaClientJersey(RELISA_URI);
    }
  
    @Test
    public void testGetServerUri(){
        String serverURI = el.getServerURI();
        assert !serverURI.isEmpty();
    }

    @Test
    public void testUploadResource() throws IOException, InterruptedException{
        el.setServerURI(RELISA_URI);
        final String FILE_PATH = "test.jar";
        final String BAD_FILE_PATH = "pom.xml";
        final String NON_EXISTING_FILE_PATH = "adsfse.jar";
        
        el.setServerURI(RELISA_URI);
        File f = new File(FILE_PATH);
        assert f.exists();
        
        el.uploadLibrary(FILE_PATH);
        
        f = new File(BAD_FILE_PATH);
        assert f.exists();
        
        boolean ex = false;
        try{
            el.uploadLibrary(BAD_FILE_PATH);
        }catch(IllegalArgumentException e){
            ex = true;
        }
        
        assert  ex;
        
        ex = false;
        try{
            el.uploadLibrary(NON_EXISTING_FILE_PATH);
        }catch(IOException e){
            ex = true;
        }
        
        assert  ex;
                
    }
    
    @Test
    public void testSetServerURI(){
        String before = el.getServerURI();
        
        el.setServerURI(MOCK_URI);
        assert(el.getServerURI().equals(MOCK_URI));
        
        el.setServerURI(RELISA_URI);
        assert(el.getServerURI().equals(RELISA_URI));
        
        el.setServerURI(before);                
    }
    
    @Test
    public void testMakeRequest() throws IOException{
        final String EXTERNAL_ID = "org.obcc.parking.gate";
        final String VERSION = "1.0.0";
        
        el.setServerURI(MOCK_URI);
        List<JClass> classes = new ArrayList<JClass>();
        JClassImpl clazz = new JClassImpl("MojeTrida");
        classes.add(clazz);
        JPackage pack = new JPackageImpl("cz.zcu.aswi.my.package", classes);
        clazz.setPackage(pack);
        pack.getJClasses().add(clazz);
        
        JMethodImpl method = new JMethodImpl("nejakaMetoda");
        method.setReturnType(new JTypeVariableImpl("void", null));
        clazz.getDeclaredMethods().add(method);

        Resources resources = el.makeRequest(new HashSet<>(Collections.singleton(pack)));
        IdentityCapabilityVO iden = resources.getResources().get(0).getIdentityCapability();
        
        assert iden.getExternalId().equals(EXTERNAL_ID);
        assert iden.getOriginalVersion().equals(VERSION);
    }

    @Test
    public void testDownloadArtifact() throws IOException {
        final String BUNDLE_ID = "6be68f5b-2af8-46c5-ac0c-8f7f0b59d8bf";
        final String LOCAL_DIR = ".";
        final OutputStream STREAM = new FileOutputStream(LOCAL_DIR + File.separator + "test.jar");

        el.setServerURI(RELISA_URI);
        el.downloadArtifact(BUNDLE_ID, STREAM);
        File f = new File(LOCAL_DIR + File.separator + "test.jar");
        assert(f.exists());
        f.delete();
    }
    
}
