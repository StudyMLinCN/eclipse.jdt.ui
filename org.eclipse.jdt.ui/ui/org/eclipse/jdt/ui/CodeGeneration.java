/*******************************************************************************
 * Copyright (c) 2000, 2003 International Business Machines Corp. and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipse.jdt.ui;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import org.eclipse.jdt.internal.corext.codemanipulation.StubUtility;

public class CodeGeneration {
	
	private CodeGeneration() {
	}
	
	/**
	 * Returns the content for a new compilation unit using the 'new file' code template.
	 * @param cu The compilation to create the source for. The compilation unit does not need to exist.
	 * @param typeComment The comment for the type to created. Used when the code template contains a ${typecomment} variable
	 * @param typeContent The code of the type, including type declaration and body.
	 * @param lineDelimiter The line delimiter to be used.
	 * @return String Returns the new content or <code>null</code> if the template is empty.
	 * @throws CoreException
	 */
	public static String getCompilationUnitContent(ICompilationUnit cu, String typeComment, String typeContent, String lineDelimiter) throws CoreException {	
		return StubUtility.getCompilationUnitContent(cu, typeComment, typeContent, lineDelimiter);
	}
	
	/**
	 * Returns the content for a new type comment using the 'typecomment' code template. The returned content is unformatted and uses '\n' as line delimiter.
	 * @param cu The compilation where the type is contained. The compilation unit does not need to exist.
	 * @param typeQualifiedName The name of the type to which the comment is added. For inner types the name must be qualified and include the outer
	 * types names (dot separated). See {@link org.eclipse.jdt.core.IType#getTypeQualifiedName(char)}
	 * @return String Returns the new content or <code>null</code> if the code template is empty. The returned content is unformatted and uses '\n' as line delimiter.
	 * @throws CoreException
	 */	
	public static String getTypeComment(ICompilationUnit cu, String typeQualifiedName) throws CoreException {
		return StubUtility.getTypeComment(cu, Signature.getSimpleName(typeQualifiedName), Signature.getQualifier(typeQualifiedName));
	}

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * @param cu The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated).
	 * @param decl The MethodDeclaration AST node that will be added as new
	 * method. The node does not need to exist in an AST (no parent needed) and does not need to resolve.
	 * @param overridden The binding of the method that will be overridden by the created
	 * method or <code>null</code> if no method is overridden.
	 * @return Returns the generated method comment or <code>null</code> if the
	 * code template is empty. The returned content is unformatted and uses '\n' as line delimiter.
	 * (formatting required)
	 * @throws CoreException
	 */
	public static String getMethodComment(ICompilationUnit cu, String declaringTypeName, MethodDeclaration decl, IMethodBinding overridden) throws CoreException {
		return StubUtility.getMethodComment(cu, declaringTypeName, decl, overridden);
	}

	/**
	 * Returns the comment for a method or constructor using the comment code templates (constructor / method / overriding method).
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and uses \n as line delimiter.
	 * <p>Exception types and return type are in signature notation. e.g. a source method declared as <code>public void foo(String text, int length)</code>
	 * would return the array <code>{"QString;","I"}</code> as parameter types. See {@link org.eclipse.jdt.core.Signature}
	 * 
	 * @param cu The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated).
	 * @param methodName Name of the method.
	 * @param paramNames Names of the parameters for the method.
	 * @param excTypeSig Throwns exceptions (Signature notation)
	 * @param retTypeSig Return type (Signature notation) or <code>null</code>
	 * for constructors.
	 * @param overridden The method thet will be overridden by the created method or
	 * <code>null</code> for non-overriding methods. If not <code>null</code>, the method must exist.
	 * @return String Returns the constructed comment or <code>null</code> if
	 * the comment code template is empty. The string uses \n as line delimiter
	 * (formatting required)
	 * @throws CoreException
	 */
	public static String getMethodComment(ICompilationUnit cu, String declaringTypeName, String methodName, String[] paramNames, String[] excTypeSig, String retTypeSig, IMethod overridden) throws CoreException {
		return StubUtility.getMethodComment(cu, declaringTypeName, methodName, paramNames, excTypeSig, retTypeSig, overridden);
	}

	/**
	 * Returns the content of body for a method or constructor using the method body templates.
	 * <code>null</code> is returned if the template is empty.
	 * <p>The returned string is unformatted and uses \n as line delimiter.
	 * 
	 * @param cu The compilation unit to which the method belongs. The compilation unit does not need to exist.
	 * @param declaringTypeName Name of the type to which the method belongs. For inner types the name must be qualified and include the outer
	 * types names (dot separated).
	 * @param methodName Name of the method.
	 * @param isConstructor Defines if the created body is for a constructor
	 * @param bodyStatement The code to be entered at the place of the variable ${body_statement}. 
	 * @return String Returns the constructed body contnet or <code>null</code> if
	 * the code template is empty. The string uses \n as line delimiter
	 * (formatting required)
	 * @throws CoreException
	 */	
	public static String getMethodBodyContent(ICompilationUnit cu, String declaringTypeName, String methodName, boolean isConstructor, String bodyStatement) throws CoreException {
		return StubUtility.getMethodBodyContent(isConstructor, cu.getJavaProject(), declaringTypeName, methodName, bodyStatement);
	}
	
}
