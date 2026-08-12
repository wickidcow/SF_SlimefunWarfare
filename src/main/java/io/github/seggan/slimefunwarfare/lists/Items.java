package io.github.seggan.slimefunwarfare.lists;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class Items {

    // region explosives
    public static final SlimefunItemStack PYRO_POWDER = new SlimefunItemStack(
        "PYRO_POWDER",
        Material.REDSTONE,
        "&4火药粉",
        "",
        "&7确实是一种极易爆炸的物质"
    );
    public static final SlimefunItemStack LIQUID_AIR = new SlimefunItemStack(
        "LIQUID_AIR",
        HeadTexture.TIN_CAN,
        "&f液化空气",
        "",
        "&7不要碰!"
    );
    public static final SlimefunItemStack LIQUID_NITROGEN = new SlimefunItemStack(
        "LIQUID_NITROGEN",
        HeadTexture.TIN_CAN,
        "&f液氮",
        "",
        "&7有人要冰淇淋吗?"
    );
    public static final SlimefunItemStack PURIFIED_LIQUID_NITROGEN = new SlimefunItemStack(
        "PURIFIED_LIQUID_NITROGEN",
        HeadTexture.TIN_CAN,
        "&f纯化液氮",
        "",
        "&7尽可能纯净"
    );
    public static final SlimefunItemStack THIOACETONE = new SlimefunItemStack(
        "THIOACETONE",
        Material.BROWN_DYE,
        "&6硫代丙酮",
        "",
        "&7确实很臭"
    );
    public static final SlimefunItemStack NITROGEN_TRIIODIDE = new SlimefunItemStack(
        "NITROGEN_TRIIODIDE",
        Material.PURPLE_DYE,
        "&5三碘化氮",
        "",
        "&7一种用于制造手榴弹的材料"
    );
    public static final SlimefunItemStack AZIDOAZIDE_AZIDE = new SlimefunItemStack(
        "AZIDOAZIDE_AZIDE",
        Material.SUGAR,
        "&e叠氮化碳",
        "",
        "&7一种用于制造手榴弹的材料"
    );
    public static final SlimefunItemStack ARSENIC = new SlimefunItemStack(
        "ARSENIC",
        Material.GUNPOWDER,
        "&7砷",
        "",
        "&7一种用于制造手榴弹的材料"
    );
    public static final SlimefunItemStack ENRICHED_URANIUM = new SlimefunItemStack(
        "ENRICHED_URANIUM",
        HeadTexture.BOOSTED_URANIUM,
        "&a浓缩铀",
        "",
        LoreBuilder.radioactive(Radioactivity.VERY_DEADLY)
    );
    public static final SlimefunItemStack EMPTY_GRENADE = new SlimefunItemStack(
        "GRENADE",
        Material.SNOWBALL,
        "&f化学手榴弹",
        "",
        "&7包含特殊物质: 无"
    );
    public static final SlimefunItemStack REINFORCED_CONCRETE = new SlimefunItemStack(
        "REINFORCED_CONCRETE",
        Material.GRAY_CONCRETE,
        "&7钢筋混凝土",
        "",
        "&7一种防爆的混凝土(不完全免疫爆炸)"
    );
    public static final SlimefunItemStack NUCLEAR_BOMB = new SlimefunItemStack(
        "NUCLEAR_BOMB",
        Material.TNT,
        "&7核弹",
        "",
        "&7KABOOM!",
        LoreBuilder.radioactive(Radioactivity.VERY_DEADLY)
    );
    // endregion

    // region general
    public static final SlimefunItemStack BORAX = new SlimefunItemStack(
        "BORAX",
        Material.QUARTZ,
        "&f硼砂",
        "",
        "&7从石头中掉落的一种常见的矿物"
    );
    public static final SlimefunItemStack BORON = new SlimefunItemStack(
        "BORON",
        Material.CHARCOAL,
        "&7硼"
    );
    public static final SlimefunItemStack SLIMESTEEL = new SlimefunItemStack(
        "SLIMESTEEL_INGOT",
        Material.IRON_INGOT,
        "&a软钢锭",
        "",
        "坚硬而有弹性的一种材料"
    );

    public static final SlimefunItemStack REINFORCED_SLIMESTEEL = new SlimefunItemStack(
        "REINFORCED_SLIMESTEEL_INGOT",
        Material.IRON_INGOT,
        "&a强化软钢锭",
        "",
        "坚硬而有弹性的一种材料"
    );

    public static final SlimefunItemStack SCOPE = new SlimefunItemStack(
        "SCOPE",
        Material.STICK,
        "&a瞄准镜"
    );

    public static final SlimefunItemStack BARREL = new SlimefunItemStack(
        "BARREL",
        Material.STICK,
        "&7枪管"
    );

    public static final SlimefunItemStack ADVANCED_BARREL = new SlimefunItemStack(
        "ADVANCED_BARREL",
        Material.STICK,
        "&7高级枪管"
    );

    public static final SlimefunItemStack BULLET_PRESS = new SlimefunItemStack(
        "BULLET_PRESS",
        Material.SMOKER,
        "&7子弹制造机",
        "",
        LoreBuilder.powerPerSecond(16),
        LoreBuilder.powerBuffer(32),
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE)
    );

    public static final SlimefunItemStack AIR_LIQUEFIER = new SlimefunItemStack(
        "AIR_LIQUEFIER",
        Material.BEACON,
        "&b空气液化器",
        "",
        LoreBuilder.powerPerSecond(64),
        LoreBuilder.powerBuffer(128),
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE)
    );

    public static final SlimefunItemStack EXPLOSIVE_SYNTHESIZER = new SlimefunItemStack(
        "EXPLOSIVE_SYNTHESIZER",
        Material.TNT,
        "&4爆炸物合成器",
        "",
        LoreBuilder.powerPerSecond(64),
        LoreBuilder.powerBuffer(128),
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE)
    );

    public static final SlimefunItemStack BOOMINATOR_9000 = new SlimefunItemStack(
        "BOOMINATOR_9000",
        Material.SMITHING_TABLE,
        "&4助泡剂 9000",
        "",
        "&7处理用于核弹的铀",
        LoreBuilder.powerPerSecond(1024),
        LoreBuilder.powerBuffer(2048),
        LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE)
    );

    public static final SlimefunItemStack IRON_BULLET = new SlimefunItemStack(
        "IRON_BULLET",
        Material.IRON_NUGGET,
        "&7铁制子弹",
        "",
        "&70.75 倍伤害"
    );

    public static final SlimefunItemStack LEAD_BULLET = new SlimefunItemStack(
        "LEAD_BULLET",
        Material.IRON_NUGGET,
        "&7铅制子弹",
        "",
        "&71 倍伤害"
    );

    public static final SlimefunItemStack DU_BULLET = new SlimefunItemStack(
        "DU_BULLET",
        Material.IRON_NUGGET,
        "&a贫铀弹",
        "",
        "&7让命中的实体着火",
        "&71.5 倍伤害"
    );

    public static final SlimefunItemStack GOLD_BULLET = new SlimefunItemStack(
        "GOLD_BULLET",
        Material.GOLD_NUGGET,
        "&6金制子弹",
        "",
        "&72 倍伤害"
    );

    public static final SlimefunItemStack TRINITROBULLETENE = new SlimefunItemStack(
        "TRINITROBULLETENE_BULLET",
        Material.GOLD_NUGGET,
        "&6三硝基丁烯弹",
        "",
        "&7让命中的实体着火",
        "&72.75 倍伤害"
    );

    public static final SlimefunItemStack GUN_CASE = new SlimefunItemStack(
        "GUN_CASE",
        Material.CROSSBOW,
        "&7枪套",
        "",
        "&7用于制造枪械"
    );

    public static final SlimefunItemStack OSMIUM_METEOR = new SlimefunItemStack(
        "OSMIUM_METEOR",
        Material.IRON_ORE,
        "&9锇流星",
        "",
        "&7稀有金属锇的来源"
    );

    public static final SlimefunItemStack OSMIUM_DUST = new SlimefunItemStack(
        "OSMIUM_DUST",
        Material.SUGAR,
        "&9锇粉",
        "",
        "&7剧毒!请勿吸入!"
    );

    public static final SlimefunItemStack OSMIUM_INGOT = new SlimefunItemStack(
        "OSMIUM_INGOT",
        Material.IRON_INGOT,
        "&9锇锭",
        "",
        "&7只有在外太空才能找到的一种坚硬的金属"
    );

    public static final SlimefunItemStack OSMIUM_SUPERALLOY = new SlimefunItemStack(
        "OSMIUM_SUPERALLOY",
        Material.IRON_INGOT,
        "&9锇高温合金",
        "",
        "&7迄今为止最硬、最坚韧、最牢固的金属"
    );

    public static final SlimefunItemStack SEGGANESSON_METEOR = new SlimefunItemStack(
        "SEGGANESSON_METEOR",
        Material.DIAMOND_ORE,
        "&7塞格尼森流星",
        "",
        "&7稀有元素塞格尼森的来源"
    );

    public static final SlimefunItemStack SEGGANESSON = new SlimefunItemStack(
        "SEGGANESSON",
        Material.LIGHT_BLUE_DYE,
        "&b塞格尼森",
        "",
        "&7一种可以点亮整座城市的稀有元素"
    );

    public static final SlimefunItemStack ENERGY_RECTIFIER = new SlimefunItemStack(
        "ENERGY_RECTIFIER",
        Material.POWERED_RAIL,
        "&b能量整流器",
        "",
        "&7将电能转化为纯净能源"
    );

    public static final SlimefunItemStack METEOR_ATTRACTOR = new SlimefunItemStack(
        "METEOR_ATTRACTOR",
        HeadTexture.MAGNET,
        "&f流星吸引器",
        "",
        "&7一块强大的磁铁",
        "&7可以从外太空吸引流星"
    );

    public static final SlimefunItemStack ELEMENTAL_REACTOR = new SlimefunItemStack(
        "ELEMENTAL_REACTOR",
        HeadTexture.GENERATOR,
        "&b元素发电机",
        "",
        "&7使用富含能量的元素",
        "&7非专利品与赛格尼森",
        "&7产生电力",
        LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
        LoreBuilder.powerBuffer(32_768),
        LoreBuilder.powerPerSecond(32_768)
    );

    public static final SlimefunItemStack FIBER_OPTIC_GLASS = new SlimefunItemStack(
        "FIBER_OPTIC_GLASS",
        Material.BLUE_STAINED_GLASS,
        "&f光纤玻璃"
    );

    public static final SlimefunItemStack FIBER_OPTIC_CABLE = new SlimefunItemStack(
        "FIBER_OPTIC_CABLE",
        Material.STRING,
        "&f光纤电缆"
    );

    public static final SlimefunItemStack LASER_DIODE = new SlimefunItemStack(
        "LASER_DIODE",
        PlayerHead.getItemStack(Heads.LASER),
        "&4激光二极管"
    );

    public static final SlimefunItemStack ULTRA_MAGNET = new SlimefunItemStack(
        "ULTRA_MAGNET",
        HeadTexture.MAGNET.getTexture(),
        "&f超磁铁"
    );

    public static final SlimefunItemStack RADIO = new SlimefunItemStack(
        "RADIO",
        Material.REDSTONE_TORCH,
        "&f无线电",
        "",
        "&7手持该物品,与其他物品栏中",
        "&7有此物品的玩家聊天.",
        "&7密钥用于加密/解密消息",
        "&7只有拥有相同密钥的玩家才可以读懂聊天内容",
        "&7手持该物品即可进行聊天",
        "&7手持该物品右键点击以设置密钥"
    );
    // endregion

    // region rare earths
    public static final SlimefunItemStack ION_EXCHANGE_SEPARATOR = new SlimefunItemStack(
        "ION_EXCHANGE_SEPARATOR",
        Material.SEA_LANTERN,
        "&b离子交换分离器",
        "",
        "&7从独居石中分离出难以分离的稀土",
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
        LoreBuilder.powerPerSecond(256),
        LoreBuilder.powerBuffer(512)
    );

    public static final SlimefunItemStack MONAZITE = new SlimefunItemStack(
        "MONAZITE",
        Material.ORANGE_DYE,
        "&e独居石",
        "",
        "&7所有稀土的来源",
        "&7可在岩浆岩中找到"
    );

    public static final SlimefunItemStack LANTHANUM_INGOT = new SlimefunItemStack(
        "LANTHANUM_INGOT",
        Material.IRON_INGOT,
        "&e镧锭",
        "",
        "&7可作为无限打火石使用",
        "&8然而并不能放在自动点火机里"
    );

    public static final SlimefunItemStack NEODYMIUM_INGOT = new SlimefunItemStack(
        "NEODYMIUM_INGOT",
        Material.NETHERITE_INGOT,
        "&e钕锭"
    );

    public static final SlimefunItemStack GADOLINIUM_INGOT = new SlimefunItemStack(
        "GADOLINIUM_INGOT",
        Material.IRON_INGOT,
        "&e钆锭"
    );

    public static final SlimefunItemStack TERBIUM_INGOT = new SlimefunItemStack(
        "TERBIUM_INGOT",
        Material.IRON_INGOT,
        "&e铽锭"
    );

    public static final SlimefunItemStack DYSPROSIUM_INGOT = new SlimefunItemStack(
        "DYSPROSIUM_INGOT",
        Material.NETHERITE_INGOT,
        "&e镝锭"
    );

    public static final SlimefunItemStack HOLMIUM_INGOT = new SlimefunItemStack(
        "HOLMIUM_INGOT",
        Material.BRICK,
        "&e钬锭"
    );

    public static final SlimefunItemStack ERBIUM_INGOT = new SlimefunItemStack(
        "ERBIUM_INGOT",
        Material.IRON_INGOT,
        "&e铒锭"
    );

    public static final SlimefunItemStack YTTERBIUM_INGOT = new SlimefunItemStack(
        "YTTERBIUM_INGOT",
        Material.IRON_INGOT,
        "&e镱锭"
    );

    public static final SlimefunItemStack TERFENOL_D = new SlimefunItemStack(
        "TERFENOL_D",
        Material.IRON_INGOT,
        "&6三苯酚",
        "",
        "&7这种合金具有在磁场中改变形状的特性"
    );

    public static final SlimefunItemStack TERFENOL_D_BLOCK = new SlimefunItemStack(
        "TERFENOL_D_BLOCK",
        Material.IRON_BLOCK,
        "&6Terfenol-D 块"
    );

    public static final SlimefunItemStack NDFEB_ALLOY = new SlimefunItemStack(
        "NDFEB_ALLOY",
        Material.NETHERITE_INGOT,
        "&6钕磁铁合金",
        "",
        "&7这种合金是人类已知的最有磁性的材料"
    );

    public static final SlimefunItemStack NDFEB_ALLOY_BLOCK = new SlimefunItemStack(
        "NDFEB_ALLOY_BLOCK",
        Material.NETHERITE_BLOCK,
        "&6钕磁铁合金块"
    );
    // endregion

    // region suits
    public static final SlimefunItemStack UNPATENTABLIUM = new SlimefunItemStack(
        "UNPATENTABLIUM",
        Material.LIGHT_BLUE_DYE,
        "&b非专利品",
        "",
        "&7由于某些原因，",
        "&7联邦政府不会让你为这种",
        "&7强大的能源申请专利"
    );

    public static final SlimefunItemStack POWER_SUIT_GENERATOR = new SlimefunItemStack(
        "POWER_SUIT_GENERATOR",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTFkNWExZmY3Zjk3NmMxYzJlYmQ0ZWY5YTkwYWQ5MTQ2Nzk1YzFjNDRmZGFlNjI5NjQ5NDg0MzRhNzI1NyJ9fX0=",
        "&6动力装甲能源核心",
        "",
        "&7所有动力装甲的能源核心"
    );

    public static final SlimefunItemStack ELEMENT_FORGE = new SlimefunItemStack(
        "ELEMENT_FORGE",
        Material.SMITHING_TABLE,
        "&c元素锻造台",
        "",
        "&7用来创建新的元素",
        "&c多方块结构"
    );

    public static final SlimefunItemStack POWER_SUIT_HELMET = new SlimefunItemStack(
        "POWER_SUIT_HELMET",
        PlayerHead.getItemStack(Heads.SUIT_HELMET),
        "&4动力装甲头盔",
        "",
        "&7动力装甲的一部分",
        "&7可安装模块",
        LoreBuilder.powerPerSecond(5),
        LoreBuilder.powerCharged(0, 1000)
    );
    public static final SlimefunItemStack POWER_SUIT_CHESTPLATE = new SlimefunItemStack(
        "POWER_SUIT_CHESTPLATE",
        Material.LEATHER_CHESTPLATE, Color.MAROON,
        "&4动力装甲胸甲",
        "",
        "&7动力装甲的一部分",
        "&7可安装模块",
        LoreBuilder.powerPerSecond(5),
        LoreBuilder.powerCharged(0, 1000)
    );
    public static final SlimefunItemStack POWER_SUIT_LEGGINGS = new SlimefunItemStack(
        "POWER_SUIT_LEGGINGS",
        Material.LEATHER_LEGGINGS, Color.MAROON,
        "&4动力装甲护腿",
        "",
        "&7动力装甲的一部分",
        "&7可安装模块",
        LoreBuilder.powerPerSecond(5),
        LoreBuilder.powerCharged(0, 1000)
    );
    public static final SlimefunItemStack POWER_SUIT_BOOTS = new SlimefunItemStack(
        "POWER_SUIT_BOOTS",
        Material.LEATHER_BOOTS, Color.MAROON,
        "&4动力装甲靴子",
        "",
        "&7动力装甲的一部分",
        "&7可安装模块",
        LoreBuilder.powerPerSecond(5),
        LoreBuilder.powerCharged(0, 1000)
    );

    public static final SlimefunItemStack MODULE_MANIPULATOR = new SlimefunItemStack(
        "MODULE_MANIPULATOR",
        Material.CRAFTING_TABLE,
        "&f模块控制台",
        "",
        "&7允许你安装、卸载、查看模块"
    );

    public static final SlimefunItemStack MODULE_CASE = new SlimefunItemStack(
        "MODULE_CASE",
        PlayerHead.getItemStack(Heads.MODULE),
        "&6模块核心"
    );
    // endregion

    // region guns
    public static final SlimefunItemStack PISTOL = new SlimefunItemStack(
        "GUN_PISTOL",
        Material.CROSSBOW,
        "&7手枪",
        "",
        "&7短射程,装填较快.",
        "&7近距离战斗时特别有用.",
        "&c射程: 10",
        "&c伤害: 6 (3 ♥)",
        "&c冷却时间: 0.5 秒"
    );
    public static final SlimefunItemStack REVOLVER = new SlimefunItemStack(
        "GUN_REVOLVER",
        Material.CROSSBOW,
        "&7左轮手枪",
        "",
        "&7短射程,装填较快.",
        "&7近距离战斗时特别有用.",
        "&c射程: 10",
        "&c伤害: 6 (3 ♥)",
        "&c冷却时间: 0.3 秒"
    );
    public static final SlimefunItemStack MACHINE_GUN = new SlimefunItemStack(
        "GUN_MACHINE_GUN",
        Material.CROSSBOW,
        "&7机枪",
        "",
        "&7哒哒哒~",
        "&c射程: 30",
        "&c最小射程: 5",
        "&c伤害: 6 (3 ♥)",
        "&c冷却时间: 0.15 秒"
    );
    public static final SlimefunItemStack MINIGUN = new SlimefunItemStack(
        "GUN_MINIGUN",
        Material.CROSSBOW,
        "&7重机枪",
        "",
        "&7为你的朋友增加点乐趣的终极装备.",
        "&c射程: 40",
        "&c最小射程: 5",
        "&c伤害: 8 (4 ♥)",
        "&c冷却时间: 无"
    );
    public static final SlimefunItemStack RIFLE = new SlimefunItemStack(
        "GUN_RIFLE",
        Material.CROSSBOW,
        "&7步枪",
        "",
        "&7一把标准步枪.",
        "&c射程: 40",
        "&c最小射程: 5",
        "&c伤害: 8 (4 ♥)",
        "&c冷却时间: 0.75 秒"
    );
    public static final SlimefunItemStack SHOTGUN = new SlimefunItemStack(
        "GUN_SHOTGUN",
        Material.CROSSBOW,
        "&7霰弹枪",
        "",
        "&7拥有比步枪更近的射程",
        "&7但能造成更多伤害.",
        "&c射程: 25",
        "&c最小射程: 5",
        "&c伤害: 13 (6.5 ♥)",
        "&c冷却时间: 1.25 秒"
    );
    public static final SlimefunItemStack ASSAULT_RIFLE = new SlimefunItemStack(
        "GUN_ASSAULT_RIFLE",
        Material.CROSSBOW,
        "&7突击步枪",
        "",
        "&7步枪的衍生物",
        "&7突击步枪是标配",
        "&c射程: 50",
        "&c最小射程: 3",
        "&c伤害: 13 (6.5 ♥)",
        "&c冷却时间: 0.3 秒"
    );
    public static final SlimefunItemStack SNIPER = new SlimefunItemStack(
        "GUN_SNIPER",
        Material.CROSSBOW,
        "&7狙击步枪",
        "",
        "&7这把枪拥有超远的射程",
        "&7狙击手的标配.",
        "&c射程: 130",
        "&c最小射程: 50",
        "&c伤害: 22 (11 ♥)",
        "&c冷却时间: 8 秒"
    );
    public static final SlimefunItemStack ENERGY_RIFLE = new SlimefunItemStack(
        "GUN_ENERGY_RIFLE",
        Material.CROSSBOW,
        "&e能量步枪",
        "",
        "&7不再需要带子弹了",
        "&c每发消耗 5J",
        "&c射程: 100",
        "&c伤害: 20 (10 ♥)",
        "&c冷却时间: 0.2 秒",
        LoreBuilder.powerCharged(0, 2500),
        "&e注意: 子弹看上去偏了一点",
        "&e但你仍然能命中目标"
    );
    // endregion

    // region melee
    public static final SlimefunItemStack ENERGY_BLADE = new SlimefunItemStack(
        "ENERGY_BLADE",
        Material.DIAMOND_SWORD,
        "&b能量之刃",
        "",
        "&7一把 \"光剑\",",
        "&7使用纯净能量斩断一切",
        "",
        "&9每次命中消耗 5J",
        LoreBuilder.powerCharged(0, 2500),
        "",
        "&7在主手时:",
        "&2 14 攻击伤害",
        "&2 1.6 攻击速度"
    );
    public static final SlimefunItemStack BATTLE_AXE = new SlimefunItemStack(
        "BATTLE_AXE",
        Material.IRON_AXE,
        "&6&l战斧",
        "",
        "&7这把斧子为战斗设计! 没有烦人的攻击冷却了!",
        "",
        "&7在主手时:",
        "&2 9 攻击伤害",
        "&2 1.6 攻击速度"
    );
    public static final SlimefunItemStack OSMIUM_SWORD = new SlimefunItemStack(
        "OSMIUM_SWORD",
        Material.IRON_SWORD,
        "&6锇剑",
        "",
        "&7拿在手中觉得很重",
        "",
        "&7在主手时:",
        "&2 10 攻击伤害",
        "&2 1.6 攻击速度"
    );
    public static final SlimefunItemStack DUMMY = new SlimefunItemStack(
        "DUMMY",
        Material.HUSK_SPAWN_EGG,
        "&f假人刷怪蛋",
        "",
        "&7右键生成一个假人",
        "&7可以显示你对其造成的伤害",
        "&7右键点击以摧毁假人"
    );
    // endregion

    static {
        ENERGY_BLADE.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);

        ItemMeta meta = Items.ENERGY_BLADE.getItemMeta();
        meta.setUnbreakable(true);
        meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(
            UUID.randomUUID(),
            "generic.attackDamage",
            13,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlot.HAND
        ));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        ENERGY_BLADE.setItemMeta(meta);

        // Sets the attack speed to match that of a sword
        meta = BATTLE_AXE.getItemMeta();
        meta.addAttributeModifier(Attribute.ATTACK_SPEED, new AttributeModifier(
            UUID.randomUUID(),
            "generic.attackSpeed",
            -2.4,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlot.HAND
        ));

        meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(
            UUID.randomUUID(),
            "generic.attackDamage",
            8,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlot.HAND
        ));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        BATTLE_AXE.setItemMeta(meta);

        OSMIUM_SWORD.addUnsafeEnchantment(Enchantment.UNBREAKING, 8);

        meta = OSMIUM_SWORD.getItemMeta();
        meta.addAttributeModifier(Attribute.ATTACK_DAMAGE, new AttributeModifier(
            UUID.randomUUID(),
            "generic.attackDamage",
            9,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlot.HAND
        ));

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        OSMIUM_SWORD.setItemMeta(meta);
    }
}
