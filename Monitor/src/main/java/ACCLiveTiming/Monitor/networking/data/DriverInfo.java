/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acclivetiming.Monitor.networking.data;

import acclivetiming.Monitor.networking.enums.DriverCategory;
import acclivetiming.Monitor.networking.enums.Nationality;


/**
 *
 * @author Leonard
 */
public class DriverInfo {

    private String firstName = "";
    private String lastName = "";
    private String shortName = "";
    private DriverCategory category = DriverCategory.ERROR;
    private Nationality driverNationality;
    
    public DriverInfo(){
    }

    public DriverInfo(String firstName, String lastName, String shortName, DriverCategory category, Nationality driverNationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.shortName = shortName;
        this.category = category;
        this.driverNationality = driverNationality;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getShortName() {
        return shortName;
    }

    public DriverCategory getCategory() {
        return category;
    }

    public Nationality getDriverNationality() {
        return driverNationality;
    }

}
