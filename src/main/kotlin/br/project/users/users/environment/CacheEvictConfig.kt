package br.project.users.users.environment

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class CacheEvictConfig(
    private val cacheManager: RedisCacheManager
) {

    //@Scheduled( fixedRate = 30000)
    fun clearCaches(){
        cacheManager.cacheNames.map {
            cacheManager.getCache(it)?.clear()
        }
    }


}