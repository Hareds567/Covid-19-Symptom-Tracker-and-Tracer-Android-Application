package com.example.logindemo2;

import android.annotation.SuppressLint;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MongoReader {
    //Adds connection string
    ConnectionString connString = new ConnectionString("mongodb+srv://Admin:admin@cluster0.zoibg.mongodb.net/COVID-App?retryWrites=true&w=majority");

    //Establish settings then initates Mongodb client
    MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connString).retryWrites(true).build();
    MongoClient mongoClient = MongoClients.create(settings);

    //Finds the COVID-19 Database
    MongoDatabase database = mongoClient.getDatabase("COVID-App");
    //Finds the CSV-Dump collection
    MongoCollection collection = database.getCollection("CSV-Dump");

    //Variables
    private String Gmail; String FirstName; String LastName;String Residence;
    ArrayList<String>CID; ArrayList<String>StudentsToNotify;
    FindIterable InstancesOfStudent; FindIterable Students;

    public MongoReader(String Gmail){

        this.Gmail= Gmail;
        FindIterable InstancesOfStudent = collection.find(eq("Gmail",Gmail));
        Document sample = ((Document)InstancesOfStudent.first());
        FirstName =sample.get("FirstName").toString();
        LastName =sample.get("LastName").toString();
        Residence =sample.get("Res").toString();
        CID = new ArrayList<>();
        StudentsToNotify = new ArrayList<>();

        if(!HasStudentAccount()){
            AddToStudentCollection();
        }
        UpdateClassList();
        AddToResidenceHall();
    }
    public void TestResults(boolean results){
        MongoCollection StudentsCol = database.getCollection("Students");
        if(!((boolean)((Document)StudentsCol.find(eq("Gmail",Gmail)).first()).get("Results"))){
            if(results){
                Notify();
                database.getCollection("ResidenceHalls").updateOne(eq("HallName",Residence),Updates.inc("NumberOfCases",1));
            }
        }else{
            if(!results){
                database.getCollection("ResidenceHalls").updateOne(eq("HallName",Residence),Updates.inc("NumberOfCases",-1));
            }
        }
        Bson update = Updates.set("Results",results);
        Bson update2 = Updates.set("ResultsDate", LocalDateTime.now().toString());
        StudentsCol.updateOne(eq("Gmail",Gmail),update);
        StudentsCol.updateOne(eq("Gmail",Gmail),update2);
    }


    public void UpdateClassList(){
        InstancesOfStudent = collection.find(eq("Gmail",Gmail));
        Consumer<Document> getClasses = new Consumer<Document>() {
            @Override
            public Consumer andThen(Consumer after) {
                return null;
            }
            public void accept(final Document doc) {
                CID.add(doc.get("CID").toString());
            }
        };
        InstancesOfStudent.forEach(getClasses);
        Bson classupdate = Updates.set("ClassList",CID);
        database.getCollection("Students").updateOne(eq("Gmail",Gmail),classupdate);
    }

    public ArrayList<String> GetClasses(){
        return (ArrayList<String>)database.getCollection("Students")
                .find(eq("Gmail",Gmail))
                .first()
                .get("ClassList");
    }
    public void AddNewClasses(ArrayList<String>NewClasses){
        NewClasses.addAll(GetClasses());
        database.getCollection("Students").updateOne(eq("Gmail", Gmail),Updates.set("ClassList",NewClasses));
    }


    public void Notify(){
        Consumer<Document> getClassMates = doc -> StudentsToNotify.add(doc.get("Gmail").toString());
        Consumer<Document> getSocial = doc -> {
            ArrayList<String>Social = ((ArrayList<String>)doc.get("SocialCircle"));
            StudentsToNotify.addAll(Social);
        };
        for(String CourseID:CID){
            Students = collection.find(eq("CID",CourseID));
            Students.forEach(getClassMates);
        }
        FindIterable SC = database.getCollection("Students").find(eq("Gmail",Gmail));
        SC.forEach(getSocial);
        System.out.println( "Students to warn: " + StudentsToNotify.toString());
    }


    public boolean HasStudentAccount(){
        MongoCollection StudentCollection = database.getCollection("Students");
        FindIterable temp = StudentCollection.find(eq("Gmail", Gmail));
        if(temp.first()==null){
            return false;
        }else return true;

    }


    public void AddToStudentCollection(){
        if(!HasStudentAccount()){
            UpdateClassList();
            Document Student = new Document("Gmail",Gmail)
                    .append("Res",Residence)
                    .append("FirstName",FirstName)
                    .append("LastName",LastName)
                    .append("ClassList",CID)
                    ;
            database.getCollection("Students").insertOne(Student);
        }
    }


    public ArrayList<String> GetSocialCircle() {
        ArrayList<String>out = new ArrayList<>();
        out.addAll((ArrayList)database.getCollection("Students").find(eq("Gmail",Gmail)).first().get("SocialCircle"));
        return out;
    }
    public void AddSocialCircle(ArrayList<String>ToAdd){
        Bson update = Updates.set("SocialCircle",ToAdd);
        database.getCollection("Students").updateOne(eq("Gmail",Gmail),update);

    }
    public void AddToResidenceHall(){
        FindIterable hall = database.getCollection("ResidenceHalls").find(eq("HallName",Residence));
        if(hall.first()==null){
            ArrayList<String>Residents = new ArrayList<>();
            Residents.add(Gmail);
            Document NewHall = new Document("HallName",Residence)
                    .append("Residents",Residents)
                    .append("NumberOfCases",0);
            database.getCollection("ResidenceHalls").insertOne(NewHall);
        }else {
            ArrayList<String>ResidentsInHall =((ArrayList<String>)database.getCollection("ResidenceHalls").find(eq("HallName",Residence)).first().get("Residents"));
            if(!ResidentsInHall.contains(Gmail)){
                ResidentsInHall.add(Gmail);
                Bson update = Updates.set("Residents",ResidentsInHall);
                database.getCollection("ResidenceHalls").updateOne(eq("Hallname",Residence),update);

            }
        }
    }
}
