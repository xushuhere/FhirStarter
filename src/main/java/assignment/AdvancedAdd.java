package assignment;

import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.api.MethodOutcome;



/**
 *
 */
public class AdvancedAdd {

    //This Connection object is the same as the one you implemented in the first FHIR task
    private Connection connection = null;

    public AdvancedAdd(Connection connection) {
        this.connection = connection;
    }

    public String addPatient(String firstName, String lastName) {
        String id = null;
        //Place your code here
        //Return the ID of the new patient
        //Be sure it is just the logical ID Part
        Patient patient = new Patient();
        patient.addName()
                .addFamily(lastName)
                .addGiven(firstName);

        MethodOutcome outcome = this.connection.getClient().create()
                .resource(patient)
                .prettyPrint().encodedJson()
                .execute();

        id = outcome.getId().getIdPart();
        return id;

    }

    public String addObservation(String patientId, String loincCode, String loincDisplayName,
                                 double value, String valueUnit, String valueCode) {
        String id = null;
        //Place your code here
        //Return the ID of the new Observation
        //Be sure it is just the logical ID Part

        Observation observation = new Observation();
        observation
                .getCode()
                .addCoding()
                .setSystem("http://loinc.org")
                .setCode(loincCode)
                .setDisplay(loincDisplayName);
        observation.setValue(
                new QuantityDt()
                        .setValue(value)
                        .setUnit(valueUnit)
                        .setCode(valueCode));
        //IdDt patID = new IdDt("Patient",patientId );

        //observation.setId(IdDt.newRandomUuid());

        //System.out.println("the observation  id is:" + id);
        //this.connection.getClient().read(Patient.class,patientId);
        observation.setSubject(new ResourceReferenceDt(new IdDt("Patient", patientId)));
        //observation.setSubject(new ResourceReferenceDt(this.connection.getClient().read(Patient.class,patientId)));
        //System.out.println("the added patient id is:" + observation.getSubject().getReference().getIdPart());

        MethodOutcome metOtm= this.connection.getClient().create().resource(observation).execute();


        id = metOtm.getId().getIdPart();

        return id;

    }

    public static void main(String[] args) {
        //     Connection conn = new Connection("https://fhirtesting.hdap.gatech.edu/hapi-fhir-jpaserver-example/baseDstu2");
        Connection conn = new Connection("http://localhost:8080/hapi-fhir-jpaserver-example/baseDstu2");
        AdvancedAdd aa = new AdvancedAdd(conn);
        String patId = aa.addPatient("John","Smith");

        System.out.println(aa.addObservation(patId,"789-8",
                "Erythrocytes [#/volume] in Blood by Automated count", 4.12,
                "10 trillion/L", "10*12/L"));

    }

}