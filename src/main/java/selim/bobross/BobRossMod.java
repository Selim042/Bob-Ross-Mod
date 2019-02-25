package selim.bobross;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod(BobRossMod.MODID)
public class BobRossMod {

	static {
		try {
			FileWriter writer = new FileWriter(new File("blah.txt"));
			writer.write("hi");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String MODID = "happylittlemod";
	public static final String NAME = "Happy Little Mod";
	public static final String VERSION = "1.1.0";
	// @Mod.Instance(value = MODID)
	// public static BobRossMod instance;
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	private static final Random rand = new Random();
	private static final String[] PREFIXES = new String[] { "prefixes." + MODID + ".happy",
			"prefixes." + MODID + ".happy_little" };
	private static final HashMap<ITextComponent, ITextComponent> PREFIX_CACHE = new HashMap<>();

	@Mod.EventBusSubscriber
	public static class EventHandler {

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void onTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			List<ITextComponent> tooltip = event.getToolTip();
			System.out.println("blerp");
			if (tooltip.size() < 1)
				return;
			ITextComponent prevName = tooltip.get(0);
			if (stack != null && stack.getItem() instanceof ItemSpawnEgg) {
				ITextComponent translatedSpawn = new TextComponentTranslation("item.monsterPlacer.name");
				// prevName = prevName.replace(translatedSpawn, "").trim();
				tooltip.set(0, new TextComponentString(
						translatedSpawn + " " + translatedSpawn.getFormattedText()));
			} else
				tooltip.set(0, getDisplayName(prevName));
		}

		private static ITextComponent getDisplayName(ITextComponent prevName) {
			if (PREFIX_CACHE.containsKey(prevName))
				return PREFIX_CACHE.get(prevName);
			ITextComponent newName = new TextComponentTranslation(
					PREFIXES[rand.nextInt(PREFIXES.length)], prevName);
			PREFIX_CACHE.put(prevName, newName);
			return newName;
		}

	}

}