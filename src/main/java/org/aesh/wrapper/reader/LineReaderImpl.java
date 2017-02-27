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

import org.aesh.readline.Prompt;
import org.aesh.readline.Readline;
import org.aesh.readline.completion.Completion;
import org.aesh.tty.terminal.TerminalConnection;
import org.aesh.wrapper.completion.CompletionWrapper;
import org.aesh.wrapper.terminal.TerminalImpl;
import org.jline.keymap.KeyMap;
import org.jline.reader.Binding;
import org.jline.reader.Completer;
import org.jline.reader.EndOfFileException;
import org.jline.reader.Expander;
import org.jline.reader.Highlighter;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.UserInterruptException;
import org.jline.reader.Widget;
import org.jline.reader.impl.BufferImpl;
import org.jline.terminal.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class LineReaderImpl implements LineReader {

    private Readline readline;
    private TerminalConnection connection;
    private TerminalImpl terminal;
    private Prompt prompt;
    private BufferImpl buf;
    private CompletionWrapper completionWrapper;
    private final Map<String, Object> variables;
    private final Map<Option, Boolean> options;
    private List<Completion> completions;

    public LineReaderImpl(Readline readline, TerminalConnection connection,
                          TerminalImpl terminal, Completer completer,
                          Prompt prompt) {
        this.readline = readline;
        this.connection = connection;
        this.terminal = terminal;
        if(prompt == null)
            this.prompt = new Prompt("");
        else
            this.prompt = prompt;

        variables = new HashMap<>();
        options = new HashMap<>();

        buf = new BufferImpl();

        connection.setCloseHandler(close -> {
            connection.close();
        });

        completionWrapper = new CompletionWrapper(this, completer);
        completions = new ArrayList<>(1);
        completions.add(completionWrapper);

        if(!connection.isReading())
            connection.openNonBlocking();
    }

    private String readInput(Prompt prompt) {
        final String[] out = new String[1];
        CountDownLatch latch = new CountDownLatch(1);
       readline.readline(connection, prompt, line -> {
            connection.suspend();
            out[0] = line;
            latch.countDown();
        }, completions);
        try {
           // Wait until interrupted
            latch.await();
        }
        catch(InterruptedException ie) {

        }
        if(out[0] != null) {
            buf.clear();
            buf.write(out[0]);
        }
        return out[0];
    }


    @Override
    public Map<String, KeyMap<Binding>> defaultKeyMaps() {
        return null;
    }

    @Override
    public String readLine() throws UserInterruptException, EndOfFileException {
        return readInput(prompt);
    }

    @Override
    public String readLine(Character character) throws UserInterruptException, EndOfFileException {
        if(character != null)
            return readInput(new Prompt(prompt.getPromptAsString(), character));
        else
            return readInput(prompt);
    }

    @Override
    public String readLine(String prompt) throws UserInterruptException, EndOfFileException {
        this.prompt = new Prompt(prompt);
        return readInput(this.prompt);
    }

    @Override
    public String readLine(String prompt, Character character) throws UserInterruptException, EndOfFileException {
        this.prompt = new Prompt(prompt, character);
        return readInput(this.prompt);
    }

    @Override
    public String readLine(String prompt, Character character, String s1) throws UserInterruptException, EndOfFileException {
        this.prompt = new Prompt(prompt, character);
        return readInput(this.prompt);
    }

    @Override
    public String readLine(String prompt, String rightPrompt, Character character, String buffer) throws UserInterruptException, EndOfFileException {
        this.prompt = new Prompt(prompt, character);
        return readInput(this.prompt);
    }

    @Override
    public void callWidget(String s) {

    }

    @Override
    public Map<String, Object> getVariables() {
        return variables;
    }

    @Override
    public Object getVariable(String s) {
        return variables.get(s);
    }


    @Override
    public void setVariable(String s, Object o) {
        variables.put(s, o);
    }

    @Override
    public boolean isSet(Option option) {
        Boolean b = options.get(option);
        return b != null ? b : option.isDef();
    }

    @Override
    public void setOpt(Option option) {
        options.put(option, Boolean.TRUE);
    }

    @Override
    public void unsetOpt(Option option) {
        options.put(option, Boolean.FALSE);
    }

    @Override
    public Terminal getTerminal() {
        return terminal;
    }

    @Override
    public Map<String, Widget> getWidgets() {
        return null;
    }

    @Override
    public Map<String, Widget> getBuiltinWidgets() {
        return null;
    }

    @Override
    public BufferImpl getBuffer() {
        return buf;
    }

    @Override
    public void runMacro(String s) {

    }

    @Override
    public History getHistory() {
        return null;
    }

    @Override
    public Parser getParser() {
        return new ParserImpl();
    }

    @Override
    public Highlighter getHighlighter() {
        return null;
    }

    @Override
    public Expander getExpander() {
        return null;
    }

    @Override
    public Map<String, KeyMap<Binding>> getKeyMaps() {
        return null;
    }

    @Override
    public ParsedLine getParsedLine() {
        return null;
    }

    @Override
    public String getSearchTerm() {
        return null;
    }

    @Override
    public RegionType getRegionActive() {
        return null;
    }

    @Override
    public int getRegionMark() {
        return 0;
    }
}
