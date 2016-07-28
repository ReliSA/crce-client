/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.kiv.crce.client.java;

import cz.zcu.kiv.crce.client.base.metadata.IdentityCapabilityVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.jacc.javatypes.JClass;
import cz.zcu.kiv.jacc.javatypes.impl.JClassImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JMethodImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JPackageImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JTypeVariableImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Josef Kazak
 */

public class CrceJavaClientTest {
    
    private static final String RELISA_URI = "http://danekja.org:8086/rest/v2";
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
        JPackageImpl pack = new JPackageImpl("cz.zcu.aswi.my.package", classes);
        clazz.setPackage(pack);
        
        JMethodImpl method = new JMethodImpl("nejakaMetoda");
        method.setReturnType(new JTypeVariableImpl("void", null));
        clazz.getDeclaredMethods().add(method);

        Resources resources = el.makeRequest(new HashSet<>(classes));
        IdentityCapabilityVO iden = resources.getResources().get(0).getIdentityCapability();
        
        assert iden.getExternalId().equals(EXTERNAL_ID);
        assert iden.getOriginalVersion().equals(VERSION);
    }
    
    
}
