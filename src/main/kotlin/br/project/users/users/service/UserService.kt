package br.project.users.users.service

import br.project.users.users.controller.request.PostUserRequest
import br.project.users.users.entity.User
import br.project.users.users.exception.BadRequestException
import br.project.users.users.exception.NotFoundException
import br.project.users.users.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class UserService(
        private val userRepository: UserRepository,
        private val bCrypt: BCryptPasswordEncoder
) {

    @CacheEvict(allEntries = true, cacheNames = ["getAll", "findById", "getAllTest"])
    fun save(request: PostUserRequest) {
        if (userRepository.existsByEmail(request.email))
            throw BadRequestException("Email already exists.", "002")

        request.password = bCrypt.encode(request.password)
        userRepository.save(request.toEntity())
    }
    @CachePut("findById")
    fun disable(id: Int): User {
        val user = userRepository.findById(id).orElseThrow { NotFoundException("User with id %s not exists.".format(id), "001") }
        user.active = "N"
        return userRepository.save(user)
    }


    @CachePut("findById")
    fun enable(id: Int): User {
        val user = userRepository.findById(id).orElseThrow { NotFoundException("User with id %s not exists.".format(id), "001") }
        user.active = "S"
        return userRepository.save(user)
    }

    @Cacheable("getAll")
    fun getAll(): List<User> {
        Thread.sleep(2000)
        return userRepository.findAll().map { it.toGetAll() }
    }

    @Cacheable("getAllTest", key = "#name", condition = "!(#name.length > 5) ")
    fun getAllWithNameTest(name : String): List<User> {
        Thread.sleep(2000)
        return userRepository.findAll().map { it.toGetAll() }
    }

    @Cacheable("findById")
    fun getById(id : Int): User{
        return userRepository.findById(id).orElseThrow { NotFoundException("User with id %s not exists.".format(id), "001") }
    }
}