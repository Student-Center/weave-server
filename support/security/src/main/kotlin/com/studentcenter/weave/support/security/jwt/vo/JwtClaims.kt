package com.studentcenter.weave.support.security.jwt.vo

import com.auth0.jwt.interfaces.DecodedJWT
import java.time.Instant
import java.util.*

/**
 * Represents the claims of a JSON Web Token (JWT).
 *
 * @property registeredClaims The registered claims of the JWT.
 * @property customClaims The custom claims of the JWT.
 */
data class JwtClaims(
    val registeredClaims: RegisteredClaims,
    val customClaims: MutableMap<String, Any> = mutableMapOf()
) {


    /**
     * Represents the registered claims defined in a JSON Web Token (JWT).
     *
     * @property sub The subject claim identifying the principal that is the subject of the JWT
     * @property exp The expiration time claim that specifies the time on or after which the JWT must not be accepted for processing
     * @property iat The issued at claim that identifies the time at which the JWT was issued
     * @property nbf The not before claim that identifies the time before which the JWT must not be accepted for processing
     * @property iss The issuer claim that identifies the entity that issued the JWT
     * @property aud The audience claim that identifies the recipients for which the JWT is intended
     * @property jti The JWT ID claim that provides a unique identifier for the JWT
     */
    data class RegisteredClaims(
        val sub: String? = null,
        val exp: Date? = Date.from(Instant.now().plusSeconds(DEFAULT_EXPIRATION_TIME_SEC)),
        val iat: Date? = Date.from(Instant.now()),
        val nbf: Date? = Date.from(Instant.now()),
        val iss: String? = null,
        val aud: List<String>? = null,
        val jti: String? = null,
    )

    val sub: String?
        get() = registeredClaims.sub
    val exp: Date?
        get() = registeredClaims.exp
    val iat: Date?
        get() = registeredClaims.iat
    val nbf: Date?
        get() = registeredClaims.nbf
    val iss: String?
        get() = registeredClaims.iss
    val aud: List<String>?
        get() = registeredClaims.aud
    val jti: String?
        get() = registeredClaims.jti

    companion object {

        const val DEFAULT_EXPIRATION_TIME_SEC: Long = 60 * 60 // 1 hour

        fun from(claims: DecodedJWT): JwtClaims {
            val registeredClaims = RegisteredClaims(
                sub = claims.subject,
                exp = claims.expiresAt,
                iat = claims.issuedAt,
                nbf = claims.notBefore,
                iss = claims.issuer,
                aud = claims.audience,
                jti = claims.id,
            )
            val customClaims: MutableMap<String, Any> = mutableMapOf()
            claims.claims
                .filter { it.key !in registeredClaims::class.members.map { member -> member.name } }
                .forEach { (key, value) ->
                    customClaims[key] = value.`as`(Any::class.java)
                }
            return JwtClaims(registeredClaims, customClaims)
        }

    }

}
