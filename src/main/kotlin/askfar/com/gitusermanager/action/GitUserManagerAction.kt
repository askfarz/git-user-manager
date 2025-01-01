package askfar.com.gitusermanager.action

import askfar.com.gitusermanager.ui.GitUserManagerDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import mu.KotlinLogging

class GitUserManagerAction : AnAction() {

    private val logger = KotlinLogging.logger {}

    override fun actionPerformed(e: AnActionEvent) {
        logger.trace { "Handling the GitUserManagerAction" }
        ApplicationManager.getApplication().invokeLater {
            val dialog = GitUserManagerDialog()
            dialog.show()
        }
    }
}
