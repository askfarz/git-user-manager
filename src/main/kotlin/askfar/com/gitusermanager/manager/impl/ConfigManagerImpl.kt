package askfar.com.gitusermanager.manager.impl

import askfar.com.gitusermanager.manager.ConfigManager
import askfar.com.gitusermanager.model.GitScripts
import askfar.com.gitusermanager.model.GitUser
import askfar.com.gitusermanager.model.GitUsersManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intellij.openapi.components.Service
import mu.KotlinLogging
import java.io.File

@Service
class ConfigManagerImpl : ConfigManager {

    private val logger = KotlinLogging.logger {}
    private val configFileName = ".git_users.json"
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile = File(System.getProperty("user.home"), configFileName)
    private lateinit var currentUser: GitUser

    init {
        if (!configFile.exists()) {
            logger.debug { "Configuration file \"${configFileName}\" does not exist, will be initialized by current user." }
            val currentUserName = Runtime.getRuntime().exec(GitScripts.CURRENT_USER_NAME.script).inputReader().readLine()
            val currentUserEmail = Runtime.getRuntime().exec(GitScripts.CURRENT_USER_EMAIL.script).inputReader().readLine()
            currentUser = GitUser(currentUserName, currentUserEmail)
            val gitUsersManager = GitUsersManager(currentUser, setOf(currentUser))

            writeConfig(gitUsersManager)
            logger.info { "The configuration file \"${configFileName}\" is initialized by the current user." }
        } else {
            logger.debug { "Configuration file \"${configFileName}\" exist" }
        }
    }

    override fun getUsersManager(): GitUsersManager {
        return gson.fromJson(configFile.readText(), GitUsersManager::class.java)
    }

    override fun saveUser(newUser: GitUser) {
        val gitUsersManager = getUsersManager()
        gitUsersManager.users = gitUsersManager.users.plus(newUser)
        writeConfig(gitUsersManager)
    }

    override fun deleteUser(user: GitUser) {
        val gitUsersManager = getUsersManager()
        gitUsersManager.users = gitUsersManager.users.minus(user)
        writeConfig(gitUsersManager)
    }

    override fun updateCurrentUser(user: GitUser) {
        val gitUsersManager = getUsersManager()
        gitUsersManager.currentUser = user
        writeConfig(gitUsersManager)
    }

    override fun getCurrentUser(): GitUser {
        return currentUser
    }

    private fun writeConfig(gitUsersManager: GitUsersManager) {
        configFile.writeText(gson.toJson(gitUsersManager))
    }
}
