package org.samu.crackshotRemake.weapon.enums

import org.bukkit.event.block.Action

enum class ShotKey(val actions: List<Action>) {
    LEFT_CLICK(listOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)),
    RIGHT_CLICK(listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)),
    BOTH(listOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK));

    fun matches(action: Action): Boolean {
        return actions.contains(action)
    }
}
