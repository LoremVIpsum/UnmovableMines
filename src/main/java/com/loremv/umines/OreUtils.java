package com.loremv.umines;


import com.loremv.umines.items.ItemWithChemical;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;


import java.util.HashMap;
import java.util.List;

public class OreUtils {

    public static HashMap<String, Item> ELEMENT_ITEM_MAP = new HashMap<>();
    public static final HashMap<Integer, String> ELEMENTS = new HashMap<>();
    static
    {
        ELEMENTS.put(1,"hydrogen");
        ELEMENTS.put(2,"helium");
        ELEMENTS.put(3,"lithium");
        ELEMENTS.put(4,"beryllium");
        ELEMENTS.put(5,"boron");
        ELEMENTS.put(6,"carbon");
        ELEMENTS.put(7,"nitrogen");
        ELEMENTS.put(8,"oxygen");
        ELEMENTS.put(9,"fluorine");
        ELEMENTS.put(10,"neon");
        ELEMENTS.put(11,"sodium");
        ELEMENTS.put(12,"magnesium");
        ELEMENTS.put(13,"aluminum");
        ELEMENTS.put(14,"silicon");
        ELEMENTS.put(15,"phosphorus");
        ELEMENTS.put(16,"sulfur");
        ELEMENTS.put(17,"chlorine");
        ELEMENTS.put(18,"argon");
        ELEMENTS.put(19,"potassium");
        ELEMENTS.put(20,"calcium");
        ELEMENTS.put(21,"scandium");
        ELEMENTS.put(22,"titanium");
        ELEMENTS.put(23,"vanadium");
        ELEMENTS.put(24,"chromium");
        ELEMENTS.put(25,"manganese");
        ELEMENTS.put(26,"iron");
        ELEMENTS.put(27,"cobalt");
        ELEMENTS.put(28,"nickel");
        ELEMENTS.put(29,"copper");
        ELEMENTS.put(30,"zinc");
        ELEMENTS.put(31,"gallium");
        ELEMENTS.put(32,"germanium");
        ELEMENTS.put(33,"arsenic");
        ELEMENTS.put(34,"selenium");
        ELEMENTS.put(35,"bromine");
        ELEMENTS.put(36,"krypton");
        ELEMENTS.put(37,"rubidium");
        ELEMENTS.put(38,"strontium");
        ELEMENTS.put(39,"yttrium");
        ELEMENTS.put(40,"zirconium");
        ELEMENTS.put(41,"niobium");
        ELEMENTS.put(42,"molybdenum");
        ELEMENTS.put(43,"technetium");
        ELEMENTS.put(44,"ruthenium");
        ELEMENTS.put(45,"rhodium");
        ELEMENTS.put(46,"palladium");
        ELEMENTS.put(47,"silver");
        ELEMENTS.put(48,"cadmium");
        ELEMENTS.put(49,"indium");
        ELEMENTS.put(50,"tin");
        ELEMENTS.put(51,"antimony");
        ELEMENTS.put(52,"tellurium");
        ELEMENTS.put(53,"iodine");
        ELEMENTS.put(54,"xenon");
        ELEMENTS.put(55,"cesium");
        ELEMENTS.put(56,"barium");
        ELEMENTS.put(57,"lanthanum");
        ELEMENTS.put(58,"cerium");
        ELEMENTS.put(59,"praseodymium");
        ELEMENTS.put(60,"neodymium");
        ELEMENTS.put(61,"promethium");
        ELEMENTS.put(62,"samarium");
        ELEMENTS.put(63,"europium");
        ELEMENTS.put(64,"gadolinium");
        ELEMENTS.put(65,"terbium");
        ELEMENTS.put(66,"dysprosium");
        ELEMENTS.put(67,"holmium");
        ELEMENTS.put(68,"erbium");
        ELEMENTS.put(69,"thulium");
        ELEMENTS.put(70,"ytterbium");
        ELEMENTS.put(71,"lutetium");
        ELEMENTS.put(72,"hafnium");
        ELEMENTS.put(73,"tantalum");
        ELEMENTS.put(74,"tungsten");
        ELEMENTS.put(75,"rhenium");
        ELEMENTS.put(76,"osmium");
        ELEMENTS.put(77,"iridium");
        ELEMENTS.put(78,"platinum");
        ELEMENTS.put(79,"gold");
        ELEMENTS.put(80,"mercury");
        ELEMENTS.put(81,"thallium");
        ELEMENTS.put(82,"lead");
        ELEMENTS.put(83,"bismuth");
        ELEMENTS.put(84,"polonium");
        ELEMENTS.put(85,"astatine");
        ELEMENTS.put(86,"radon");
        ELEMENTS.put(87,"francium");
        ELEMENTS.put(88,"radium");
        ELEMENTS.put(89,"actinium");
        ELEMENTS.put(90,"thorium");
        ELEMENTS.put(91,"protactinium");
        ELEMENTS.put(92,"uranium");
        ELEMENTS.put(93,"neptunium");
        ELEMENTS.put(94,"plutonium");
        ELEMENTS.put(95,"americium");
        ELEMENTS.put(96,"curium");
        ELEMENTS.put(97,"berkelium");
        ELEMENTS.put(98,"californium");
        ELEMENTS.put(99,"einsteinium");
        ELEMENTS.put(100,"fermium");
        ELEMENTS.put(101,"mendelevium");
        ELEMENTS.put(102,"nobelium");
        ELEMENTS.put(103,"lawrencium");
        ELEMENTS.put(104,"rutherfordium");
        ELEMENTS.put(105,"dubnium");
        ELEMENTS.put(106,"seaborgium");
        ELEMENTS.put(107,"bohrium");
        ELEMENTS.put(108,"hassium");
        ELEMENTS.put(109,"meitnerium");
        ELEMENTS.put(110,"darmstadtium");
        ELEMENTS.put(111,"roentgenium");
        ELEMENTS.put(112,"copernicium");
        ELEMENTS.put(113,"nihonium");
        ELEMENTS.put(114,"flerovium");
        ELEMENTS.put(115,"moscovium");
        ELEMENTS.put(116,"livermorium");
        ELEMENTS.put(117,"tennessine");
        ELEMENTS.put(118,"oganesson");

    }
    public static final HashMap<String, int[]> ORES = new HashMap<>();
    public static final HashMap<String,int[]> ALT_ORES = new HashMap<>();
    static
    {
        ORES.put("Bauxite",new int[]{13,13,13,31,23});
        ORES.put("Pyrite",new int[]{26,16,16});
        ORES.put("Galena",new int[]{82,47,16});
        ORES.put("Carnotite",new int[]{19,19,92,23});
        ORES.put("Malachite",new int[]{29,29,6});
        ORES.put("Smithsonite",new int[]{30,30,6,48});
        ORES.put("Magnesite",new int[]{12,6});
        ORES.put("Hemimorphite",new int[]{30,30,30,30,14,14});
        ORES.put("Monazite-Ce",new int[]{58,57,60,90});
        ALT_ORES.put("Monazite-Sm",new int[]{62,64,58,90});
        ALT_ORES.put("Halite",new int[]{11,17});
        ALT_ORES.put("Romanechite",new int[]{56,25});
        ORES.put("Pentlandite",new int[]{26,28,16});
        ORES.put("Chromite",new int[]{12,26,24});
        ORES.put("Stibnite",new int[]{51,51,16,16,16});
        ORES.put("Rutile",new int[]{22});
        ORES.put("Proustite",new int[]{47,47,47,33,16,16,16});
        ORES.put("Lepidolite",new int[]{19,3,13,14,37});
        ORES.put("Sphalerite",new int[]{30,26});
        ORES.put("Cassiterite",new int[]{50});
        ORES.put("Chalcopyrite",new int[]{26,29,16,16});
        ORES.put("Petzite",new int[]{47,79,52});
    }

