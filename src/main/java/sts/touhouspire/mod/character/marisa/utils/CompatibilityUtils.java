package sts.touhouspire.mod.character.marisa.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class CompatibilityUtils {

    public static void addPhiloStoneToMonster(AbstractMonster monster) {
        if (AbstractDungeon.player.hasRelic(Identifiers.Relics.PHILO_STONE)) {
            monster.addPower(new StrengthPower(monster, 1));
            AbstractDungeon.onModifyPower();
        }
    }


    public static void addModPowersToMonster(AbstractMonster monster) {
        if (ModHelper.isModEnabled(Identifiers.Mods.LETHALITY)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(monster, monster, new StrengthPower(monster, 3), 3)
            );
        }

        if (ModHelper.isModEnabled(Identifiers.Mods.TIME_DILATION)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(monster, monster, new SlowPower(monster, 0))
            );
        }
    }
}
