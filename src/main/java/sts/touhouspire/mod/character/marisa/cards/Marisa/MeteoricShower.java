package sts.touhouspire.mod.character.marisa.cards.Marisa;

import sts.touhouspire.mod.character.marisa.action.MeteoricShowerAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MeteoricShower extends MarisaCard {

	public static final String ID = Identifiers.Cards.METEORIC_SHOWER;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/meteonic.png";

	private static final int COST = MarisaCard.COST_X;
	private static final int ATTACK_DAMAGE = 3;
	private static final int UPGRADE_DAMAGE = 1;


	public MeteoricShower() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);

		this.baseDamage = ATTACK_DAMAGE;
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {
		int count = EnergyPanel.totalCount + 1;
		if (player.hasRelic(Identifiers.Relics.CHEMICAL_X)) {
			count += 2;
		}

		this.addToBot(new MeteoricShowerAction(count, this.damage, this.freeToPlayOnce));
	}

	public AbstractCard makeCopy() {
		return new MeteoricShower();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
		}
	}
}