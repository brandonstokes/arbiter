package io.fantasy.football

import com.fasterxml.classmate.TypeResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Autowired
    lateinit var typeResolver: TypeResolver

    @Bean
    fun docket(): Docket =
        Docket(DocumentationType.SWAGGER_2).select()
            .paths(PathSelectors.ant("/draft/**"))
            .build()
            .additionalModels(typeResolver.resolve(ResponseEntity::class.java))
            .apiInfo(
                ApiInfo(
                    "Arbiter API",
                    "Re-orders list of members based off of when a player was kept and their penalties.",
                    null, null,
                    Contact("Brandon Stokes", "", "stokes.brandon.j@gmail.com"),
                    null, null, emptyList()
                )
            )
}
