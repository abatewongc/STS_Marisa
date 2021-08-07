package io.aleosiss.sts.character.marisa.abstracts;

import basemod.abstracts.CustomCard;

public abstract class MarisaModCard extends CustomCard {
	public MarisaModCard(String id, String name, String imgUrl, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, imgUrl, cost, rawDescription, type, color, rarity, target);
	}
}
