
package ui;

import dao.DerbyRecipeDAO;
import dao.DerbyUserDAO;
import domain.Service;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RecipeCollectionUi extends Application {
    private Scene beginningScene, newUserScene, loggedInScene, newRecipeScene;
    private final int sceneH = 390;
    private final int sceneW = 720;
    private Stage stage;
    
    private Service service;
        
     @Override
    public void init() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));

        String userFile = prop.getProperty("user");
        String recipeFile = prop.getProperty("recipe");
        
        DerbyUserDAO userDao = new DerbyUserDAO(userFile);
        DerbyRecipeDAO recipeFile = new DerbyRecipeDAO(userDao, recipeFile);
        Service = new Service(recipeFile, userDao);
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
        
        this.beginningScene= new Scene(border, sceneW, sceneH);
        return beginningScene;
    }
    
    public Scene loggedInScene() {
        BorderPane border = new BorderPane();
        border.setRight(middleButton("Add A New Recipe", 2));
        border.setLeft(list());
        
        border.setTop(userMenu());
        this.loggedInScene = new Scene(border, sceneW, sceneH);
        return loggedInScene;
    }
    
    public Scene newUserScene() {
        BorderPane border = new BorderPane();
        border.setCenter(loginSigninBox("Sign up", "Sign up", 1));
        
        this.newUserScene = new Scene(border, sceneW, sceneH);
        return newUserScene;
    }
    
    public Scene newRecipeScene() {
        BorderPane border = new BorderPane();
        border.setRight(addInstructions());
        border.setLeft(addIngedients());
        
        this.newRecipeScene = new Scene(border, sceneW, sceneH);
        return newRecipeScene;
    }
    
    public BorderPane middleButton(String text, int nro) {
        //used in login page as "Create an Account" and logged in page as "make a new Recipe"
        //nro 1 stands for create account and 2 for new recipe
        //done
        BorderPane border = new BorderPane();
        Button button = new Button(text);
        button.setMinHeight(60);
        border.setPrefWidth(sceneW / 2);
        
        border.setCenter(button);
        
        if (nro == 1) {
            button.setOnMouseClicked(e -> {
            stage.setScene(newUserScene());});
        } else if (nro == 2) {
            button.setOnMouseClicked(e -> {
            stage.setScene(newRecipeScene());});
        }
        
        return border;
    }
    
    public GridPane loginSigninBox(String text, String button, int i) {
        //0 for signing in and 1 for signing up
        GridPane grid = gridPane();
        
        Text upText = new Text(text);
        upText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(upText, 0, 1);
        
        if (i == 1) {
            Label name = new Label("Name:");
            grid.add(name, 0, 2);
            TextField nameTextField = new TextField();
            grid.add(nameTextField, 1, 2);
        }
        
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 2+i);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2+i);
        Label password = new Label("Password:");
        grid.add(password, 0, 3+i);
        PasswordField pwField = new PasswordField();
        grid.add(pwField, 1, 3+i);
        
        Button downButton = new Button(button);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(downButton);
        grid.add(hbBtn, 1, 4+1);
        //sign in action
        downButton.setOnMouseClicked(e -> {
            if (i == 0) {
                //kirjaudu sisään
                //get texts and make sure they can be found from the database
                //if not -> put up a text "Incorrect username or password"
                
                stage.setScene(loggedInScene());
            } else {
                
        //Check that the user doesn't match with any of the existing users (from our list of users <User>)
        //If exists -> put up a text "You already have an account"
        //If doesn't -> go to the loggingScene
                stage.setScene(this.beginningScene);
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
        grid.setPrefWidth(sceneW / 2);
        
        return grid;
    }
    
    public MenuBar userMenu() {
        //gotta add user's name 
        //userMenu(String name)
        //https://o7planning.org/en/11115/javafx-contextmenu-tutorial // maybe?
        Menu userMenu = new Menu("User name");
        MenuItem sighOut = new MenuItem("Sign out");

        userMenu.getItems().add(sighOut);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(userMenu);
        
        //change the action!
        sighOut.setOnAction(e -> {
            System.out.println("Out");
        });
        
        return menuBar;
    }
    public BorderPane addInstructions() {
        Text text = new Text("Instructions");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
       

        TextArea addInstructions = new TextArea();
        
        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");
        
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, saveButton);
        buttons.setMinWidth(30);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10, 0, 0, 0));
        
        BorderPane border = new BorderPane();
        
        border.setTop(text);
        border.setCenter(addInstructions);
        border.setBottom(buttons);
        border.setPrefWidth(sceneW / 2);
        border.setPadding(new Insets(10, 10, 10, 10));
        
        backButton.setOnMouseClicked(e -> {
            stage.setScene(this.loggedInScene);
        });
        saveButton.setOnMouseClicked(e -> {
            //save to the database
            stage.setScene(this.loggedInScene);
        });
        
        return border;
    }
    
    public BorderPane addIngedients() {
        List<String> ingredients = new ArrayList();
        
        Text text = new Text("Ingedients");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField addField = new TextField();
        Button add = new Button("Add");
        
        HBox addingStuff = new HBox();
        addingStuff.getChildren().addAll(addField, add);
        addingStuff.setPadding(new Insets(10, 10, 10, 10));
        addingStuff.setSpacing(10);
        
        add.setOnMouseClicked(e -> {
            String newIngredient = addField.getText();
            addField.clear();
            ingredients.add(newIngredient);
            list(ingredients);
            
        });
        
        //hmmm propably gotta add list(1) etc.
        StackPane list = list(ingredients);
        
        BorderPane border = new BorderPane();
        border.setPrefWidth(sceneW / 2);
        border.setPadding(new Insets(10, 10, 10, 10));
        
        border.setTop(text);
        border.setTop(addingStuff);
        border.setCenter(list);
        
        return border;
    }
    
    public StackPane list() {
        //List is gonna be filled with user's recipe names
        ObservableList<String> data = FXCollections.observableArrayList();

        ListView<String> listView = new ListView<String>(data);
        listView.setPrefSize(200, 250);
        //Stream here, data.add
        data.addAll("Recipe A", "Recipe B", "Recipe C", "Recipe D", "Recipe E");

        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, 
                String new_val) -> {
                    System.out.println(new_val);

        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        root.setPrefWidth(sceneW / 2);


        return root;
    }
    
    public StackPane list(List<String> all) {
        //ei laita arvoja...
        ObservableList<String> data = FXCollections.observableArrayList(all);

        ListView<String> listView = new ListView<String>(data);
        listView.setPrefSize(200, 250);
        
        all.stream().forEach(i -> data.add(i));

        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, 
                String new_val) -> {
                    System.out.println(new_val);

        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        root.setPrefWidth(sceneW / 2);


        return root;
    }
    
   
}
