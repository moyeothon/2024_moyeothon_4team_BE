package org.fourkibiki.tomorrowletter.moyeothon.controller;

import lombok.RequiredArgsConstructor;
import org.fourkibiki.tomorrowletter.moyeothon.service.KakaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/callback")
public class KakaoLoginController {
    private final KakaoService kakaoService;

    // 액세스 토큰을 받아오는 역할(테스트용)
    @GetMapping
    public @ResponseBody String kakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);
        return "액세스 토큰 받아오기 성공 ! 액세스 토큰: " + accessToken;
    }
    /*@GetMapping
    public @ResponseBody KakaoUserInfo kakaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);
        KakaoUserInfo userInfo = kakaoService.getUserInfo(accessToken);
        System.out.println("사용자 정보 받아오기 성공 !");
        return userInfo;
    }*/
}