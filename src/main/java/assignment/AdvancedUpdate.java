package assignment;

//import assignment.Connection;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.HashSet;
//import ca.uhn.fhir.context.FhirContext;
//import ca.uhn.fhir.rest.client.IGenericClient;
//import ca.uhn.fhir.model.dstu2.resource.Bundle;
//import ca.uhn.fhir.model.dstu2.resource.Patient;
//import ca.uhn.fhir.model.dstu2.resource.Observation;
//import ca.uhn.fhir.model.dstu2.resource.Bundle.Entry;
//import ca.uhn.fhir.rest.gclient.TokenClientParam;
//import ca.uhn.fhir.rest.api.MethodOutcome;
//import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.composite.ContactPointDt;
import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointSystemEnum;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointUseEnum;
import ca.uhn.fhir.rest.api.MethodOutcome;

/**
 *
 */
public class AdvancedUpdate {

    //This Connection object is the same as the one you implemented in the first FHIR task
    private Connection connection = null;

    public AdvancedUpdate(Connection connection) {
        this.connection = connection;
    }

    public String updatePatientHomePhone(String patientId, String homePhoneNumber) {
        String id = null;
        //Place your code here
        //Return the Outcome ID
        //Be sure it is just the logical ID Part
        Patient patient = this.connection.getClient().read(Patient.class,patientId);
        patient.addTelecom(new ContactPointDt()
                .setValue(homePhoneNumber)
                .setSystem(ContactPointSystemEnum.PHONE)
                .setUse(ContactPointUseEnum.HOME));
        this.connection.getClient().update()
                .resource(patient)
                .prettyPrint().encodedJson()
                .execute();
        id = patient.getId().getIdPart();
        return id;
    }

    public String updateObservationValue(String observationId, double value) {
        String id = null;
        //Place your code here
        //Return the Outcome ID
        //Be sure it is just the logical ID Part

        Observation observation = this.connection.getClient().read(Observation.class, observationId);
        observation.setValue(new QuantityDt().setValue(value));
        MethodOutcome outcome= this.connection.getClient().update().resource(observation).execute();

        id = outcome.getId().getIdPart();

        return id;
    }

    public static void main(String[] args) {
        //  Connection conn = new Connection("https://fhirtesting.hdap.gatech.edu/hapi-fhir-jpaserver-example/baseDstu2");
        Connection conn = new Connection("http://localhost:8080/hapi-fhir-jpaserver-example/baseDstu2");
        AdvancedUpdate au = new AdvancedUpdate(conn);
        // id values as arguments  are invented; you want to change them
        //System.out.println("id for patient: " + au.updatePatientHomePhone("657","2051354659"));

        System.out.println("id for obs: " + au.updateObservationValue("658", 4.36));



    }


}