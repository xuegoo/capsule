package capsule.enchantments;

import capsule.Config;
import capsule.Main;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;

public class Enchantments {

    protected static final Logger LOGGER = LogManager.getLogger(Enchantments.class);

    public static Enchantment recallEnchant = null;
    @SuppressWarnings("rawtypes")
    public static final Predicate hasRecallEnchant = new Predicate() {
        public boolean apply(Entity entityIn) {
            return entityIn instanceof EntityItem
                    && EnchantmentHelper.getEnchantmentLevel(Enchantments.recallEnchant, ((EntityItem) entityIn).getItem()) > 0;
        }

        public boolean test(Object obj) {
            return this.apply((Entity) obj);
        }
    };

    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {

        Rarity enchantRarity = Rarity.RARE;
        try {
            enchantRarity = Rarity.valueOf(Config.enchantRarity);
        } catch (Exception e) {
            LOGGER.warn("Couldn't find the rarity " + Config.enchantRarity + ". Using RARE instead.");
        }

        EnumEnchantmentType recallEnchantTypeEnumValue = null;
        try {
            recallEnchantTypeEnumValue = EnumEnchantmentType.valueOf(Config.recallEnchantType);
        } catch (IllegalArgumentException ignored) {
        }

        Enchantments.recallEnchant = new RecallEnchant(
                new ResourceLocation(Main.MODID, "recall"), // name
                enchantRarity, // weight (chances to appear)
                recallEnchantTypeEnumValue // possible targets
        );

        event.getRegistry().register(Enchantments.recallEnchant);
    }
}
