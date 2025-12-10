package controller;

import dao.StudentImp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Formation;
import model.Student;

import java.sql.SQLException;

public class AddFormController {

    @FXML
    private Button ConfirmerAjoutStudentForm;

    @FXML
    private TextField FielID;

    @FXML
    private TextField FieldNOM;

    @FXML
    private TextField fieldMOYENNE;

    private Formation formation;

    public void setFormation(Formation f) {
        this.formation = f;
    }
    StudentImp studentService = new StudentImp();

    @FXML
    public void initialize() {
        ConfirmerAjoutStudentForm.setOnAction(event -> ajouterStudent());
    }

    @FXML
    private void ajouterStudent() {
        try {
            String id = FielID.getText();
            String name = FieldNOM.getText();
            double moyenne = Double.parseDouble(fieldMOYENNE.getText());

            if (moyenne < 0 || moyenne > 20) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("La moyenne doit être comprise entre 0 et 20");
                alert.showAndWait();
                return;
            }
            Student student = new Student(id,name,moyenne,formation);
            studentService.create(student); // insertion en DB
            Stage stage = (Stage) ConfirmerAjoutStudentForm.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La moyenne doit être un nombre valide !");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
