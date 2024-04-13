package edu.duke.ece651.team4.shared;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class PermissionDAOImpl implements PermissionDAO {
  public boolean checkTableExist() {
    Connection conn = DatabaseConnectionUtil.getConnection();
    try{
      String sql = "SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'permission'";
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery(sql);
      if (res.next() == false){
        return false;
      }
      return true;
    } catch(SQLException e){
      return false;
    }
  }

  public void createTable(String status) {
    Connection conn = DatabaseConnectionUtil.getConnection();
    try{
      Statement stmt = conn.createStatement();
      String sql = "CREATE TABLE permission (status VARCHAR(255))";
      stmt.executeUpdate(sql);
      sql = "INSERT INTO permission (status) VALUES ('" + status + "')";
      stmt.executeUpdate(sql);
    } catch(SQLException e){
      e.printStackTrace();
    }
  }

  public String getPermissionStatus() {
    Connection conn = DatabaseConnectionUtil.getConnection();
    try{
      String sql = "SELECT * FROM permission";
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery(sql);
      if (res.next() == false){
        return null;
      }
      return res.getString("status");
    } catch(SQLException e){
      e.printStackTrace();
    }
    return null;
  }
}
