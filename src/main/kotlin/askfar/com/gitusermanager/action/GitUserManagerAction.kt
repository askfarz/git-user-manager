package askfar.com.gitusermanager.action

import askfar.com.gitusermanager.ui.GitUserManagerDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import mu.KotlinLogging

class GitUserManagerAction : AnAction() {

    private val logger = KotlinLogging.logger {}
    private val dialog = GitUserManagerDialog()

    override fun actionPerformed(e: AnActionEvent) {
        logger.trace { "Handling the GitUserManagerAction" }
        dialog.show()
    }
}
