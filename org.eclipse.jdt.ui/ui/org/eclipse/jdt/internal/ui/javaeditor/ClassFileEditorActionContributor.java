package org.eclipse.jdt.internal.ui.javaeditor;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import java.util.ResourceBundle;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.texteditor.BasicTextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;

import org.eclipse.jdt.ui.IContextMenuConstants;

import org.eclipse.jdt.internal.ui.JavaPlugin;


public class ClassFileEditorActionContributor extends BasicTextEditorActionContributor {
	
	protected OpenOnSelectionAction fOpenOnSelection;
	protected OpenOnSelectionAction fOpenHierarchyOnSelection;
	protected TogglePresentationAction fTogglePresentationAction;
	
	/* 1GEYIIA: ITPJUI:WINNT - Hover Toggle not available for classfile editors */
	protected ToggleTextHoverAction fToggleTextHover;
	
	
	public ClassFileEditorActionContributor() {
		super();
		
		ResourceBundle bundle= JavaPlugin.getResourceBundle();
		
		fOpenOnSelection= new OpenOnSelectionAction(bundle, "Editor.OpenOnSelection.");
		fOpenHierarchyOnSelection= new OpenHierarchyOnSelectionAction(bundle, "Editor.OpenHierarchyOnSelection.");
		fTogglePresentationAction= new TogglePresentationAction(bundle, "Editor.TogglePresentation.");
		
		/* 1GEYIIA: ITPJUI:WINNT - Hover Toggle not available for classfile editors */
		fToggleTextHover= new ToggleTextHoverAction(bundle, "Editor.ToggleTextHover.");
	}
	
	/**
	 * @see EditorActionBarContributor#contributeToMenu(IMenuManager)
	 */
	public void contributeToMenu(IMenuManager menu) {
		
		super.contributeToMenu(menu);
		
		IMenuManager editMenu= menu.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
		if (editMenu != null) {
			
			editMenu.add(new Separator(IContextMenuConstants.GROUP_OPEN));
			
			editMenu.appendToGroup(IContextMenuConstants.GROUP_OPEN, fOpenOnSelection);
			editMenu.appendToGroup(IContextMenuConstants.GROUP_OPEN, fOpenHierarchyOnSelection);
		}
	}
	
	/**
	 * @see EditorActionBarContributor#contributeToToolBar(IToolBarManager)
	 */
	public void contributeToToolBar(IToolBarManager tbm) {
		tbm.add(new Separator());
		tbm.add(fTogglePresentationAction);
		
		/* 1GEYIIA: ITPJUI:WINNT - Hover Toggle not available for classfile editors */
		tbm.add(fToggleTextHover);
	}
	
	/**
	 * @see IEditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart part) {
		
		super.setActiveEditor(part);
		
		ITextEditor textEditor= null;
		if (part instanceof ITextEditor)
			textEditor= (ITextEditor) part;
		
		fOpenOnSelection.setContentEditor(textEditor);
		fOpenHierarchyOnSelection.setContentEditor(textEditor);
		
		fTogglePresentationAction.setEditor(textEditor);

		/* 1GEYIIA: ITPJUI:WINNT - Hover Toggle not available for classfile editors */
		fToggleTextHover.setEditor(textEditor);
	}
}