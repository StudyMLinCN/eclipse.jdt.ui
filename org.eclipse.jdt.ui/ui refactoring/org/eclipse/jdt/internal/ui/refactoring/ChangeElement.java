/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
package org.eclipse.jdt.internal.ui.refactoring;



/**
 * Instances of <code>ChangeElement<code> are used to present <code>
 * IChange</code> object as nodes in a tree.
 */
abstract class ChangeElement {
	
	/** Flag indicating that the change element isn't active */
	public final static int INACTIVE=			0;
	/** Flag indicating that the change element is partly active (some children are inactive) */
	public final static int PARTLY_ACTIVE=	1;	
	/** Flage indicating that the change element is active */
	public final static int ACTIVE=				2;
	
	protected final static int[][] ACTIVATION_TABLE= new int[][] {
											/*INACTIVE*/		/*PARTLY_ACTIVE */		/*ACTIVE */
		/* INACTIVE */		{	INACTIVE,			PARTLY_ACTIVE,				PARTLY_ACTIVE },
		/* PARTLY_ACTIVE*/{	PARTLY_ACTIVE, 	PARTLY_ACTIVE,				PARTLY_ACTIVE },
		/* ACTIVE */			{	PARTLY_ACTIVE, 	PARTLY_ACTIVE,				ACTIVE}
	};
	
	protected static final ChangeElement[] EMPTY_CHILDREN= new ChangeElement[0];
	
	private ChangeElement fParent;

	/**
	 * Creates a new <code>ChangeElement</code> with the
	 * given parent
	 * 
	 * @param parent the change element's parent or <code>null
	 * 	</code> if the change element doesn't have a parent
	 */
	public ChangeElement(ChangeElement parent) {
		fParent= parent;
	}

	/**
	 * Returns the change element's parent.
	 * 
	 * @return the change element's parent
	 */
	public ChangeElement getParent() {
		return fParent;
	}
	
	/**
	 * Sets the activation status for this <code>ChangeElement</code>. When a 
	 * change element is not active,  then executing it is expected to do nothing.
	 *
	 * @param active the activation status for this change element
	 */
	public abstract void setActive(boolean active);
	
	/**
	 * Returns the activation status of this <code>ChangeElement</code>.
	 * Returns one of the following values: <code>IChange.ACTIVE</code>
	 * if the node and all its children are active, <code>IChange.INACTIVE</code>
	 * if all children and the node itself is inactive, and <code>IChange.PARTLy_ACTIVE
	 * </code>otherwise.
	 *
	 * @return the change element's activation status.
	 */
	public abstract int getActive();
	
	/**
	 * Returns the change element's children.
	 * 
	 * @return the change element's children.
	 */
	public abstract ChangeElement[] getChildren();	
}