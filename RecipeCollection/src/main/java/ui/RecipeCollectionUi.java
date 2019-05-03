
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
import javafx.scene.control.Tooltip;
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
/**
 * Graphic user interface
 */
public class RecipeCollectionUi extends Application {
    private Scene loggedInScene;
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
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setResizable(false);
        Scene scene = beginningScene();
        
        stage.setTitle("RecipeCollection");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    /**
    * Builds beginning scene
    *
    * @see ui.RecipeCollectionUi#loginSigninBox(java.lang.String, java.lang.String, int) 
    * @see ui.RecipeCollectionUi#middleButton(java.lang.String, java.lang.String, int) 
    * 
    * @return beginning Scene
    */
    public Scene beginningScene() {
        BorderPane border = new BorderPane();
        
        border.setLeft(loginSigninBox("Sign in", "Sign in", 0));
        border.setRight(middleButton("Create An Account", "Join Today!", 30));
        
        return new Scene(border, sceneL, sceneK);
    }
    /**
    * Builds the scene that user sees when logged in
    *
    * 
    * @see ui.RecipeCollectionUi#list(java.util.List, int) 
    * @see ui.RecipeCollectionUi#middleButton(java.lang.String, java.lang.String, int) 
    * @see ui.RecipeCollectionUi#userMenu() 
    * @see domain.Service#userRecipeNames() 
    * 
    * @return loggedIn Scene
    */
    public Scene loggedInScene() {
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
            String name = searchBy.getText().trim().toLowerCase();
            if (name.isEmpty()) {
                borderList.setCenter(list(empty, 0));
            } else {
                ArrayList<String> names = this.service.userRecipeNames().stream().filter(r -> r.toLowerCase().contains(name)).collect(Collectors.toCollection(ArrayList::new));
                borderList.setCenter(list(names, 1));
            }
        });
        
        this.loggedInScene = new Scene(border, sceneL, sceneK);
        return loggedInScene;
    }
    /**
    * Builds scene for creating a new account
    *
    * @see ui.RecipeCollectionUi#loginSigninBox(java.lang.String, java.lang.String, int) 
    * @see ui.RecipeCollectionUi#beginningScene() 
    * 
    * @return newUser Scene
    */
    public Scene newUserScene() {
        Button back = new Button("Back");
        
        BorderPane border = new BorderPane();
        border.setCenter(loginSigninBox("Sign up", "Sign up", 1));
        border.setRight(back);
        border.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        border.setPadding(new Insets(10, 10, 10, 10));
        
        back.setOnAction(e -> {
            this.stage.setScene(beginningScene());
        });
        
        return new Scene(border, sceneL, sceneK);
    }
    /**
    * Builds scene for viewing a recipe
    *
    * @param recipeName name of the recipe user wants to view
    * 
    * @throws java.lang.Exception
    * @see ui.RecipeCollectionUi#instructions(java.lang.String) 
    * @see ui.RecipeCollectionUi#ingredients(java.lang.String) 
    * @see ui.RecipeCollectionUi#newRecipeScene(java.lang.String) 
    * @see ui.RecipeCollectionUi#loggedInScene
    * @see domain.Service#deleteRecipe(java.lang.String) 
    * 
    * @return beginning Scene
    */
    public Scene recipeScene(String recipeName) throws Exception {
        BorderPane borderDown = new BorderPane();
        borderDown.setRight(instructions(recipeName));
        borderDown.setLeft(ingredients(recipeName));
        
        Button backButton = new Button("Back");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Text name = new Text(recipeName);
        name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
        
        HBox hb = new HBox();
        hb.getChildren().add(name);
        hb.getChildren().add(editButton);
        hb.getChildren().add(deleteButton);
        hb.getChildren().add(backButton);
        hb.setSpacing(10);
        
        BorderPane border = new BorderPane();
        border.setTop(hb);
        border.setCenter(borderDown);
        
        border.setPadding(new Insets(10, 10, 10, 10));
        
        editButton.setOnAction(a -> {
            try {
                this.stage.setScene(newRecipeScene(recipeName));
            } catch (Exception e) {
                System.out.println("An exception occurred in changing a scene into an editing-scene: " + e.getMessage());
            }
        });
        
        backButton.setOnAction(e -> {
                this.stage.setScene(loggedInScene());
        });
        
        deleteButton.setOnAction(e -> {
            try {
                this.service.deleteRecipe(recipeName);
                
            } catch (Exception ex) {
                System.out.println("An exception occurred in deleting a recipe: " + ex.getMessage());
            }
            this.stage.setScene(loggedInScene());
        });
        
        return new Scene(border, sceneL, sceneK);
    }
    /**
    * Builds scene for creating a new recipe
    *
    * @param nameOrNull null if we are creating a new recipe or recipe's name if we are editing a recipe
    * @throws java.lang.Exception
    * @see domain.Service#getRecipeIngredienstByRecipeName(java.lang.String) 
    * @see ui.RecipeCollectionUi#list(java.util.List, int)
    * @see domain.Service#getRecipeInstructionsByRecipeName(java.lang.String) 
    * @see domain.Service#update(java.lang.String, java.lang.String, java.util.List, java.lang.String) 
    * @see domain.Service#createNewRecipe(java.lang.String, java.util.List, java.lang.String) 
    * 
    * @return newRecipe Scene
    */
    public Scene newRecipeScene(String nameOrNull) throws Exception {
        this.addedIngredients = new ArrayList();
        if (nameOrNull != null) {
            List<String> nameIngredients = service.getRecipeIngredienstByRecipeName(nameOrNull);
            nameIngredients.stream().forEach(i -> addedIngredients.add(i));
        }
        
        TextField recipeNameField = new TextField();
        recipeNameField.setPrefHeight(40);
        recipeNameField.setPromptText("Name of the recipe");
        recipeNameField.setPrefWidth(261);
        
        if (nameOrNull != null) {
            recipeNameField.setText(nameOrNull);
        }
        
        Text text = new Text("Ingredients");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TextField addField = new TextField();
        addField.setPromptText("Add/Delete ingredient");
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
                borderList.setCenter(list(addedIngredients, 2));
        });
        
        add.setOnMouseClicked(e -> {
            String newIngredient = addField.getText();
            addField.clear();
            newIngredient = newIngredient.trim();
            if (!newIngredient.isEmpty()) {
                addedIngredients.add(newIngredient);
            }
                borderList.setCenter(list(addedIngredients, 2));
        });
        
        vbox1.getChildren().add(text);
        vbox1.getChildren().add(addingStuff);
        vbox1.getChildren().add(borderList);
        
        Text textInstructions = new Text("Instructions");
        textInstructions.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        TextArea addInstructions = new TextArea();
        addInstructions.setWrapText(true);
        addInstructions.setPromptText("Add instructions here");
        addInstructions.setPrefSize(sceneL / 2 -20, sceneK - 90);
        
        if (nameOrNull != null) {
            addInstructions.setText(service.getRecipeInstructionsByRecipeName(nameOrNull));
        }
        
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
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Name must be unique. \nFields can't be empty.");
        saveButton.setTooltip(tooltip);
        
        backButton.setOnMouseClicked(e -> {
                stage.setScene(loggedInScene());
        });
        
        saveButton.setOnMouseClicked(e -> {
            String instuctions = addInstructions.getText();
            String recipeName = recipeNameField.getText();
            if (!instuctions.isEmpty() && !recipeName.isEmpty() && !addedIngredients.isEmpty()) {
                if (nameOrNull != null) {
                    try {
                        if (this.service.update(nameOrNull, recipeName, addedIngredients, instuctions)) {
                            addInstructions.clear();
                            recipeNameField.clear();
                            this.addedIngredients.clear();
                            
                                list(this.addedIngredients, 2);
                                this.loggedInScene = loggedInScene();
                            stage.setScene(this.loggedInScene);
                        }
                    } catch (Exception ex) {
                        System.out.println("An exception occurred in updating a recipe: " + ex.getMessage());
                    }
                } else {
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
                        stage.setScene(this.loggedInScene);
                    }
                }
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
        
        return new Scene(borderOut, sceneL, sceneK);
    }
    /**
    * Creates a big button and text above it that are placed in middle of a BorderPane
    * 
    * @param buttonText text that is set to the button
    * @param headText text that is above the button
    * @param nro when nro is 30, button is used for creating a new account - when nro is 0, nutton is used for creating a new recipe
    * 
    * @return BorderPane with a big button
    */
    public BorderPane middleButton(String buttonText, String headText, int nro) {
        //30 for new account 
        //0 for new recipe
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
                    stage.setScene(newRecipeScene(null));
                } catch (Exception ex) {
                    System.out.println("An exception occurred in changing a scene into an newRecipe-scene: " + ex.getMessage());
                }
            });
        }
        
        return border;
    }
    /**
    * Creates vbox including recipe's instructions
    * 
    * @param name recipe's name
    * 
    * @see domain.Service#getRecipeInstructionsByRecipeName(java.lang.String) 
    * 
    * @return instructions
    */
    public VBox instructions(String name) {
        Text text = new Text("Instructions");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Text instructions = new Text();
        String instructionText = service.getRecipeInstructionsByRecipeName(name);
        instructions.setText(instructionText);
        instructions.setWrappingWidth(sceneL / 2 - 30);
        VBox vbox = new VBox();
        vbox.getChildren().add(text);
        vbox.getChildren().add(instructions);
        vbox.setPrefSize(sceneL / 2, sceneK / 2);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        return vbox;
    }
    /**
    * Creates vbox including recipe's ingredients
    * 
    * @param name recipe's name
    * 
    * @see domain.Service#getRecipeIngredienstByRecipeName(java.lang.String) 
    * @see ui.RecipeCollectionUi#list(java.util.List, int) 
    * 
    * @return ingredients
    */
    public VBox ingredients(String name) {
        Text text = new Text("Ingredients");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        List<String> ingredients = this.service.getRecipeIngredienstByRecipeName(name);
        StackPane list = list(ingredients, 2);
        VBox vbox = new VBox();
        vbox.getChildren().add(text);
        vbox.getChildren().add(list);
        vbox.setPrefSize(sceneL / 2, sceneK / 2);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);
        return vbox;
    }
    /**
    * Creates GridPane used for login and sign in 
    * 
    * @param text text next to first TextField
    * @param button text next to second TextField
    * @param i 0 for login and 1 for creating a new user
    * 
    * @see ui.RecipeCollectionUi#gridPane() 
    * 
    * @return GridPane
    */
    public GridPane loginSigninBox(String text, String button, int i) {
        GridPane grid = gridPane();
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
                    if (service.logIn(un, pw)) {
                        this.stage.setScene(loggedInScene());
                        userTextField.clear();
                        pwField.clear();
                    } else {
                        Text failedLogin = new Text("Incorrect username or password");
                        grid.add(failedLogin, 1, 5);
                    }
            } else if (i == 1){
                String un = userTextField.getText();
                String pw = pwField.getText(); 
                
                try {
                    if (service.createNewUser(un, pw)) {
                        stage.setScene(beginningScene());
                    } else {
                        Text failedLogin = new Text("This username is already in use\nor it contains an illegal mark '';''");
                        grid.add(failedLogin, 1, 5);
                    }
                } catch (Exception ex) {
                    System.out.println("An exception occurred in creating a new user: " + ex.getMessage());
                }
            }
        });
        
        return grid;
    }
    /**
    * Creates a GridPane 
    * @return GridPane
    */
    public GridPane gridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(30);
        grid.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setPrefWidth(sceneL / 2);
        
        return grid;
    }
    /**
    * Creates an usermenu
    * 
    * @return MenuBar
    */
    public MenuBar userMenu() {
        String username = service.getLoggenInUser().getUsername();
        Menu userMenu = new Menu(username);
        userMenu.setStyle("-fx-background-color:#FA8072;");
        MenuItem sighOut = new MenuItem("Sign out");
        MenuItem delete = new MenuItem("Delete account");
        
        userMenu.getItems().add(sighOut);
        userMenu.getItems().add(delete);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(userMenu);
        
        sighOut.setOnAction(e -> {
            service.logOut();
            stage.setScene(beginningScene());
        });
        
        delete.setOnAction(e -> {
            Button back2 = new Button("Back");
            Button delete2 = new Button("Delete");
            HBox deleteBack = new HBox();
            deleteBack.setSpacing(20);
            deleteBack.setPadding(new Insets(10, 0, 0, 90));
            deleteBack.getChildren().addAll(back2, delete2);
            Text confirmText = new Text("Are you sure you want to delete this account?");
            confirmText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            BorderPane pb = new BorderPane();
            pb.setPadding(new Insets(20, 20, 20, 20));
            
            pb.setTop(confirmText);
            pb.setCenter(deleteBack);
            
            Scene confirmScene = new Scene(pb, 340, 100);
            Stage confirmStage = new Stage();
            confirmStage.setResizable(false);
            confirmStage.setScene(confirmScene);
            confirmStage.show();
            
            back2.setOnAction(e2 -> {
                confirmStage.close();
            });
            
            delete2.setOnAction(e3 -> {
                try {
                if (service.deleteAccount()) {
                    confirmStage.close();
                    stage.setScene(beginningScene());
                }
            } catch (Exception ex) {
                System.out.println("An exception occurred in deleting an account: " + ex.getMessage());
            }
            });
        });
        
        return menuBar;
    }
    /**
    * Creates a list 
    * 
    * @param recipesOrIngredients a list of recipe names or ingredients
    * @param i 0 for listing all recipes, 1 for listing specific recipes, 2 for listing ingredients
    * @return a list
    */
    public StackPane list(List<String> recipesOrIngredients, int i) {
        ObservableList<String> data = FXCollections.observableArrayList();
        data.clear();
        
        ListView<String> listView = new ListView<>();
        listView.setPrefSize(200, 250);
        
        if(recipesOrIngredients.isEmpty() && i == 0 ) {
            recipesOrIngredients = service.userRecipeNames();
        }
        data.addAll(recipesOrIngredients);
        
        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener (
            (ObservableValue<? extends String> ov, String old_val, 
            String new_val) -> {
            if (i == 0 || i == 1) {
                try {
                    this.stage.setScene(recipeScene(new_val));
                } catch (Exception ex) {
                System.out.println("An exception occurred in changing a scene into a recipe-scene: " + ex.getMessage());
                }
            }

        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        root.setPrefWidth(sceneL / 2);

        return root;
    }
}
