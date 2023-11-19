package com.mrbysco.roughlyenoughprofessions;

import com.mrbysco.roughlyenoughprofessions.compat.CompatibilityHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.IExtensionPoint.DisplayTest;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class RoughlyEnoughProfessionsNeoForge {

    public RoughlyEnoughProfessionsNeoForge() {
        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> "Trans Rights Are Human Rights",
                        (remoteVersionString, networkBool) -> networkBool));

        if (FMLEnvironment.dist == Dist.CLIENT) {
            NeoForge.EVENT_BUS.addListener(CompatibilityHelper::handleTooltips);
        }
    }
}