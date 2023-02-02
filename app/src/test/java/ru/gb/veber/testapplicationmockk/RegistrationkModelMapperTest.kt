package ru.gb.veber.testapplicationmockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class RegistrationModelMapperTest {

    private val registrationModelMapper = RegistrationModelMapper()

    private val registrationResponse = RegistrationResponse("id")
    private val emptyRegistrationResponse = RegistrationResponse(null)

    private val initRegistrationModel = InitRegistrationModel("id")

    @Test
    fun empty_response() {
        val expected = BadDataResponseException::class.java
        assertThrows(expected) {
            registrationModelMapper.invoke(emptyRegistrationResponse)
        }
    }

    @Test
    fun success() {
        val model = registrationModelMapper.invoke(registrationResponse)
        assertEquals(initRegistrationModel, model)
    }

//    operator fun invoke(response: RegistrationResponse, ): InitRegistrationModel {
//        return InitRegistrationModel(response.id ?: throw BadDataResponseException())
//    }
}