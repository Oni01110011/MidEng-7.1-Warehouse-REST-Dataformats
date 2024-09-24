package election.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ElectionData {
    private int regionID;
    private String regionName;
    private String regionAddress;
    private String regionPostalCode;
    private String federalState;
    private String timestamp;
    private ArrayList<Party> countingData;

    public ElectionData() {
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        this.countingData = new ArrayList<>();
    }

    public ElectionData(int regionID, String regionName, String regionAddress, String regionPostalCode, String federalState, ArrayList<Party> countingData) {
        this.regionID = regionID;
        this.regionName = regionName;
        this.regionAddress = regionAddress;
        this.regionPostalCode = regionPostalCode;
        this.federalState = federalState;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        this.countingData = countingData;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionAddress() {
        return regionAddress;
    }

    public void setRegionAddress(String regionAddress) {
        this.regionAddress = regionAddress;
    }

    public String getRegionPostalCode() {
        return regionPostalCode;
    }

    public void setRegionPostalCode(String regionPostalCode) {
        this.regionPostalCode = regionPostalCode;
    }

    public String getFederalState() {
        return federalState;
    }

    public void setFederalState(String federalState) {
        this.federalState = federalState;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Party> getCountingData() {
        return countingData;
    }

    public void setCountingData(ArrayList<Party> countingData) {
        this.countingData = countingData;
    }
}
