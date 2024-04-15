package com.studentcenter.weave.domain.common

interface DomainEvent<T : DomainEntity> {

    val entity: T

}
