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

import org.aesh.tty.Signal;
import org.jline.terminal.Terminal;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class SignalTransformer {

    public static Signal transformSignalToAesh(Terminal.Signal signal) {
        if(signal == Terminal.Signal.CONT)
            return Signal.CONT;
        else if(signal == Terminal.Signal.INFO)
            return Signal.INFO;
        else if(signal == Terminal.Signal.INT)
            return Signal.INT;
        else if(signal == Terminal.Signal.QUIT)
            return Signal.QUIT;
        else if(signal == Terminal.Signal.TSTP)
            return Signal.TSTP;
        else
            return Signal.WINCH;
    }

    public static Terminal.Signal transformSignalToJLine(Signal signal) {
        if(signal == Signal.CONT)
            return Terminal.Signal.CONT;
        else if(signal == Signal.INFO)
            return Terminal.Signal.INFO;
        else if(signal == Signal.INT)
            return Terminal.Signal.INT;
        else if(signal == Signal.QUIT)
            return Terminal.Signal.QUIT;
        else if(signal == Signal.TSTP)
            return Terminal.Signal.TSTP;
        else
            return Terminal.Signal.WINCH;
    }

}
