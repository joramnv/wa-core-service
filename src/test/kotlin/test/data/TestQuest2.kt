package test.data

import com.sparetimedevs.core.quest.Quest
import test.userId1
import java.util.UUID

private const val MOST_SIG_BITS_QUEST_ID_2 = 87654L
private const val LEAST_SIG_BITS_QUEST_ID_2 = 45678L
private const val NAME_2 = "The name of quest two"
private const val DESCRIPTION_2 = "Test description regarding this quest"
private const val ACHIEVEMENT_POINTS_2 = 10L

val questId2 = UUID(MOST_SIG_BITS_QUEST_ID_2, LEAST_SIG_BITS_QUEST_ID_2)
val testQuest2 = Quest(questId2, userId1, NAME_2, DESCRIPTION_2, ACHIEVEMENT_POINTS_2)
