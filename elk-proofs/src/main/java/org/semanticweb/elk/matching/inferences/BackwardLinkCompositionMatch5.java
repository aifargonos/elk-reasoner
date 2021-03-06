package org.semanticweb.elk.matching.inferences;

/*
 * #%L
 * ELK Proofs Package
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

import org.semanticweb.elk.matching.conclusions.BackwardLinkMatch2;
import org.semanticweb.elk.matching.conclusions.ConclusionMatchExpressionFactory;
import org.semanticweb.elk.matching.conclusions.ForwardLinkMatch3;
import org.semanticweb.elk.matching.conclusions.SubPropertyChainMatch1;
import org.semanticweb.elk.matching.root.IndexedContextRootMatch;
import org.semanticweb.elk.matching.root.IndexedContextRootMatchChain;

public class BackwardLinkCompositionMatch5
		extends AbstractInferenceMatch<BackwardLinkCompositionMatch4> {

	private IndexedContextRootMatchChain intermediateRoots_;

	private final IndexedContextRootMatch destinationMatch_;

	BackwardLinkCompositionMatch5(BackwardLinkCompositionMatch4 parent,
			ForwardLinkMatch3 thirdPremiseMatch) {
		super(parent);
		this.intermediateRoots_ = thirdPremiseMatch.getIntermediateRoots();
		this.destinationMatch_ = thirdPremiseMatch.getTargetMatch();
	}

	public IndexedContextRootMatchChain getIntermediateRoots() {
		return intermediateRoots_;
	}

	public IndexedContextRootMatch getDestinationMatch() {
		return destinationMatch_;
	}

	public SubPropertyChainMatch1 getSecondPremiseMatch(
			ConclusionMatchExpressionFactory factory) {
		return factory.getSubPropertyChainMatch1(
				getParent().getParent().getParent().getParent().getParent()
						.getSecondPremise(factory),
				getParent().getParent().getParent().getFirstProperty(), 0);
	}

	public BackwardLinkMatch2 getConclusionMatch(
			ConclusionMatchExpressionFactory factory) {
		return factory.getBackwardLinkMatch2(
				getParent().getParent().getParent().getParent()
						.getConclusionMatch(factory),
				getParent().getParent().getParent()
						.getConclusionRelationMatch(),
				getDestinationMatch());
	}

	@Override
	public <O> O accept(InferenceMatch.Visitor<O> visitor) {
		return visitor.visit(this);
	}

	/**
	 * The visitor pattern for instances
	 * 
	 * @author Yevgeny Kazakov
	 *
	 * @param <O>
	 *            the type of the output
	 */
	public interface Visitor<O> {

		O visit(BackwardLinkCompositionMatch5 inferenceMatch4);

	}

	/**
	 * A factory for creating instances
	 * 
	 * @author Yevgeny Kazakov
	 *
	 */
	public interface Factory {

		BackwardLinkCompositionMatch5 getBackwardLinkCompositionMatch5(
				BackwardLinkCompositionMatch4 parent,
				ForwardLinkMatch3 thirdPremiseMatch);

	}

}
