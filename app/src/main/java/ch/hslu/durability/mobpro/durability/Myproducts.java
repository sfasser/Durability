package ch.hslu.durability.mobpro.durability;

import java.util.Date;

/**
 * Created by Sandro Fasser on 22.05.2017.
 */

public class Myproducts {

    //private variables
    int id;
    String name;
    Date date;

    // Empty constructor
    public Myproducts(){

    }
    // constructor
    public Myproducts(int id, String name, Date date){
        this.id = id;
        this.name = name;
        this.date = date;
    }

    // getting ID
    public int getID(){
        return this.id;
    }


    // getting name
    public String getName(){
        return this.name;
    }


    //
    public Date getDate(){
        return this.date;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    // setting id
    public void setName(String name){
        this.name = name;
    }

    // setting id
    public void setDate(Date date){
        this.date = date;
    }

}
