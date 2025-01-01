package askfar.com.gitusermanager.action

import askfar.com.gitusermanager.ui.GitUserManagerDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GitUserManagerAction : AnAction() {

    private val dialog = GitUserManagerDialog()

    override fun actionPerformed(e: AnActionEvent) {
        dialog.show()
    }
}
