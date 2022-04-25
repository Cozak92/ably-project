package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.api.AuthInBoundPort
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/authorize")
class AuthController(private val authInBoundPort: AuthInBoundPort) {

    @PostMapping
    @Operation(description = "이메일과 비밀번호를 받아서 로그인을 진행하고 토큰을 발급합니다.")
    fun authorize(@RequestBody @Valid userAuthDto: UserAuthDto): ResponseEntity<UserAuthResponseDto> {
        val token = authInBoundPort.authorize(userAuthDto)
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer ${token.value}")
        return ResponseEntity.created(URI("/authorize"))
            .headers(httpHeaders)
            .body(token)
    }

    @DeleteMapping
    @Operation(
        description = "로그아웃 처리는 시간 관계상 구현하지 못했습니다 😥 \n" +
                "만약 구현해야 한다면, redis의 time-to-live를 현재 토큰의 expired와 똑같이 설정해 넣은다음, 로그인 요청시 확인해 로그아웃된 토큰인지 확인합니다. "
    )
    fun unauthorize() {
        TODO("Not yet implemented")
    }
}