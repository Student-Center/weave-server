package com.studentcenter.weave.support.security.jwt.vo

import java.time.Instant
import java.util.*

/**
 * Marks the annotated class as a DSL marker for creating JwtClaims objects.
 */
@DslMarker
annotation class JwtClaimsDsl

/**
 * Builder class for creating JwtClaims objects.
 */
@JwtClaimsDsl
class JwtClaimsBuilder {
    private var registeredClaims = JwtClaims.RegisteredClaims()
    private val customClaims = mutableMapOf<String, Any>()

    /**
     * Sets the registered claims for a JSON Web Token (JWT) using the provided DSL.
     * ```
     * sub: Subject
     * exp: Expiration Time
     * iat: Issued At
     * nbf: Not Before
     * iss: Issuer
     * aud: Audience
     * jti: JWT ID
     * ```
     *
     * @param init The DSL for setting registered claims.
     * @see JwtClaims.registeredClaims
     **/
    fun registeredClaims(init: RegisteredClaimsBuilder.() -> Unit) {
        val builder = RegisteredClaimsBuilder().apply(init)
        registeredClaims = builder.build()
    }

    /**
     * Sets the custom claims for a JSON Web Token (JWT) using the provided DSL.
     * ```
     * this["customKey1"] = "customValue1"
     * this["customKey2"] = "customValue2"
     * ```
     * @param block The DSL for setting custom claims.
     * @see JwtClaims.customClaims
     */
    fun customClaims(block: MutableMap<String, Any>.() -> Unit) {
        customClaims.apply(block)
    }

    fun build(): JwtClaims = JwtClaims(registeredClaims, customClaims)
}

/**
 * A builder class that creates instances of `JwtClaims.RegisteredClaims`.
 * The class provides methods to set the registered claims for a JSON Web Token (JWT).
 */
@JwtClaimsDsl
class RegisteredClaimsBuilder {
    var sub: String? = null
    var exp: Date? = Date.from(Instant.now().plusSeconds(JwtClaims.DEFAULT_EXPIRATION_TIME_SEC))
    var iat: Date? = Date.from(Instant.now())
    var nbf: Date? = Date.from(Instant.now())
    var iss: String? = null
    var aud: List<String>? = null
    var jti: String? = null

    fun build(): JwtClaims.RegisteredClaims =
        JwtClaims.RegisteredClaims(sub, exp, iat, nbf, iss, aud, jti)
}

/**
 * Creates a new JwtClaims object using the provided DSL.
 * ```
 * val jwt = JwtClaims {
 *     registeredClaims {
 *         sub = "subject"
 *         // ... Set Registered Claim ...
 *     }
 *     customClaims {
 *         this["customKey1"] = "customValue1"
 *         // ... Set Custom Claim ...
 *     }
 * }
 * ```
 * @param init The DSL for creating a JwtClaims object.
 * @return A new JwtClaims object.
 * @see JwtClaimsBuilder
 * @see JwtClaimsDsl
 * @see RegisteredClaimsBuilder
 * @example
 *
 */
fun JwtClaims(init: JwtClaimsBuilder.() -> Unit): JwtClaims = JwtClaimsBuilder().apply(init).build()
