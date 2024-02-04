package com.studentcenter.weave.bootstrap.university.controller

import com.studentcenter.weave.bootstrap.university.api.UnivApi
import com.studentcenter.weave.bootstrap.university.dto.UniversityResponse
import com.studentcenter.weave.bootstrap.university.dto.MajorsResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversitiesResponse
import com.studentcenter.weave.bootstrap.common.exception.ApiExceptionType
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.uuid.UuidCreator
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UnivRestController : UnivApi {

    override fun findAll(): UniversitiesResponse {
        return UniversitiesResponse(listOf(
            UniversitiesResponse.UniversityDto(KU.id, KU.name, KU.domainAddress, KU.logoAddress),
            UniversitiesResponse.UniversityDto(DKU.id, DKU.name, DKU.domainAddress, DKU.logoAddress),
            UniversitiesResponse.UniversityDto(MJU.id, MJU.name, MJU.domainAddress, MJU.logoAddress),
        ))
    }

    override fun getAllMajorByUniv(@PathVariable univId: UUID): MajorsResponse {
        val majorDtos = if (KU_ID == univId) KU_MAJORS
        else if (DKU_ID == univId) DKU_MAJORS
        else if (MJU_ID == univId) MJU_MAJORS
        else throw CustomException(
            type = ApiExceptionType.INVALID_PARAMETER,
            message = "대학교를 찾을 수 없습니다",
        )
        return MajorsResponse(majorDtos)
    }

    override fun get(id: UUID): UniversityResponse {
        return if (KU_ID == id) KU
        else if (DKU_ID == id) DKU
        else if (MJU_ID == id) MJU
        else throw CustomException(
            type = ApiExceptionType.INVALID_PARAMETER,
            message = "대학교를 찾을 수 없습니다",
        )

    }

    companion object {
        private val KU_ID = UuidCreator.create()
        private val KU = UniversityResponse(KU_ID, "건국대학교", "konkuk.ac.kr", "public/university/$KU_ID/logo")
        private val DKU_ID = UuidCreator.create()
        private val DKU = UniversityResponse(DKU_ID, "단국대학교", "dankook.ac.kr", "public/university/$DKU_ID/logo")
        private val MJU_ID = UuidCreator.create()
        private val MJU = UniversityResponse(MJU_ID, "명지대학교", "mju.ac.kr", "public/university/$MJU_ID/logo")
        private val KU_MAJORS = listOf(
            "화장품공학과",
            "국제무역학과",
            "수학교육과",
            "산업디자인학과",
            "기계항공공학부",
            "K뷰티산업융합학과",
            "식품유통공학과",
            "현대미술학과",
            "철학과",
            "산업공학과",
            "부동산학과",
            "시스템생명공학과",
            "융합인재학과",
            "의상디자인학과",
            "응용통계학과",
            "일어교육과",
            "영어영문학과",
            "환경보건과학과",
            "생물공학과",
            "교육공학과",
            "국어국문학과",
            "경제학과",
            "기술경영학과",
            "수의예과",
            "화학과",
            "미래에너지공학과",
            "스마트운행체공학과",
            "화학공학부",
            "경영학과",
            "전기전자공학부",
            "영어교육과",
            "사회환경공학부",
            "커뮤니케이션디자인학과",
            "의생명공학과",
            "지리학과",
            "식량자원과학과",
            "문화콘텐츠학과",
            "스마트ICT융합공학과",
            "수학과",
            "생명과학특성학과",
            "건축학전공",
            "음악교육과",
            "글로벌비즈니스학과",
            "영상영화학과",
            "컴퓨터공학부",
            "줄기세포재생공학과",
            "정치외교학과",
            "물리학과",
            "중어중문학과",
            "신산업융합학과",
            "융합생명공학과",
            "동물자원과학과",
            "건축공학전공",
            "산림조경학과",
            "수의학과",
            "축산식품생명공학과",
            "리빙디자인학과",
            "행정학과",
            "사학과",
            "건축학부",
            "미디어커뮤니케이션학과",
            "체육교육과",
        ).map { MajorsResponse.MajorDto(UuidCreator.create(), it) }
        private val DKU_MAJORS = listOf(
            "국제스포츠학부 태권도전공",
            "생명자원학부 식량생명공학전공",
            "SW융합학부 글로벌SW융합전공",
            "음악학부 피아노전공",
            "식품영양학과",
            "SW융합학부",
            "경영학부 회계학전공",
            "과학교육과",
            "광고홍보전공",
            "한문교육과",
            "글로벌한국어과",
            "특수교육과",
            "경영학부 경영학전공",
            "영상콘텐츠전공",
            "보건행정학과",
            "국제학부",
            "공연영화학부 영화전공",
            "의학과",
            "자유교양대학(의학계열)",
            "SW융합대학",
            "유럽중남미학부 스페인중남미학전공",
            "자유교양대학(자연과학계열)",
            "법학과",
            "의예과",
            "자유교양대학(예체능계열)",
            "공연영화학부 연극전공",
            "자유교양대학(인문사회계열)",
            "사회과학대학",
            "문예창작과",
            "치위생학과",
            "아시아중동학부 중동학전공",
            "유럽중남미학부 독일학전공",
            "사회복지학과",
            "생활체육학과",
            "SW융합학부 SW융합법학전공",
            "유럽중남미학부",
            "영미인문학과",
            "아시아중동학부 베트남학전공",
            "디자인학부 패션산업디자인전공",
            "고분자공학전공",
            "약학대학",
            "정보통계학과",
            "국제스포츠학부 국제스포츠전공",
            "음악학부 국악전공",
            "음악학부 관현악전공",
            "약학과",
            "화학공학과",
            "전자전기공학부 전자전기공학전공",
            "도예과",
            "예술대학",
            "경제학과",
            "공연영화학부",
            "해병대군사학과",
            "의생명공학부 의생명공학전공",
            "아시아중동학부 몽골학전공",
            "생명과학부 미생물학전공",
            "전자전기공학부 융합반도체공학전공",
            "조소전공",
            "식품공학과",
            "스포츠경영학과",
            "경영학부",
            "문과대학",
            "아시아중동학부",
            "생명자원학부",
            "뉴뮤직과",
            "간호대학",
            "저널리즘전공",
            "공연영화학부 뮤지컬전공",
            "자유교양대학(공학계열)",
            "SW융합학부 SW융합경제경영전공",
            "법과대학",
            "체육교육과",
            "상담학과",
            "신소재공학과",
            "환경원예조경학부",
            "수학교육과",
            "공과대학",
            "음악학부 성악전공",
            "디자인학부 커뮤니케이션디자인전공",
            "생명공학대학",
            "미술학부",
            "음악학부 작곡전공",
            "의생명공학부",
            "임상병리학과",
            "토목환경공학과",
            "음악・예술대학",
            "융합시스템공학전공",
            "생명과학부 생명과학전공",
            "바이오헬스융합학부",
            "경영공학과",
            "국어국문학과",
            "서양화전공",
            "산업보안학과",
            "음악학부",
            "화학과",
            "아시아중동학부 중국학전공",
            "과학기술대학",
            "SW융합학부 SW융합바이오전공",
            "공예전공",
            "고분자시스템공학부",
            "도시지역계획학전공",
            "교직교육과",
            "무용과",
            "사범대학",
            "정치외교학과",
            "물리학과",
            "건축학부 건축학전공",
            "국제스포츠학부",
            "영어과",
            "환경원예조경학부 환경원예학전공",
            "물리치료학과",
            "사학과",
            "생명자원학부 동물생명공학전공",
            "건축학부",
            "기타모집단위",
            "국제학부 국제경영학전공",
            "고분자시스템공학부 파이버융합소재공학전공",
            "디자인학부",
            "심리치료학과",
            "SW융합학부 SW융합콘텐츠전공",
            "기계공학과",
            "공공정책학과",
            "소프트웨어학과",
            "국제스포츠학부 운동처방재활전공",
            "치과대학",
            "컴퓨터공학과",
            "동양화전공",
            "철학과",
            "산업경영학과",
            "경영경제대학",
            "유럽중남미학부 러시아학전공",
            "전자전기공학부",
            "환경원예조경학부 녹지조경학전공",
            "아시아중동학부 일본학전공",
            "환경자원경제학과",
            "생명과학부",
            "유럽중남미학부 포르투갈브라질학전공",
            "고분자시스템공학부 고분자공학전공",
            "수학과",
            "외국어대학",
            "치의예과",
            "스포츠과학대학",
            "건축학부 건축공학전공",
            "제약공학과",
            "도시계획부동산학부",
            "유럽중남미학부 프랑스학전공",
            "간호학과",
            "미디어커뮤니케이션학부",
            "부동산학전공",
            "모바일시스템공학과",
            "행정학과",
            "에너지공학과",
            "의과대학",
            "무역학과",
            "치의학과",
        ).map { MajorsResponse.MajorDto(UuidCreator.create(), it) }
        private val MJU_MAJORS = listOf(
            "융합전공학부(인문)",
            "전공자유학부",
            "예술학부(뮤지컬공연전공)",
            "식품영양학과",
            "창의융합인재학부",
            "건축대학",
            "공간디자인학과",
            "아동심리상담학과",
            "부동산학과",
            "청소년지도학과",
            "법무행정학과",
            "환경에너지공학과",
            "기계산업경영공학부",
            "경영정보학과",
            "국제학부",
            "인문학부",
            "법학과",
            "건축학부 공간디자인전공(5년제)",
            "자연과학대학",
            "미래사회인재학부",
            "예술학부(성악전공)",
            "미용예술학과",
            "산업경영공학과",
            "복지상담경영학과",
            "건축학부 건축학전공(5년제)",
            "반도체공학과",
            "바둑학과",
            "전기공학과",
            "전자공학과",
            "정보통신공학과",
            "사회복지학과",
            "스포츠학부",
            "ICT융합대학",
            "복지경영학과",
            "생명과학정보학과",
            "글로벌한국어학과",
            "디자인학부 패션디자인전공",
            "건축학부 전통건축전공(5년제)",
            "융합예술실용음악학과",
            "예술학부(피아노전공)",
            "웹툰콘텐츠학과",
            "화학공학과",
            "아동복지경영학과",
            "경제학과",
            "국제통상학과",
            "미래융합경영학과",
            "아랍지역학과",
            "미술사학과",
            "물류유통경영학과",
            "중어중문학과",
            "유통산업경영학과",
            "글로벌아시아문화학과",
            "신소재공학과",
            "공과대학",
            "멀티디자인학과",
            "일어일문학과",
            "융합소프트웨어학부",
            "영어영문학과",
            "토목환경공학과",
            "예술학부",
            "디지털콘텐츠디자인학과",
            "디자인학부 영상디자인전공",
            "융합디자인학과",
            "문예창작학과",
            "토목교통공학부",
            "국어국문학과",
            "화학과",
            "교통공학과",
            "정치외교학과",
            "화공신소재환경공학부",
            "물리학과",
            "예술학부(영화전공)",
            "융합공학부",
            "어문학부",
            "아동학과",
            "뮤직콘텐츠학과",
            "사학과",
            "기타모집단위",
            "청소년지도・아동학부",
            "디자인학부",
            "심리치료학과",
            "기계공학과",
            "스포츠산업경영학과",
            "아동보육상담학과",
            "컴퓨터공학과",
            "디지털미디어학과",
            "철학과",
            "건축학부(건축학전공/전통건축전공) (5년제)",
            "문헌정보학과",
            "미래융합대학",
            "경영학과",
            "환경생명공학과",
            "전기전자공학부",
            "디자인학부 산업디자인전공",
            "예술학부(아트앤멀티미디어작곡전공)",
            "경영대학",
            "수학과",
            "부동산지적GIS학과",
            "디자인학부 시각디자인전공",
            "스포츠학부 스포츠지도학전공",
            "행정학과",
            "인문대학",
            "교양학과",
            "만화애니콘텐츠학과",
        ).map { MajorsResponse.MajorDto(UuidCreator.create(), it) }
    }
}
