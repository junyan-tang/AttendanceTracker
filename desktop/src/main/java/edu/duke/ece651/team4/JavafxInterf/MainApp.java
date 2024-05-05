package edu.duke.ece651.team4.JavafxInterf;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar;
import javafx.beans.property.ReadOnlyObjectWrapper;


import java.io.File;


import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.util.Pair;
import java.util.stream.Collectors;

// import com.google.api.services.gmail.Gmail.Users.Drafts.List;

import edu.duke.ece651.team4.shared.*;

// import

public class MainApp extends Application {

  Stage primaryStage; 
  Stage addCourseStage;
  Stage addSectionStage;
  Stage addStudentRoasterStage;
  Stage addUserStage;
  TextField txtUsername;
  PasswordField txtPassword;
  List<List<String>> csvRawData;

  private TableView<Course> courseTable = new TableView<>();
  private ObservableList<Course> courseData = FXCollections.observableArrayList();

  private TableView<List<String>> csvTable = new TableView<>();
  private ObservableList<List<String>> csvData = FXCollections.observableArrayList();

  private TableView<Section> sectionTable = new TableView<>();
  private ObservableList<Section> sectionData = FXCollections.observableArrayList();

  private TableView<User> userTable = new TableView<>();
  private ObservableList<User> userData = FXCollections.observableArrayList();

  @Override
  public void start(Stage stage) {
      this.primaryStage = stage;

      TextField txtUsername = new TextField();
      txtUsername.setPromptText("Enter your NetID");
      this.txtUsername = txtUsername;
      PasswordField txtPassword = new PasswordField();
      txtPassword.setPromptText("Enter your password");
      Button btnLogin = new Button("Login");
      this.txtPassword = txtPassword;

      TableColumn<Course, String> courseIDColumn = new TableColumn<>("Course ID");
      courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
      TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
      courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

      TableColumn<Section, String> sectionIDColumn = new TableColumn<>("Section ID");
      sectionIDColumn.setCellValueFactory(new PropertyValueFactory<>("sectionID"));
      TableColumn<Section, String> sectioncourseIDColumn = new TableColumn<>("Course ID");
      sectioncourseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
      TableColumn<Section, String> sectionTeacherIDColumn = new TableColumn<>("Teacher ID");
      sectionTeacherIDColumn.setCellValueFactory(new PropertyValueFactory<>("teacherID"));

      TableColumn<User, String> userNetIDColumn = new TableColumn<>("NetID");
      userNetIDColumn.setCellValueFactory(new PropertyValueFactory<>("netid"));
      TableColumn<User, String> userRoleColumn = new TableColumn<>("Name");
      userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("preferredName"));
      TableColumn<User, String> userEmailColumn = new TableColumn<>("Email");
      userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


  
      courseTable.getColumns().addAll(courseIDColumn, courseNameColumn);
      courseTable.setItems(courseData);

      sectionTable.getColumns().addAll(sectionIDColumn, sectioncourseIDColumn, sectionTeacherIDColumn);
      sectionTable.setItems(sectionData);

      userTable.getColumns().addAll(userNetIDColumn, userRoleColumn, userEmailColumn);
      userTable.setItems(userData);

      btnLogin.setOnAction(e -> {
        String netID = txtUsername.getText();
        String password = txtPassword.getText();
        System.out.println(password);
        System.out.println(password.length());
        // String res = LoginManager.logUser(netID, password, "admin");
        String res = "Login successful";
        if(res.equals("Login successful")){
          //System.out.println("Login successful");
          this.primaryStage.hide();
          showMainStage();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Login failed"+res);
          alert.show();
          //System.out.println("Login failed");
        }
      });

