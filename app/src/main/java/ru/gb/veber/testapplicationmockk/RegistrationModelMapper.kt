package ru.gb.veber.testapplicationmockk

class RegistrationModelMapper {
    operator fun invoke(response: RegistrationResponse ): InitRegistrationModel {
       return InitRegistrationModel(response.id ?: throw BadDataResponseException())
    }
}

data class InitRegistrationModel(val id: String)

data class RegistrationResponse(val id: String?)

class BadDataResponseException : RuntimeException()