    public static final List<String> keys = OreUtils.ORES.keySet().stream().toList();

    public static HashMap<String,Item> REGISTRY = new HashMap<>();
    public static boolean registerOres(DeferredRegister<Item> reg)
    {
        for(String a: ORES.keySet())
        {
            reg.register(a.toLowerCase(),()->{
                Item item = new ItemWithChemical(new Item.Properties(),ORES.get(a),a);
                REGISTRY.put(a.toLowerCase(),item);
                return item;
            });

            //ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(UnmovableMines.TAB).get()).register(content -> content.add(item));
        }
        return true;
    }

    public static void setElementItemMap()
    {
        //UnmovableMines.LOGGER.info("guessing raw ores from elements");
        List<Item> dusts = BuiltInRegistries.ITEM.stream().filter(a->BuiltInRegistries.ITEM.getKey(a).getPath().contains("raw")||BuiltInRegistries.ITEM.getKey(a).getPath().contains("dust")).toList();
        for(String element: ELEMENTS.values())
        {
            if(AltOreLoader.MAPPED.containsKey(element))
            {
                ELEMENT_ITEM_MAP.put(element,BuiltInRegistries.ITEM.get(ResourceLocation.parse(AltOreLoader.MAPPED.get(element))));
                continue;
            }
            for(Item dust: dusts)
            {
                if(BuiltInRegistries.ITEM.getKey(dust).getPath().equals(element+"_dust"))
                {
                    ELEMENT_ITEM_MAP.put(element,dust);
                    break;
                }
                if(BuiltInRegistries.ITEM.getKey(dust).getPath().equals("raw_"+element))
                {
                    ELEMENT_ITEM_MAP.put(element,dust);
                    break;
                }
            }
        }
        //UnmovableMines.LOGGER.info("raw ores guessed, "+ELEMENT_ITEM_MAP.keySet().size()+" found");
    }

}
