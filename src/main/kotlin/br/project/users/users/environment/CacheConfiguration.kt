package br.project.users.users.environment

import br.project.users.users.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.scheduling.annotation.Scheduled
import java.time.Duration


@Configuration
class CacheConfiguration(
//        private val connectionFactory: RedisConnectionFactory,
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
//                .withCacheConfiguration(
//                    "getAll",
//                    RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofMinutes(10))
//                        .disableCachingNullValues()
//                        .serializeValuesWith(SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
//                )
                .withCacheConfiguration(
                    "findById",
                    RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(5))
                        .disableCachingNullValues()
                        .serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                )
        }
    }


//    @Bean
//    fun cacheManager(): CacheManager {
//        val redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory)
//
//        val cache1MinuteConfig = RedisCacheConfiguration.defaultCacheConfig()
//            .entryTtl(Duration.ofMinutes(1))
//            .computePrefixWith { name -> "UserApi:$name:" }
//
//        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(User::class.java).apply {
//            this.setObjectMapper(objectMapper)
//        }
//        val cache1MinuteConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(1))
//                .computePrefixWith { name -> "UserApi:$name:" }
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
//
//
//
////        val cache60MinuteConfiguration = RedisCacheConfiguration.defaultCacheConfig()
////                .entryTtl(Duration.ofMinutes(60))
////                .computePrefixWith { name -> "UserApi:$name:" }
////                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
//
//        return RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(redisCacheWriter)
//                .withCacheConfiguration("getAll", cache1MinuteConfig)
////                .withCacheConfiguration("count", cache1MinuteConfiguration)
//                .withCacheConfiguration("findById", cache1MinuteConfiguration)
//                .build()
//    }
}