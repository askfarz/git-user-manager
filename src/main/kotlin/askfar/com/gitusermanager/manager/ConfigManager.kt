package askfar.com.gitusermanager.manager

import askfar.com.gitusermanager.model.GitUser

interface ConfigManager {

    fun getUsers(): Set<GitUser>

    fun saveUsers(newUsers: GitUser)
}
