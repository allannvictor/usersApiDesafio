package br.project.users.users.controller

import br.project.users.users.controller.request.PostUserRequest
import br.project.users.users.entity.User
import br.project.users.users.service.UserService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import kotlin.random.Random

@RestController
@RequestMapping("users")
class UserController(
        private val userService: UserService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<User> {
        return userService.getAll()
    }
    @GetMapping("/test/{name}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllTest(@PathVariable name : String) : List<User> {
        return userService.getAllWithNameTest(name)
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: Int): User {
        return userService.getById(id)
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request : PostUserRequest){
        val user = PostUserRequest(
                age = request.age,
                name = request.name + Random.nextInt(999).toString(),
                phone = request.phone,
                email = request.email.substringBefore('@')  + Random.nextInt(999).toString() + request.email.substringAfter('@'),
                password = request.password,
                address = null

        )
        return userService.save(user)
    }

    @PatchMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun disable(@PathVariable id : Int) : User{
        return userService.disable(id)
    }

    @PatchMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun enable(@PathVariable id : Int) : User{
        return userService.enable(id)
    }



}
