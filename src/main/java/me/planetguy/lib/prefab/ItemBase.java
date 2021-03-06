package me.planetguy.lib.prefab;

import java.util.HashMap;
import java.util.List;

import me.planetguy.lib.util.Lang;
import me.planetguy.lib.util.LibProperties;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ItemBase extends Item implements IPrefabItem {

	private static String	modIDCache;

	public static void prepare(String modID) {
		modIDCache = modID;
	}

	@Deprecated
	public static IPrefabItem load(Class<? extends ItemBase> clazz, HashMap<String, IPrefabItem> map) {
		ItemBase item;
		try {
			item = clazz.newInstance();
			item.modID = modIDCache;
			GameRegistry.registerItem(item, item.name);
			map.put(item.name, item);
			return item;

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final String	name;

	public String		modID;

	public ItemBase(String name) {
		this.name = name;
		setUnlocalizedName(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		itemIcon = ir.registerIcon(modID + ":" + name);
	}

	// Tooltip infrastructure - shared between ItemBase and ItemBlockBase
	@Override
	public final void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedTooltipsActive) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			describe(stack, player, lines);
		} else {
			lines.add(Lang.translate(LibProperties.instructionsShowTooltip));
		}
	}

	public void describe(ItemStack stack, EntityPlayer player, List<String> lines) {
		for (int i = 1; i <= countTooltipLines(); i++) {
			lines.add(replaceInTooltips(Lang.translate(this.getUnlocalizedName() + ".tooltip." + i), stack, player));
		}
	}

	public int countTooltipLines() {
		return 2;
	}

	@Override
	public void loadCrafting() {}

	public String replaceInTooltips(String s, ItemStack item, EntityPlayer player) {
		return s;
	}

}
