/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
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
package org.semanticweb.elk.owl.filters;

import org.semanticweb.elk.owl.interfaces.ElkAxiom;

/**
 * A filter producing objects in {@link ElkAxiom} from objects of
 * this type.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public interface ElkAxiomFilter extends ElkAnnotationAxiomFilter,
		ElkAssertionAxiomFilter, ElkClassAxiomFilter,
		ElkDataPropertyAxiomFilter, ElkDatatypeDefinitionAxiomFilter,
		ElkDeclarationAxiomFilter, ElkHasKeyAxiomFilter,
		ElkObjectPropertyAxiomFilter, ElkPropertyAxiomFilter,
		ElkSWRLRuleFilter {

	// combined visitor
}
