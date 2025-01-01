package askfar.com.gitusermanager.manager.impl

import askfar.com.gitusermanager.manager.ConfigManager
import askfar.com.gitusermanager.model.GitScripts
import askfar.com.gitusermanager.model.GitUser
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

    init {
        if (!configFile.exists()) {
            logger.debug { "Configuration file \"${configFileName}\" does not exist, will be initialized by current user." }
            val currentUserName = Runtime.getRuntime().exec(GitScripts.CURRENT_USER_NAME.script).inputReader().readLine()
            val currentUserEmail = Runtime.getRuntime().exec(GitScripts.CURRENT_USER_EMAIL.script).inputReader().readLine()
            val currentGitUser = GitUser(currentUserName, currentUserEmail)

            configFile.writeText(gson.toJson(setOf(currentGitUser)))
            logger.info { "The configuration file \"${configFileName}\" is initialized by the current user." }
        } else {
            logger.debug { "Configuration file \"${configFileName}\" exist" }
        }
    }

    override fun getUsers(): Set<GitUser> {
        return gson.fromJson(configFile.readText(), Array<GitUser>::class.java)?.toHashSet() ?: emptySet()
    }

    override fun saveUsers(newUsers: GitUser) {
        val allUsers = getUsers().plus(newUsers)
        configFile.writeText(gson.toJson(allUsers))
    }
}
