package eyeq.youseisannagagutsu.item;

import eyeq.util.UItemArmor;
import eyeq.util.entity.player.EntityPlayerUtils;
import eyeq.youseisannagagutsu.YouseisanNagagutsu;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRainBoots extends UItemArmor {
    private static final ResourceLocation armorName = new ResourceLocation(YouseisanNagagutsu.MOD_ID, "rain_boots");

    public ItemRainBoots(int renderIndex, EntityEquipmentSlot equipmentSlot) {
        super(ArmorMaterial.LEATHER, renderIndex, equipmentSlot, armorName);
        this.setMaxDamage(200);
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(type == null && slot == EntityEquipmentSlot.FEET) {
            return super.getArmorTexture(itemStack, entity, slot, type);
        }
        return null;
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return itemStack.getItemDamage() > 0;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    protected void onArmorTickFeet(World world, EntityPlayer player, ItemStack itemStack) {
        int damage = itemStack.getItemDamage();
        if(!player.onGround) {
            if(damage == 0) {
                player.fallDistance = 1.0F;
            }
            return;
        }
        itemStack.setItemDamage(damage - 1);
    }


    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(itemStack.getItemDamage() == 0) {
            if(EntityPlayerUtils.onItemPlace(player, world, itemStack, pos, facing, Blocks.WATER.getDefaultState())) {
                itemStack.setItemDamage(itemStack.getMaxDamage());
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
}
