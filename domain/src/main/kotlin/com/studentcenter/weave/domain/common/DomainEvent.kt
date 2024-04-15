package com.studentcenter.weave.domain.common

interface DomainEvent<T : AggregateRoot> {

    val entity: T

}
