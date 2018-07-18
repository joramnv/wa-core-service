package test.data

import com.sparetimedevs.core.quest.Quest
import test.userId1
import java.util.UUID

private const val MOST_SIG_BITS_QUEST_ID_1 = 65432L
private const val LEAST_SIG_BITS_QUEST_ID_1 = 23456L
private const val NAME_1 = "The name of the quest"
private const val DESCRIPTION_1 = "Test description 101"
private const val ACHIEVEMENT_POINTS_1 = 5L

val questId1 = UUID(MOST_SIG_BITS_QUEST_ID_1, LEAST_SIG_BITS_QUEST_ID_1)
val testQuest1 = Quest(questId1, userId1, NAME_1, DESCRIPTION_1, ACHIEVEMENT_POINTS_1)
