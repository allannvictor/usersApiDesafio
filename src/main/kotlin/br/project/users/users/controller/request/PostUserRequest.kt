package br.project.users.users.controller.request

import br.project.users.users.entity.Address
import br.project.users.users.entity.User
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class PostUserRequest(
        @field:NotEmpty(message = "O nome é obrigatório.")
        val name: String,
        @field:Min(value = 18, message =  "É necessário ter ao menos 18 anos para cadastrar usuário.")
        val age: Int,
        val address: Address?,
        @field:NotEmpty(message = "O telefone é obrigatório.")
        val phone: String,
        @field:NotEmpty(message = "O e-mail é obrigatório.")
        val email: String,
        @field:NotEmpty(message = "A senha é obrigatória.")
        var password: String
){
        fun toEntity() : User{
                return  User(
                        name = this.name,
                        age = this.age,
                        phone = this.phone,
                        email = this.email,
                        password = this.password,
                        active = "S"
                )
        }
}