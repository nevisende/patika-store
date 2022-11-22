package store;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Sorts.ascending;


public class Interactive extends Console{
    MongoDBConnection mongo = new MongoDBConnection();
    MongoCollection<Document> cellPhonesCollection = mongo.getCollection("electronics","cell_phones");
    MongoCollection<Document> notebooksCollection = mongo.getCollection("electronics","notebooks");


    Interactive() {
        syncCellPhoneInstancesWithMongoDB();
    }

    public void startStages(){
        welcomeStage();
        int mainStageAnswer = mainStage();
        preventSkipScannerIssue();
        while(mainStageAnswer != mainStageAnswers.QUIT.getStageNumber()) {
            if(mainStageAnswer == mainStageAnswers.LIST_PRODUCTS.getStageNumber()){
                listProductStage();
            } else if (mainStageAnswer == mainStageAnswers.ADD_PRODUCT.getStageNumber()){
                addProductStage();
            } else if (mainStageAnswer == mainStageAnswers.DELETE_PRODUCT.getStageNumber()){
                deleteCategoryStage();
            } else if(mainStageAnswer == mainStageAnswers.FILTER_PRODUCT.getStageNumber()){
                filterStage();
            } else {
                System.out.println("Please enter valid options (1, 2, 3, 4 or 5)!");
            }
            mainStageAnswer = mainStage();
        }
    }
    public void welcomeStage(){
        System.out.println("Welcome to Patika Store");
        System.out.println("-----------------------");
    }

    public int mainStage(){
        System.out.println("""
                1- List products
                2- Add a product
                3- Delete a product
                4- Filter products
                5- Quit
                """);
        System.out.print("Enter the number of the option you want: ");
        return input.nextInt();
    }

    public int categoryStage(){
        System.out.println("""
                There are two categories
                1-> Notebook
                2-> Cell Phone""");
        System.out.print("Enter the number of the category you want: ");
        return input.nextInt();
    }

    public void listProductStage() {
        int categoryStageAnswer = categoryStage();
        if (categoryStageAnswer == categoryStageAnswers.NOTEBOOK.getCategoryNumber()){
            Notebook.printTableHeader();
            Notebook.printAllInstances();
            Notebook.printLine();
        } else if(categoryStageAnswer == categoryStageAnswers.CELL_PHONE.getCategoryNumber()) {
            CellPhone.printTableHeader();
            CellPhone.printAllInstances();
            CellPhone.printLine();
        }
    }

    public void addProductStage() {
        System.out.println("To add->");
        int categoryStageAnswer = categoryStage();
        if (categoryStageAnswer == categoryStageAnswers.NOTEBOOK.getCategoryNumber()){
            Electronic notebook = Notebook.createElectronicFromTerminal();
            mongo.insertOneDocumentToCollection(notebook.generateMongoDBDocument(),notebooksCollection);
            Notebook.printTableHeader();
            notebook.printDetails();
        } else if(categoryStageAnswer == categoryStageAnswers.CELL_PHONE.getCategoryNumber()) {
            Electronic cellPhone = CellPhone.createElectronicFromTerminal();
            mongo.insertOneDocumentToCollection(cellPhone.generateMongoDBDocument(), cellPhonesCollection);
            CellPhone.printTableHeader();
            cellPhone.printDetails();
        }
        System.out.println("\n-----> Created successfully :)");

    }
    public void deleteCategoryStage() {
        MongoCollection<Document> collection = null;
        System.out.println("To delete ->");
        int categoryStorageAnswer = categoryStage();
        if(categoryStorageAnswer == categoryStageAnswers.NOTEBOOK.getCategoryNumber()){
            collection = notebooksCollection;
        } else if(categoryStorageAnswer == categoryStageAnswers.CELL_PHONE.getCategoryNumber()){
            collection = cellPhonesCollection;
        } else {
            System.out.println("Please enter valid option now it starts again!");
            startStages();
        }
        preventSkipScannerIssue();
        System.out.print("Delete the id of a product you want to delete: ");
        String id = input.nextLine();
        assert collection != null;
        mongo.deleteOneDocument(id,collection);
    }

    public void filterStage() {
        ArrayList<Electronic> filteredArray = null;
        System.out.println("To filter ------> ");
        int categoryStageAnswer = categoryStage();

        System.out.println("1-> Filter by id \n" +
                "2-> Filter by brand name");
        System.out.print("Enter a filter option (1 or 2): ");
        int filterStageAnswer = input.nextInt();

        preventSkipScannerIssue();

        if(filterStageAnswer == 1) {
            System.out.print("Enter the id: ");
            String id = input.nextLine();

            if(categoryStageAnswer == categoryStageAnswers.NOTEBOOK.getCategoryNumber()){
                filteredArray = Notebook.filterBy("id", id);
            } else if (categoryStageAnswer == categoryStageAnswers.CELL_PHONE.getCategoryNumber()){
                filteredArray = CellPhone.filterBy("id", id);
            }
        } else if(filterStageAnswer == 2 ) {
            if(categoryStageAnswer == categoryStageAnswers.NOTEBOOK.getCategoryNumber()){
                filteredArray = Notebook.filterBy("brandName", askAndGetBrand());
            } else if (categoryStageAnswer == categoryStageAnswers.CELL_PHONE.getCategoryNumber()){
                filteredArray = CellPhone.filterBy("brandName", askAndGetBrand());
            }
        } else {
            System.out.println("You entered invalid value! ");
            filterStage();
            return;
        }

        CellPhone.printTableHeader();
        assert filteredArray != null;
        CellPhone.printElectronicArrayList(filteredArray);
        CellPhone.printLine();
    }

    public void syncCellPhoneInstancesWithMongoDB() {
        ArrayList<Document> cellPhonesOnDB = cellPhonesCollection.find().sort(ascending("brand_name")).into(new ArrayList<>());
        for(Document cellPhoneDocument : cellPhonesOnDB) {
            CellPhone.createFromMongoDBDocument(cellPhoneDocument);
        }
    }
}

enum mainStageAnswers{
    LIST_PRODUCTS(1),
    ADD_PRODUCT(2),
    DELETE_PRODUCT(3),
    FILTER_PRODUCT(4),
    QUIT(5);
    private final int categoryNumber;

    public int getStageNumber() {
        return this.categoryNumber;
    }

    mainStageAnswers(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }
}

enum categoryStageAnswers{
    NOTEBOOK(1), CELL_PHONE(2);
    private final int categoryNumber;

    public int getCategoryNumber() {
        return this.categoryNumber;
    }

    categoryStageAnswers(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }
}
