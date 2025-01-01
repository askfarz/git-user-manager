package askfar.com.gitusermanager.config

import askfar.com.gitusermanager.model.GitScripts
import askfar.com.gitusermanager.model.GitUser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intellij.openapi.components.Service
import java.io.File

@Service
class ConfigManager {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile = File(System.getProperty("user.home"), ".git_users.json")

    init {
        if (!configFile.exists()) {
            val currentUserName = Runtime.getRuntime().exec(GitScripts.CURRENT_USER_NAME.script).inputReader().readLine()
            val currentUserEmail = Runtime.getRuntime().exec(GitScripts.CURRENT_USER_EMAIL.script).inputReader().readLine()
            val currentGitUser = GitUser(currentUserName, currentUserEmail)
            configFile.writeText(gson.toJson(listOf(currentGitUser)))
        }
    }

    fun getUsers(): List<GitUser> {
        return gson.fromJson(configFile.readText(), Array<GitUser>::class.java)?.toList() ?: emptyList()
    }

    fun saveUsers(users: List<GitUser>) {
        configFile.writeText(gson.toJson(users))
    }
}
