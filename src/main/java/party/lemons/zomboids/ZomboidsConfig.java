package party.lemons.zomboids;

import net.minecraftforge.common.config.Config;

/**
 * Created by Sam on 5/11/2018.
 */
@Config(modid = Zomboids.MODID)
public class ZomboidsConfig
{
    public static boolean USE_SHADER = true;
    public static boolean DO_MENU = true;
    public static String SHADER = "zomboids:shaders/desat.json";
}
