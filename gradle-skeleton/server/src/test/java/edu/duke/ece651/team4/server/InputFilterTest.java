package edu.duke.ece651.team4.server;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class InputFilterTest {
  @Test
  public void test_readFile() throws IOException {
    InputStream is = InputFilter.class.getResourceAsStream("/Roster.csv");
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(is));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputFilter ft = new InputFilter(inputReader, out);
    List<List<String>> rec = ft.readCSVFile(); 
  }
}
