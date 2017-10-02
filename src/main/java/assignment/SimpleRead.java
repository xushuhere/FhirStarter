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
        org.apache.log4j.BasicConfigurator.configure();

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
        System.out.println("Name:--- " + sr.getNameByPatientID("075786e8-383c-4eee-abb6-264b3b967482"));
        System.out.println(sr.getIDByPatientName("Smith"));

/*

        Connection conn2 = new Connection("https://fhirtesting.hdap.gatech.edu/hapi-fhir-jpaserver-example/#");
        SimpleRead sr2 = new SimpleRead(conn2);
        // id value as argument  is invented; you want to change them
        System.out.println("Name:--- " + sr2.getNameByPatientID("1e19bb7a-d990-4924-9fae-be84f19c53c1"));
        System.out.println(sr2.getIDByPatientName("Armstrong"));
*/


    }


}