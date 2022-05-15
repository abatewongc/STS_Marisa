package ThMod;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;

public class ThMod {
    public static boolean Amplified(AbstractCard card, int cost) {
        return AmplifyUtils.Amplify(card, cost);
    }

    public static AbstractCard getRandomMarisaCard() {
        return MarisaHelpers.getRandomMarisaCard();
    }
}