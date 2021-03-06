/**
 * 
 */
package org.semanticweb.elk.proofs.inferences.classes;
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


/**
 * 
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public interface ClassInferenceVisitor<I, O> {
	
	public O visit(ClassSubsumption inf, I input);
	
	public O visit(ConjunctionComposition inf, I input);
	
	public O visit(ConjunctionDecomposition inf, I input);
	
	public O visit(DisjointnessContradiction inf, I input);
	
	public O visit(DisjunctionComposition inf, I input);
	
	public O visit(ExistentialComposition inf, I input);
	
	public O visit(ExistentialChainAxiomComposition inf, I input);
	
	public O visit(ExistentialLemmaChainComposition inf, I input);
	
	public O visit(InconsistentDisjointness inf, I input);
	
	public O visit(NegationContradiction inf, I input);
	
	public O visit(ReflexiveExistentialComposition inf, I input);
	
	public O visit(NaryExistentialAxiomComposition inf, I input);
	
	public O visit(NaryExistentialLemmaComposition inf, I input);
	
	public O visit(UnsatisfiabilityInference inf, I input);

}
