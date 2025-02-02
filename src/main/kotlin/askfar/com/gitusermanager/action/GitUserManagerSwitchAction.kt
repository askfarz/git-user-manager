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
        val gitUsersManager = configManager.getUsersManager()
        val userNames = gitUsersManager.users.map { "${it.name} <${it.email}>" }.toTypedArray()

        val selectedUser = JOptionPane.showInputDialog(
            null,
            "Select Git User:",
            "Switch User",
            JOptionPane.QUESTION_MESSAGE,
            Messages.getQuestionIcon(),
            userNames,
            gitUsersManager.currentUser
        )

        selectedUser?.let {
            val selected = gitUsersManager.users.firstOrNull { user -> "${user.name} <${user.email}>" == it }
            selected?.let { user ->
                if (gitUsersManager.currentUser.email != user.email) {
                    Runtime.getRuntime().exec(String.format(GitScripts.CHANGE_USER_NAME.script, user.name)).waitFor()
                    Runtime.getRuntime().exec(String.format(GitScripts.CHANGE_USER_EMAIL.script, user.email)).waitFor()
                    configManager.updateCurrentUser(user)
                }
                Messages.showMessageDialog("Switched to user:\n ${user.name} <${user.email}>", "Success", Messages.getInformationIcon())
            }
        }
    }
}
