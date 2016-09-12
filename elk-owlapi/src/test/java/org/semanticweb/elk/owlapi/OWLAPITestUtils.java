/*
 * #%L
 * ELK OWL API Binding
 * 
 * $Id$
 * $HeadURL$
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
/**
 * 
 */
package org.semanticweb.elk.owlapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.semanticweb.elk.config.ConfigurationFactory;
import org.semanticweb.elk.owl.parsing.Owl2ParseException;
import org.semanticweb.elk.reasoner.TestReasonerUtils;
import org.semanticweb.elk.reasoner.config.ReasonerConfiguration;
import org.semanticweb.elk.reasoner.stages.ReasonerStageExecutor;
import org.semanticweb.elk.reasoner.stages.RestartingStageExecutor;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * A collection of utility methods to be used in OWL API related tests
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class OWLAPITestUtils {

	private static OWLOntologyManager sharedOwlManager_ = null;

	public static OWLOntologyManager getSharedOwlManager() {
		if (sharedOwlManager_ == null) {
			sharedOwlManager_ = OWLManager.createOWLOntologyManager();
		}
		return sharedOwlManager_;
	}

	public static void removeAllOntologies(final OWLOntologyManager manager) {
		final List<OWLOntology> ontologies = new ArrayList<OWLOntology>(
				manager.getOntologies());
		for (final OWLOntology ontology : ontologies) {
			manager.removeOntology(ontology);
		}
	}

	public static ElkReasoner createReasoner(InputStream stream)
			throws IOException, Owl2ParseException {
		OWLOntologyManager manager = getSharedOwlManager();
		OWLOntology ontology = null;

		try {
			ontology = manager.loadOntologyFromOntologyDocument(stream);
		} catch (OWLOntologyCreationIOException e) {
			throw new IOException(e);
		} catch (OWLOntologyCreationException e) {
			throw new Owl2ParseException(e);
		}

		return createReasoner(ontology, false, new RestartingStageExecutor());
	}

	public static ElkReasoner createReasoner(OWLOntology ontology) {
		return createReasoner(ontology, false, new RestartingStageExecutor());
	}
	
	public static ElkProver createProver(OWLOntology ontology) {
		return new ElkProver(createReasoner(ontology));
	}

	public static ElkReasoner createReasoner(OWLOntology ontology,
			ReasonerConfiguration config) {
		return new ElkReasoner(ontology, false,
				new ElkReasonerConfiguration(ElkReasonerConfiguration
						.getDefaultOwlReasonerConfiguration(null), config));

	}

	public static ElkReasoner createReasoner(final OWLOntology ontology,
			final boolean isBufferingMode,
			final ReasonerStageExecutor stageExecutor) {

		ReasonerConfiguration config = null;
		try {
			final ResourceBundle bundle = ResourceBundle.getBundle(
					TestReasonerUtils.TEST_CONFIG_NAME, Locale.getDefault(),
					ReasonerConfiguration.class.getClassLoader());
			config = new ConfigurationFactory().getConfiguration(bundle,
					ReasonerConfiguration.REASONER_CONFIG_PREFIX,
					ReasonerConfiguration.class);
		} catch (MissingResourceException e) {
			config = ReasonerConfiguration.getConfiguration();
		}

		return new ElkReasoner(ontology, isBufferingMode,
				new ElkReasonerConfiguration(
						ElkReasonerConfiguration
								.getDefaultOwlReasonerConfiguration(null),
						config),
				stageExecutor);
	}

}
