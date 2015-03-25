/**
 * 
 */
package org.semanticweb.elk.owlapi.proofs;
/*
 * #%L
 * ELK Proofs Package
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapitools.proofs.ExplainingOWLReasoner;
import org.semanticweb.owlapitools.proofs.OWLInference;
import org.semanticweb.owlapitools.proofs.exception.ProofGenerationException;
import org.semanticweb.owlapitools.proofs.expressions.OWLExpression;

/**
 * 
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class RecursiveInferenceVisitor {

	public static void visitInferences(ExplainingOWLReasoner reasoner, OWLAxiom axiom, OWLInferenceVisitor visitor, boolean allProofs) throws ProofGenerationException {
		OWLExpression root = reasoner.getDerivedExpression(axiom);
		
		visitInferences(reasoner, root, visitor, allProofs);
	}
	
	public static void visitInferencesOfInconsistency(ExplainingOWLReasoner reasoner, OWLInferenceVisitor visitor, boolean allProofs) throws ProofGenerationException {
		OWLExpression root = reasoner.getDerivedExpressionForInconsistency();
		
		visitInferences(reasoner, root, visitor, allProofs);
	}
	
	static void visitInferences(ExplainingOWLReasoner reasoner, OWLExpression root, OWLInferenceVisitor visitor, boolean allProofs) throws ProofGenerationException {
		// start recursive unwinding
		Queue<OWLExpression> toDo = new LinkedList<OWLExpression>();
		Set<OWLExpression> done = new HashSet<OWLExpression>();
		OWLExpression next = root;
		
		toDo.add(next);
		done.add(next);
		
		for (;;) {
			next = toDo.poll();
			
			if (next == null) {
				break;
			}
			
			for (OWLInference inf : next.getInferences()) {
				// pass to the client
				visitor.visit(inf);
				// recursively unwind premise inferences
				for (OWLExpression premise : inf.getPremises()) {
					// proof reader guarantees equality for structurally equivalent expressions so we avoid infinite loops here
					if (done.add(premise)) {
						toDo.add(premise);
					}
				}
				
				if (!allProofs) {
					// a single proofs is sufficient
					break;
				}
			}
		}
	}
}