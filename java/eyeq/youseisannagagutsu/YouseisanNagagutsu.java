package eyeq.youseisannagagutsu;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.oredict.UOreDictionary;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.youseisannagagutsu.item.ItemRainBoots;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.youseisannagagutsu.YouseisanNagagutsu.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class YouseisanNagagutsu {
    public static final String MOD_ID = "eyeq_youseisannagagutsu";

    @Mod.Instance(MOD_ID)
    public static YouseisanNagagutsu instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item rainBoots;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        rainBoots = new ItemRainBoots(0, EntityEquipmentSlot.FEET).setUnlocalizedName("rainBoots");

        GameRegistry.register(rainBoots, resource.createResourceLocation("rain_boots"));
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rainBoots), "XSX", "XWX",
                'X', UOreDictionary.OREDICT_LEATHER, 'S', UOreDictionary.OREDICT_SWEET, 'W', Items.WATER_BUCKET));
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(rainBoots);
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-YouseisanNagagutsu");
    	
        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, rainBoots, "Rain Boots");
        language.register(LanguageResourceManager.JA_JP, rainBoots, "長靴");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, rainBoots, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
