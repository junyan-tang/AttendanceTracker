package edu.duke.ece651.team4.shared;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class SectionDAOImpl implements SectionDAO{
    public void addSection(Section section){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO SECTION (sectionID, courseID, teacherID) VALUES ('" + 
                         section.sectionID + "','" + section.courseID + "','" + section.teacherID + "')";

            stmt.executeUpdate(sql);    
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
    public Section getSection(String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE sectionID = '" + sectionID+ "'";
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                Section section = new Section(res.getString("sectionID"), res.getString("courseID"), res.getString("teacherID"));
                // public Section(String sectionID, String courseID, String teacherID
                return section;
            }
           
           
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void removeSection(String sectionID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM SECTION WHERE sectionID = '" + sectionID + "'";
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Section> getAllSectionsCour(String courseID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE courseID = '" + courseID + "'";
            ResultSet res = stmt.executeQuery(sql);

            List<Section> sections = new ArrayList<Section>();
            while(res.next()){
                sections.add(new Section(res.getString("sectionID"), res.getString("courseID"), res.getString("teacherID")));
            }
            return sections;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Section> getAllSectionsTea(String teacherID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE teacherID = '" + teacherID + "'";
            ResultSet res = stmt.executeQuery(sql);

            List<Section> sections = new ArrayList<Section>();
            while(res.next()){
                sections.add(new Section(res.getString("sectionID"), res.getString("courseID"), res.getString("teacherID")));
            }
            return sections;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateSection(Section section) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE SECTION SET courseID = '" + section.courseID + "', teacherID = '" + section.teacherID + "' WHERE sectionID = '" + section.sectionID + "'";
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Whether teacher has section
     * @param teacher's ID
     * @return True if teacher has section, false otherwise
     */
    public boolean isTeacherHasSection(String teacherID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE teacherID = '" + teacherID + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()){
                return true;
            }
            return false;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Find all students in a section
     * @param sectionID Section ID
     * @return List of students in the section
     */
    public List<Student> getAllStudentsInSection(String sectionID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ROSTER, USER_ WHERE ROSTER.studentID = USER_.netid AND ROSTER.sectionID = '" + sectionID + "'";
            ResultSet res = stmt.executeQuery(sql);

            List<Student> students = new ArrayList<Student>();
            while(res.next()){
                students.add(new Student(res.getString("studentID"), res.getString("firstName"), res.getString("lastName"), res.getString("password"), res.getString("preferredName"), res.getString("identity"), res.getString("email")));
            }
            return students;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Teacher select a section
     * @param teacherID Teacher's ID
     * @param sectionID Section ID
     * @return True if teacher select the section successfully, false otherwise
     */
    public boolean teacherSelectSection(String teacherID, String sectionID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE sectionID = '" + sectionID + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(res.getString("teacherID") != null){
                return false;
            }
            else{
                String update_sql = "Update SECTION SET teacherID = '" + teacherID + "' WHERE sectionID = '" + sectionID + "'";
                stmt.executeUpdate(update_sql);
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTeacherHasThatSection(String teacherID, String sectionID){
        try(Connection conn = DatabaseConnectionUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE teacherID = '" + teacherID + "' AND sectionID = '" + sectionID + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()){
                return true;
            }
            return false;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        //return false if teacher does not have that section
    }
 }
