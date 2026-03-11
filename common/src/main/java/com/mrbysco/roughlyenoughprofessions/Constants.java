package com.mrbysco.roughlyenoughprofessions;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final String MOD_ID = "roughlyenoughprofessions";
	public static final String MOD_NAME = "Roughly Enough Professions";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}