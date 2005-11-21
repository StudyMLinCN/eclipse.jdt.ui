/*******************************************************************************
 * Copyright (c) 2005 Tobias Widmer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *     Tobias Widmer - initial API and implementation
 *******************************************************************************/
package org.eclipse.ltk.internal.core.refactoring;

import org.eclipse.ltk.core.refactoring.RefactoringDescriptorProxy;

/**
 * Default implementation of a refactoring descriptor proxy.
 * 
 * @since 3.2
 */
public final class RefactoringDescriptorProxyImplementation extends RefactoringDescriptorProxy {

	/** The description of the refactoring */
	private final String fDescription;

	/** The non-empty name of the project, or <code>null</code> */
	private final String fProject;

	/** The time stamp of the refactoring */
	private final long fTimeStamp;

	/**
	 * Creates a new refactoring descriptor proxy implementation.
	 * 
	 * @param description
	 *            a non-empty human-readable description of the particular
	 *            refactoring instance
	 * @param project
	 *            the non-empty name of the project, or <code>null</code>
	 * @param stamp
	 *            the time stamp of the refactoring
	 */
	public RefactoringDescriptorProxyImplementation(final String description, final String project, final long stamp) {
		Assert.isTrue(project == null || !"".equals(project)); //$NON-NLS-1$
		Assert.isTrue(description != null && !"".equals(description)); //$NON-NLS-1$
		fDescription= description.intern();
		fProject= project != null ? project.intern() : null;
		fTimeStamp= stamp;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int compareTo(final Object object) {
		if (object instanceof RefactoringDescriptorProxyImplementation) {
			final RefactoringDescriptorProxyImplementation proxy= (RefactoringDescriptorProxyImplementation) object;
			return (int) (fTimeStamp - proxy.fTimeStamp);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean equals(final Object object) {
		if (object instanceof RefactoringDescriptorProxyImplementation) {
			final RefactoringDescriptorProxyImplementation proxy= (RefactoringDescriptorProxyImplementation) object;
			return fTimeStamp == proxy.fTimeStamp && fDescription.equals(proxy.fDescription);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getDescription() {
		return fDescription;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getProject() {
		return fProject;
	}

	/**
	 * {@inheritDoc}
	 */
	public final long getTimeStamp() {
		return fTimeStamp;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int hashCode() {
		return (int) (fDescription.hashCode() + 17 * fTimeStamp);
	}

	/**
	 * {@inheritDoc}
	 */
	public final String toString() {

		final StringBuffer buffer= new StringBuffer(128);

		buffer.append(getClass().getName());
		buffer.append("[stamp="); //$NON-NLS-1$
		buffer.append(fTimeStamp);
		buffer.append(",project="); //$NON-NLS-1$
		buffer.append(fProject);
		buffer.append(",description="); //$NON-NLS-1$
		buffer.append(fDescription);
		buffer.append("]"); //$NON-NLS-1$

		return buffer.toString();
	}
}
