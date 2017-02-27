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

import org.aesh.tty.Capability;
import org.jline.terminal.Attributes;
import org.jline.terminal.Cursor;
import org.jline.terminal.MouseEvent;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.function.IntConsumer;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class TerminalImpl implements Terminal {

    private final org.aesh.terminal.Terminal base;

    public TerminalImpl(org.aesh.terminal.Terminal terminal) {
        base = terminal;
    }

    public org.aesh.terminal.Terminal getBase() {
        return base;
    }

    public String getName() {
        return base.getName();
    }

    @Override
    public SignalHandler handle(Signal signal, SignalHandler signalHandler) {
        return new SignalHandlerToJLine(
                base.handle(SignalTransformer.transformSignalToAesh(signal), new SignalHandlerToAesh(signalHandler)));
    }

    @Override
    public void raise(Signal signal) {
        base.raise(SignalTransformer.transformSignalToAesh(signal));
    }

    @Override
    public NonBlockingReader reader() {
        return null;
    }

    @Override
    public PrintWriter writer() {
        return base.writer();
    }

    @Override
    public InputStream input() {
        return base.input();
    }

    @Override
    public OutputStream output() {
        return base.output();
    }

    @Override
    public Attributes enterRawMode() {
        return AttributeTransformer.transformToJlineAttributes(base.enterRawMode());
    }

    @Override
    public boolean echo() {
        return base.echo();
    }

    @Override
    public boolean echo(boolean b) {
        return base.echo(b);
    }

    @Override
    public Attributes getAttributes() {
        return AttributeTransformer.transformToJlineAttributes(base.getAttributes());
    }

    @Override
    public void setAttributes(Attributes attributes) {
        base.setAttributes(AttributeTransformer.transformToAeshAttributes(attributes));
    }

    @Override
    public Size getSize() {
        return new Size(base.getWidth(), base.getHeight());
    }

    @Override
    public void setSize(Size size) {
    }

    @Override
    public void flush() {
        base.flush();
    }

    @Override
    public String getType() {
        return base.getType();
    }

    @Override
    public boolean puts(InfoCmp.Capability capability, Object... objects) {
        return base.puts(Capability.valueOf(capability.name()), objects);
    }

    @Override
    public boolean getBooleanCapability(InfoCmp.Capability capability) {
        return base.getBooleanCapability(Capability.valueOf(capability.name()));
    }

    @Override
    public Integer getNumericCapability(InfoCmp.Capability capability) {
        return base.getNumericCapability(Capability.valueOf(capability.name()));
    }

    @Override
    public String getStringCapability(InfoCmp.Capability capability) {
        return base.getStringCapability(Capability.valueOf(capability.name()));
    }

    @Override
    public Cursor getCursorPosition(IntConsumer intConsumer) {
        return new Cursor(0,0);
    }

    @Override
    public boolean hasMouseSupport() {
        return base.getStringCapability(Capability.key_mouse) != null;
    }

    @Override
    public boolean trackMouse(MouseTracking mouseTracking) {
        return false;
    }

    @Override
    public MouseEvent readMouseEvent() {
        return null;
    }

    @Override
    public void close() throws IOException {
        base.close();
    }
}
