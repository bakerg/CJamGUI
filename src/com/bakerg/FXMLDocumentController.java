/*
 * The MIT License
 *
 * Copyright 2014 Geoff Baker.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.bakerg;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Geoff
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView list;
    @FXML
    private TextArea codeTextArea;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea outputTextArea;
    private PrintStream ps;
    public static Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Console console = new Console(outputTextArea);
        ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setIn(new Input(inputTextArea));
        list.setItems(FXCollections.observableList(readOperators()));
    }

    @FXML
    private void runProgram(MouseEvent me) {
        outputTextArea.clear();
        CJamThread cj = new CJamThread(codeTextArea.getText());
        cj.start();
    }

    @FXML
    private void showCJamWiki(ActionEvent ae) {
        URI wiki;
        try {
            wiki = new URI("http://sourceforge.net/p/cjam/wiki/Home/");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(wiki);
            }
        } catch (URISyntaxException | IOException ex) {
        }

    }

    @FXML
    private void openFile(ActionEvent ae) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(CJamGUI.getStage());
        codeTextArea.setText(readFile(f.getAbsolutePath()));
    }

    @FXML
    private void saveFile(ActionEvent ae) {
        FileChooser fc = new FileChooser();
        File f = fc.showSaveDialog(CJamGUI.getStage());
        writeFile(codeTextArea.getText(), f.getAbsolutePath());
    }

    @FXML
    private void showAbout(ActionEvent ae) {
        try {
            Parent root = FXMLLoader.load(FXMLDocumentController.class.getResource("AboutPane.fxml"));
            Scene about = new Scene(root);
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(about);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> readOperators() {
        String input = "";
        Scanner in;
        in = new Scanner(CJamGUI.class.getResourceAsStream("operators.txt"));
        while (in.hasNext()) {
            input += in.nextLine() + "\n";
        }
        in.close();
        List<String> l = new ArrayList();
        l.addAll(Arrays.asList(input.split("\n")));
        return l;
    }

    private void writeFile(String output, String filepath) {
        try {
            FileWriter fstream = new FileWriter(filepath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(output);
            out.close();
        } catch (IOException e) {
        }
    }

    private String readFile(String filepath) {
        String input = "";
        try {
            Scanner in;
            in = new Scanner(new File(filepath));
            while (in.hasNext()) {
                input += in.nextLine() + "\n";
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }
}
