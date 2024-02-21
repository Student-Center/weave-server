package com.studentcenter.weave.domain.user.vo

@JvmInline
value class MbtiAffinityScore private constructor(val value: Int) {

    init {
        require(value in 1..5) { "Mbti 케미 점수는 1~5점 사이여야 해요!" }
    }

    companion object {

        fun Mbti.getAffinityScore(targetMbti: Mbti): MbtiAffinityScore {
            val uppercaseMbti1 = this.value.uppercase()
            val uppercaseMbti2 = targetMbti.value.uppercase()
            val index1 = mbtiAffinityScoreIndex[uppercaseMbti1]
                ?: throw IllegalArgumentException("Invalid MBTI")
            val index2 = mbtiAffinityScoreIndex[uppercaseMbti2]
                ?: throw IllegalArgumentException("Invalid MBTI")
            val score = mbtiAffinityScore[index1][index2]
            return MbtiAffinityScore(score)
        }

        private val mbtiAffinityScoreIndex = mapOf(
            "INFP" to 0,
            "ENFP" to 1,
            "INFJ" to 2,
            "ENFJ" to 3,
            "INTJ" to 4,
            "ENTJ" to 5,
            "INTP" to 6,
            "ENTP" to 7,
            "ISFP" to 8,
            "ESFP" to 9,
            "ISTP" to 10,
            "ESTP" to 11,
            "ISFJ" to 12,
            "ESFJ" to 13,
            "ISTJ" to 14,
            "ESTJ" to 15,
        )

        private val mbtiAffinityScore = listOf(
            listOf(4, 4, 4, 5, 4, 5, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2),
            listOf(4, 4, 5, 4, 5, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2),
            listOf(4, 5, 4, 4, 4, 4, 4, 5, 2, 2, 2, 2, 2, 2, 2, 2),
            listOf(5, 4, 4, 4, 4, 4, 4, 4, 5, 2, 2, 2, 2, 2, 2, 2),
            listOf(4, 5, 4, 4, 4, 4, 4, 5, 3, 3, 3, 3, 3, 3, 3, 3),
            listOf(5, 4, 4, 4, 4, 4, 5, 4, 3, 3, 3, 3, 3, 3, 3, 3),
            listOf(4, 4, 4, 4, 4, 5, 4, 4, 3, 3, 3, 3, 3, 3, 3, 5),
            listOf(4, 4, 5, 4, 5, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3),
            listOf(2, 2, 2, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5, 3),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 5),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 3, 3),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 3, 5, 3, 5, 4, 4, 4, 4),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 5, 3, 5, 3, 4, 4, 4, 4),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 3, 5, 3, 3, 4, 4, 4, 4),
            listOf(2, 2, 2, 2, 3, 3, 3, 3, 5, 3, 5, 3, 4, 4, 4, 4),
        )
    }

}
