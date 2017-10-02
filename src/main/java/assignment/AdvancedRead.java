package assignment;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import ca.uhn.fhir.model.api.Bundle;
//import ca.uhn.fhir.model.dstu2.resource.Bundle;

import java.util.HashSet;
import java.util.List;

/**
 *
 */
public class AdvancedRead {

    //This Connection object is the same as the one you implemented in the first FHIR task
    private Connection connection = null;

    public AdvancedRead(Connection connection) {
        this.connection = connection;
    }

    public int getTotalNumPatientsByObservation(String loincCode) {
        int num = -1;
        //Place your code here
        Bundle bundle = this.connection.getClient().search()
                .forResource(Observation.class)
                .where(new TokenClientParam("code")
                        .exactly().systemAndCode("http://loinc.org", loincCode))
                .prettyPrint()
                .execute();

        List<Observation> observations = bundle.getResources(Observation.class);
        HashSet hs = new HashSet();
        for(Observation o : observations){

            hs.add(o.getSubject().getReference().getIdPart());
        }
        num = hs.size();
        return num;
    }


    public static void main(String[] args) {
        //  Connection conn = new Connection("https://fhirtesting.hdap.gatech.edu/hapi-fhir-jpaserver-example/baseDstu2");
        Connection conn = new Connection("http://localhost:8080/hapi-fhir-jpaserver-example/baseDstu2");
        AdvancedRead ar = new AdvancedRead(conn);
        System.out.println(ar.getTotalNumPatientsByObservation("4548-4"));

    }



}