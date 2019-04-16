
package ui;

import dao.DerbyRecipeDAO;
import dao.DerbyUserDAO;
import domain.Service;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
        Scene scene = buildBeginningScene();
        
        stage.setTitle("RecipeCollection");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Scene buildBeginningScene() {
        BorderPane border = new BorderPane();
        
        border.setRight(loginSigninBox("Sign in", "Sign in", 0));
        border.setLeft(middleButton("Create An Account", 1));
        
        this.beginningScene= new Scene(border, sceneL, sceneK);
        return beginningScene;
    }
    
    public Scene loggedInScene() throws Exception {
        BorderPane border = new BorderPane();
        border.setRight(middleButton("Add A New Recipe", 2));
        BorderPane borderIngredients = new BorderPane();
        borderIngredients.setPrefWidth(sceneL / 2);
        
        HBox searching = new HBox();
        searching.setPrefWidth(sceneL / 2);
        Button search = new Button("Search");
        TextField searchBy = new TextField();
        searchBy.setPrefWidth(300);
        searching.getChildren().add(searchBy);
        searching.getChildren().add(search);
        borderIngredients.setTop(searching);
        ArrayList<String> empty = new ArrayList<>();
        borderIngredients.setCenter(list(empty, 0));
        
        border.setLeft(borderIngredients);
        border.setTop(userMenu());
        
        search.setOnAction(a -> {
            String name = searchBy.getText();
            try {
                if (name.isEmpty()) {
                    borderIngredients.setCenter(list(empty, 0));
                } else {
                    ArrayList<String> names = this.service.userRecipeNames().stream().filter(r -> r.contains(name)).collect(Collectors.toCollection(ArrayList::new));
                    borderIngredients.setCenter(list(names, 1));
                }
            } catch (Exception ex) {
                Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.loggedInScene = new Scene(border, sceneL, sceneK);
        return loggedInScene;
    }
    
    public Scene newUserScene() {
        BorderPane border = new BorderPane();
        border.setCenter(loginSigninBox("Sign up", "Sign up", 1));
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
        BorderPane border = new BorderPane();
        
        this.addedIngredients = new ArrayList();
        
        Text text = new Text("Ingredients");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField recipeNameField = new TextField();
        recipeNameField.setPromptText("Name of the recipe");
        
        TextField addField = new TextField();
        Button add = new Button("Add");
        //lisää delete nappula tämän viereen, joka mahdollistaa listalta poistamisen ja uudelleen kirjoittamisen
        
        HBox addingStuff = new HBox();
        addingStuff.getChildren().addAll(addField, add);
        addingStuff.setPadding(new Insets(10, 0, 5, 0));
        addingStuff.setSpacing(10);
        
        GridPane grid = new GridPane();
        grid.setPrefWidth(sceneL / 2);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        add.setOnMouseClicked(e -> {
            String newIngredient = addField.getText();
            addField.clear();
            newIngredient = newIngredient.trim();
            if (!newIngredient.isEmpty()) {
                addedIngredients.add(newIngredient);
            }
            try {
                StackPane list = list(addedIngredients, 2);
                grid.add(list, 0, 3);
            } catch (Exception ex) {
                Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        StackPane list = list(addedIngredients, 3);
        
        grid.add(recipeNameField, 0, 0);
        grid.add(text, 0, 1);
        grid.add(addingStuff, 0, 2);
        grid.add(list, 0, 3);
        
        Text textInstructions = new Text("Instructions");
        textInstructions.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextArea addInstructions = new TextArea();
        addInstructions.setPrefSize(sceneL / 2 -20, sceneK - 90);
        
        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");
        
        
        GridPane grid2 = new GridPane();
        grid2.add(textInstructions, 0, 0);
        grid2.add(backButton, 2, 0);
        grid2.add(saveButton, 3, 0);
        grid2.setPadding(new Insets(0, 0, 40, 0));
        grid2.setHgap(10);
        BorderPane border2 = new BorderPane();
        
        border2.setTop(grid2);
        border2.setCenter(addInstructions);
        border2.setPrefWidth(sceneL / 2);
        border2.setPadding(new Insets(10, 10, 10, 10));
        border2.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        backButton.setOnMouseClicked(e -> {
            stage.setScene(this.loggedInScene);
        });
        
        saveButton.setOnMouseClicked(e -> {
            String instuctions = addInstructions.getText();
            String recipeName = recipeNameField.getText();
            
            if (this.service.createNewRecipe(recipeName, addedIngredients, instuctions)) {
                //reseptin luominen onnistuu
                addInstructions.clear();
                recipeNameField.clear();
                this.addedIngredients.clear();
                try {
                    list(this.addedIngredients, 2);
                } catch (Exception ex) {
                    Logger.getLogger(RecipeCollectionUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            stage.setScene(this.loggedInScene);
        });
        
        
        border.setRight(border2);
        border.setLeft(grid);
        
        
        
        this.newRecipeScene = new Scene(border, sceneL, sceneK);
        return newRecipeScene;
    }
    
    public BorderPane middleButton(String text, int nro) {
        //nro 1 stands for create account and 2 for new recipe
        BorderPane border = new BorderPane();
        Button button = new Button(text);
        button.setMinHeight(60);
        border.setPrefWidth(sceneL / 2);
        
        border.setCenter(button);
        
        if (nro == 1) {
            button.setOnMouseClicked(e -> {
            stage.setScene(newUserScene());});
        } else if (nro == 2) {
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
                String pw = pwField.getText();
                try {
                    if (service.logIn(un, pw)) {
                        stage.setScene(loggedInScene());
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
    
    public StackPane list() throws Exception {
        //delete????
        //List is gonna be filled with user's recipe names
        ObservableList<String> data = FXCollections.observableArrayList();

        ListView<String> listView = new ListView<String>();
        listView.setPrefSize(200, 250);
        
        List<String> recipeNames = service.userRecipeNames();
        data.addAll(recipeNames);

        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, 
                String new_val) -> {
                //getText
                //set scene resepti
                    System.out.println(new_val);

        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        root.setPrefWidth(sceneL / 2);

        return root;
    }
    
    public StackPane list(List<String> recipes, int i) throws Exception {
        //0 for recipe name list
        ObservableList<String> data = FXCollections.observableArrayList();
        data.clear();
        
        ListView<String> listView = new ListView<String>();
        listView.setPrefSize(200, 250);
        
        //jos haluaan kaikki reseptit
        if(recipes.isEmpty() && i == 0) {
            recipes = service.userRecipeNames();
        }
        data.addAll(recipes);
        
        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, 
                String new_val) -> {
                if (i == 0) {
                    try {
                        this.stage.setScene(recipeScene(new_val));
                    } catch (Exception ex) {
                        System.out.println("ei toimi");
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
