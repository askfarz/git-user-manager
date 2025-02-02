package askfar.com.gitusermanager.manager

import askfar.com.gitusermanager.model.GitUser
import askfar.com.gitusermanager.model.GitUsersManager

interface ConfigManager {

    fun getUsersManager(): GitUsersManager

    fun saveUser(newUser: GitUser)

    fun deleteUser(user: GitUser)

    fun updateCurrentUser(user: GitUser)

    fun getCurrentUser(): GitUser
}
