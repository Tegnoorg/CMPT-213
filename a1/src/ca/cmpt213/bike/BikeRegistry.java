/**
 * This class is a constructor for the object BikeRegistry. it has simple getter and setter functions.
 * with the addition of toString which turns the object to a string for printing purposes..
 * @author Tegnoor Gill
 * @version 1.0
 */

package ca.cmpt213.bike;

public class BikeRegistry {
    private String owner;
    private String type;
    private String serialNumber;
    private String brakeType;
    private double wheelSize;
    private int ID;

    public BikeRegistry(int ID, String owner, String type, String serialNumber, String brakeType, double wheelSize) {
        this.ID = ID;
        this.owner = owner;
        this.type = type;
        this.serialNumber = serialNumber;
        this.brakeType = brakeType;
        this.wheelSize = wheelSize;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrakeType() {
        return brakeType;
    }

    public void setBrakeType(String brakeType) {
        this.brakeType = brakeType;
    }

    public double getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(double wheelSize) {
        this.wheelSize = wheelSize;
    }

    /**
     * prints the class.name[object information]
     * */
    @Override
    public String toString(){
        return getClass().getName()
                + "[ID:" + this.ID
                + ", Owner:" + this.owner
                + ", type:"+ this.type
                + ", Serial:" + this.serialNumber
                + ", Brake:" + this.brakeType
                + ", WheelSize:"+ this.wheelSize+"]";
    }
}

