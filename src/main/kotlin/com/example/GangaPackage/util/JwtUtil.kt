package com.example.GangaPackage.util

import com.example.GangaPackage.models.User
import com.example.GangaPackage.services.UserServices
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtUtil(private val userServices: UserServices) {

    private val SECRET_KEY = "4b4777ffbdb6db25ffd034537261e17a5c01fbaeeec920b10e37e104fb975a7e"
    private val key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun extractIssuedAt(token: String): Date {
        return extractClaim(token, Claims::getIssuedAt)
    }




    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: SecurityException) {
            throw RuntimeException("Invalid JWT signature", e)
        } catch (e: MalformedJwtException) {
            throw RuntimeException("Invalid JWT token", e)
        } catch (e: ExpiredJwtException) {
            throw e
        } catch (e: UnsupportedJwtException) {
            throw RuntimeException("JWT token is unsupported", e)
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("JWT claims string is empty", e)
        }
    }

    fun generateToken(username: String): String {
        val claims = HashMap<String, Any>()
        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUserDetails(token:String):User{
        return userServices.findUserById(extractUsername(token))
    }



}
