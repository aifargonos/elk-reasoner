/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.reasoner.saturation;

import org.semanticweb.elk.reasoner.indexing.IndexedClassExpression;

/**
 * @author Frantisek Simancik
 *
 */
public interface QueueableVisitor<O> {
	O visit(IndexedClassExpression indexedClassExpression);
	O visit(DecomposedClassExpression compositeClassExpression);
	O visit(BackwardLink backwardLink);
	O visit(ForwardLink forwardLink);
	O visit(Propagation propagation);
}