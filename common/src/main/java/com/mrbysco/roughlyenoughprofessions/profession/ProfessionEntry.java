package com.mrbysco.roughlyenoughprofessions.profession;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.roughlyenoughprofessions.VillagerCache;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ProfessionEntry(Holder<VillagerProfession> profession, List<ItemStack> blockStacks) {
	private static final Codec<Holder<VillagerProfession>> VILLAGER_CODEC = BuiltInRegistries.VILLAGER_PROFESSION
			.holderByNameCodec();
	public static final Codec<ProfessionEntry> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					VILLAGER_CODEC.fieldOf("profession").forGetter(ProfessionEntry::profession),
					ItemStack.CODEC.listOf().fieldOf("blockStacks").forGetter(ProfessionEntry::blockStacks)
			).apply(instance, ProfessionEntry::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, ProfessionEntry> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.fromCodecWithRegistries(VILLAGER_CODEC),
			ProfessionEntry::profession,
			ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
			ProfessionEntry::blockStacks,
			ProfessionEntry::new
	);

	@Nullable
	public Villager getVillagerEntity() {
		return VillagerCache.getVillagerEntity(this.profession);
	}
}
