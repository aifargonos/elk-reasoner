/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.reasoner.taxonomy;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.semanticweb.elk.io.IOUtils;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.iris.ElkPrefixDeclarationsImpl;
import org.semanticweb.elk.owl.parsing.Owl2ParseException;
import org.semanticweb.elk.owl.parsing.Owl2Parser;
import org.semanticweb.elk.owl.parsing.javacc.Owl2FunctionalStyleParser;
import org.semanticweb.elk.reasoner.InconsistentOntologyException;
import org.semanticweb.elk.reasoner.Reasoner;

/**
 * Tests loading/dumping of class taxonomies
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class ClassTaxonomyIOTest {

	@Test
	public void roundtrip() throws IOException, Owl2ParseException, InconsistentOntologyException {
		Taxonomy<ElkClass> original = loadAndClassify("io/taxonomy.owl");
		StringWriter writer = new StringWriter();

		/*
		 * Writer outWriter = new OutputStreamWriter(System.out);
		 * ClassTaxonomyPrinter.dumpClassTaxomomy(original, outWriter, false);
		 * outWriter.flush();
		 */

		ClassTaxonomyPrinter.dumpClassTaxomomy(original, writer, false);

		StringReader reader = new StringReader(writer.getBuffer().toString());
		Owl2Parser parser = new Owl2FunctionalStyleParser(reader);
		Taxonomy<ElkClass> loaded = ClassTaxonomyLoader.load(parser);
		// compare
		/*
		 * outWriter = new OutputStreamWriter(System.out);
		 * ClassTaxonomyPrinter.dumpClassTaxomomy(original, outWriter, false);
		 * outWriter.flush();
		 */

		assertEquals(ClassTaxonomyPrinter.getHashString(original),
				ClassTaxonomyPrinter.getHashString(loaded));
	}

	private Taxonomy<ElkClass> loadAndClassify(String resource)
			throws IOException, Owl2ParseException,
			InconsistentOntologyException {
		InputStream stream = null;
		TestReasoner reasoner = new TestReasoner();

		try {
			stream = getClass().getClassLoader().getResourceAsStream(resource);
			reasoner = new TestReasoner();
			reasoner.loadOntologyFromStream(stream);
			return reasoner.getTaxonomy();
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}
}

class TestReasoner extends Reasoner {

	protected TestReasoner() {
		super(Executors.newSingleThreadExecutor(), 1);
	}

	public void loadOntologyFromStream(InputStream stream) throws IOException,
			Owl2ParseException {
		reset();

		Owl2Parser parser = new Owl2FunctionalStyleParser(stream);
		parser.setPrefixDeclarations(new ElkPrefixDeclarationsImpl());

		parser.parseOntology(ontologyIndex.getAxiomInserter());
	}

}