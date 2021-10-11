package sts.touhouspire.mod.character.marisa.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AbstractCardEnum {

	@SpireEnum
	public static AbstractCard.CardColor MARISA_COLOR;

	@SpireEnum(name = "Marisa Derivations")
	public static AbstractCard.CardColor MARISA_DERIVATIONS;

}