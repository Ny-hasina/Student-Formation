package dao;

import model.Formation;
import model.Student;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentImp implements CRUD<Student,String> {

    @Override
    public void create(Student student) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "insert into student values(?,?,?,?)";
        try {
            PreparedStatement statement = Myconnection.prepareStatement(req);
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            if (student.getMoyenne() < 0 || student.getMoyenne() > 20)
                throw new SQLException("Moyenne doit être comprise entre 0 et 20");
            statement.setDouble(3, student.getMoyenne());
            statement.setString(4, student.getFormation().getId());
            statement.execute();
            System.out.println("Etudiant créé");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création d'un étudiant");
            throw e;
        }

    }

    @Override
    public void update(Student student) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "update student set name=?,moyenne=?,formation_id=? where id=?";
        try {
            PreparedStatement statement = Myconnection.prepareStatement(req);
            statement.setString(1, student.getName());
            if (student.getMoyenne() < 0 || student.getMoyenne() > 20)
                throw new SQLException("Moyenne doit être comprise entre 0 et 20");
            statement.setDouble(2, student.getMoyenne());
            statement.setString(3, student.getFormation().getId());
            statement.setString(4, student.getId());
            statement.execute();
            System.out.println("Etudiant mis à jour");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour");
            throw e;
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "delete from student where id=?";
        try {
            PreparedStatement statement = Myconnection.prepareStatement(req);
            statement.setString(1, id);
            statement.execute();
            System.out.println("Supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppresion");
            throw e;
        }
    }

    @Override
    public List<Student> getAll() throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "select * from student";
        List<Student> students = new ArrayList<>();
        FormationImp formationImple = new FormationImp();

        try {
            PreparedStatement statement = Myconnection.prepareStatement(req);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Optional<Formation> formation = formationImple.get(rs.getString(4));
                Formation formation2 = formation.orElse(null);
                Student etudiant = new Student(rs.getString(1), rs.getString(2), rs.getDouble(3), formation2);
                students.add(etudiant);
            }
            System.out.println("Succès");
        } catch (SQLException e) {
            System.out.println("Erreur");
            throw e;
        }
        return students;
    }

    @Override
    public Optional<Student> get(String id) throws SQLException {
        //not now
        return null;
    }

    public List<Student> getByFormation(String formationId) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "select * from student where formation_id=?";
        List<Student> studentsPerFormation = new ArrayList<>();
        FormationImp formationImple = new FormationImp();
        Optional<Formation> f = formationImple.get(formationId);
        Formation formation = f.orElse(null);
        try {
            PreparedStatement statement = Myconnection.prepareStatement(req);
            statement.setString(1, formationId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString(1), rs.getString(2), rs.getDouble(3), formation);
                studentsPerFormation.add(student);
            }
            System.out.println("Affichage réussi getbyformation");
        } catch (SQLException e) {
            System.out.println("Erreur getByFormation");
            throw e;
        }
        return studentsPerFormation;
    }

    public Double MoyenneFormation(String formationId) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "select avg(moyenne) from student where formation_id=?";
        try {
            PreparedStatement statement = Myconnection.prepareStatement(req);
            statement.setString(1, formationId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Double avg = rs.getDouble(1);
                if (rs.wasNull()) {
                    return null;
                }
                return avg;
            }
        }catch (SQLException e){
            System.out.println("Erreur");
            throw e;
        }
        return null;

    }
}