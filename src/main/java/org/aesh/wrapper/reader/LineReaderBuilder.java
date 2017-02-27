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

import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.history.InMemoryHistory;
import org.aesh.tty.terminal.TerminalConnection;
import org.aesh.wrapper.terminal.TerminalBuilder;
import org.aesh.wrapper.terminal.TerminalImpl;
import org.jline.reader.Completer;
import org.jline.reader.Expander;
import org.jline.reader.Highlighter;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class LineReaderBuilder {

    private Terminal terminal;
    private String appName;
    private Map<String, Object> variables;
    private History history;
    private Completer completer;
    private History memoryHistory;
    private Highlighter highlighter;
    private Parser parser;
    private Expander expander;

    public static LineReaderBuilder builder() {
        return new LineReaderBuilder();
    }

    private LineReaderBuilder() {
        variables = new HashMap<>();
    }

    private LineReaderBuilder apply(Consumer<LineReaderBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public LineReaderBuilder terminal(Terminal terminal) {
        return apply(c -> c.terminal = terminal);
    }

    public LineReaderBuilder history(History history) {
        return apply(c -> c.history = history);
    }

    public LineReaderBuilder completer(Completer completer) {
        return apply(c -> c.completer = completer);
    }

    public LineReaderBuilder parser(Parser parser) {
        return apply(c -> c.parser = parser);
    }

    public LineReaderBuilder highlighter(Highlighter highlighter) {
        return apply(c -> c.highlighter = highlighter);
    }

    public LineReaderBuilder expander(Expander expander) {
        return apply(c -> c.expander = expander);
    }

    public LineReaderBuilder variable(String name, Object value) {
        return apply(c -> c.variables.put(name, value));
    }

    public LineReaderBuilder variables(Map<String,Object> variables) {
        return apply(c -> c.variables.putAll(variables));
    }

    public LineReader build() {
        try {
            if(terminal != null && !(terminal instanceof TerminalImpl)) {
                throw new RuntimeException("ERROR: The terminal given must be created by the TerminalBuilder!");
            }
            if(terminal == null) {
                System.out.println("creating a new terminal..");
                terminal = TerminalBuilder.builder().build();
            }

            TerminalConnection connection = new TerminalConnection(((TerminalImpl) terminal).getBase());

            ReadlineBuilder builder = ReadlineBuilder.builder();
            if(history != null)
                builder.history(new InMemoryHistory(history.size()));

            return new LineReaderImpl(builder.build(), connection, ((TerminalImpl) terminal), completer, null);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
