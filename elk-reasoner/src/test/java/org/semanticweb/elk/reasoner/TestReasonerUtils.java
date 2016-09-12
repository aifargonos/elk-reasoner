/**
 * 
 */
package org.semanticweb.elk.reasoner;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
 * %%
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
 * #L%
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.semanticweb.elk.config.ConfigurationFactory;
import org.semanticweb.elk.loading.AxiomLoader;
import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.owl.iris.ElkPrefix;
import org.semanticweb.elk.owl.parsing.Owl2ParseException;
import org.semanticweb.elk.owl.parsing.Owl2Parser;
import org.semanticweb.elk.owl.parsing.Owl2ParserAxiomProcessor;
import org.semanticweb.elk.owl.parsing.javacc.Owl2FunctionalStyleParserFactory;
import org.semanticweb.elk.reasoner.config.ReasonerConfiguration;
import org.semanticweb.elk.reasoner.incremental.TestChangesLoader;
import org.semanticweb.elk.reasoner.stages.LoggingStageExecutor;
import org.semanticweb.elk.reasoner.stages.ReasonerStageExecutor;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class TestReasonerUtils {

	public static final String TEST_CONFIG_NAME = "elk-test";

	public static Reasoner createTestReasoner(AxiomLoader axiomLoader,
			ReasonerStageExecutor stageExecutor, ReasonerConfiguration config) {
		return new ReasonerFactory().createReasoner(axiomLoader, stageExecutor,
				config);
	}

	public static Reasoner createTestReasoner(AxiomLoader axiomLoader,
			ReasonerStageExecutor stageExecutor) {

		ReasonerConfiguration config = null;
		try {
			final ResourceBundle bundle = ResourceBundle.getBundle(
					TEST_CONFIG_NAME, Locale.getDefault(),
					ReasonerConfiguration.class.getClassLoader());
			config = new ConfigurationFactory().getConfiguration(bundle,
					ReasonerConfiguration.REASONER_CONFIG_PREFIX,
					ReasonerConfiguration.class);
		} catch (MissingResourceException e) {
			config = ReasonerConfiguration.getConfiguration();
		}

		return createTestReasoner(axiomLoader, stageExecutor, config);
	}

	public static Reasoner createTestReasoner(AxiomLoader axiomLoader,
			ReasonerStageExecutor stageExecutor, int maxWorkers) {
		ReasonerConfiguration config = ReasonerConfiguration.getConfiguration();

		config.setParameter(ReasonerConfiguration.NUM_OF_WORKING_THREADS,
				String.valueOf(maxWorkers));

		return createTestReasoner(axiomLoader, stageExecutor, config);
	}

	public static Reasoner loadAndClassify(Set<? extends ElkAxiom> ontology)
			throws Exception {

		TestChangesLoader initialLoader = new TestChangesLoader();
		ReasonerStageExecutor executor = new LoggingStageExecutor();

		Reasoner reasoner = TestReasonerUtils.createTestReasoner(initialLoader,
				executor);

		for (ElkAxiom axiom : ontology) {
			initialLoader.add(axiom);
		}

		try {
			reasoner.getTaxonomy();
		} catch (ElkInconsistentOntologyException e) {
			// shit happens
		}

		return reasoner;
	}

	public static Set<? extends ElkAxiom> loadAxioms(InputStream stream)
			throws IOException, Owl2ParseException {
		return loadAxioms(new InputStreamReader(stream));
	}

	public static Set<? extends ElkAxiom> loadAxioms(String resource)
			throws Exception {
		return loadAxioms(TestReasonerUtils.class.getClassLoader()
				.getResourceAsStream(resource));
	}

	public static Set<? extends ElkAxiom> loadAxioms(File file)
			throws Exception {
		InputStream stream = new FileInputStream(file);
		Set<? extends ElkAxiom> result = loadAxioms(stream);
		stream.close();
		return result;
	}

	public static Set<ElkAxiom> loadAxioms(Reader reader)
			throws IOException, Owl2ParseException {
		Owl2Parser parser = new Owl2FunctionalStyleParserFactory()
				.getParser(reader);
		final Set<ElkAxiom> axioms = new HashSet<ElkAxiom>();

		parser.accept(new Owl2ParserAxiomProcessor() {

			@Override
			public void visit(ElkPrefix elkPrefix) throws Owl2ParseException {
				// ignored
			}

			@Override
			public void visit(ElkAxiom elkAxiom) throws Owl2ParseException {
				axioms.add(elkAxiom);
			}

			@Override
			public void finish() throws Owl2ParseException {
				// everything is processed immediately
			}
		});

		return axioms;
	}
}
