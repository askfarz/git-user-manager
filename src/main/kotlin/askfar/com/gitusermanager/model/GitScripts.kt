package askfar.com.gitusermanager.model

enum class GitScripts(val script: String) {

    CURRENT_USER_NAME("git config --global user.name"),
    CURRENT_USER_EMAIL("git config --global user.email"),
    CHANGE_USER_NAME("git config --global user.name \"%s\""),
    CHANGE_USER_EMAIL("git config --global user.email \"%s\"")
}
