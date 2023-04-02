package com.proyecto.centraldetaxi.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.centraldetaxi.models.Driver;


public class DriverProvider {

    DatabaseReference mDatabase;

    public DriverProvider(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");

    }
    public Task<Void> create(Driver driver){

        return mDatabase.child(driver.getId()).setValue(driver);
    }


}
