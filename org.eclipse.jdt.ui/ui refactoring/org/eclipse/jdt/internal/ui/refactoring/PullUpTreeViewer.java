/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
package org.eclipse.jdt.internal.ui.refactoring;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;

public class PullUpTreeViewer extends CheckboxTreeViewer {
	
	private Set fActiveElements;
	
	public PullUpTreeViewer(Tree tree) {
		super(tree);
		
		fActiveElements= new HashSet();
		
		addCheckStateListener(createCheckStateListener());

		addTreeListener(new ITreeViewerListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
			}
			public void treeExpanded(TreeExpansionEvent event) {
				IMember element= (IMember)event.getElement();
				initializeChildren(element);
			}
		});
	}

	private ICheckStateListener createCheckStateListener() {
		return new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event){
				IMember element= (IMember)event.getElement();
				boolean checked= event.getChecked();

				if (checked)
					fActiveElements.add(element);
				else
					fActiveElements.remove(element);

				setSubtreeChecked(element, checked);
				setSubtreeGrayed(element, false);
				IJavaElement parent= getParent(element);
				if (parent == null)	
					return;
				if (checked) {
					while(parent != null) {
						setChecked(parent, checked);
						boolean grayed= isPartlyActive(parent);
						setGrayed(parent, grayed);
						parent= getParent(parent);
					}
				} else {
					setParentsGrayed(parent, true);
				}
			}
		};
	}
	
	private ITypeHierarchy getTypeHierarchyInput(){
		return (ITypeHierarchy)getInput();
	}
	
	private boolean isPartlyActive(IJavaElement member){
		if (member instanceof IMethod)
			return false;
		if (!(member instanceof IType))
			return false;
			
		boolean checked= getChecked(member);
		Object[] children=getFilteredChildren(member);
		for (int i= 0; i < children.length; i++) {
			if (! (children[i] instanceof IJavaElement))
				continue;
			IJavaElement iJavaElement= (IJavaElement)children[i];
			if (checked){
				 if (! getChecked(iJavaElement))
				 	return true;
				 if (isPartlyActive(iJavaElement))	
					return true; 
			}
				
			if (! checked){
				if (getChecked(iJavaElement))
					return true;
				if (isPartlyActive(iJavaElement))	
					return true;
			}	
		}
		return false;
	}
	
	private IMember getParent(IJavaElement member){
		if (member instanceof IType)
			return getTypeHierarchyInput().getSuperclass((IType)member);
		if (member instanceof IMethod)
			return ((IMethod)member).getDeclaringType();		
		return null;
	}
	
	private IMember[] getChildren(IMember member){	
		if (member instanceof IType)
			return getTypeHierarchyInput().getSubtypes((IType)member);
		return null;	
	}
	
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		initializeChildren(((ITypeHierarchy)input).getType());
	}
	
	public void expandToLevel(Object element, int level) {
		super.expandToLevel(element, level);
		if (element instanceof IMember)	
			initializeChildren((IMember)element);
		if (element instanceof ITypeHierarchy)	
			initializeChildren(((ITypeHierarchy)element).getType());
	}
	
	private void initializeChildren(IMember element) {
		if (element == null)
			return;
		IMember[] children= getChildren(element);
		if (children == null)
			return;
		for (int i= 0; i < children.length; i++) {
			IMember child= children[i];
			boolean checked= fActiveElements.contains(child);
			if (checked)
				setChecked(child, checked);
			boolean grayed= isPartlyActive(child);
			if (grayed)
				setGrayed(child, grayed);
		}
	}
	
	public void setSubtreeGrayed(Object element, boolean grayed) {
		Widget widget= findItem(element);
		if (widget instanceof TreeItem) {
			TreeItem item= (TreeItem)widget;
			if (item.getGrayed() != grayed) {
				item.setGrayed(grayed);
				grayChildren(getChildren(item), grayed);
			}
		}
	}
	
	private void grayChildren(Item[] items, boolean grayed) {
		for (int i= 0; i < items.length; i++) {
			Item element= items[i];
			if (element instanceof TreeItem) {
				TreeItem item= (TreeItem)element;
				if (item.getGrayed() != grayed) {
					item.setGrayed(grayed);
					grayChildren(getChildren(item), grayed);
				}
			}
		}
	}
}