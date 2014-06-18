/*
 * The MIT License
 *
 * Copyright 2014 Geoff Baker <me at bakerg.com>.
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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Geoff Baker <me at bakerg.com>
 */
public class UpdatePaneController implements Initializable {

    @FXML
    private Label versionLabel;

    @FXML
    protected void yesButtonClicked(MouseEvent me) {
        CJamGUI.getUpdateStage().close();
        Updater.installUpdate();
    }

    @FXML
    protected void noButtonClicked(MouseEvent me) {
        CJamGUI.getUpdateStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] versions = Updater.getVersions();
        versionLabel.setText("Local version: " + versions[0] + " Remote version: " + versions[1]);
    }

}
