/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nesp.gradle.plugin.javafx;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 22:39
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public class FXMLParser {

    private static final String TAG = "FXMLParser";

    public static final String FX_NAMESPACE_PREFIX = "fx";
    public static final String FX_CONTROLLER_ATTRIBUTE = "controller";
    public static final String FX_ID_ATTRIBUTE = "id";
    public static final String FX_VALUE_ATTRIBUTE = "value";
    public static final String ROOT_TAG = "root";
    public static final String CONTROLLER_KEYWORD = "controller";
    public static final String CONTROLLER_SUFFIX = "Controller";
    public static final String INITIALIZE_METHOD_NAME = "initialize";

    protected XMLStreamReader mXMLStreamReader = null;
    protected File mFXMLFile;

    public void parse(File fxmlFile) throws ParseException {
        this.mFXMLFile = fxmlFile;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fxmlFile);
            parse(inputStream);
        } catch (FileNotFoundException e) {
            throw constructParseException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void parse(URL url) throws ParseException {
        try {
            this.mFXMLFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw constructParseException(e);
        }
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            parse(inputStream);
        } catch (IOException e) {
            throw constructParseException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void parse(InputStream inputStream) throws ParseException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream is null");
        }

        try {
            final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty("javax.xml.stream.isCoalescing", true);

            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            mXMLStreamReader = new StreamReaderDelegate(xmlInputFactory.createXMLStreamReader(inputStreamReader)) {
                @Override
                public String getPrefix() {
                    String prefix = super.getPrefix();
                    if (prefix != null
                            && prefix.length() == 0) {
                        prefix = null;
                    }
                    return prefix;
                }

                @Override
                public String getAttributePrefix(final int index) {
                    String attributePrefix = super.getAttributePrefix(index);
                    if (attributePrefix != null
                            && attributePrefix.length() == 0) {
                        attributePrefix = null;
                    }
                    return attributePrefix;
                }
            };
        } catch (XMLStreamException e) {
            throw constructParseException(e);
        }

        // Parse the XML stream
        try {
            onPreParse();
            while (mXMLStreamReader.hasNext()) {
                final int event = mXMLStreamReader.next();
                switch (event) {
                    case XMLStreamConstants.PROCESSING_INSTRUCTION -> processProcessingInstruction();
                    case XMLStreamConstants.COMMENT -> processComment();
                    case XMLStreamConstants.START_ELEMENT -> processStartElement();
                    case XMLStreamConstants.END_ELEMENT -> processEndElement();
                    case XMLStreamConstants.CHARACTERS -> processCharacters();
                }
            }
            onPostParse();
        } catch (XMLStreamException e) {
            throw constructParseException(e);
        }
    }

    protected void onPreParse() {

    }

    protected void onPostParse() {

    }

    private void processProcessingInstruction() {
        /* do nothing */
    }

    protected void processComment() {
        /* do nothing */
    }

    protected void processStartElement() {

    }

    protected void processEndElement() {
        /* do nothing */
    }

    protected void processCharacters() {
        /* do nothing */
    }

    public File getFXMLFile() {
        return mFXMLFile;
    }

    private ParseException constructParseException(Throwable cause) {
        return new ParseException(constructFXMLTrace(), cause);
    }

    private String constructFXMLTrace() {
        return "\n" + (mFXMLFile != null ? mFXMLFile.getPath() : "unknown path") +
                ":" +
                mXMLStreamReader.getLocation().getLineNumber();
    }
}
