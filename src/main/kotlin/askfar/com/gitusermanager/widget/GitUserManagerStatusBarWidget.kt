package askfar.com.gitusermanager.widget

import askfar.com.gitusermanager.action.GitUserManagerSwitchAction
import askfar.com.gitusermanager.manager.ConfigManager
import askfar.com.gitusermanager.manager.impl.ConfigManagerImpl
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.util.Consumer
import java.awt.event.MouseEvent

class GitUserManagerStatusBarWidget(private val statusBar: StatusBar) : StatusBarWidget, StatusBarWidget.TextPresentation, StatusBarWidget.WidgetPresentation {

    private val configManager: ConfigManager = ConfigManagerImpl.create()
    private val gitUserManagerSwitchAction: GitUserManagerSwitchAction = GitUserManagerSwitchAction()

    override fun ID(): String {
        return "GitUserManagerStatusBarWidget"
    }

    override fun getAlignment(): Float {
        return 0f
    }

    override fun getText(): String {
        return "<${configManager.getCurrentUser().email}>"
    }

    override fun getTooltipText(): String {
        return "Git user: ${configManager.getCurrentUser().name} (${configManager.getCurrentUser().email})"
    }

    override fun getClickConsumer(): Consumer<MouseEvent> {
        return Consumer { _ ->
            gitUserManagerSwitchAction.actionPerformed()
            statusBar.updateWidget(ID())
        }
    }

    override fun getPresentation(): StatusBarWidget.WidgetPresentation {
        return this
    }
}
