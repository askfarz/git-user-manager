package askfar.com.gitusermanager.ui

import askfar.com.gitusermanager.exception.ValidationException
import askfar.com.gitusermanager.manager.ConfigManager
import askfar.com.gitusermanager.manager.impl.ConfigManagerImpl
import askfar.com.gitusermanager.model.GitUser
import askfar.com.gitusermanager.utils.ValidateUtils
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.Messages.showMessageDialog
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import mu.KotlinLogging
import javax.swing.JOptionPane
import javax.swing.JTextField

class GitUserManagerUI {

    private val logger = KotlinLogging.logger {}
    private val configManager: ConfigManager = ConfigManagerImpl()
    private lateinit var nameField: JTextField
    private lateinit var emailField: JTextField

    fun createPanel(): DialogPanel {
        return panel {
            group("Git Users") {
                row("Name:") {
                    nameField = textField().applyToComponent { toolTipText = "Enter to user's name" }.component
                }
                row("Email:") {
                    emailField = textField().applyToComponent { toolTipText = "Enter to user's email" }.component
                }
                row {
                    button("➕") {
                        addUserProcess(nameField.text, emailField.text)
                    }.align(AlignX.LEFT)

                    button("➖") {
                        deleteUserProcess()
                    }.align(AlignX.RIGHT)
                }
            }
        }
    }

    private fun addUserProcess(name: String, email: String) {
        try {
            ValidateUtils.validate(name, email)
            configManager.saveUser(GitUser(name, email))
            showMessageDialog("Added user: Name = $name, Email = $email", "Success", Messages.getInformationIcon())
        } catch (e: ValidationException) {
            logger.error("Validation exception in GitUserManagerAction", e)
            showMessageDialog(e.message, "Warn", Messages.getWarningIcon())
        } catch (e: Exception) {
            logger.error("Unexpected exception in GitUserManagerAction", e)
            showMessageDialog(e.message, "Error", Messages.getErrorIcon())
        }
    }

    private fun deleteUserProcess() {
        val gitUsersManager = configManager.getUsersManager()
        val userNames = gitUsersManager.users.map { "${it.name} <${it.email}>" }.toTypedArray()

        val selectedUser = JOptionPane.showInputDialog(
            null,
            "Select Git User for deleting:",
            "Delete User",
            JOptionPane.QUESTION_MESSAGE,
            Messages.getQuestionIcon(),
            userNames,
            gitUsersManager.currentUser
        )

        selectedUser?.let {
            val selected = gitUsersManager.users.firstOrNull { user -> "${user.name} <${user.email}>" == it }
            selected?.let { user ->
                if (gitUsersManager.currentUser.email == user.email) {
                    showMessageDialog("This user is current and cannot be deleted:\n ${user.name} <${user.email}>", "Failure", Messages.getErrorIcon())
                } else {
                    configManager.deleteUser(user)
                    showMessageDialog("Deleted user:\n ${user.name} <${user.email}>", "Success", Messages.getInformationIcon())
                }
            }
        }
    }
}
