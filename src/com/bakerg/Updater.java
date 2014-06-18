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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geoff Baker <me at bakerg.com>
 */
public class Updater {

    final static String version = "1.1.0";
    static String latestVersion;

    public static boolean checkForUpdate() {
        boolean updateAvailable = false;
        try {
            URL latestVersionFile = new URL("http://bakerg.com/cjam/latest/version.txt");
            Scanner in = new Scanner(latestVersionFile.openStream());
            latestVersion = in.next("[0-9]\\.[0-9]\\.[0-9]");
            if (!latestVersion.equals(version)) {
                updateAvailable = true;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateAvailable;
    }

    public static void installUpdate() {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            URL programPath = Updater.class.getProtectionDomain().getCodeSource().getLocation();
            URL latestJar = new URL("http://bakerg.com/cjam/latest/CJamGUI.jar");
            URLConnection connection = latestJar.openConnection();
            InputStream input = connection.getInputStream();
            bis = new BufferedInputStream(input);
            fos = new FileOutputStream(programPath.getPath().substring(0, programPath.getPath().length() - 4) + "latest.jar");
            int bytesWritten = 0, bytesRead = 0;
            byte[] byteBuffer = new byte[1024];
            while ((bytesRead = bis.read(byteBuffer)) != -1) {
                fos.write(byteBuffer);
                bytesWritten += bytesRead;
            }
            System.out.println("Latest CJam GUI successfully downloaded to " + programPath.getPath().substring(0, programPath.getPath().length() - 4) + "latest.jar Size:" + bytesWritten + " bytes");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
                bis.close();
            } catch (IOException ex) {
                Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String[] getVersions() {
        return new String[]{version, latestVersion};
    }
}
