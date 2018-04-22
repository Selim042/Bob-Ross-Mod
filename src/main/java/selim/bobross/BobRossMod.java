package selim.bobross;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = BobRossMod.MODID, name = BobRossMod.NAME, version = BobRossMod.VERSION,
		clientSideOnly = true)
public class BobRossMod {

	public static final String MODID = "happylittlemod";
	public static final String NAME = "Happy Little Mod";
	public static final String VERSION = "1.0.0";
	@Mod.Instance(value = MODID)
	public static BobRossMod instance;
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	private static final Random rand = new Random();
	private static final String[] PREFIXES = new String[] { "prefixes." + MODID + ":happy",
			"prefixes." + MODID + ":happy_little" };
	private static final HashMap<String, String> PREFIX_CACHE = new HashMap<String,String>();

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		List<String> tooltip = event.getToolTip();
		if (tooltip.size() < 1)
			return;
		String prevName = tooltip.get(0);
		tooltip.set(0, getDisplayName(prevName));
	}

	private static String getDisplayName(String prevName) {
		if (PREFIX_CACHE.containsKey(prevName))
			return PREFIX_CACHE.get(prevName);
		String newName = I18n.format(PREFIXES[rand.nextInt(PREFIXES.length)], prevName);
		PREFIX_CACHE.put(prevName,newName);
		return newName;
	}

}
