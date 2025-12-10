package util;

import dao.StudentImp;
import model.Formation;
import model.Student;

import java.sql.SQLException;
import java.util.List;

public class main {
    public static void main(String[] args) throws SQLException {
        StudentImp studentImp = new StudentImp();
//        List<Student> listStudent = studentImp.getAll();
//        System.out.println(listStudent.size());// Vérifie qu'il y a des étudiants
//        List<Student> listStudentGINF = studentImp.getByFormation("GINF");
//        for (Student student : listStudentGINF) {
//            System.out.println(student);
//        }
        Formation f = new Formation("TEST","formation test");
        List<Student> listStudenttest = studentImp.getByFormation(f.getId());
        for (Student student : listStudenttest) {
            System.out.println(student);
        }


    }
}
