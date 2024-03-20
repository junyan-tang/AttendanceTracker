package edu.duke.ece651.team4.server;

/**
 * This is the interface for the Course class
 * It has the following methods:
 * modifyName: modify student name of the course
 * notifyAll: notify all participants of the course
 * generateReport: generate report for the course
 */
public interface Course {
  /**
   * Modify student name of the course
   * @param key: the key of the student
   * @param val: the new name of the student
   * @return: true if the name is modified successfully, false otherwise
   */
  Boolean modifyName(String oldName, String newName);

  /**
   * Notify all participants of the course
   * @param message: the message to be sent
   */
  void notifyAll(String message);

  /**
   * Generate report for the course
   * @return: the report of the course
   */
  String generateReport();
}
