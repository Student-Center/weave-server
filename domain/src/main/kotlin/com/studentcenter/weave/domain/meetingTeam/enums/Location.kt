package com.studentcenter.weave.domain.meetingTeam.enums

enum class Location(
    val value: String,
    val isCapitalArea: Boolean,
) {
    // 수도권(서울)
    KONDAE_SEONGSU("건대•성수", true),
    HONGDAE_SINCHON("홍대•신촌", true),
    GANGNAM_JAMSIL("강남•잠실", true),
    NOWON_GONGREUNG("노원•공릉", true),
    DAEHANGNO_HYEHWA("대학로•혜화", true),
    // 수도권 (인천, 경기)
    INCHON("인천", true),
    SUWON("수원", true),
    YONGIN("용인", true),
    BUCHUN("부천", true),
    SIHEUNG("시흥", true),
    // 비수도권
    BUSAN("부산", false),
    DAEGU("대구", false),
    GWANGJU("광주", false),
    DAEJEON("대전", false),
    GANGWON("강원", false),
    CHUNGNAM("충남", false),
    CHUNGBUK("충북", false),
}
