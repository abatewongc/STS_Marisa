package ThMod.characters;

import basemod.abstracts.CustomPlayer;

public abstract class Marisa extends CustomPlayer {
	public Marisa(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, String model, String animation) {
		super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, model, animation);
	}
}
