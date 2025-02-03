package askfar.com.gitusermanager.widget

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.WindowManager

class GitUserManagerStatusBarWidgetFactory : StatusBarWidgetFactory {

    override fun getId(): String {
        return "GitUserManagerStatusBarWidgetFactory"
    }

    override fun getDisplayName(): String {
        return "Git User Manager"
    }

    override fun createWidget(project: Project): StatusBarWidget {
        val statusBar: StatusBar = WindowManager.getInstance().getStatusBar(project)
        return GitUserManagerStatusBarWidget(statusBar)
    }
}
