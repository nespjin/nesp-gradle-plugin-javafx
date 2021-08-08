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

package com.nesp.gradle.plugin.javafx.fxml;

import com.nesp.gradle.plugin.javafx.ParseException;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/8/4 20:22
 * Project: nesp-gradle-plugin-javafx
 * Description:
 **/
public class FXMLParseException extends ParseException {

    /**
     * {@inheritDoc}
     */
    public FXMLParseException() {
        super();
    }

    /**
     * @param message {@inheritDoc}
     */
    public FXMLParseException(String message) {
        super(message);
    }

    /**
     * @param cause {@inheritDoc}
     */
    public FXMLParseException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message {@inheritDoc}
     * @param cause   {@inheritDoc}
     */
    public FXMLParseException(String message, Throwable cause) {
        super(message, cause);
    }
}