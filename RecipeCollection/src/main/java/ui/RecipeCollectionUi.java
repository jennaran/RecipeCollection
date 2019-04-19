
package ui;

import dao.DerbyRecipeDAO;
import dao.DerbyUserDAO;
import domain.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RecipeCollectionUi extends Application {
    private Scene beginningScene, newUserScene, loggedInScene, newRecipeScene, recipeScene;
    private final int sceneK = 390;
    private final int sceneL = 720;
    private Stage stage;
    
    private Service service;
    
    private List<String> addedIngredients;
        
    
    @Override
    public void init() throws Exception {
        String userFile = "user.txt";
        String recipeFile = "recipe.txt";
        
        DerbyUserDAO userDao = new DerbyUserDAO(userFile);
        DerbyRecipeDAO recipeDao = new DerbyRecipeDAO(userDao, recipeFile);
        this.service = new Service(userDao, recipeDao);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        Scene scene = BeginningScene();
        
        stage.setTitle("RecipeCollection");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Scene BeginningScene() {
        BorderPane border = new BorderPane();
        
        border.setLeft(loginSigninBox("Sign in", "Sign in", 0));
        border.setRight(middleButton("Create An Account", "Join Today!", 30));
        
        this.beginningScene= new Scene(border, sceneL, sceneK);
        return beginningScene;
    }
    
    public Scene loggedInScene() throws Exception {
        BorderPane borderList = new BorderPane();
        ArrayList<String> empty = new ArrayList<>();
        borderList.setCenter(list(empty, 0));
        
        HBox searching = new HBox();
        searching.setPrefWidth(sceneL / 2);
        searching.setSpacing(10);
        
        Button search = new Button("Search");
        
        TextField searchBy = new TextField();
        searchBy.setPromptText("Search recipe by name");
        searchBy.setPrefWidth(270);
        searching.getChildren().add(searchBy);
        searching.getChildren().add(search);
        
        Text text = new Text("Your Recipes");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        VBox borderIngredients = new VBox();
        borderIngredients.setPrefWidth(sceneL / 2);
        borderIngredients.setPadding(new Insets(10, 10, 10, 10));
        borderIngredients.setSpacing(10);
        
        borderIngredients.getChildren().add(text);
        borderIngredients.getChildren().add(searching);
        borderIngredients.getChildren().add(borderList);
        
        BorderPane border = new BorderPane();
        border.setRight(middleButton("Add A New Recipe", "Got a new Recipe?",0));
        border.setLeft(borderIngredients);
        border.setTop(userMenu());
        
        search.setOnAction(a -> {
            String name = searchBy.getText();
            try {
                if (name.isEmpty()) {
                    borderList.setCenter(list(empty, 0));
                } else {
                    ArrayList<String> names = this.service.userRecipeNames().stream().filter(r -> r.contains(name)).collect(Collectors.toCollection(ArrayList::new));
                    borderList.setCenter(list(names, 1));
                }
            } catch (Exception ex) {
                Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.loggedInScene = new Scene(border, sceneL, sceneK);
        return loggedInScene;
    }
    
    public Scene newUserScene() {
        Button back = new Button("Back");
        
        BorderPane border = new BorderPane();
        border.setCenter(loginSigninBox("Sign up", "Sign up", 1));
        border.setRight(back);
        border.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        border.setPadding(new Insets(10, 10, 10, 10));
        //border.setPadding(new Insets(10, 10, 10, 10));
        
        this.newUserScene = new Scene(border, sceneL, sceneK);
        return newUserScene;
    }
    
    public Scene recipeScene(String recipeName) throws Exception {
        
        Text name = new Text(recipeName);
        name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
        BorderPane borderDown = new BorderPane();
        borderDown.setRight(instructions(recipeName));
        borderDown.setLeft(ingedients(recipeName));
        
        BorderPane border = new BorderPane();
        border.setTop(name);
        border.setCenter(borderDown);
        border.setPadding(new Insets(10, 10, 10, 10));
        
        this.newRecipeScene = new Scene(border, sceneL, sceneK);
        return newRecipeScene;
    }
    
    public Scene newRecipeScene() throws Exception {
        this.addedIngredients = new ArrayList();
        
        TextField recipeNameField = new TextField();
        recipeNameField.setPrefHeight(40);
        recipeNameField.setPromptText("Name of the recipe");
        recipeNameField.setPrefWidth(261);
        
        Text text = new Text("Ingredients");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField addField = new TextField();
        addField.setPromptText("Add ingredient");
        addField.setPrefWidth(219);
        Button add = new Button("Add");
        Button delete = new Button("Delete");
        
        HBox addingStuff = new HBox();
        addingStuff.getChildren().addAll(addField, add, delete);
        addingStuff.setSpacing(10);
        addingStuff.setPadding(new Insets(10, 0, 3, 0));
        
        VBox vbox1 = new VBox();
        vbox1.setPrefWidth(sceneL / 2);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.setSpacing(3);
        
        BorderPane borderList = new BorderPane();
        borderList.setCenter(list(addedIngredients, 2));
        
        delete.setOnAction(e -> {
            String deleteIngredient = addField.getText();
            deleteIngredient = deleteIngredient.trim();
            addField.clear();
            if (!addedIngredients.isEmpty()) {
                this.addedIngredients.remove(deleteIngredient);
            }
            try {
                borderList.setCenter(list(addedIngredients, 2));
            } catch (Exception ex) {
                Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        add.setOnMouseClicked(e -> {
            String newIngredient = addField.getText();
            addField.clear();
            newIngredient = newIngredient.trim();
            if (!newIngredient.isEmpty()) {
                addedIngredients.add(newIngredient);
            }
            try {
                borderList.setCenter(list(addedIngredients, 2));
            } catch (Exception ex) {
                Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        vbox1.getChildren().add(text);
        vbox1.getChildren().add(addingStuff);
        vbox1.getChildren().add(borderList);
        
        Text textInstructions = new Text("Instructions");
        textInstructions.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        TextArea addInstructions = new TextArea();
        addInstructions.setPromptText("Add instructions here");
        addInstructions.setPrefSize(sceneL / 2 -20, sceneK - 90);
        
        VBox vbox2 = new VBox();
        vbox2.setPrefWidth(sceneL / 2);
        //vbox2.setPadding(new Insets(10, 10, 10, 0));
        vbox2.getChildren().add(textInstructions);
        vbox2.getChildren().add(addInstructions);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.setSpacing(13);
        
        BorderPane borderInner = new BorderPane();
        borderInner.setRight(vbox2);
        borderInner.setLeft(vbox1);

        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");
        
        backButton.setOnMouseClicked(e -> {
            stage.setScene(this.loggedInScene);
        });
        
        saveButton.setOnMouseClicked(e -> {
            String instuctions = addInstructions.getText();
            String recipeName = recipeNameField.getText();
            if (!instuctions.isEmpty() && !recipeName.isEmpty() && !addedIngredients.isEmpty()) {
            if (this.service.createNewRecipe(recipeName, addedIngredients, instuctions)) {
                //reseptin luominen onnistuu
                addInstructions.clear();
                recipeNameField.clear();
                this.addedIngredients.clear();
                try {
                    list(this.addedIngredients, 2);
                    this.loggedInScene = loggedInScene();
                } catch (Exception ex) {
                    Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            stage.setScene(this.loggedInScene);
            }
        });
        
        HBox savingStuff = new HBox();
        savingStuff.getChildren().addAll(backButton, saveButton);
        savingStuff.setPadding(new Insets(0, 0, 0, 300));
        savingStuff.setSpacing(10);
        
        BorderPane toRight = new BorderPane();
        toRight.setRight(savingStuff);
        
        Text nameText = new Text("Name:");
        nameText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(10, 10, 10, 10));
        hboxTop.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        hboxTop.getChildren().add((nameText));
        hboxTop.getChildren().add(recipeNameField);
        hboxTop.getChildren().add(toRight);
        
        
        hboxTop.setSpacing(20);
        
        BorderPane borderOut = new BorderPane();
        borderOut.setTop(hboxTop);
        borderOut.setCenter(borderInner);
        
        this.newRecipeScene = new Scene(borderOut, sceneL, sceneK);
        return newRecipeScene;
    }
    
    public BorderPane middleButton(String buttonText, String headText, int nro) {
        //nro 1 stands for create account and 2 for new recipe
        
        Text text = new Text(headText);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Button button = new Button(buttonText);
        button.setMinHeight(60);
        
        BorderPane border = new BorderPane();
        border.setPrefWidth(sceneL / 2);
        border.setCenter(button);
        border.setTop(text);
        border.setPadding(new Insets(135, 100, 135, 90 + nro));
        
        if (nro == 30) {
            button.setOnMouseClicked(e -> {
            stage.setScene(newUserScene());});
        } else if (nro == 0) {
            button.setOnMouseClicked(e -> {
                try {
                    stage.setScene(newRecipeScene());
                } catch (Exception ex) {
                    Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        return border;
    }
    
    public VBox instructions(String name) {
        Text text = new Text("Instructions");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Text instructions = new Text();
        String instructionText = service.getRecipeInstructionsByRecipeName(name);
        instructions.setText(instructionText);
        VBox vbox = new VBox();
        vbox.getChildren().add(text);
        vbox.getChildren().add(instructions);
        vbox.setPrefSize(sceneL / 2, sceneK / 2);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        return vbox;
    }
    
    public VBox ingedients(String name) throws Exception {
        Text text = new Text("Instructions");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        List<String> ingedients = this.service.getRecipeIngredienstByRecipeName(name);
        StackPane list = list(ingedients, 3);
        VBox vbox = new VBox();
        vbox.getChildren().add(text);
        vbox.getChildren().add(list);
        vbox.setPrefSize(sceneL / 2, sceneK / 2);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        return vbox;
    }
    
    public GridPane loginSigninBox(String text, String button, int i) {
        //0 for signing in and 1 for signing up
        GridPane grid = gridPane();
        //maybe set prefsize to textFields????????????
        Text upText = new Text(text);
        upText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(upText, 0, 1);
        
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 2);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);
        Label password = new Label("Password:");
        grid.add(password, 0, 3);
        PasswordField pwField = new PasswordField();
        grid.add(pwField, 1, 3);
        
        Button downButton = new Button(button);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(downButton);
        grid.add(hbBtn, 1, 4);

        downButton.setOnMouseClicked(e -> {
            if (i == 0) {
                String un = userTextField.getText();
                System.out.println(un);
                String pw = pwField.getText();
                System.out.println(pw);
                try {
                    if (service.logIn(un, pw)) {
                        System.out.println("true");
                        this.stage.setScene(loggedInScene());
                        System.out.println("scene set");
                        userTextField.clear();
                        pwField.clear();
                    } else {
                        Text failedLogin = new Text("Incorrect username or password");
                        grid.add(failedLogin, 1, 5);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else if (i == 1){
                String un = userTextField.getText();
                String pw = pwField.getText(); 
                
                try {
                    if (service.createNewUser(un, pw)) {
                        stage.setScene(this.beginningScene);
                    } else {
                        Text failedLogin = new Text("This username is already in use");
                        grid.add(failedLogin, 1, 5);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);;
                }
            }
        });
        
        return grid;
    }
    
    public GridPane gridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(30);
        grid.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setPrefWidth(sceneL / 2);
        
        return grid;
    }
    
    public MenuBar userMenu() {
        String username = service.getLoggenInUser().getUsername();
        Menu userMenu = new Menu(username);
        MenuItem sighOut = new MenuItem("Sign out");
        MenuItem delete = new MenuItem("Delete account");
        
        userMenu.getItems().add(sighOut);
        userMenu.getItems().add(delete);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(userMenu);
        
        sighOut.setOnAction(e -> {
            service.logOut();
            stage.setScene(this.beginningScene);
        });
        
        delete.setOnAction(e -> {
            try {
                if (service.deleteAccount()) {
                stage.setScene(this.beginningScene);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        
        return menuBar;
    }
    
    public StackPane list(List<String> recipes, int i) throws Exception {
        //0 for recipe name list
        ObservableList<String> data = FXCollections.observableArrayList();
        data.clear();
        
        ListView<String> listView = new ListView<String>();
        listView.setPrefSize(200, 250);
        
        //jos haluaan kaikki reseptit
        if(recipes.isEmpty() && i == 0 ) {
            recipes = service.userRecipeNames();
        }
        data.addAll(recipes);
        
        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, 
                String new_val) -> {
                if (i == 0 || i == 1) {
                    try {
                        this.stage.setScene(recipeScene(new_val));
                    } catch (Exception ex) {
                        Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        root.setPrefWidth(sceneL / 2);


        return root;
    }
    
   
}
