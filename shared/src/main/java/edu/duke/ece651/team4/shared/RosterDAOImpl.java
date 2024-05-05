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
            String sql = "INSERT INTO roster (student_id, section_id) VALUES ('" + studentID + "', '" + sectionID + "')";
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            // e.printStackTrace();
            throw new IllegalArgumentException(e);
        } 
    }
    public boolean isStudentInSection(String studentID, String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM roster WHERE student_id = '" + studentID + "' AND section_id = '" + sectionID + "'";
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
            String sql = "DELETE FROM roster WHERE student_id = '" + studentID + "' AND section_id = '" + sectionID + "'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<String> getSectionsOfStudent(String studentID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            List<String> sectionList = new ArrayList<String>();
            String sql = "SELECT section_id FROM roster WHERE student_id = '" + studentID + "'";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                sectionList.add(res.getString("section_id"));
            }
            return sectionList;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getStudentsInSection(String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            List<String> studentList = new ArrayList<String>();
            String sql = "SELECT student_id FROM roster WHERE section_id = '" + sectionID + "'";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                studentList.add(res.getString("student_id"));
            }
            return studentList;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
