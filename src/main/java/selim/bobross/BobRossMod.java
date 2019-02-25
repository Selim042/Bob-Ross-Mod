package selim.bobross;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBucketFish;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(BobRossMod.MODID)
public class BobRossMod {

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

	@Mod.EventBusSubscriber(Dist.CLIENT)
	public static class EventHandler {

		@SubscribeEvent
		public static void onTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			List<ITextComponent> tooltip = event.getToolTip();
			if (tooltip.size() < 1)
				return;
			ITextComponent prevName = tooltip.get(0);
			if (stack != null && stack.getItem() instanceof ItemBucketFish) {
				if (PREFIX_CACHE.containsKey(prevName)) {
					tooltip.set(0, PREFIX_CACHE.get(prevName));
					return;
				}
				String[] parts = prevName.getFormattedText()
						.split(I18n.format("misc." + MODID + ".fish_bucket_seperator"));
				ITextComponent newName = new TextComponentTranslation(
						PREFIXES[rand.nextInt(PREFIXES.length)] + "_fish_bucket", parts[0], parts[1]);
				PREFIX_CACHE.put(prevName, newName);
				tooltip.set(0, newName);
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