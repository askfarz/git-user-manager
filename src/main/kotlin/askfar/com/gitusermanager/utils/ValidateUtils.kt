package askfar.com.gitusermanager.utils

import askfar.com.gitusermanager.exception.ValidationException

object ValidateUtils {

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    fun validate(name: String, email: String) {
        if (name.isBlank() || email.isBlank()) {
            throw ValidationException("Please fill in both fields")
        }

        if (!emailRegex.matches(email)) {
            throw ValidationException("The email field does not match the pattern")
        }
    }
}
