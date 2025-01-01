package askfar.com.gitusermanager.ui

import askfar.com.gitusermanager.exception.ValidationException
import askfar.com.gitusermanager.manager.ConfigManager
import askfar.com.gitusermanager.manager.impl.ConfigManagerImpl
import askfar.com.gitusermanager.model.GitUser
import askfar.com.gitusermanager.utils.ValidateUtils
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.Messages
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import mu.KotlinLogging
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
                        handleData(nameField.text, emailField.text)
                    }.align(AlignX.CENTER)
                }
            }
        }
    }

    private fun handleData(name: String, email: String) {
        try {
            ValidateUtils.validate(name, email)
            configManager.saveUsers(GitUser(name, email))
            Messages.showMessageDialog("Added user: Name = $name, Email = $email", "Success", Messages.getInformationIcon())
        } catch (e: ValidationException) {
            logger.error("Validation exception in GitUserManagerAction", e)
            Messages.showMessageDialog(e.message, "Warn", Messages.getWarningIcon())
        } catch (e: Exception) {
            logger.error("Unexpected exception in GitUserManagerAction", e)
            Messages.showMessageDialog(e.message, "Error", Messages.getErrorIcon())
        }
    }
}
