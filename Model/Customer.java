package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author matgreten
 */
public class Customer {
    
    // Initialize​ variables for the Customer
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty address1;
    private final StringProperty address2;
    private final StringProperty city;
    private final StringProperty zip;
    private final StringProperty country;
    private final IntegerProperty primaryKey;
    private final IntegerProperty addressKey;
    private final IntegerProperty cityKey;
    private final IntegerProperty countryKey;
    private final StringProperty customerActive;

    public static final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    //Return method for populating Customer table in Customer selection screen. 
    public static ObservableList<Customer> getCustomerData() {
        return customerList;
    }
    
    public StringProperty customerNameProperty(){
        return name;
    }
    
    public final void setName(String name){
        customerNameProperty().set(name);
        
    }
    
    public final String getName(){
        
        return customerNameProperty().get();
    }
    
    public StringProperty customerPhoneProperty(){
        return phone;
    }
    
    public final void setPhone(String phone){
        customerPhoneProperty().set(phone);
        
    }
    
    public final String getPhone(){
        
        return customerPhoneProperty().get();
    }
    
    public StringProperty customerAddress1Property(){
        return address1;
    }
    
    public final void setAddress1(String address1){
        customerAddress1Property().set(address1);
        
    }
    
    public final String getAddress1(){
        
        return customerAddress1Property().get();
    }
    
    public StringProperty customerAddress2Property(){
        return address2;
    }
    
    public final void setAddress2(String address2){
        customerAddress2Property().set(address2);
        
    }
    
    public final String getAddress2(){
        
        return customerAddress2Property().get();
    }
    
    public StringProperty customerCityProperty(){
        return city;
    }
    
    public final void setCity(String city){
        customerCityProperty().set(city);
        
    }
    
    public final String getCity(){
        
        return customerCityProperty().get();
    }
    
    public StringProperty customerZipProperty(){
        return zip;
    }
    
    public final void setZip(String zip){
        customerZipProperty().set(zip);
        
    }
    
    public final String getZip(){
        
        return customerZipProperty().get();
    }
    
    public StringProperty customerCountryProperty(){
        return country;
    }
    
    public final void setCountry(String country){
        customerCountryProperty().set(country);
        
    }
    
    public final String getCountry(){
        
        return customerCountryProperty().get();
    }
    
    public IntegerProperty customerPKProperty(){
        return primaryKey;
    }
    
    public final void setPK(int primaryKey){
        customerPKProperty().set(primaryKey);
        
    }
    
    public final int getPK(){
        
        return customerPKProperty().get();
    }
    
    public IntegerProperty customerAddKeyProperty(){
        return addressKey;
    }
    
    public final void setAddressKey(int addressKey){
        customerAddKeyProperty().set(addressKey);
        
    }
    
    public final int getAddressKey(){
        
        return customerAddKeyProperty().get();
    }
    
    public IntegerProperty customerCityKeyProperty(){
        return cityKey;
    }
    
    public final void setCityKey(int cityKey){
        customerCityKeyProperty().set(cityKey);
        
    }
    
    public final int getCityyKey(){
        
        return customerCityKeyProperty().get();
    }
    
    public IntegerProperty customerCountryKeyProperty(){
        return countryKey;
    }
    
    public final void setCountryKey(int countryKey){
        customerCountryKeyProperty().set(countryKey);
        
    }
    
    public final int getCountryKey(){
        
        return customerCountryKeyProperty().get();
    }
    
    public StringProperty customerActiveProperty(){
        return customerActive;
    }
    
    public final String getActive(){
        
        return customerActiveProperty().get();
    }
    
    public final void setActive(String inputText){
        customerActiveProperty().setValue(inputText);
    }
    
    public Customer(int primaryKey, String name, String phone, String address1, String address2, String city, String zip, String country, int addressKey, int cityKey, int countryKey, String inputActive){
        this.primaryKey = new SimpleIntegerProperty(primaryKey);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address1 = new SimpleStringProperty(address1);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.zip = new SimpleStringProperty(zip);
        this.country = new SimpleStringProperty(country);
        this.addressKey = new SimpleIntegerProperty(addressKey);
        this.cityKey = new SimpleIntegerProperty(cityKey);
        this.countryKey = new SimpleIntegerProperty(countryKey);
        this.customerActive = new SimpleStringProperty(inputActive);
    }
    
}
