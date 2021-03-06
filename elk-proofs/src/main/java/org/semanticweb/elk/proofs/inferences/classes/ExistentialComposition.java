/**
 * 
 */
package org.semanticweb.elk.proofs.inferences.classes;

/*
 * #%L
 * ELK Reasoner
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

import java.util.Arrays;
import java.util.Collection;

import org.semanticweb.elk.owl.interfaces.ElkSubClassOfAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyOfAxiom;
import org.semanticweb.elk.proofs.expressions.AxiomExpression;
import org.semanticweb.elk.proofs.expressions.Expression;
import org.semanticweb.elk.proofs.inferences.AbstractInference;
import org.semanticweb.elk.proofs.inferences.InferenceRule;
import org.semanticweb.elk.proofs.inferences.InferenceVisitor;
import org.semanticweb.elk.proofs.utils.InferencePrinter;

/**
 * Represents the existential composition inference: (A <= R some B, B <= C, R <= S) |- A <= S some C   
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class ExistentialComposition extends AbstractInference<AxiomExpression<ElkSubClassOfAxiom>> {

	private final Expression existentialPremise_;

	private final AxiomExpression<ElkSubClassOfAxiom> subsumerPremise_;

	private final AxiomExpression<ElkSubObjectPropertyOfAxiom> propertyPremise_;

	public ExistentialComposition(
			AxiomExpression<ElkSubClassOfAxiom> conclusion, 
			AxiomExpression<ElkSubClassOfAxiom> subsumerPremise,
			Expression existentialPremise,
			AxiomExpression<ElkSubObjectPropertyOfAxiom> propPremise) {
		super(conclusion);

		existentialPremise_ = existentialPremise;
		subsumerPremise_ = subsumerPremise;
		propertyPremise_ = propPremise;
	}
	
	@Override
	public <I, O> O accept(InferenceVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public Collection<Expression> getRawPremises() {
		return Arrays.<Expression>asList(existentialPremise_, subsumerPremise_, propertyPremise_);
	}
	
	public AxiomExpression<ElkSubObjectPropertyOfAxiom> getSubPropertyPremise() {
		return propertyPremise_;
	}
	
	public Expression getExistentialPremise() {
		return existentialPremise_;
	}

	public AxiomExpression<ElkSubClassOfAxiom> getSubsumerPremise() {
		return subsumerPremise_;
	}
	
	@Override
	public String toString() {
		return InferencePrinter.print(this);
	}
	
	@Override
	public InferenceRule getRule() {
		return InferenceRule.R_EXIST_COMPOSITION;
	}
}
