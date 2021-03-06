/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tika.fork;



import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.ParserFactory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Lightweight, easily serializable class that contains enough information
 * to build a {@link ParserFactory}
 */
public class ParserFactoryFactory implements Serializable {

    /** Serial version UID */
    private static final long serialVersionUID = 4710974869988895410L;

    private final String className;
    private final Map<String, String> args;

    public ParserFactoryFactory(String className, Map<String, String> args) {
        this.className = className;
        this.args = args;
    }

    public ParserFactory build() throws TikaException {
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> con = clazz.getConstructor(Map.class);
            return (ParserFactory) con.newInstance(args);
        } catch (ReflectiveOperationException|IllegalStateException e) {
            throw new TikaException("Couldn't create factory", e);
        }
    }

}
