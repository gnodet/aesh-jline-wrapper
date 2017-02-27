/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.wrapper.reader;

import org.aesh.parser.LineParser;
import org.jline.reader.ParsedLine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class ParsedLineImpl implements ParsedLine {


    private final org.aesh.parser.ParsedLine parsedLine;

    public ParsedLineImpl(String line, int cursor) {
        org.aesh.parser.ParsedLine parsedLine = LineParser.parseLine(line, cursor);
        this.parsedLine = parsedLine;
    }

    @Override
    public String word() {
        return parsedLine.selectedWord().word();
    }

    @Override
    public int wordCursor() {
        return parsedLine.wordCursor();
    }

    @Override
    public int wordIndex() {
        return parsedLine.selectedIndex();
    }

    @Override
    public List<String> words() {
        List<String> w = new ArrayList<>(parsedLine.words().size());
        for(org.aesh.parser.ParsedWord word : parsedLine.words())
            w.add(word.word());
        return w;
    }

    @Override
    public String line() {
        return parsedLine.line();
    }

    @Override
    public int cursor() {
        return parsedLine.cursor();
    }
}
