package com.studentcenter.weave.domain.user.vo

@JvmInline
value class Mbti(val value: String) {

    companion object {

        private const val VALIDATION_REGEX = "^(I|E|i|e)(S|N|s|n)(T|F|t|f)(J|P|j|p)\$"
        private const val MBTI_UPPERCASE_SCORE = 2
        private const val MBTI_LOWERCASE_SCORE = 1

        fun getDominantMbti(mbtis: List<Mbti>): Mbti {
            val mbtiScores = mutableMapOf(
                'E' to 0, 'I' to 0, 'N' to 0, 'S' to 0, 'F' to 0, 'T' to 0, 'P' to 0, 'J' to 0
            )

            mbtis.forEach { mbti ->
                mbti.value.forEach {
                    val key = it.uppercaseChar()
                    val score = if (it.isUpperCase()) MBTI_UPPERCASE_SCORE else MBTI_LOWERCASE_SCORE
                    mbtiScores[key] = mbtiScores.getOrDefault(key, 0) + score
                }
            }

            return Mbti(buildString {
                append(selectDominantTrait('E', 'I', mbtiScores))
                append(selectDominantTrait('N', 'S', mbtiScores))
                append(selectDominantTrait('F', 'T', mbtiScores))
                append(selectDominantTrait('P', 'J', mbtiScores))
            })
        }

        private fun selectDominantTrait(
            trait1: Char,
            trait2: Char,
            scores: Map<Char, Int>
        ): Char {
            val score1 = scores.getOrDefault(trait1, 0)
            val score2 = scores.getOrDefault(trait2, 0)
            return if (score1 >= score2) trait1 else trait2
        }
    }

    init {
        require(value.matches(Regex(VALIDATION_REGEX))) {
            "MBTI는 I/E, S/N, T/F, J/P로 이루어진 4글자의 문자열이어야 합니다.(소문자 허용)"
        }
    }
}
