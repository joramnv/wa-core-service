package com.sparetimedevs.core.quest

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.UUID

@RepositoryRestResource(collectionResourceRel = "quests", path = "/quests")
interface QuestRepository : PagingAndSortingRepository<Quest, UUID> {

	fun findByUserId(@Param("USER_ID") userId: UUID): Iterable<Quest>
}
