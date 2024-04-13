package edu.duke.ece651.team4.shared;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RosterDAOImpl implements RosterDAO{
    public void addStudentToSection(String studentID, String sectionID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try  {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO roster (studentID, sectionID) VALUES ('" + studentID + "', '" + sectionID + "')";
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        } 
    }
    public boolean isStudentInSection(String studentID, String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM roster WHERE studentID = '" + studentID + "' AND sectionID = '" + sectionID + "'";
            Statement stmt = conn.createStatement();
            if(stmt.executeQuery(sql).next()){
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void removeStudentFromSection(String studentID, String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM roster WHERE studentID = '" + studentID + "' AND sectionID = '" + sectionID + "'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<String> getStudentsInSection(String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            List<String> studentList = new ArrayList<String>();
            String sql = "SELECT studentID FROM roster WHERE sectionID = '" + sectionID + "'";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                studentList.add(res.getString("studentID"));
            }
            return studentList;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
