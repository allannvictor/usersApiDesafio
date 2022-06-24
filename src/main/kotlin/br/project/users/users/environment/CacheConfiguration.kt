package br.project.users.users.environment

import br.project.users.users.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration


@Configuration
class CacheConfiguration(
        private val objectMapper: ObjectMapper
) {
    @Bean
    fun cacheConfig(): RedisCacheConfiguration? {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1))
            .disableCachingNullValues()
            .serializeValuesWith(SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
    }

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer? {
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(User::class.java).apply {
            this.setObjectMapper(objectMapper)
        }
        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManagerBuilder ->
            builder
                .withCacheConfiguration(
                    "findById",
                    RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(5))
                        .disableCachingNullValues()
                        .serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                )
        }
    }

}