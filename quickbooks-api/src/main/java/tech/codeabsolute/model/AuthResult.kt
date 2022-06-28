package tech.codeabsolute.model

sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null) : AuthResult<T>(data)
    class Unauthorized<T> : AuthResult<T>()
    class Error<T>(val message: String) : AuthResult<T>()
}