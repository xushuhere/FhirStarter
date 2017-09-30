package assignment;


import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SimpleRead {

    //This Connection object is the same as the one you implemented in the first FHIR task
    private Connection connection = null;

    public SimpleRead(Connection connection) {
        this.connection = connection;
    }

    public String getNameByPatientID(String id) {
        String name = null;

        Patient pat = this.connection.getClient().read(Patient.class,id);
        HumanNameDt patName = pat.getName().get(0);
        name = patName.getNameAsSingleString();



        return name;

    }

    public List<String> getIDByPatientName(String name) {
        List<String> answer = new ArrayList<String>();

        Bundle results = this.connection.getClient().search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value(name)).returnBundle(ca.uhn.fhir.model.dstu2.resource.Bundle.class).execute();

        List<Bundle.Entry> list = results.getEntry();
        for (Bundle.Entry bundleEntry : list){
            Patient patient = (Patient) bundleEntry.getResource();
            answer.add(patient.getId().getIdPart());
        }

        return answer;
    }


    public static void main(String[] args) {
        Connection conn = new Connection("http://localhost:8080/hapi-fhir-jpaserver-example/baseDstu2");
        //Connection conn = new Connection("https://fhirtest.uhn.ca/baseDstu2");
        SimpleRead sr = new SimpleRead(conn);
        // id value as argument  is invented; you want to change them
        System.out.println("Name: " + sr.getNameByPatientID("1"));
        System.out.println(sr.getIDByPatientName("Smith"));

    }


}