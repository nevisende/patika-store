package store;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDBConnection {
    public MongoClient mongoClient;

    MongoDBConnection(){
        String connectionString = "mongodb+srv://patikastore:DQjJpjJMvI2KXwNk@cluster0.ln2anoi.mongodb.net/?retryWrites=true&w=majority";
        this.mongoClient = MongoClients.create(connectionString);
    }

    public MongoCollection<Document> getCollection(String dbName, String collectionName){
        return this.mongoClient.getDatabase(dbName).getCollection(collectionName);
    }

    public void insertOneDocumentToCollection(Document document, MongoCollection<Document> collection) {
        collection.insertOne(document);
    }

    public void deleteOneDocument(String id, MongoCollection<Document> collection){
        collection.deleteOne(new Document("_id",new ObjectId(id)));
        System.out.println("Deleted successfully!");
    }
}