package edu.duke.ece651.team4.shared;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class SectionDAOImpl implements SectionDAO{
    public void addSection(Section section){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO SECTION (section_id, course_id, teacher_id) VALUES ('" + 
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
            String sql = "SELECT * FROM SECTION WHERE section_id = '" + sectionID+ "'";
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                Section section = new Section(res.getString("section_id"), res.getString("course_id"), res.getString("teacher_id"));
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
            String sql = "DELETE FROM SECTION WHERE section_id = '" + sectionID + "'";
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Section> getAllSections() {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION";
            ResultSet res = stmt.executeQuery(sql);

            List<Section> sections = new ArrayList<Section>();
            while(res.next()){
                sections.add(new Section(res.getString("section_id"), res.getString("course_id"), res.getString("teacher_id")));
            }
            return sections;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Section> getAllSectionsCour(String courseID) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION WHERE course_id = '" + courseID + "'";
            ResultSet res = stmt.executeQuery(sql);

            List<Section> sections = new ArrayList<Section>();
            while(res.next()){
                sections.add(new Section(res.getString("section_id"), res.getString("course_id"), res.getString("teacher_id")));
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
            String sql = "SELECT * FROM SECTION WHERE teacher_id = '" + teacherID + "'";
            ResultSet res = stmt.executeQuery(sql);

            List<Section> sections = new ArrayList<Section>();
            while(res.next()){
                sections.add(new Section(res.getString("section_id"), res.getString("course_id"), res.getString("teacher_id")));
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
            String sql = "UPDATE SECTION SET course_id = '" + section.courseID + "', teacher_id = '" + section.teacherID + "' WHERE section_id = '" + section.sectionID + "'";
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
            String sql = "SELECT * FROM SECTION WHERE teacher_id = '" + teacherID + "'";
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
            if (conn.isClosed()){
                System.out.println("Connection is closed");
            }
            else{
                System.out.println("Connection is open");
            }
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ROSTER, users WHERE ROSTER.student_id = users.netid AND ROSTER.section_id = '" + sectionID + "'";
            ResultSet res = stmt.executeQuery(sql);

            List<Student> students = new ArrayList<Student>();
            while(res.next()){
                students.add(new Student(res.getString("student_id"), res.getString("first_name"), res.getString("last_name"), res.getString("password"), res.getString("preferred_name"), res.getString("identity"), res.getString("email")));
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
            String sql = "SELECT * FROM SECTION WHERE section_id = '" + sectionID + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(!res.next()){
                return false;
            }            
            if(!res.getString("teacher_id").equals("null")){
                System.out.println("teacher_id"+res.getString("teacher_id"));
                return false;
            }
            else{
                String update_sql = "Update SECTION SET teacher_id = '" + teacherID + "' WHERE section_id = '" + sectionID + "'";
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
            String sql = "SELECT * FROM SECTION WHERE teacher_id = '" + teacherID + "' AND section_id = '" + sectionID + "'";
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

    public Professor getProfessor(String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM SECTION, users WHERE section_id = '" + sectionID + "' AND users.netid = SECTION.teacher_id";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()){
                return new Professor(res.getString("teacher_id"), res.getString("first_name"), res.getString("last_name"), res.getString("preferred_name"), res.getString("password"),res.getString("identity"), res.getString("email"));
            }
            return null;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public String generateWeekReport(String sectionID) {
        LocalDate today = LocalDate.now();
        if (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
            return "This function should be called on Sunday.";
        }
        LocalDate monday = today.minusDays(6);
        LocalDate friday = today.minusDays(2);
        StringBuilder report = new StringBuilder("");
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE section_id = '" + sectionID + "' AND date BETWEEN '" + monday + "' AND '" + friday + "' ORDER BY date ASC";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()) {
                String courseId = res.getString("course_id");
                String sectionId = res.getString("section_id");
                report.append("course_id: " + courseId + "      ");
                report.append("section_id: " + sectionId + "\n");
            }
            while (res.next()) {
                String studentId = res.getString("student_id");
                String inquire_name = "SELECT * FROM users WHERE netid = '" + studentId + "'";
                ResultSet res_name = stmt.executeQuery(inquire_name);
                if(res.next()){
                    String firstName = res_name.getString("first_name");
                    String lastName = res_name.getString("last_name");
                    report.append(firstName + "    " + lastName + "\n");
                }
                String status = res.getString("status");
                double score;
                if (status == "PRESENT") {
                    score = 1;
                }
                else if (status == "TARDY") {
                    score = 0.8;
                }
                else{
                    score = 0;
                }
                report.append("studentID: " + studentId + "      ");
                report.append("status: " + status + "     ");
                report.append("score: " + score + "\n");
            }
            return report.toString();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
 }
