package assignment;

public class mHealth {

    String sURL = "https://inginious.hdap.gatech.edu/course/IHI_2017_Fall/mHealth/mHealthData.json";

    private Connection connection = null;

    public mHealth(Connection connection) {
        this.connection = connection;
    }


   // String json = this.connection.getClient().newJsonParser().encodeResourceToString(obs);


}

//