package com.mrbysco.roughlyenoughprofessions;

import com.mrbysco.roughlyenoughprofessions.compat.CompatibilityHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class RoughlyEnoughProfessionsNeoForge {

	public RoughlyEnoughProfessionsNeoForge(Dist dist) {
		if (dist.isClient()) {
			NeoForge.EVENT_BUS.addListener(CompatibilityHelper::handleTooltips);
		}
	}
}