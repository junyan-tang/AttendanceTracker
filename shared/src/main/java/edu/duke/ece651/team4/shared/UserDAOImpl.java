package edu.duke.ece651.team4.shared;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of UserDAO
 */
public class UserDAOImpl implements UserDAO {
    /**
     * Creates a new user
     * 
     * @param user user information
     */
    public void createUser(User user) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (netid, first_name, last_name," +
                    "password, preferred_name, identity, email) VALUES ('" +
                    user.netid + "', '" + user.firstName + "', '" + user.lastName + "', '" +
                    user.password + "', '" + user.preferredName + "', '" + user.identity + "', '" +
                    user.email + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates user information
     * 
     * @param user new user information
     */
    public void updateUser(User user) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE users SET first_name = '" + user.firstName + "', last_name = '" + user.lastName + 
                         "', preferred_name = '" + user.preferredName + "', identity = '" +
                         user.identity + "', email = '" + user.email + "' WHERE netid = '" + user.netid + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user
     * 
     * @param netid specific netid of that user
     */
    public void deleteUser(String netid) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM users WHERE netid = '" + netid + "'";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a user
     * 
     * @param netid specific netid of that user
     * @return user information
     */
    public User getUser(String netid) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE netid = '" + netid + "'";
            ResultSet res = stmt.executeQuery(sql);
            User user = null;

            if (res.next()) {
                if (res.getString("identity").equals("student")) {
                    user = new Student(res.getString("netid"), res.getString("first_name"), res.getString("last_name"),
                                       res.getString("preferred_name"),
                                       res.getString("password"),
                                       res.getString("identity"), res.getString("email"));
                } else if (res.getString("identity").equals("professor")) {
                    user = new Professor(res.getString("netid"), res.getString("first_name"), res.getString("last_name"),
                                         res.getString("preferred_name"),        
                                         res.getString("password"),
                                         res.getString("identity"), res.getString("email"));
                } else {
                    user = new Admin(res.getString("netid"), res.getString("password"),res.getString("identity"));
                }
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param netid    is the netid of the user
     * @param password is the password of the user
     * @return 0 if the user is a student, 1 if the user is a professor, 2 for
     *         invalid login
     */
    public int userLogin(String netid, String password) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE netid = '" + netid + "' AND password = '" + password + "'";
            ResultSet res = stmt.executeQuery(sql);
            if (res.next() == false) {
                return 2;
            }
            if (res.getString("identity") == "student") {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param netid is the netid of the user
     * @return true if the user is faculty, false if the user is a student
     */
    public boolean isFaculty(String netid) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE netid = '" + netid + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next() == false){
                return false;
            }
            if (res.getString("identity").equals("professor")) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllProfessor() {
        List<User> professors = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE identity = 'professor'";
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                User user = new Professor(res.getString("netid"), res.getString("first_name"), res.getString("last_name"),
                res.getString("preferred_name"), res.getString("password"),
                         res.getString("identity"), res.getString("email"));
                professors.add(user);
            }
            return professors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professors;
    }

    public List<User> getAllStudent() {
        List<User> students = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE identity = 'student'";
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                User user = new Student(res.getString("netid"), res.getString("first_name"), res.getString("last_name"),
                res.getString("preferred_name"), res.getString("password"), res.getString("identity"), res.getString("email"));
                students.add(user);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean isStudent(String netid) {
        try (Connection conn = DatabaseConnectionUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE netid = '" + netid + "'";
            ResultSet res = stmt.executeQuery(sql);
            if (res.next() == false) {
                return false;
            }
            if (res.getString("identity").equals("student")) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        // return true if the user is a student, false if the user is not student(
        // faculty or invalid user)
    }

    public void changeNotificationPreference(String netid, String choice) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql_inquire = "SELECT * FROM notify WHERE netid = '" + netid + "'";
            ResultSet res = stmt.executeQuery(sql_inquire);
            if (res.next() == false) {
                String sql = "INSERT INTO notify (netid, choice) VALUES ('" + netid + "', '" + choice + "')";
                stmt.executeUpdate(sql);
            } else {
                String sql = "UPDATE notify SET choice = '" + choice + "' WHERE netid = '" + netid + "'";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUsernameListByID(List<String> netidList) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        List<String> usernameList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            for (String netid : netidList) {
                String sql = "SELECT * FROM users WHERE netid = '" + netid + "'";
                ResultSet res = stmt.executeQuery(sql);
                if(res.next() == false){
                    continue;
                }
                usernameList.add(res.getString("first_name") + " " + res.getString("last_name"));
            }
            return usernameList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernameList;

    }

    public List<String> getIDListByUsername(List<String> usernameList) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        List<String> netidList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            for (String username : usernameList) {
                String[] name = username.split(" ");
                String sql = "SELECT * FROM users WHERE first_name = '" + name[0] + "' AND last_name = '" + name[1] + "'";
                ResultSet res = stmt.executeQuery(sql);
                if(res.next() == false){
                    continue;
                }
                netidList.add(res.getString("netid"));
            }
            return netidList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return netidList;
    }

    public String getUsrenameByID(String netid) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE netid = '" + netid + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next() == false){
                return null;
            }
            return res.getString("first_name") + " " + res.getString("last_name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getIDByUsername(String username) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String[] name = username.split(" ");
            String sql = "SELECT * FROM users WHERE first_name = '" + name[0] + "' AND last_name = '" + name[1] + "'";
            ResultSet res = stmt.executeQuery(sql);
            if(res.next() == false){
                return null;
            }
            return res.getString("netid");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
