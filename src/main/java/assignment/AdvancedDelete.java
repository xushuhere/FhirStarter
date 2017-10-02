package assignment;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;

/**
 *
 */
public class AdvancedDelete {

    //This Connection object is the same as the one you implemented in the first FHIR task
    private Connection connection = null;

    public AdvancedDelete(Connection connection) {
        this.connection = connection;
    }

    public void deletePatient(String patientId) {
        //Place your code here
        Patient patient = this.connection.getClient().read(Patient.class,patientId);
        this.connection.getClient().delete()
                .resource(patient)
                .prettyPrint().encodedJson()
                .execute();
    }

    public void deleteObservation(String observationId) {
        //Place your code here
        Observation observation = this.connection.getClient().read(Observation.class,observationId);
        this.connection.getClient().delete()
                .resource(observation)
                .prettyPrint().encodedJson()
                .execute();
    }


    public static void main(String[] args) {
        //  Connection conn = new Connection("https://fhirtesting.hdap.gatech.edu/hapi-fhir-jpaserver-example/baseDstu2");
        Connection conn = new Connection("http://localhost:8080/hapi-fhir-jpaserver-example/baseDstu2");
        AdvancedDelete ad = new AdvancedDelete(conn);

     // id values as arguments  are invented; you want to change them
        ad.deleteObservation("658");
        ad.deletePatient("657");

    }


}