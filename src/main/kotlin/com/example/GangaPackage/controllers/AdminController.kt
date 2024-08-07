package com.example.GangaPackage.controllers

import com.example.GangaPackage.models.User
import com.example.GangaPackage.repositories.Pricing
import com.example.GangaPackage.services.UserServices
import com.example.GangaPackage.util.JwtUtil

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(
    private val userServices: UserServices,
    private val jwtUtil: JwtUtil,
) {


    val secretKey = "3F2A1B4C5D6E7F8091A2B3C4D5E6F7A8"

    @GetMapping("/admin/{key}", produces = ["application/json"])
    fun admin(
        @PathVariable key: String
    ): ResponseEntity<List<UserToSend>> {
        println("its workin $key")
        if (key != secretKey) {
            return ResponseEntity.badRequest().body(emptyList())
        } else {

            val list = mutableListOf<UserToSend>()

            userServices.getAllUser().forEach {

                val x = UserToSend(
                    id = it.id,
                    name = it.name,
                    email = it.gmail,
                    subscribed = it.subscribed
                )
                list.add(x)
            }

            return ResponseEntity.ok(
            ).body(
                list.ifEmpty { emptyList() }
            )
        }
    }


    // get pricing
    @GetMapping("/admin/prices/{key}", produces = ["application/json"])
    fun getPRice(
        @PathVariable key: String
    ): ResponseEntity<List<Pricing>> {


        if (key != secretKey) {
            return ResponseEntity.badRequest().body(emptyList())
        } else {
            return ResponseEntity.ok().body(userServices.findPrice())
        }
    }

    // adding subscription

    @GetMapping("/admin/subscribe/{id}/{key}", produces = ["application/json"])
    fun subscribe(
        @PathVariable id: String
        , @PathVariable key: String
    ): ResponseEntity<String> {
        if (key != secretKey) {
            return ResponseEntity.badRequest().body("Invalid key")
        }

        userServices.findUserById(id).let {
            it.subscribed = !it.subscribed
            userServices.save(it)
        }
        return ResponseEntity.ok().body("Subscribed")
    }


    // add price
    @GetMapping("/admin/{time}/{price}/{key}")
    fun addPrice(
        @PathVariable time: String,
        @PathVariable price: String,
        @PathVariable key: String
    ): ResponseEntity<List<Pricing>> {
        if (key != secretKey) {
            return ResponseEntity.badRequest().body(emptyList())
        }
        userServices.saveprice(
            Pricing(
                id = time,
                price = price
            )
        )

        return ResponseEntity.ok().body(userServices.findPrice())


    }

    // delete price
    @GetMapping("/admin/delete/{id}/{key}")
    fun delete(
        @PathVariable id: String,
        @PathVariable key: String
    ): ResponseEntity<List<Pricing>> {

        println("its workin $id")

        if (key != secretKey) {
            return ResponseEntity.badRequest().body(emptyList())
        }
        userServices.deletePrice(id)
        return ResponseEntity.ok().body(userServices.findPrice())
    }









}

data class UserToSend(
    val id: String,
    val name: String,
    val email: String,
    val subscribed: Boolean
)

