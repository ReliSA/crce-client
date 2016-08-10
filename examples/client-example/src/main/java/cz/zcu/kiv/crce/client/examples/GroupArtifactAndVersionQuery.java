package cz.zcu.kiv.crce.client.examples;

import java.io.IOException;

import cz.zcu.kiv.crce.client.base.metadata.ResourceVO;
import cz.zcu.kiv.crce.client.base.metadata.Resources;
import cz.zcu.kiv.crce.client.java.CrceJavaClient;
import cz.zcu.kiv.crce.client.java.CrceJavaClientJersey;

/**
 * This example shows search based on basic artifact coordinates (groupId, artifactId, version).
 *
 * Date: 10.8.16
 *
 * @author Jakub Danek
 */
public class GroupArtifactAndVersionQuery {

    public static void main(String[] args) {
        CrceJavaClient client = new CrceJavaClientJersey("http://147.228.127.152:8080/rest/v2/");

        try {
            Resources resources = client.makeRequest("obcc-parking-example", "carpark", "3.0.0");
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
