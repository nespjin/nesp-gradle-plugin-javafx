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

import com.nesp.gradle.plugin.javafx.fxml.BaseControllerClass;
import com.nesp.gradle.plugin.javafx.fxml.BaseControllerFXMLParser;
import com.nesp.gradle.plugin.javafx.fxml.FXMLParseException;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 22:17
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
class BaseControllerFXMLParserTest {

    @Test
    void parse() throws FXMLParseException {
        final BaseControllerFXMLParser baseControllerFXMLParser = new BaseControllerFXMLParser();
         baseControllerFXMLParser.parse(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml_test.fxml")));
        final BaseControllerClass baseControllerClass = baseControllerFXMLParser.getBaseControllerClass();
  /*      System.out.println(new Gson().toJson(baseControllerClass));
        System.out.println(new Gson().toJson(baseControllerFXMLParser.getClassesInfo()));
        System.out.println(baseControllerFXMLParser.getClasses());*/
    }

    @Test
    void testParse() {
    }
}