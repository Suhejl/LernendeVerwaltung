package ch.noseryoung.lernendeverwaltung.repository;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class that represents the entity in the database.
 */
@Entity(tableName = "apprentices")
public class Apprentice {

    // Constructor of Apprentice
    public Apprentice(String firstName, String lastName, String picture){
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
    }

    // ID for identification of every dataset
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    // Saves String of the path to the picture on the phone
    @ColumnInfo(name = "picture")
    private String picture;

    // Getter and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}