package edu.duke.ece651.team4.JavafxInterf;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;

public class InputCourseFilter {
  // private final BufferedReader fileReader;
  // private final PrintStream outStream;
  // private final BufferedReader inStream;

  // public InputFilter(PrintStream outStream, BufferedReader inStream) {
  //   // this.fileReader = fileReader;
  //   this.outStream = outStream;
  //   this.inStream = inStream;
  // }

  public static List<List<String>> readCSVFile(String path) throws IOException {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
      List<List<String>> records = new ArrayList<>();
      try {
        String line;
        while ((line = fileReader.readLine()) != null) {
          String[] values = line.split(",");
          records.add(Arrays.asList(values));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return records;
    } catch (EOFException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void insertIntoDB(List<List<String>> records, PrintStream outStream) {
    for (List<String> record : records) {
      if (record.size() == 2) {
        if (!CourseController.addCourse(record.get(0), record.get(1))){
          outStream.println("Course already exists in DB: "+record.get(0)+", "+record.get(1));
        }else{
          outStream.println("Course added to DB: "+record.get(0)+", "+record.get(1));
        };
      } 
      else if (record.size() == 3) {
        if(!SectionController.addSection(record.get(0), record.get(1), record.get(2))){
          outStream.println("Section already exists in DB or courseID doesn't exist: "+record.get(0)+", "+record.get(1)+", "+record.get(2));
        }else{
          outStream.println("Section added to DB: "+record.get(0)+", "+record.get(1)+", "+record.get(2));
        };
      } 
      // else if (record.size() == 4) {
      //   StudentController.addStudent(record.get(0), record.get(1), record.get(2), record.get(3));
      // } 
      else {
        outStream.println("Invalid record"+record.toString());
      }
    }
  }

  public static String insertIntoDBCourse(List<List<String>> records, String type) {
    String result = "";
    if(type.equals("course")){
    for (List<String> record : records) {
      if (record.size() == 2) {
        if (!CourseController.addCourse(record.get(0), record.get(1))){
          result+="Course already exists in DB: "+record.get(0)+", "+record.get(1)+"\n";
        }else{
          result+="Course added to DB: "+record.get(0)+", "+record.get(1)+"\n";
        };
      } else {
        result+="Invalid record"+record.toString()+"\n";
      }
    }
  }else if(type.equals("section")){
    for (List<String> record : records) {
      if (record.size() == 3) {
        if(!SectionController.addSection(record.get(0), record.get(1), record.get(2))){
          result+="Section already exists in DB or courseID doesn't exist: "+record.get(0)+", "+record.get(1)+", "+record.get(2)+"\n";
        }else{
          result+="Section added to DB: "+record.get(0)+", "+record.get(1)+", "+record.get(2)+"\n";
        };
      } else {
        result+="Invalid record"+record.toString()+"\n";
      }
    }
  }else if(type.equals("roaster")){
    for (List<String> record : records) {
      if (record.size() == 2) {
      try{
        if (!StuController.addStudentToSection(record.get(0), record.get(1))){
          result+="Student already exists in roaster: "+record.get(0)+", "+record.get(1)+"\n";
        }else{
          result+="Student added to roaster: "+record.get(0)+", "+record.get(1)+"\n";
        };
      }catch(Exception e){
        result+="Invalid record"+record.toString()+" sectionID or studentID doesn't exist in DB"+"\n";
      }
      }else{
        result+="Invalid record"+record.toString()+"\n";
      }
    } 
  }
  return result;
  }

  public static void insertIntoDBRoaster(List<List<String>> records, PrintStream outStream){
    for (List<String> record : records) {
      if (record.size() == 2) {
      try{
        if (!StuController.addStudentToSection(record.get(0), record.get(1))){
          outStream.println("Student already exists in roaster: "+record.get(0)+", "+record.get(1));
        }else{
          outStream.println("Student added to roaster: "+record.get(0)+", "+record.get(1));
        };
      }catch(Exception e){
        outStream.println("Invalid record"+record.toString()+" sectionID doesn't exist in DB");
      }
      }else{
        outStream.println("Invalid record"+record.toString());
      }
    } 

  }

}
