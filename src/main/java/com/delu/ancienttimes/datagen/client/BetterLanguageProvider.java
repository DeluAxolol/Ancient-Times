package com.delu.ancienttimes.datagen.client;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class BetterLanguageProvider implements DataProvider {

    protected final Map<String, String> data = new TreeMap<>();
    protected final PackOutput output;
    protected final String modid;
    protected final String locale;

    public BetterLanguageProvider(PackOutput output, String modid, String locale) {
        this.output = output;
        this.modid = modid;
        this.locale = locale;
    }

    protected abstract void addTranslations();

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.data.clear();
        addTranslations();

        if (!data.isEmpty())
            return save(cache, this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(this.modid).resolve("lang").resolve(this.locale + ".json"));

        return CompletableFuture.allOf();
    }

    @Override
    public String getName() {
        return "Languages: " + locale;
    }

    private CompletableFuture<?> save(CachedOutput cache, Path target) {
        // TODO: DataProvider.saveStable handles the caching and hashing already, but creating the JSON Object this way seems unreliable. -C
        JsonObject json = new JsonObject();
        this.data.forEach(json::addProperty);

        return DataProvider.saveStable(cache, json, target);
    }

    public void simpleEntity(EntityType<?> type){
        this.add(type, toTitleCase(ForgeRegistries.ENTITY_TYPES.getKey(type).getPath()));
    }

    public void simpleEffect(MobEffect effect){
        this.add(effect, toTitleCase(ForgeRegistries.MOB_EFFECTS.getKey(effect).getPath()));
    }

    public void simpleItem(Item item){
        this.add(item, toTitleCase(ForgeRegistries.ITEMS.getKey(item).getPath()));
    }

    public void addBlock(Supplier<? extends Block> key, String name) {
        add(key.get(), name);
    }

    public void add(Block key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addSimpleBlock(Block key) {
        add(key.getDescriptionId(), toTitleCase(ForgeRegistries.BLOCKS.getKey(key).getPath()));
    }

    public void addItem(Supplier<? extends Item> key, String name) {
        add(key.get(), name);
    }

    public void add(Item key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addItemStack(Supplier<ItemStack> key, String name) {
        add(key.get(), name);
    }

    public void add(ItemStack key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEnchantment(Supplier<? extends Enchantment> key, String name) {
        add(key.get(), name);
    }

    public void add(Enchantment key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEffect(Supplier<? extends MobEffect> key, String name) {
        add(key.get(), name);
    }

    public void add(MobEffect key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        add(key.get(), name);
    }

    public void add(EntityType<?> key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void add(String key, String value) {
        if (data.put(key, value) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();

        // Handling custom names for specific cases
        switch (input) {
            case "meal_log":
                return "Mealwood Log";
            case "stripped_meal_log":
                return "Stripped Mealwood Log";
            case "meal_planks":
                return "Mealwood Planks";
            case "meal_leaves":
                return "Mealwood Leaves";
            case "meal_trapdoor":
                return "Mealwood Trapdoor";
            case "meal_door":
                return "Mealwood Door";
            case "meal_slab":
                return "Mealwood Slab";
            case "meal_sapling":
                return "Mealwood Sapling";
            case "meal_stairs":
                return "Mealwood Stairs";
            case "diamond_chisel":
                return "Chisel";
            case "sus_andesite":
                return "Sus Stone";
            default:
                // For general cases, convert to title case
                boolean nextTitleCase = true;
                for (char c : input.toCharArray()) {
                    if (c == '_') {
                        nextTitleCase = true;
                        titleCase.append(" ");
                        continue;
                    }

                    if (nextTitleCase) {
                        c = Character.toTitleCase(c);
                        nextTitleCase = false;
                    } else {
                        c = Character.toLowerCase(c);
                    }

                    titleCase.append(c);
                }
                return titleCase.toString();
        }
    }

}

