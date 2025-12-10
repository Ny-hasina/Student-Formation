package controller;

import dao.FormationImp;
import dao.StudentImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Formation;
import model.Student;

import java.sql.SQLException;
import java.util.List;

public class HelloController {

    @FXML
    private Button AddStudent;

    @FXML
    private ComboBox<Formation> ComboFormation;

    @FXML
    private Label LabelMoyenne;

    @FXML
    private TableView<Student> TableViewStudent;

    @FXML
    private TableColumn<Student, Double> MoyenneStudent;

    @FXML
    private TableColumn<Student, String> NomStudent;

    @FXML
    private TableColumn<Student, String> idStudent;

    @FXML
    private Button UpdateStudent;

    @FXML
    private Button DeleteStudent;

    @FXML
    private TextField IDsuppr;

    Formation ftest=new Formation("Test","TEST");
    private final FormationImp formationService = new FormationImp();
    private final StudentImp studentService = new StudentImp();
    ObservableList<Student> ObListStudent= FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        idStudent.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomStudent.setCellValueFactory(new PropertyValueFactory<>("name"));
        MoyenneStudent.setCellValueFactory(new PropertyValueFactory<>("moyenne"));
        ListerFormation();
        ListerEtudiants();
        TableViewStudent.setItems(ObListStudent);
        ComboFormation.setOnAction(event -> {
            filtrerParFormation();
            AfficherMoyenneFormation();
        });

        AddStudent.setOnAction(event -> openAddStudentForm());
        DeleteStudent.setOnAction(event -> supprimerStudent());

    }

    public void ListerFormation() {
        try {
            List<Formation> listFormation = formationService.getAll();
            ComboFormation.getItems().clear();
            ComboFormation.getItems().addAll(listFormation);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void ListerEtudiants() {
        try {
            List<Student> listStudent = studentService.getAll();
            ObListStudent.clear();
            ObListStudent.addAll(listStudent);
            TableViewStudent.setItems(ObListStudent);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void filtrerParFormation() {
        Formation formationSelectionnee = ComboFormation.getValue();
        System.out.println("FormationSelectionnee: " + formationSelectionnee.getId());
        if (formationSelectionnee == null) return;
        ObListStudent.clear();
        try {
            List<Student> listStudent = studentService.getByFormation(formationSelectionnee.getId());
            ObListStudent.clear();
            ObListStudent.addAll(listStudent);
            TableViewStudent.setItems(ObListStudent);
        } catch (SQLException e) {
            System.out.println();;
        }
    }

    @FXML
    private void openAddStudentForm() {
        Formation formationSelectionnee = ComboFormation.getValue();
        if (formationSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune formation sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une formation avant d'ajouter un étudiant.");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/_122025/AddForm-view.fxml"));
            Parent root = loader.load();
            // Récupérer le contrôleur du formulaire pour passer la formation
            AddFormController controller = loader.getController();
            controller.setFormation(formationSelectionnee);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un étudiant");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            filtrerParFormation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void AfficherMoyenneFormation() {
        Double moyenneformation;
        Formation formationSelectionnee = ComboFormation.getValue();
        System.out.println("FormationSelectionnee: " + formationSelectionnee.getId());
        if (formationSelectionnee == null) return;
        try {
           moyenneformation = studentService.MoyenneFormation(formationSelectionnee.getId());
            if (moyenneformation == null) {
                LabelMoyenne.setText("--");
            } else {
                LabelMoyenne.setText(String.format("%.2f", moyenneformation));
            }
        } catch (SQLException e) {
            System.out.println();;
        }
    }

    public void supprimerStudent() {
        try {
            String id =IDsuppr.getText();
            studentService.delete(id);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }




}
