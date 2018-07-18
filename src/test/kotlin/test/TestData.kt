package test

import java.util.UUID

const val EMAIL_ADDRESS_1 = "test@e-mail.address"
const val EMAIL_ADDRESS_2 = "second@test.mail"
const val PASSWORD_1 = "test_password"
const val PASSWORD_2 = "a_different_test_password"

private const val MOST_SIG_BITS_USER_ID_1 = 54321L
private const val LEAST_SIG_BITS_USER_ID_1 = 12345L
val userId1 = UUID(MOST_SIG_BITS_USER_ID_1, LEAST_SIG_BITS_USER_ID_1)

private const val MOST_SIG_BITS_USER_PASSWORD_ID_1 = 76543L
private const val LEAST_SIG_BITS_USER_PASSWORD_ID_1 = 34567L
val userPasswordId1 = UUID(MOST_SIG_BITS_USER_PASSWORD_ID_1, LEAST_SIG_BITS_USER_PASSWORD_ID_1)
