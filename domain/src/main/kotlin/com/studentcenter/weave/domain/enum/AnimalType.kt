package com.studentcenter.weave.domain.enum

enum class AnimalType(private val description: String) {
    PUPPY("🐶강아지상"),
    CAT("🐱고양이상"),
    HEDGEHOG("🦔고슴도치상"),
    FOX("🦊여우상"),
    RABBIT("🐰토끼상"),
    TIGER("🐯호랑이상"),
    MONKEY("🐵원숭이상"),
    TURTLE("🐢꼬부기상"),
    DEER("🦌사슴상"),
    HAMSTER("🐹햄스터상"),
    WOLF("🐺늑대상"),
    TEDDY_BEAR("🧸곰돌이상"),
    PANDA("🐼판다상"),
    SNAKE("🐍뱀상"),
    OTTER("🦦수달상"),
    FISH("🐠물고기상"),
    CHICK("🐤병아리상"),
    DINOSAUR("🦕공룡상"),
    HORSE("🐴말상"),
    SLOTH("🦥나무늘보상"),
    LION("🦁사자상"),
    CAMEL("🐪낙타상");

    override fun toString(): String {
        return this.description
    }
}
