package com.witchend.domain.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomNicknameGenerator {

    public String generateRandomNickname(String initialName) {

        // UUID를 생성하고 문자열로 변환합니다.
        String uuid = UUID.randomUUID().toString();

        // UUID에서 첫 번째 섹션(하이픈까지)만 추출합니다.
        String[] split = uuid.split("-");
        String shortUuid = split[0] + split[1];

        return initialName + "_" + shortUuid;
    }
}
