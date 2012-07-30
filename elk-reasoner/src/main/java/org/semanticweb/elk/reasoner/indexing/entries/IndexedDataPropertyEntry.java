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
package org.semanticweb.elk.reasoner.indexing.entries;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDataProperty;
import org.semanticweb.elk.util.collections.entryset.StrongKeyEntry;
import org.semanticweb.elk.util.hashing.HashGenerator;

/**
 * 
 * @author Pospishnyi Olexandr
 * 
 * @param <T>
 *            The type of the elements in the set where this entry is used
 * 
 * @param <K>
 *            the type of the wrapped indexed object used as the key of the
 *            entry
 */
public class IndexedDataPropertyEntry<T, K extends IndexedDataProperty> extends
		StrongKeyEntry<T, K> {

	public IndexedDataPropertyEntry(K representative) {
		super(representative);
	}

	@Override
	public int computeHashCode() {
		return HashGenerator.combinedHashCode(IndexedDataProperty.class,
				this.key.getProperty().getIri());
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other instanceof IndexedDataPropertyEntry<?, ?>) {
			IndexedDataPropertyEntry<?, ?> otherEntry = (IndexedDataPropertyEntry<?, ?>) other;
			return this.key.getProperty().getIri()
					.equals(otherEntry.key.getProperty().getIri());
		}
		return false;
	}
}
