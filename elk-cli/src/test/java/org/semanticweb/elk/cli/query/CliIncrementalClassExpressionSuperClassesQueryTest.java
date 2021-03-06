/*
 * #%L
 * ELK OWL API Binding
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2016 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.cli.query;

import java.util.Arrays;
import java.util.Set;

import org.junit.runner.RunWith;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkClassExpression;
import org.semanticweb.elk.reasoner.incremental.CliIncrementalReasoningTestDelegate;
import org.semanticweb.elk.reasoner.query.ClassQueryTestInput;
import org.semanticweb.elk.reasoner.query.RelatedEntitiesTestOutput;
import org.semanticweb.elk.reasoner.taxonomy.model.Node;
import org.semanticweb.elk.testing.PolySuite;
import org.semanticweb.elk.testing.TestInput;
import org.semanticweb.elk.testing.TestManifest;

@RunWith(PolySuite.class)
public class CliIncrementalClassExpressionSuperClassesQueryTest extends
		CliIncrementalClassExpressionQueryTest<RelatedEntitiesTestOutput<ElkClass>> {

	// @formatter:off
	static final String[] IGNORE_LIST = {
			"Disjunctions.owl",// Disjuctions not supported
			"OneOf.owl",// Disjuctions not supported
		};
	// @formatter:on

	static {
		Arrays.sort(IGNORE_LIST);
	}

	@Override
	protected boolean ignore(final TestInput input) {
		return Arrays.binarySearch(IGNORE_LIST, input.getName()) >= 0;
	}

	public CliIncrementalClassExpressionSuperClassesQueryTest(
			final TestManifest<ClassQueryTestInput<ElkClassExpression>> manifest) {
		super(manifest,
				new CliIncrementalReasoningTestDelegate<RelatedEntitiesTestOutput<ElkClass>, RelatedEntitiesTestOutput<ElkClass>>(
						manifest) {

					@Override
					public RelatedEntitiesTestOutput<ElkClass> getExpectedOutput()
							throws Exception {
						final Set<? extends Node<ElkClass>> subNodes = standardReasoner_
								.getSuperClassesQuietly(
										manifest.getInput().getClassQuery(),
										true);
						return new CliRelatedEntitiesTestOutput(subNodes);
					}

					@Override
					public RelatedEntitiesTestOutput<ElkClass> getActualOutput()
							throws Exception {
						final Set<? extends Node<ElkClass>> subNodes = incrementalReasoner_
								.getSuperClassesQuietly(
										manifest.getInput().getClassQuery(),
										true);
						return new CliRelatedEntitiesTestOutput(subNodes);
					}

				});
	}

}
