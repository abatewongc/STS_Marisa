package sts.touhouspire.mod.character.marisa.abstracts;

public abstract class AmplifyCard extends MarisaCard {
    public AmplifyCard(String id, String name, String imgUrl, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, imgUrl, cost, rawDescription, type, color, rarity, target);
    }

    //protected abstract void amplify();
}
