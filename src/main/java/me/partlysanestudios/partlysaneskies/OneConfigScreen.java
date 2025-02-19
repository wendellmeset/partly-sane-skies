//
// Written by Su386.
// See LICENSE for copyright and license notices.
//


package me.partlysanestudios.partlysaneskies;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.annotations.Number;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class OneConfigScreen extends Config {

    public OneConfigScreen() {
        // Available mod types: PVP, HUD, UTIL_QOL, HYPIXEL, SKYBLOCK
        super(new Mod("Partly Sane Skies", ModType.SKYBLOCK), "partly-sane-skies/config.json");
        initialize();
    }

    public void resetBrokenStrings() {
        if (arrowLowChatMessage.isEmpty()) {
            arrowLowChatMessage = "Partly Sane Skies > Warning! {player} only has {count} arrows remaining!";
            save();
        }
        if (watcherChatMessage.isEmpty()) {
            watcherChatMessage = "Partly Sane Skies > The watcher is done spawning mobs. Ready to clear.";
            save();
        }
    }

    @Info(
        type = InfoType.INFO,
        text = "Hover over an option to see a description and more information."

    )
    public static boolean ignored;

    @HypixelKey
    @Text(
        secure = true, 
        name = "API Key", 
        category = "General", 
        subcategory = "API", 
        description = "Do /api new to automatically set your API Key. Do not show your API key to anyone unless you know what you're doing."
    )
    public String apiKey = "";

    @Number(
        min = .1f,
        max = 30f,
        name = "Time between requests",
        category = "General",
        subcategory = "API",
        description = "The time between API calls. Only change if you know what you're doing. Changing this will reduce the amount of time API requests take, however may result in more errors"
    )
    public float timeBetweenRequests = 2f;

    @Dropdown(
        options = {
            "Commas (1,000,000)",
            "Spaces (1 000 000)",
            "Periods (1.000.000)",
    }, 
        category = "General", 
        subcategory = "Appearance", 
        name = "Hundreds Place Format", 
        description = "The seperator between different hundreds places."
    )
    public int hundredsPlaceFormat = 1;

    @Dropdown(
        options = {
            "Commas (1,52)",
            "Periods (1.52)",
    }, 
        category = "General", 
        subcategory = "Appearance", 
        name = "Decimal Place Format", 
        description = "The character to represent decimal places."
    )
    public int decimalPlaceFormat = 1;

    @Switch(
        category = "General",
        subcategory = "Appearance",
        name = "24 hour time",
        description = "Display time in 24-hour hour time (15:30) instead of 12 hour time (3:30 PM)"
    )
    public boolean hour24time = false;
    

    @Switch(
        category = "General",
        subcategory = "Appearance",
        name = "Legacy Version Warning",
        description = "Warns you if you are using a legacy version of Partly Sane Skies"
    )
    public boolean legacyVersionWarning = true;
    // Main Menu

    @Switch(
        category = "General", 
        subcategory = "Main Menu", 
        name = "Show a Custom Minecraft Main Menu"
    )
    public boolean customMainMenu = true;

    @Switch(
        category = "General", 
        subcategory = "Main Menu", 
        name = "Announcements on Main Menu",
        description = "Display announcements such as recent skyblock updates on the main menu"
    )
    public boolean displayAnnouncementsCustomMainMenu = true;

    @Dropdown(
        options = {
            "Random Image",
            "View of Main Hub Mountain",
            "Aerial View of Hub from Community House",
            "Stunning Aerial View of Hub",
            "View from Hub Portal (Day)",
            "Hub Portal (Night)",
            "Wolf Ruins",
            "Custom Image"
        }, 
        category = "General", 
        subcategory = "Main Menu", 
        name = "Custom Minecraft Main Menu Image",
        description = "Select one of our many high quality included images, or you can use your custom image.\nTo use your own image, place your image in the /config/partly-sane-skies folder and title your image background.png"
    )
    public int customMainMenuImage = 1;

    @Switch(
        name = "Color Private Messages", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Private messages pink to make them more visable in busy lobbies."
    )
    public boolean colorPrivateMessages = false;

    @Switch(
        name = "Color Nons Messages", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Color messages from the non-ranked players to white to make them more visable in busy lobbies."
    )
    public boolean colorNonMessages = false;

    @Switch(
        name = "Color Party Chat", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Color messages from the party chat blue to make them more visable in busy lobbies."
    )
    public boolean colorPartyChat = false;

    @Switch(
        name = "Color Guild Chat", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Color messages from the guild chat green to make them more visable in busy lobbies."
    )
    public boolean colorGuildChat = false;

    @Switch(
        name = "Color Guild Officer Chat", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Color messages from the guild officer chat aqua to make them more visable in busy lobbies."
    )
    public boolean colorOfficerChat = false;

    @Switch(
        name = "Skyblock Co-op Chat", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Color messages from the skyblock coop chat aqua to make them more visable in busy lobbies."
    )
    public boolean colorCoopChat = false;

    @Switch(
        name = "Visable Colors", 
        category = "General", 
        subcategory = "Chat Color", 
        description = "Converts the custom colors mentioned above to more visable colors. Dark Green -> Light Green and Blue -> Gold. (Recommended)"
    )
    public boolean visableColors = false;

    @Switch(
        name = "Print errors in chat",
        category = "General",
        subcategory = "API",
        description = "Send errors on getting APIs in chat (Recommended, however if you get spammed or have a bad internet connection, turn it off)"
        
    )
    public boolean printApiErrors = true;

    // ----------------- Category: Skyblock -------------------
    // Rare Drop
    @Switch( 
        name = "Rare Drop Banner", 
        subcategory = "Rare Drop", 
        description = "On rare drop, get a Pumpkin Dicer like banner.", 
        category = "Skyblock"
    )
    public boolean rareDropBanner = false;


    @Slider(
        min = 1, 
        max = 7, 
        subcategory = "Rare Drop", 
        name = "Rare Drop Banner Time", 
        description = "The amount of seconds the rare drop banner appears for.", 
        category = "Skyblock"
    )
    public float rareDropBannerTime = 3.5f;

    @Switch(
        name = "Custom Rare Drop Sound", 
        subcategory = "Rare Drop", 
        description = "Plays a custom sound when you get a rare drop.", 
        category = "Skyblock"
    )
    public boolean rareDropBannerSound = false;

    // Location Banner
    @Switch( 
        name = "Location Banner", 
        subcategory = "Location Banner", 
        description = "An MMO RPG style banner shows up when you switch locations.", 
        category = "Skyblock"
    )
    public boolean locationBannerDisplay = false;

    @Slider(
        min = 1, 
        max = 7, 
        subcategory = "Location Banner", 
        name = "Location Banner Time", 
        description = "The amount of seconds the location banner appears for.", 
        category = "Skyblock"
    )
    public float locationBannerTime = 3.5f;

    // Open Wiki
    @Switch( 
        name = "Open Wiki Automatically", 
        category = "Skyblock", 
        description = "When the Open Wiki Article Keybind is used, automatically open the article without confirmation first.", 
        subcategory = "Open Wiki"
    )
    public boolean openWikiAutomatically = true;

    // Pet Minion Alert
    @Switch(
        name = "Incorrect Pet for Minion Alert", 
        category = "Skyblock", 
        description = "Warns you if you don't have the right pet for leveling up the minions, that way you never lose any pet EXP because you still have your level 100 dungeon pet activated.\nRequires pets to be visable.", 
        subcategory = "Incorrect Pet for Minion Alert"
    )
    public boolean incorrectPetForMinionAlert = false;

    @Switch(
        name = "Air Raid Siren", 
        category = "Skyblock", 
        description = "Plays a WWII air raid siren when you have the wrong pet. \nPros: \nKeeps you up at late night grinds \n(RECOMMENDED, ESPECIALLY AT 3 AM).", 
        subcategory = "Incorrect Pet for Minion Alert"
    )
    public boolean incorrectPetForMinionAlertSiren = false;

    @Text(
        category = "Skyblock",
        subcategory = "Incorrect Pet for Minion Alert",
        name = "Selected Pet",
        description = "The selected pet that will be used for minion collecting (Use /pets and click the pet keybind to select",
        secure =  true
    )
    public String selectectedPet = "";

    @Slider(
        min = 1, 
        max = 15, 
        subcategory = "Incorrect Pet for Minion Alert", 
        name = "Mute Time", 
        description = "The amount of minutes the pet alert will mute for when you mute it.", 
        category = "Skyblock"
    )
    public float petAlertMuteTime = 7.5f;

    @Dropdown(
        category = "Skyblock",
        subcategory = "Enhanced Skyblock Sounds",
        name = "Note Block Instrument Type",
        options = {
            "Default Skyblock Noteblocks",
            "Clarinet (Live)",
            "Clarinet (Computer)",
            "Electric Piano",
            "Flute",
            "Organ",
            "Piano",
            "String Orchestra",
            "Trombone",
            "Trumpet",
            "Violin",
            "Wind Ensemble",
            "Discord New Message Sound",
            "Kazoo",
        }
    )
    public int customSoundOption = 0;

    @Dropdown(
        category = "Skyblock",
        subcategory = "Enhanced Skyblock Sounds",
        name = "Explosions",
        options = {
            "Default",
            "Off",
            "Realistic"
        }
    )
    public int customExplosion = 0;

    // -------------- Category: Mining --------------
    // Worm Warning
    @Switch(
        name = "Worm Warning Banner", 
        subcategory = "Worm Warning", 
        description = "A banner appears on your screen when a worm spawns.", 
        category = "Mining"
    )
    public boolean wormWarningBanner = false;

    @Color(
        subcategory = "Worm Warning", 
        name = "Worm Warning Banner Color", 
        description = "The color of the worm warning text", 
        category = "Mining"
    )
    public OneColor wormWarningBannerColor = new OneColor(34, 255, 0);

    @Slider(
        min = 1, 
        max = 7, 
        subcategory = "Worm Warning", 
        name = "Worm Warning Banner Time", 
        description = "The amount of seconds the worm warning banner appears for.", 
        category = "Mining"
    )
    public float wormWarningBannerTime = 3.5f;

    @Switch(
        name = "Worm Warning Sound", 
        subcategory = "Worm Warning", 
        description = "Plays a sound when a worm spawns.", 
        category = "Mining"
    )
    public boolean wormWarningBannerSound = false;

    // ------------- Category: Dungeons ---------------------------------
    // Party Manager
    @Switch(
        name = "Automatically kick offline on party manager load", 
        subcategory = "Party Manager", 
        description = "Automatically kicks offline members in your party when you open party manager.", 
        category = "Dungeons"
    )
    public boolean autoKickOfflinePartyManager = false;

    @Switch(
        name = "Warn Low Arrows in Chat", 
        subcategory = "Party Manager", 
        description = "Warns you party when a member has low arrows.", 
        category = "Dungeons"
    )
    public boolean warnLowArrowsInChat = false;

    @Text(
        subcategory = "Party Manager", 
        name = "Arrow Low Warning", 
        description = "Message to send when a player has low arrows.\nUse {player} to signify the player's username, and {count} to signfy the remaining arrow count.", 
        category = "Dungeons"
    )
    public String arrowLowChatMessage = "Partly Sane Skies > Warning! {player} only has {count} arrows remaining!";

    @Number(
        name = "Arrow Low Count", 
        min = 0,
        max = 1000,
        subcategory = "Party Manager", 
        description = "The amount of arrows you must have to be considered low on arrows.", 
        category = "Dungeons"
    )
    public int arrowLowCount = 300;

    @Switch(
        name = "Print errors in chat",
        category = "Dungeons",
        subcategory = "Party Manager",
        description = "Send errors on getting data in chat (Recommended, however if you get spammed or have a bad internet connection, turn it off)"
        
    )
    public boolean printPartyManagerApiErrors = true;

    @Switch(
        name = "Get data on party join", 
        subcategory = "Party Manager", 
        description = "Automatically gets the data for party members someone joins the party. This saves time and reduces the chance of the data not being able to be accessed.", 
        category = "Dungeons"
    )
    public boolean getDataOnJoin = true;

    @Switch(
        name = "Toggle Run Colors in Partymanager",
        subcategory = "Party Manager",
        description = "Toggles the colors of the runs in the party manager. ",
        category = "Dungeons"
    )
    public boolean toggleRunColors = true;

    @Number(
        name = "Customize Max Runs for Red in Run Colors",
        min = 0,
        max = Integer.MAX_VALUE,
        subcategory = "Party Manager",
        description = "Customize maximum runs required for the color red",
        category = "Dungeons"
    )
    public int runColorsRedMax = 1;

    @Number(
        name = "Customize Max Runs for Yellow in Run Colors",
        min = 0,
        max = Integer.MAX_VALUE,
        subcategory = "Party Manager",
        description = "Customize maximum runs required for the color yellow",
        category = "Dungeons"
    )
    public int runColorsYellowMax = 9;

    @Slider(
        name = "Party Manager Cache Time", 
        min = 0, 
        max = 90, 
        subcategory = "Party Manager", 
        description = "Saves the data from other party members to save time upon loading Party Manager. The bigger the value the more minutes you will save but the less accurate your data will be.", 
        category = "Dungeons"
    )
    public int partyManagerCacheTime = 30;

    // Watcher Ready Warning
    @Switch(
        name = "Watcher Ready Warning", 
        subcategory = "Watcher Ready", 
        description = "Sends a warning when the watcher is done spawning mobs.", 
        category = "Dungeons"
    )
    public boolean watcherReadyBanner = false;

    @Switch(
        name = "Watcher Ready Sound", 
        subcategory = "Watcher Ready", 
        description = "Plays a sound when the watcher is done spawning mobs.", 
        category = "Dungeons"
    )
    public boolean watcherReadySound = false;

    @Slider(
        min = 1, 
        max = 7, 
        subcategory = "Watcher Ready", 
        name = "Watcher Ready Banner Time", 
        description = "The amount of seconds the watcher ready banner appears for.", 
        category = "Dungeons"
    )
    public float watcherReadyBannerTime = 3.5f;

    @Color(
        subcategory = "Watcher Ready", 
        name = "Watcher Ready Banner Color", 
        description = "The color of the watcher ready text", 
        category = "Dungeons"
    )
    public OneColor watcherReadyBannerColor = new OneColor(255, 45, 6);

    @Switch(
        name = "Watcher Ready Chat Message", 
        subcategory = "Watcher Ready", 
        description = "Send a message to your party when watcher is done spawning mobs.", 
        category = "Dungeons"
    )
    public boolean watcherReadyChatMessage = false;


    @Text(
        subcategory = "Watcher Ready", 
        name = "Watcher Ready Text", 
        description = "Message to send when the watcher is ready to clear.", 
        category = "Dungeons"
    )
    public String watcherChatMessage = "Partly Sane Skies > The watcher is done spawning mobs. Ready to clear.";

    @Switch(
        subcategory = "Watcher Ready", 
        name = "Air Raid Siren", 
        description = "Plays a WWII air raid siren when the watcher is done spawning mobs. \nPros: \nKeeps you up at late night grinds \n(RECOMMENDED, ESPECIALLY AT 3 AM)", 
        category = "Dungeons"
    )
    public boolean watcherReadyAirRaidSiren = false;

    @Switch(
        subcategory = "Dungeon Player Breakdown", 
        name = "Dungeon Player Breakdown", 
        description = "At the end of the dungeon, send a message informing you how much of the dungeon each player has completed", 
        category = "Dungeons"
    )
    public boolean dungeonPlayerBreakdown = false;

    @Dropdown(
        subcategory = "Dungeon Player Breakdown", 
        name = "Message Content", 
        description = "Shows more information about how many blessings and secrets each player collected", 
        category = "Dungeons",
        options = {
            "Condensed",
            "Standard",
            "Enhanced"
        }
    )
    public int enhancedDungeonPlayerBreakdown = 1;

    @Switch(
        subcategory = "Dungeon Player Breakdown", 
        name = "Send in Party Chat", 
        description = "Send a condensed version to the rest of your party.", 
        category = "Dungeons"
    )
    public boolean partyChatDungeonPlayerBreakdown = false;

    // ------------- Category: Economy ---------------------------------
    // Garden
    @Switch(
        subcategory = "Garden", 
        name = "Garden Shop Trade Cost", 
        description = "Gives you information about the cost of garden shop trades.", 
        category = "Economy"
    )
    public boolean gardenShopTradeInfo = false;
    
    @Switch(
        subcategory = "Garden", 
        name = "Best Crops to Compost", 
        description = "Gives you information about which crops are the best to compost.", 
        category = "Economy"
    )
    public boolean bestCropsToCompost = false;

    @Switch(
        subcategory = "Community Center", 
        name = "Best Item for Bits", 
        description = "Gives you information about which item in the Bits Shop is the best to sell.", 
        category = "Economy"
    )
    public boolean bestBitShopItem = false;

    @Switch(
        subcategory = "Community Center", 
            name = "Only Show Affordable Items", 
        description = "When making recommendations for what you can buy, only recommend the items that you are able to afford.", 
        category = "Economy"
    )
    public boolean bitShopOnlyShowAffordable = true;

     // Auction House
     @Switch(
        name = "Custom Auction House GUI", 
        category = "Economy", 
        subcategory = "Auction House", 
        description = "Toggle using the custom Auction House GUI and BIN Sniper Helper."
    )
    public boolean customAhGui = true;

    @Dropdown(

        name = "Custom Auction House GUI Icons",
        category = "Economy",
        options = {
            "Partly Sane Studios",
            "FurfSky Reborn"
        },
        subcategory = "Auction House",
        description = "Use either the Partly Sane Studios developed textures, or the FurfSky Reborn developed textures\n\nAll of the textures under FurfSky Reborn are fully developed by the FurfSky Reborn team.\nhttps://furfsky.net/"
    )
    public int customAhGuiTextures = 0;

    @Slider( 
        min = 0, 
        max = 100, 
        category = "Economy", 
        subcategory = "Auction House", 
        name = "BIN Snipe Percentage", 
        description = "The percent of the price that the BIN sniper considers a \"snipe\". Example: 85%, Lowest BIN: 1 000 000, will look for a price of 850000 or less."
    )
    public float BINSniperPercent = 87f;

    @Color(
        name = "Bin Sniper Highlight Color",
        description = "Pick a color to highlight your BIN snipes",
        subcategory = "Auction House",
        category = "Economy"
    )
    public OneColor BINSniperColor = new OneColor(PartlySaneSkies.ACCENT_COLOR);
    // Execessive Coin warning
    @Switch(
        name = "Excessive Coin and No Booster Cookie", 
        category = "Economy", 
        description = "Warns you if you have a lot of coins in your purse and no booster cookie.", 
        subcategory = "Excessive Coin Warning"
    )
    public boolean noCookieWarning = false;

    @Number(
        min = 0, 
        max = Integer.MAX_VALUE, 
        name = "Maximum Allowed Amount Without Booster Cookie", 
        category = "Economy", 
        description = "The maximum allowed amount of money allowed before it warns you about having no booster cookie.", 
        subcategory = "Excessive Coin Warning"
    )
    public int maxWithoutCookie = 750000;

    @Slider(
        min = 1, 
        max = 7, 
        subcategory = "Excessive Coin Warning", 
        name = "Excessive Coin Warning Time", 
        description = "The amount of seconds the warning appears for appears for.", 
        category = "Economy"
    )
    public float noCookieWarnTime = 3.5f;

    @Slider(
        min = 1, 
        max = 300, 
        subcategory = "Excessive Coin Warning", 
        name = "Excessive Coin Warn Cooldown", 
        description = "The amount of seconds between each warning", 
        category = "Economy"
    )
    public int noCookieWarnCooldown = 20;


}