      VBox layout = new VBox(10, txtUsername, txtPassword, btnLogin);
      Scene scene = new Scene(layout, 500, 500);
      stage.setTitle("Login");
      stage.setScene(scene);
      stage.show();
  }

  public void showMainStage(){
    Stage mainStage = new Stage();
    Label welcomeLabel = new Label("Welcome admin");
    Button btnMod = new Button("Modify course list");
    Button btnSec = new Button("Modify section list");
    Button btnStu = new Button("Modify student list");
    Button btnUse = new Button("Modify user list");
    Button btnLogout = new Button("Logout");

    btnMod.setOnAction(e->{
      mainStage.hide();
      showAddCourseStage();
    });

    btnSec.setOnAction(e->{
      mainStage.hide();
      showAddSectionStage();
    });

    btnLogout.setOnAction(e->{
      mainStage.close();
      this.txtPassword.clear();
      this.primaryStage.show();
    });

    btnStu.setOnAction(e->{
      mainStage.hide();
      showAddStudentRoasterStage();
    });

    btnUse.setOnAction(e->{
      mainStage.hide();
      showAddUserStage();
    });

    VBox layout = new VBox(10, welcomeLabel, btnMod, btnSec, btnStu, btnUse, btnLogout);
    Scene scene = new Scene(layout, 500, 500);
    mainStage.setTitle("Admin Panel");
    mainStage.setScene(scene);
    mainStage.show();
  }

  private void loadCourses(){
    courseData.clear();
    List<Course> courses = CourseController.getAllCourses();
    courseData.addAll(courses);
  }

  private void loadSections(){
    sectionData.clear();
    List<Section> sections = SectionController.getAllSections();
    sectionData.addAll(sections);
  }

  private void loadSections(String courseID){
    sectionData.clear();
    List<Section> sections = SectionController.getAllSectionsCour(courseID);
    // List<Section> sections = SectionController.getAllSections();
    sectionData.addAll(sections);
  }

  private void loadUsers(String type){
    userData.clear();
    if(type.equals("student")){
      List<User> users = StuController.getAllStudent();
      // System.out.println(users.get(0).password);
      userData.addAll(users);
    }else if(type.equals("professor")){
      List<User> users = StuController.getAllTeacher();
      // System.out.println(users.get(0).password);
      userData.addAll(users);
    }
  }

  private void updateTableWithCSV(List<List<String>> csv){
    csvTable.getColumns().clear();
    csvData.setAll(csv);
    if(!csv.isEmpty()){
      int numColumns = csv.get(0).size();
      for(int i = 0; i < numColumns; i++){
        // TableColumn<List<String>, String> column = new TableColumn<>("Column " + i);
        final int colIndex = i;
        TableColumn<List<String>, String> column = new TableColumn<>("Column " + (i + 1));
        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colIndex)));
        csvTable.getColumns().add(column);
      }
    csvTable.setItems(csvData);
  }

  // InputCourseFilter.insertIntoDB(csv, System.out);
}

  private void loadCSV(Stage stage){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("CSV Files", "*.csv")
    );
    File file = fileChooser.showOpenDialog(stage);
    if(file != null){
      try{
        List<List<String>> csv = InputCourseFilter.readCSVFile(file.getAbsolutePath());
        csvRawData = csv;
        updateTableWithCSV(csv);
      }catch(Exception e){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Error reading CSV file");
        alert.show();
      }
    }else{
      Alert alert = new Alert(AlertType.ERROR);
      alert.setContentText("No file selected");
      alert.show();
      csvData.clear();
      csvRawData.clear();
    }
      
  }

  private void showUpdateDialog(){
    Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

    if(selectedCourse == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a course to update");
      alert.show();
      return;
    }

    TextInputDialog dialog = new TextInputDialog(selectedCourse.getCourseName());
    dialog.setTitle("Update Course");
    dialog.setHeaderText("Update Course Name");
    dialog.setContentText("Enter new course name:");

    dialog.showAndWait().ifPresent(courseName->{
      if(courseName.equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("CourseName cannot be empty");
        alert.show();
        return;
      }
      if(CourseController.updateCourse(selectedCourse.getCourseID(), courseName)){
        loadCourses();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Course updated successfully");
        alert.show();
        courseTable.refresh();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("CourseName already exists");
        alert.show();
      }
    });
  }

  private void showRemoveDialog(){
    Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

    if(selectedCourse == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a course to remove");
      alert.show();
      return;
    }

    Alert doubleCheck = new Alert(AlertType.CONFIRMATION);
    doubleCheck.setTitle("Confirm Remove");
    doubleCheck.setContentText("Are you sure you want to remove this course?");

    doubleCheck.showAndWait().ifPresent(response->{
      if(response == ButtonType.OK){
        if(CourseController.deleteCourse(selectedCourse.getCourseName())){
          loadCourses();
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setContentText("Course removed successfully");
          alert.show();
        }else{
          Alert alert = new Alert(AlertType.ERROR);
          alert.setContentText("Course does not exist");
          alert.show();
        }
      }
    });

  }

  private void showAddDialog(){
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Add Course");
    dialog.setHeaderText("Add a new course");

    ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    Label lblCourseID = new Label("Course ID");
    Label lblCourseName = new Label("Course Name");
    TextField txtCourseID = new TextField();
    txtCourseID.setPromptText("Course ID");
    TextField txtCourseName = new TextField();
    txtCourseName.setPromptText("Course Name");

    dialog.getDialogPane().setContent(new VBox(8, lblCourseID, txtCourseID, lblCourseName, txtCourseName));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){
        return new Pair<>(txtCourseID.getText(), txtCourseName.getText());
      }
      return null;
    });

    dialog.showAndWait().ifPresent(course->{
      if(course.getKey().equals("") || course.getValue().equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("CourseName or CourseID cannot be empty");
        alert.show();
        return;
      }
      if(CourseController.addCourse(course.getKey(), course.getValue())){
        loadCourses();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Course added successfully");
        alert.show();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("CourseName or CourseID already exists");
        alert.show();
      }
    });
  }

  private void showCSVDialog(String type){
    Stage csvStage = new Stage();
    Button btnLoadCSV = new Button("Load CSV");
    Button btnInsert = new Button("Insert into DB");
    Button btnBack = new Button("Back");

    btnLoadCSV.setOnAction(e->{
      loadCSV(csvStage);
    });

    btnInsert.setOnAction(e->{
      if(csvData.isEmpty()||csvRawData.isEmpty()){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("No data to insert");
        alert.show();
        return;
      }
      String res = InputCourseFilter.insertIntoDBCourse(csvRawData, type);
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setContentText(res);
      alert.show();
      csvData.clear();
      csvRawData.clear();
      loadCourses();
    });

    btnBack.setOnAction(e->{
      if(csvData!=null){
        csvData.clear();
      }
      if(csvRawData!=null){
        csvRawData.clear();
      }
      csvStage.close();
      if(type.equals("course")){
        loadCourses();
        this.addCourseStage.show();
      }else if(type.equals("section")){
        loadSections();
        this.addSectionStage.show();
      }else if(type.equals("roaster")){
        loadCourses();
        this.addStudentRoasterStage.show();
      }
    });

    VBox layout = new VBox(10, csvTable, btnLoadCSV, btnInsert, btnBack);
    Scene scene = new Scene(layout, 500, 500);
    csvStage.setTitle("Load CSV");
    csvStage.setScene(scene);
    csvStage.show();
  }

  private void showAddCourseStage(){
    Stage addCourseStage = new Stage();
    this.addCourseStage = addCourseStage;
    // Label lblCourseID = new Label("Course ID");
    // TextField txtCourseID = new TextField();
    // Label lblCourseName = new Label("Course Name");
    // TextField txtCourseName = new TextField();
    Button btnAddCourse = new Button("Add Course");
    Button btnUpdate = new Button("Update Course");
    Button btnRemove = new Button("Remove Course");
    Button btnLoadCSV = new Button("Load Course List from CSV");
    Button btnBack = new Button("Back");

    loadCourses();
    courseTable.setEditable(true);

    btnAddCourse.setOnAction(e->{
      showAddDialog();
    });

    btnBack.setOnAction(e->{
      courseTable.setEditable(false);
      addCourseStage.close();
      showMainStage();
    });

    btnUpdate.setOnAction(e->{
      showUpdateDialog();
    });

    btnRemove.setOnAction(e->{
      showRemoveDialog();
    });

    btnLoadCSV.setOnAction(e->{
      addCourseStage.hide();
      showCSVDialog("course");
    });

    VBox layout = new VBox(10, courseTable, btnAddCourse, btnUpdate, btnRemove, btnLoadCSV, btnBack);
    // VBox layout = new VBox(10, courseTable, lblCourseID, txtCourseID, lblCourseName, txtCourseName, btnAddCourse, btnUpdate, btnRemove, btnBack);
    Scene scene = new Scene(layout, 500, 500);
    addCourseStage.setTitle("Add a new course");
    addCourseStage.setScene(scene);
    addCourseStage.show();
  }

  private void showAddSecDialog(){
    // Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

    // if(selectedCourse == null){
    //   Alert alert = new Alert(AlertType.WARNING);
    //   alert.setContentText("Please select a course to add section");
    //   alert.show();
    //   return;
    // }

    Dialog<ArrayList<String>> dialog = new Dialog<>();
    // loadSections(selectedCourse.getCourseID());
    dialog.setTitle("Add Section");
    dialog.setHeaderText("Add a new section");

    ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    Label lblCourseID = new Label("Course ID");
    Label lblSectionID = new Label("Section ID");
    Label lblTeacherID = new Label("Teacher ID");

    ComboBox<String> txtCourseID = new ComboBox<>();
    txtCourseID.setPromptText("Course ID");
    List<String> courseIDList = CourseController.getAllCourses().stream().map(course->course.courseID).toList();
    txtCourseID.setEditable(true);
    txtCourseID.getItems().addAll(courseIDList);

    TextField txtSectionID = new TextField();
    txtSectionID.setPromptText("Section ID");

    ComboBox<String> txtTeacherID = new ComboBox<>();
    txtTeacherID.setPromptText("Teacher ID");
    txtTeacherID.setEditable(true);
    List<String> teacherIDList = StuController.getAllTeacher().stream().map(user->user.netid).toList();
    teacherIDList.add("null");
    txtTeacherID.getItems().addAll(teacherIDList);

    dialog.getDialogPane().setContent(new VBox(20, lblCourseID, txtCourseID, lblTeacherID, txtTeacherID, lblSectionID, txtSectionID));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){
        ArrayList<String> res = new ArrayList<String>(Arrays.asList(txtSectionID.getText(), txtCourseID.getValue(), txtTeacherID.getValue()));

        return res;
      }
      return null;
    });

    dialog.showAndWait().ifPresent(section->{
      if(section.get(0).equals("") || section.get(1).equals("") || section.get(2).equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("SectionID or TeacherID cannot be empty");
        alert.show();
        return;
      }
      if(SectionController.addSection(section.get(0), section.get(1), section.get(2))){
        loadSections();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Section added successfully");
        alert.show();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("SectionID already exists");
        alert.show();
      }
    });

  }

  private void showUpdateSecDialog(){
    Section selectedSection = sectionTable.getSelectionModel().getSelectedItem();

    if(selectedSection == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a section to update");
      alert.show();
      return;
    }

    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Update Section");
    dialog.setHeaderText("Update Teacher ID");
    ButtonType addButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
    Label lblTeacherID = new Label("Teacher ID");

    ComboBox<String> txtTeacherID = new ComboBox<>();
    txtTeacherID.setPromptText("Teacher ID");
    txtTeacherID.setEditable(true);
    List<String> teacherIDList = StuController.getAllTeacher().stream().map(user->user.netid).toList();
    txtTeacherID.getItems().addAll(teacherIDList);

    dialog.getDialogPane().setContent(new VBox(20, lblTeacherID, txtTeacherID));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){

        return txtTeacherID.getValue();
      }
      return null;
    });

    dialog.showAndWait().ifPresent(teacherID->{
      if(teacherID.equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("TeacherID cannot be empty");
        alert.show();
        return;
      }
      if(SectionController.updateSection(selectedSection.getSectionID(), teacherID)){
        loadSections();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Section updated successfully");
        alert.show();
        sectionTable.refresh();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Fail to update section");
        alert.show();
      }
    });
  }

  private void showRemoveSecDialog(){
    Section selectedSection = sectionTable.getSelectionModel().getSelectedItem();

    if(selectedSection == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a section to remove");
      alert.show();
      return;
    }

    Alert doubleCheck = new Alert(AlertType.CONFIRMATION);
    doubleCheck.setTitle("Confirm Remove");
    doubleCheck.setContentText("Are you sure you want to remove this section?");

    doubleCheck.showAndWait().ifPresent(response->{
      if(response == ButtonType.OK){
        if(SectionController.deleteSection(selectedSection.getSectionID())){
          loadSections();
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setContentText("Section removed successfully");
          alert.show();
        }else{
          Alert alert = new Alert(AlertType.ERROR);
          alert.setContentText("Section does not exist");
          alert.show();
        }
      }
    });

  }

  private void showAddSectionStage(){
    Stage addSectionStage = new Stage();
    this.addSectionStage = addSectionStage;
    Button btnAddSec = new Button("Add Section");
    Button btnUpdateSec = new Button("Update Section");
    Button btnRemoveSec = new Button("Remove Section");
    Button btnLoadCSVSec = new Button("Load Section List from CSV");
    Button btnBackSec = new Button("Back");

    loadSections();
    courseTable.setEditable(true);

    btnAddSec.setOnAction(e->{
      showAddSecDialog();
    });

    btnUpdateSec.setOnAction(e->{
      showUpdateSecDialog();
    });

    btnRemoveSec.setOnAction(e->{
      showRemoveSecDialog();
    });

    btnLoadCSVSec.setOnAction(e->{
      addSectionStage.hide();
      showCSVDialog("section");
    });

    btnBackSec.setOnAction(e->{
      // courseTable.setEditable(false);
      addSectionStage.close();
      showMainStage();
    });


    VBox layout = new VBox(10, sectionTable, btnAddSec, btnUpdateSec, btnRemoveSec, btnLoadCSVSec, btnBackSec);
    Scene scene = new Scene(layout, 500, 500);
    addSectionStage.setTitle("Modiy Section List");
    addSectionStage.setScene(scene);
    addSectionStage.show();
  }

  private void showAddStuRoaDialog(){
    Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

    if(selectedCourse == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a course to add student");
      alert.show();
      return;
    }

    Dialog<Pair<String, String>> dialog = new Dialog<>();

    dialog.setTitle("Add Student to Roaster");

    dialog.setHeaderText("Add a new student to roaster");

    ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);

    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    Label lblSectionID = new Label("Section ID");
    Label lblStudentID = new Label("Student ID");

    ComboBox<String> txtSectionID = new ComboBox<>();
    txtSectionID.setPromptText("Section ID");
    ComboBox<String> txtStudentID = new ComboBox<>();
    txtStudentID.setPromptText("Student ID");
    List<String> sectionIDList = SectionController.getAllSectionsCour(selectedCourse.courseID).stream().map(section->section.sectionID).toList();
    txtSectionID.setEditable(true);
    txtSectionID.getItems().addAll(sectionIDList);
    txtSectionID.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
      Set<String> studentIDSet = StuController.getStudentSections(newVal).stream().collect(Collectors.toSet());
      List<String> studentIDList = StuController.getAllStudent().stream().map(user->user.netid).filter(
        studentID->!studentIDSet.contains(studentID)
      ).toList();
      txtStudentID.getItems().clear();
      txtStudentID.getItems().addAll(studentIDList);
    });

    dialog.getDialogPane().setContent(new VBox(20, lblSectionID, txtSectionID, lblStudentID, txtStudentID));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){
        return new Pair<>(txtSectionID.getValue(), txtStudentID.getValue());
      }
      return null;
    });


    dialog.showAndWait().ifPresent(section->{
      if(section.getKey().equals("") || section.getValue().equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("SectionID or StudentID cannot be empty");
        alert.show();
        return;
      }
      if(StuController.addStudentToSection(section.getValue(), section.getKey())){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Student added successfully");
        alert.show();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Student already in section");
        alert.show();
      }
    });
  }

  private void showRemoveStuRoaDialog(){
    Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();

    if(selectedCourse == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a course to remove student");
      alert.show();
      return;
    }

    Dialog<Pair<String, String>> dialog = new Dialog<>();

    dialog.setTitle("Remove Student from Roaster");

    dialog.setHeaderText("Remove a student from roaster");

    ButtonType addButtonType = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);

    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    Label lblSectionID = new Label("Section ID");
    Label lblStudentID = new Label("Student ID");

    ComboBox<String> txtSectionID = new ComboBox<>();
    txtSectionID.setPromptText("Section ID");
    ComboBox<String> txtStudentID = new ComboBox<>();
    txtStudentID.setPromptText("Student ID");
    List<String> sectionIDList = SectionController.getAllSectionsCour(selectedCourse.courseID).stream().map(section->section.sectionID).toList();
    txtSectionID.setEditable(true);
    txtSectionID.getItems().addAll(sectionIDList);
    txtSectionID.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
      List<String> studentIDList = StuController.getStudentSections(newVal);
      txtStudentID.getItems().clear();
      txtStudentID.getItems().addAll(studentIDList);
    });

    dialog.getDialogPane().setContent(new VBox(20, lblSectionID, txtSectionID, lblStudentID, txtStudentID));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){
        return new Pair<>(txtSectionID.getValue(), txtStudentID.getValue());
      }
      return null;
    });


    dialog.showAndWait().ifPresent(section->{
      if(section.getKey().equals("") || section.getValue().equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("SectionID or StudentID cannot be empty");
        alert.show();
        return;
      }
      if(StuController.removeStudentFromSection(section.getValue(), section.getKey())){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Student removed successfully");
        alert.show();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Student not in section");
        alert.show();
      }
    });
  }
    
  private void showAddStudentRoasterStage(){
    Stage addStudentRoasterStage = new Stage();
    this.addStudentRoasterStage = addStudentRoasterStage;
    Button btnAddRoa = new Button("Add Student to Roaster");
    // Button btnUpdateSec = new Button("Update Section");
    Button btnRemoveRoa = new Button("Drop Student from Section");
    Button btnLoadCSVRoa = new Button("Load Roaster List from CSV");
    Button btnBackRoa = new Button("Back");

    // loadSections();
    loadCourses();
    // courseTable.setEditable(true);

    btnAddRoa.setOnAction(e->{
      showAddStuRoaDialog();
    });

    btnRemoveRoa.setOnAction(e->{
      showRemoveStuRoaDialog();
    });

    btnLoadCSVRoa.setOnAction(e->{
      addStudentRoasterStage.hide();
      showCSVDialog("roaster");
    });

    btnBackRoa.setOnAction(e->{
      // courseTable.setEditable(false);
      addStudentRoasterStage.close();
      showMainStage();
    });


    VBox layout = new VBox(10, courseTable, btnAddRoa, btnRemoveRoa, btnLoadCSVRoa, btnBackRoa);
    Scene scene = new Scene(layout, 500, 500);
    addStudentRoasterStage.setTitle("Modiy Student Roaster List");
    addStudentRoasterStage.setScene(scene);
    addStudentRoasterStage.show();

  }

  private void showAddUserDialog(String type){
    Dialog<ArrayList<String>> dialog = new Dialog<>();
    dialog.setTitle("Add User");
    dialog.setHeaderText("Add a new "+ type);

    ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    Label lblNetID = new Label("NetID");
    Label lblPassword = new Label("Password");
    Label lblEmail = new Label("Email");
    Label lblPreferredName = new Label("Preferred Name");
    Label lblLastName = new Label("Last Name");
    Label lblFirstName = new Label("First Name");

    TextField txtNetID = new TextField();
    txtNetID.setPromptText("NetID");
    PasswordField txtPassword = new PasswordField();
    txtPassword.setPromptText("Password");
    TextField txtEmail = new TextField();
    txtEmail.setPromptText("Email");
    TextField txtPreferredName = new TextField();
    txtPreferredName.setPromptText("Preferred Name");
    TextField txtLastName = new TextField();
    txtLastName.setPromptText("Last Name");
    TextField txtFirstName = new TextField();
    txtFirstName.setPromptText("First Name");

    dialog.getDialogPane().setContent(new VBox(8, lblNetID, txtNetID, lblPassword, txtPassword, lblEmail, txtEmail, lblPreferredName, txtPreferredName, lblLastName, txtLastName, lblFirstName, txtFirstName));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){
        ArrayList<String> res = new ArrayList<String>(Arrays.asList(txtNetID.getText(), txtFirstName.getText(), txtLastName.getText(), txtPreferredName.getText(), txtPassword.getText(), type, txtEmail.getText()));
        return res;
      }
      return null;
    });

    dialog.showAndWait().ifPresent(user->{
      if(user.get(0).equals("") || user.get(1).equals("") || user.get(2).equals("") || user.get(3).equals("") || user.get(4).equals("") || user.get(5).equals("")|| user.get(6).equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("NetID, Password, Email, Preferred Name, Last Name, First Name cannot be empty");
        alert.show();
        return;
      }
      if(StuController.addUser(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4), user.get(5), user.get(6))){
        loadUsers(type);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("User added successfully");
        alert.show();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("User already exists");
        alert.show();
      }
    });

    
  }

  private void showUpdateUserDialog(String type){
    User selectedUser = userTable.getSelectionModel().getSelectedItem();

    if(selectedUser == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a user to update");
      alert.show();
      return;
    }

    Dialog<ArrayList<String>> dialog = new Dialog<>();
    dialog.setTitle("Update User");
    dialog.setHeaderText("Update "+type+" Information");

    ButtonType addButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    // Label lblNetID = new Label("NetID");
    Label lblPassword = new Label("Password");
    Label lblEmail = new Label("Email");
    Label lblPreferredName = new Label("Preferred Name");
    Label lblLastName = new Label("Last Name");
    Label lblFirstName = new Label("First Name");

    PasswordField txtPassword = new PasswordField();
    txtPassword.setPromptText("Password");
    // txtPassword.setText();
    TextField txtEmail = new TextField();
    txtEmail.setPromptText("Email");
    txtEmail.setText(selectedUser.email);
    TextField txtPreferredName = new TextField();
    txtPreferredName.setPromptText("Preferred Name");
    txtPreferredName.setText(selectedUser.preferredName);
    TextField txtLastName = new TextField();
    txtLastName.setPromptText("Last Name");
    txtLastName.setText(selectedUser.lastName);
    TextField txtFirstName = new TextField();
    txtFirstName.setPromptText("First Name");
    txtFirstName.setText(selectedUser.firstName);

    dialog.getDialogPane().setContent(new VBox(8, lblPassword, txtPassword, lblEmail, txtEmail, lblPreferredName, txtPreferredName, lblLastName, txtLastName, lblFirstName, txtFirstName));

    dialog.setResultConverter(dialogButton->{
      if(dialogButton == addButtonType){
        ArrayList<String> res = new ArrayList<String>(Arrays.asList(selectedUser.netid, txtFirstName.getText(), txtLastName.getText(), txtPreferredName.getText(), txtPassword.getText(), type, txtEmail.getText()));
        return res;
      }
      return null;
    });

    dialog.showAndWait().ifPresent(user->{
      if(user.get(0).equals("") || user.get(1).equals("") || user.get(2).equals("") || user.get(3).equals("") || user.get(4).equals("") || user.get(5).equals("")|| user.get(6).equals("")){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("NetID, Password, Email, Preferred Name, Last Name, First Name cannot be empty");
        alert.show();
        return;
      }
      if(StuController.updateUser(user.get(0), user.get(1), user.get(2), user.get(3), user.get(4), user.get(5), user.get(6))){
        loadUsers(type);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("User updated successfully");
        alert.show();
      }else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("User does not exist");
        alert.show();
      }
    });
  }

  private void showRemoveUserDialog(String type){
    User selectedUser = userTable.getSelectionModel().getSelectedItem();

    if(selectedUser == null){
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("Please select a user to remove");
      alert.show();
      return;
    }

    Alert doubleCheck = new Alert(AlertType.CONFIRMATION);
    doubleCheck.setTitle("Confirm Remove");
    doubleCheck.setContentText("Are you sure you want to remove this user?");

    doubleCheck.showAndWait().ifPresent(response->{
      if(response == ButtonType.OK){
        if(StuController.deleteUser(selectedUser.netid)){
          loadUsers(type);
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setContentText("User removed successfully");
          alert.show();
        }else{
          Alert alert = new Alert(AlertType.ERROR);
          alert.setContentText("User does not exist");
          alert.show();
        }
      }
    });

  }

  public void showAddUserStage(){
    Stage addUserStage = new Stage();
    this.addUserStage = addUserStage;
    Label welcomeLabel = new Label("Welcome admin");
    Button btnAddUser = new Button("Add User");
    Button btnUpdateUser = new Button("Update User");
    Button btnRemoveUser = new Button("Remove User");
    Button btnBack = new Button("Back");



    ComboBox<String> txtIdentity = new ComboBox<>();
    txtIdentity.setPromptText("Select User Type");
    txtIdentity.getItems().addAll("professor", "student");
    txtIdentity.setEditable(true);
    txtIdentity.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
      loadUsers(newVal);
      btnAddUser.setOnAction(e->{
        showAddUserDialog(newVal);
      });

      btnUpdateUser.setOnAction(e->{
        showUpdateUserDialog(newVal);
      });

      btnRemoveUser.setOnAction(e->{
        showRemoveUserDialog(newVal);
      });
    });

    btnBack.setOnAction(e->{
      addUserStage.close();
      showMainStage();
    });




    VBox layout = new VBox(10, welcomeLabel, txtIdentity, userTable, btnAddUser,btnUpdateUser, btnRemoveUser, btnBack);
    Scene scene = new Scene(layout, 500, 500);
    addUserStage.setTitle("Add User Panel");
    addUserStage.setScene(scene);
    addUserStage.show();
  }

  public static void main(String[] args) {
      launch();
  }
}
