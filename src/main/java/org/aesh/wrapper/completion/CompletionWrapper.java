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
package org.aesh.wrapper.completion;

import org.aesh.readline.completion.CompleteOperation;
import org.aesh.readline.completion.Completion;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.ParsedLine;
import org.aesh.wrapper.reader.LineReaderImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class CompletionWrapper implements Completion<CompleteOperation> {

    private final LineReaderImpl lineReader;
    private Completer jlineCompleter;

    public CompletionWrapper(LineReaderImpl lineReader) {
        this.lineReader = lineReader;
    }

    public CompletionWrapper(LineReaderImpl lineReader, Completer jlineCompleter) {
        this.lineReader = lineReader;
        this.jlineCompleter = jlineCompleter;
    }

    public void setJlineCompleter(Completer jlineCompleter) {
        this.jlineCompleter = jlineCompleter;
    }

    @Override
    public void complete(CompleteOperation completeOperation) {

        List<Candidate> candidates = new ArrayList<>();
        ParsedLine parsedLine = lineReader.getParser().parse(completeOperation.getBuffer(), completeOperation.getCursor());
        jlineCompleter.complete(lineReader, parsedLine, candidates);

        completeOperation.addCompletionCandidates(CandidateConverter.convertFromCandidates(candidates));
    }

}
