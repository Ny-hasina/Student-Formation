package dao;

import model.Formation;
import util.DBConnection;

import javax.xml.transform.Source;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class FormationImp implements CRUD<Formation,String> {


    @Override
    public void create(Formation formation) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "insert into formation values(?,?)";
        //PreparedStatement ps = null;
        try {
            PreparedStatement ps = Myconnection.prepareStatement(req);
            ps.setString(1, formation.getId());
            ps.setString(2, formation.getName());
            ps.executeUpdate();
            System.out.println("Added with success");
        } catch (Exception e) {
            System.out.println("Failed to create new formation");
            throw e;
        }
    }

    @Override
    public void update(Formation formation) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "update formation set name=? where id=?";
        //PreparedStatement ps = null;
        try {
            PreparedStatement ps = Myconnection.prepareStatement(req);
            ps.setString(1, formation.getName());
            ps.setString(2, formation.getId());
            ps.executeUpdate();
            System.out.println("Updated with success");
        } catch (Exception e) {
            System.out.println("Update failed");
            throw e;
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "delete from formation where id=?";
        //PreparedStatement ps = null;
        try {
            PreparedStatement ps = Myconnection.prepareStatement(req);
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("Deleted with success");
        } catch (Exception e) {
            System.out.println("Delete failed");
            throw e;
        }
    }

    @Override
    public List<Formation> getAll() throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "select * from formation";
        //PreparedStatement ps = null;
        ResultSet rs = null;
        List<Formation> formations = null;
        try {
            PreparedStatement ps = Myconnection.prepareStatement(req);
            rs = ps.executeQuery();

            formations = new ArrayList<>();
            while (rs.next()) {
                Formation formation = new Formation(rs.getString(1), rs.getString(2));
                formations.add(formation);
            }
            System.out.println("All formations retrieved successfully");
        } catch (Exception e) {
            System.out.println("Error retrieving formations");
            throw e;
        }
        return formations;
    }

    @Override
    public Optional<Formation> get(String id) throws SQLException {
        Connection Myconnection = DBConnection.getInstance().getConnection();
        String req = "select * from formation where id=?";
        //PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            PreparedStatement ps = Myconnection.prepareStatement(req);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Formation found");
                Formation formation = new Formation(rs.getString(1), rs.getString(2));
                return Optional.of(formation);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving formation");
            throw e;
        }
        return Optional.empty();
    }
}
