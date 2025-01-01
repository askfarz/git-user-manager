package askfar.com.gitusermanager.ui

import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent

class GitUserManagerDialog : DialogWrapper(true) {

    private val ui = GitUserManagerUI()

    init {
        title = "Git User Manager"
        init()
    }

    override fun createCenterPanel(): JComponent {
        return ui.createPanel()
    }
}
