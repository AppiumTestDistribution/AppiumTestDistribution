package com.report.factory;

import com.appium.utils.AppiumDevice;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.util.Collections;

public class MongoDB {
    private static MongoDB instance;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection collection;

    private MongoDB() {
        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Collections.singletonList(new ServerAddress("localhost", 27017))))
                        .build());
    }

    public static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
        }
        return instance;
    }

    public MongoDB createDB(String dbName, String collectionName) {
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(collectionName);
        return this;
    }

    public void insertDataToDB(Object appiumDevice) {
        BasicDBObject document = (BasicDBObject) JSON.parse(new Gson().toJson(appiumDevice));
        collection.insertOne(new Document(document));
    }
}
