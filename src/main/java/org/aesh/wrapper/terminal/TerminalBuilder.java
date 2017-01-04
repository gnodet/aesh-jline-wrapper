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
package org.aesh.wrapper.terminal;

import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal.SignalHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class TerminalBuilder {

    private String name;
    private InputStream input;
    private OutputStream output;
    private String type;
    private String encoding;
    private Boolean system;
    private boolean jna;
    private Boolean dumb;
    private Attributes attributes;
    private Size size;
    private boolean nativeSignals = false;
    private SignalHandler signalHandler;

    public static Terminal terminal() throws IOException {
        return builder().build();
    }

    public static TerminalBuilder builder() {
        return new TerminalBuilder();
    }

    private TerminalBuilder() {
        this.signalHandler = SignalHandler.SIG_DFL;
    }

    private TerminalBuilder apply(Consumer<TerminalBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public TerminalBuilder name(String name) {
        return apply(c -> c.name = name);
    }

    public TerminalBuilder input(InputStream input) {
        return apply(c -> c.input = input);
    }

    public TerminalBuilder output(OutputStream output) {
        return apply(c -> c.output = output);
    }

    public TerminalBuilder type(String type) {
        return apply(c -> c.type = type);
    }

    public TerminalBuilder encoding(String encoding) {
        return apply(c -> c.encoding = encoding);
    }

    public TerminalBuilder system(boolean system) {
        return apply(c -> c.system = system);
    }

     public TerminalBuilder jna(boolean jna) {
        return apply(c -> c.jna = jna);
    }

     public TerminalBuilder dumb(boolean dumb) {
        return apply(c -> c.dumb = dumb);
    }

    public TerminalBuilder attributes(Attributes attributes) {
        return apply(c -> c.attributes = attributes);
    }

    public TerminalBuilder nativeSignals(boolean nativeSignals) {
        return apply(c -> c.nativeSignals = nativeSignals);
    }

    public TerminalBuilder size(Size size) {
        return apply(c -> c.size = size);
    }

    public TerminalBuilder signalHandler(SignalHandler signalHandler) {
        return apply(c -> c.signalHandler = signalHandler);
    }

    public Terminal build() throws IOException {
        return new TerminalImpl(
                org.aesh.terminal.TerminalBuilder
                        .builder()
                        .encoding(encoding)
                        .input(input)
                        .output(output)
                        .nativeSignals(nativeSignals)
                        .system(false)
                        .type(type)
                        .build());
    }

}
