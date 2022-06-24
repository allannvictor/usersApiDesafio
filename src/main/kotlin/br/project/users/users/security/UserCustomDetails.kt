package br.project.users.users.security

import br.project.users.users.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(
        private val user: User
) : UserDetails {

    val id : Int = user.id!!
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority("General"))

    override fun getPassword(): String = user.password.toString()

    override fun getUsername(): String = user.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = user.active == "S"
}