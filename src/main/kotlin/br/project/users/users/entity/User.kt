package br.project.users.users.entity

import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity
@Table(name = "tb_usuario")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class User(

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int? = null,

        @Column(name = "ds_nome")
        val name : String,

        @Column(name = "nm_idade")
        val age : Int,

        @OneToMany(mappedBy = "user")
        val address: Set<Address>? = null,

        @Column(name = "ds_telefone")
        val phone : String,

        @Column(name = "ds_email")
        val email : String,

        @Column(name = "fl_ativo")
        var active : String,

        @Column(name = "ds_senha")
        val password : String? = null



){
        fun toGetAll() : User{
                return User(
                        name = this.name,
                        age = this.age,
                        phone = this.phone,
                        email = this.email,
                        active = this.active
                )
        }
}
