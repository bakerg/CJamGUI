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

import java.io.IOException;
import java.io.InputStream;
import javafx.scene.control.TextArea;

/**
 *
 * @author Geoff
 */
public class Input extends InputStream {

    private final TextArea input;
    int currentPos = 0;

    public Input(TextArea ta) {
        this.input = ta;
    }

    @Override
    public int read() throws IOException {
        int returnVal = -1;
        byte[] bytes = input.getText().getBytes();
        int length = bytes.length;
        if (currentPos < length) {
            returnVal = bytes[currentPos];
        }
        currentPos++;
        return returnVal;
    }

}
