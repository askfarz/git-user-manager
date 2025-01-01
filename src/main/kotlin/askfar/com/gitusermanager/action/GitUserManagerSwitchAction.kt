package askfar.com.gitusermanager.action

import askfar.com.gitusermanager.manager.ConfigManager
import askfar.com.gitusermanager.manager.impl.ConfigManagerImpl
import askfar.com.gitusermanager.model.GitScripts
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import mu.KotlinLogging
import javax.swing.JOptionPane

class GitUserManagerSwitchAction : AnAction() {

    private val logger = KotlinLogging.logger {}
    private val configManager: ConfigManager = ConfigManagerImpl()

    override fun actionPerformed(e: AnActionEvent) {
        logger.trace { "Handling the GitUserManagerSwitchAction" }
        val users = configManager.getUsers()
        val userNames = users.map { "${it.name} <${it.email}>" }.toTypedArray()

        val selectedUser = JOptionPane.showInputDialog(
            null,
            "Select Git User:",
            "Switch User",
            JOptionPane.QUESTION_MESSAGE,
            Messages.getQuestionIcon(),
            userNames,
            userNames.firstOrNull()
        )

        selectedUser?.let {
            val selected = users.firstOrNull { user -> "${user.name} <${user.email}>" == it }
            selected?.let { user ->
                Runtime.getRuntime().exec(String.format(GitScripts.CHANGE_USER_NAME.script, user.name)).waitFor()
                Runtime.getRuntime().exec(String.format(GitScripts.CHANGE_USER_EMAIL.script, user.email)).waitFor()
                Messages.showMessageDialog("Switched to user: ${user.name} <${user.email}>", "Success", Messages.getInformationIcon())
            }
        }
    }
}
