package com.example.sksb.domain.member.controller;

import com.example.sksb.domain.article.entity.Article;
import com.example.sksb.domain.member.entity.Member;
import com.example.sksb.domain.member.service.MemberService;
import com.example.sksb.global.exceptions.GlobalException;
import com.example.sksb.global.rq.Rq;
import com.example.sksb.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @AllArgsConstructor
    @Getter
    public static class LoginResponseBody {
        @NotBlank
        private String refreshToken;
        @NotBlank
        private String accessToken;
    }

    @Getter
    @Setter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(
            @Valid @RequestBody LoginRequestBody body
    ) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.authAndMakeTokens(body.getUsername(), body.getPassword());

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().getRefreshToken());
        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().getAccessToken());

        return RsData.of(
                authAndMakeTokensRs.getResultCode(),
                authAndMakeTokensRs.getMsg(),
                new LoginResponseBody(
                        authAndMakeTokensRs.getData().getRefreshToken(),
                        authAndMakeTokensRs.getData().getAccessToken()
                )
        );
    }
}
