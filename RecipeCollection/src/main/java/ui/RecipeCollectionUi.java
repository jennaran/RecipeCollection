
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
    private Scene beginningScene, newUserScene, loggedInScene, newRecipeScene, recipeScene;
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
        
        this.beginningScene= new Scene(border, sceneW, sceneH);
        return beginningScene;
    }
    
    public Scene loggedInScene() throws Exception {
        BorderPane border = new BorderPane();
        border.setRight(middleButton("Add A New Recipe", 2));
        BorderPane borderIngredients = new BorderPane();
        borderIngredients.setPrefWidth(sceneW / 2);
        
        HBox searching = new HBox();
        searching.setPrefWidth(sceneW / 2);
        Button search = new Button("Search");
        TextField searchBy = new TextField();
        searchBy.setPrefWidth(300);
        searching.getChildren().add(searchBy);
        searching.getChildren().add(search);
        borderIngredients.setTop(searching);
        borderIngredients.setCenter(list());
        
        search.setOnAction(a -> {
            String name = searchBy.getText();
        });
        
        border.setLeft(borderIngredients);
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
    
    public Scene recipeScene() {
        BorderPane border = new BorderPane();
        border.setRight(Instructions());
        //border.setLeft(ingedients());
        
        this.newRecipeScene = new Scene(border, sceneW, sceneH);
        return newRecipeScene;
    }
    
    public BorderPane middleButton(String text, int nro) {
        //nro 1 stands for create account and 2 for new recipe
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
    
    public GridPane Instructions() {
        Text text = new Text("Instructions");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Text instructions = new Text();
        
        String instructionText = "";
        GridPane grid = gridPane();
        grid.add(grid, sceneH, sceneH);
        return grid;
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
        grid.setPrefWidth(sceneW / 2);
        
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
                service.deleteAccount();
                stage.setScene(this.beginningScene);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        
        return menuBar;
    }
    
    public BorderPane addInstructions() {
        Text text = new Text("Instructions");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextArea addInstructions = new TextArea();
        
        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");
        
        
        GridPane grid = new GridPane();
        grid.add(text, 0, 0);
        grid.add(backButton, 2, 0);
        grid.add(saveButton, 3, 0);
        grid.setPadding(new Insets(0, 0, 40, 0));
        grid.setHgap(10);
        BorderPane border = new BorderPane();
        
        border.setTop(grid);
        border.setCenter(addInstructions);
        border.setPrefWidth(sceneW / 2);
        border.setPadding(new Insets(10, 10, 10, 10));
        border.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        
        backButton.setOnMouseClicked(e -> {
            stage.setScene(this.loggedInScene);
        });
        saveButton.setOnMouseClicked(e -> {
            //save to the database
            stage.setScene(this.loggedInScene);
        });
        
        return border;
    }
    
    public GridPane addIngedients() {
        List<String> ingredients = new ArrayList();
        
        Text text = new Text("Ingedients");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField addField = new TextField();
        Button add = new Button("Add");
        
        
        HBox addingStuff = new HBox();
        addingStuff.getChildren().addAll(addField, add);
        addingStuff.setPadding(new Insets(10, 0, 5, 0));
        addingStuff.setSpacing(10);
             
        add.setOnMouseClicked(e -> {
            String newIngredient = addField.getText();
            addField.clear();
            ingredients.add(newIngredient);
            list(ingredients);
            
        });
        
        //hmmm propably gotta add list(1) etc.
        StackPane list = list(ingredients);
        
        GridPane grid = new GridPane();
        grid.setPrefWidth(sceneW / 2);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        grid.add(text, 0, 0);
        grid.add(addingStuff, 0, 1);
        grid.add(list, 0, 2);
        
        return grid;
    }
    
    public StackPane list() throws Exception {
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
        listView.setPrefSize(300, 310);
        
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
