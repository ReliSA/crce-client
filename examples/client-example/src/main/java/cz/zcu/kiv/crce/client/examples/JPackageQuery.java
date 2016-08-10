package cz.zcu.kiv.crce.client.examples;

import java.io.IOException;
import java.util.Collections;

import cz.zcu.kiv.jacc.javatypes.impl.JClassImpl;
import cz.zcu.kiv.jacc.javatypes.impl.JPackageImpl;

import cz.zcu.kiv.crce.client.base.metadata.ResourceVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.crce.client.java.CrceJavaClient;
import cz.zcu.kiv.crce.client.java.CrceJavaClientJersey;

/**
 * This example shows search based on javatypes parameters.
 *
 * Date: 10.8.16
 *
 * @author Jakub Danek
 */
public class JPackageQuery {

    public static void main(String[] args) {
        CrceJavaClient client = new CrceJavaClientJersey("http://147.228.127.152:8080/rest/v2/");

        JClassImpl jClass = new JClassImpl("ILaneStatistics");
        JPackageImpl p = new JPackageImpl("cz.zcu.kiv.osgi.demo.parking.lane.statistics", Collections.singletonList(jClass));

        try {
            Resources resources = client.makeRequest(Collections.singleton(p));
            for (ResourceVO res : resources.getResources()) {
                //externalId = groupId.artifactId
                String externalId = res.getIdentityCapability().getExternalId();
                //version = major.minor.micro-qualifier
                String version = res.getIdentityCapability().getVersion();
                //id can be used to download the binary file
                String id = res.getId();

                System.out.println(externalId);
                System.out.println(version);
                System.out.println(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //log me, handle me, do something about me!
        }
    }
}
