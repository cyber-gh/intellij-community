/*
 * User: anna
 * Date: 27-Aug-2008
 */
package com.intellij.refactoring.inlineSuperClass;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.util.CommonRefactoringUtil;

import java.util.Collection;

public class InlineSuperClassRefactoringHandler {
  public static final String REFACTORING_NAME = "Inline Super Class";

  private InlineSuperClassRefactoringHandler() {
  }

  public static void invoke(final Project project, final Editor editor, final PsiClass superClass, Collection<PsiClass> inheritors) {
    if (inheritors.size() > 1) {
      CommonRefactoringUtil.showErrorHint(project, editor, "Classes which have multiple subclasses cannot be inlined", REFACTORING_NAME, null);
      return;
    }

    final PsiClass inheritor = inheritors.iterator().next();

    if (PsiTreeUtil.isAncestor(superClass, inheritor, false)) {
      CommonRefactoringUtil.showErrorHint(project, editor, "Cannot inline into the inner class. Move \'" + inheritor.getName() + "\' to upper level", REFACTORING_NAME, null);
      return;
    }

    new InlineSuperClassRefactoringDialog(project, superClass, inheritor).show();
  }
}