package com.ftbicolor.mixin;

import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
	@Inject(method = "clicked", at = @At("HEAD"), cancellable = true)
	private void ftbicolor$toggleCurioKit(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
		if (clickType != ClickType.PICKUP || button != 1 || slotId < 0) {
			return;
		}

		AbstractContainerMenu menu = (AbstractContainerMenu) (Object) this;
		if (slotId >= menu.slots.size() || !menu.getCarried().isEmpty()) {
			return;
		}

		Slot slot = menu.slots.get(slotId);
		if (!isCurioSlot(slot)) {
			return;
		}

		ItemStack stack = slot.getItem();
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem)) {
			return;
		}

		boolean active = FTBIColorSprayPaintKitColor.toggleCurioActive(stack);
		slot.setChanged();
		if (!player.level.isClientSide()) {
			player.displayClientMessage(FTBIColorText.translatable(active
					? "item.ftbicolor.spray_paint_kit.curio_active"
					: "item.ftbicolor.spray_paint_kit.curio_inactive"), true);
		}
		ci.cancel();
	}

	private static boolean isCurioSlot(Slot slot) {
		String name = slot.getClass().getName();
		return name.equals("top.theillusivec4.curios.common.inventory.CurioSlot")
				|| name.equals("top.theillusivec4.curios.common.inventory.CosmeticCurioSlot");
	}
}
