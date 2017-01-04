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

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class AttributeTransformer {

    public static Attributes.LocalFlag transformLocalFlagFromAeshToJLine(org.aesh.terminal.Attributes.LocalFlag aeshLocalFlag) {
        return Attributes.LocalFlag.valueOf(aeshLocalFlag.name());
    }

    public static org.aesh.terminal.Attributes.LocalFlag transformLocalFlagFromJLineToAesh(Attributes.LocalFlag jlineLocalFlag) {
        return org.aesh.terminal.Attributes.LocalFlag.valueOf(jlineLocalFlag.name());
    }

    public static Attributes.ControlFlag transformControlFlagFromAeshToJLine(org.aesh.terminal.Attributes.ControlFlag aeshControlFlag) {
        return Attributes.ControlFlag.valueOf(aeshControlFlag.name());
    }

    public static org.aesh.terminal.Attributes.ControlFlag transformControlFlagFromJLineToAesh(Attributes.ControlFlag jlineControlFlag) {
        return org.aesh.terminal.Attributes.ControlFlag.valueOf(jlineControlFlag.name());
    }

    public static Attributes.OutputFlag transformOutputFlagFromAeshToJLine(org.aesh.terminal.Attributes.OutputFlag aeshOutputFlag) {
        return Attributes.OutputFlag.valueOf(aeshOutputFlag.name());
    }

    public static org.aesh.terminal.Attributes.OutputFlag transformOutputFlagFromJLineToAesh(Attributes.OutputFlag jlineOutputFlag) {
        return org.aesh.terminal.Attributes.OutputFlag.valueOf(jlineOutputFlag.name());
    }

    public static Attributes.InputFlag transformInputFlagFromAeshToJLine(org.aesh.terminal.Attributes.InputFlag aeshInputFlag) {
        return Attributes.InputFlag.valueOf(aeshInputFlag.name());
    }

    public static org.aesh.terminal.Attributes.InputFlag transformInputFlagFromJLineToAesh(Attributes.InputFlag jlineInputFlag) {
        return org.aesh.terminal.Attributes.InputFlag.valueOf(jlineInputFlag.name());
    }

    public static Attributes.ControlChar transformControlCharFromAeshToJLine(org.aesh.terminal.Attributes.ControlChar aeshControlChar) {
        return Attributes.ControlChar.valueOf(aeshControlChar.name());
    }

    public static org.aesh.terminal.Attributes.ControlChar transformControlCharFromJLineToAesh(Attributes.ControlChar jlineControlChar) {
        return org.aesh.terminal.Attributes.ControlChar.valueOf(jlineControlChar.name());
    }



    public static Attributes transformToJlineAttributes(org.aesh.terminal.Attributes aeshAttributes) {
        Attributes attributes = new Attributes();
        aeshAttributes.getControlFlags().forEach(controlFlag ->
                attributes.setControlFlag(transformControlFlagFromAeshToJLine(controlFlag), true)
        );
         aeshAttributes.getLocalFlags().forEach(localFlag ->
                attributes.setLocalFlag(transformLocalFlagFromAeshToJLine(localFlag), true)
        );
          aeshAttributes.getOutputFlags().forEach(outputFlag ->
                attributes.setOutputFlag(transformOutputFlagFromAeshToJLine(outputFlag), true)
        );
           aeshAttributes.getInputFlags().forEach(inputFlag ->
                attributes.setInputFlag(transformInputFlagFromAeshToJLine(inputFlag), true)
        );
        aeshAttributes.getControlChars().forEach((controlChar, integer) ->
                attributes.setControlChar( transformControlCharFromAeshToJLine(controlChar), integer)
        );
        return attributes;
    }

    public static org.aesh.terminal.Attributes transformToAeshAttributes(Attributes jlineAttributes) {
        org.aesh.terminal.Attributes attributes = new org.aesh.terminal.Attributes();
        jlineAttributes.getControlFlags().forEach(controlFlag ->
                attributes.setControlFlag(transformControlFlagFromJLineToAesh(controlFlag), true)
        );
         jlineAttributes.getLocalFlags().forEach(localFlag ->
                attributes.setLocalFlag(transformLocalFlagFromJLineToAesh(localFlag), true)
        );
          jlineAttributes.getOutputFlags().forEach(outputFlag ->
                attributes.setOutputFlag(transformOutputFlagFromJLineToAesh(outputFlag), true)
        );
           jlineAttributes.getInputFlags().forEach(inputFlag ->
                attributes.setInputFlag(transformInputFlagFromJLineToAesh(inputFlag), true)
        );
        jlineAttributes.getControlChars().forEach((controlChar, integer) ->
                attributes.setControlChar( transformControlCharFromJLineToAesh(controlChar), integer)
        );
        return attributes;
    }


}
