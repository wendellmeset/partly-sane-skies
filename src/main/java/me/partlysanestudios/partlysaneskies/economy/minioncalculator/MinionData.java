package me.partlysanestudios.partlysaneskies.economy.minioncalculator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.partlysanestudios.partlysaneskies.SkyblockItem;
import me.partlysanestudios.partlysaneskies.utils.StringUtils;
import me.partlysanestudios.partlysaneskies.utils.Utils;
import me.partlysanestudios.partlysaneskies.utils.requests.Request;
import me.partlysanestudios.partlysaneskies.utils.requests.RequestsManager;
import org.apache.logging.log4j.Level;

import java.net.MalformedURLException;
import java.util.*;

public class MinionData {
//    The URL with the location of the minion data
    private static final String MINIONS_DATA_URL = "https://raw.githubusercontent.com/PartlySaneStudios/partly-sane-skies-public-data/main/data/constants/minion_data.json";

//    A hashmap with the key as the minion id, and the value as the minion object
    public static final HashMap<String, Minion> minionMap = new HashMap<>();

//    A Hashmap with the key as the id of the fuel, and the value as the fuel object
    public static final HashMap<String, MinionFuel> fuelMap = new HashMap<>();

//    init: runs before the request ------ CALL THIS TO INIT
    public static void preRequestInit() throws MalformedURLException {
        RequestsManager.newRequest(new Request(MINIONS_DATA_URL, MinionData::postRequestInit));
    }

//    Runs after the request
    public static void postRequestInit(Request request) {
//        Creates a json object from the request response
        JsonObject jsonObj = new JsonParser().parse(request.getResponse()).getAsJsonObject();
//        Gets the minions object from the json
        JsonObject minionObjects = Utils.getJsonFromPath(jsonObj, "/minions").getAsJsonObject();


//        For every item in the json, create a minion from it
        for (Map.Entry<String, JsonElement> en : minionObjects.entrySet()) {
            String id = en.getKey();
            JsonObject minionObj = en.getValue().getAsJsonObject();
            Minion minion= new Minion(id, minionObj);
            minionMap.put(id, minion); // Add the minion to the minion map
        }

//        Gets the fuel object from the json
        JsonObject fuelObjects = Utils.getJsonFromPath(jsonObj, "/fuels").getAsJsonObject();
        for (Map.Entry<String, JsonElement> en : fuelObjects.entrySet()) {
            String id = en.getKey();
            JsonObject fuelObj = en.getValue().getAsJsonObject();
            MinionFuel fuel = new MinionFuel (id, fuelObj);
            fuelMap.put(id, fuel); // Add the fuel to the fuel map
        }
    }

    public static String getMostProfitMinionString(double hours, Minion.Upgrade[] upgrades, MinionFuel fuel) {
        String str = "§7In §6" + hours + "§7 hour(s): (Upgrade:" + Arrays.asList(upgrades) + ")";
        HashMap<Minion, Double> mostProfitableMinions = getMostProfitMinion(upgrades, fuel);

        int i = 1;

        for (Map.Entry<Minion, Double> en : mostProfitableMinions.entrySet()) {
            str += "\n\n§7"+ i + ". " +  en.getKey().costBreakdown(en.getKey().maxTier, hours, upgrades, fuel);

            i++;
        }


        return str;
    }

    public static LinkedHashMap<Minion, Double> getMostProfitMinion(Minion.Upgrade[] upgrades, MinionFuel fuel) {
        HashMap<Minion, Double> priceMap = new HashMap<>();

        for (Map.Entry<String, Minion> en : minionMap.entrySet()) {
            Minion minion = en.getValue();
            double minionProfit = 0;
            minionProfit +=  minion.getTotalProfitPerMinute(minion.maxTier, upgrades, fuel);

            priceMap.put(minion, minionProfit);
        }
        return sortMap(priceMap);
    }

    public static Minion getMinion(String id) {
        return minionMap.get(id);
    }

    // Sorts the hashmap in descending order
    public static LinkedHashMap<Minion, Double> sortMap(HashMap<Minion, Double> map) {
        List<Map.Entry<Minion, Double>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        LinkedHashMap<Minion, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Minion, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    public static class Minion {
        public enum Upgrade {
            DIAMOND_SPREADING,
            KRAMPUS_HELMET,
            POTATO_SPREADING,
            MINION_EXPANDER,
            FLYCATCHER_UPGRADE,
            LESSER_SOULFLOW_ENGINE,
            SOULFLOW_ENGINE
        }
        public final String id;
        public final String displayName;
        public final HashMap<String, Double> drops;
        public final HashMap<Integer, Double> cooldowns;
        public final String category;
        public final int maxTier;


        public Minion(String id, JsonObject obj) {
            this.id = id;
            displayName = Utils.getJsonFromPath(obj, "/displayName").getAsString();
            maxTier = Utils.getJsonFromPath(obj, "/maxTier").getAsInt();
            category = Utils.getJsonFromPath(obj, "/category").getAsString();

            JsonObject dropsJson = Utils.getJsonFromPath(obj, "/drops").getAsJsonObject();
            drops = new HashMap<>();
            for (Map.Entry<String, JsonElement> en : dropsJson.entrySet()) {
                drops.put(en.getKey(), en.getValue().getAsDouble());
            }

            JsonObject cooldownJson = Utils.getJsonFromPath(obj, "/cooldown").getAsJsonObject();
            cooldowns = new HashMap<>();
            for (Map.Entry<String, JsonElement> en : cooldownJson.entrySet()) {
                cooldowns.put(Integer.parseInt(en.getKey()), en.getValue().getAsDouble());
            }
        }

        @Override
        public String toString() {
            return "" + id + ": Drops:" + drops.toString() + " Cooldowns:" + cooldowns.toString();
        }

        String[] kraumpusSpeedIncrease = {"SNOW_GENERATOR"};

        public HashMap<String, Double> getBaseItemsPerMinute(int tier, Upgrade[] upgrades, MinionFuel fuel) {
            List<Upgrade> upgradesList = Arrays.asList(upgrades);
            double cooldownInSeconds = cooldowns.get(tier);

            if (tier > maxTier) {
                cooldownInSeconds = cooldowns.get(maxTier);
            }
            else {
                cooldownInSeconds = cooldowns.get(tier);
            }

            double speedUpgrade = 0;
            if (upgradesList.contains(Upgrade.MINION_EXPANDER)) {
                speedUpgrade += 0.05;
            }
            if (upgradesList.contains(Upgrade.FLYCATCHER_UPGRADE)) {
                speedUpgrade += 0.2;
            }
            if (upgradesList.contains(Upgrade.SOULFLOW_ENGINE) || upgradesList.contains(Upgrade.LESSER_SOULFLOW_ENGINE)) {
                speedUpgrade -= 0.5;
            }
            if (upgradesList.contains(Upgrade.SOULFLOW_ENGINE) && id.equals("VOIDLING_GENERATOR")) {
                speedUpgrade += 0.03 * tier;
            }

            if (fuel != null) {
                speedUpgrade += fuel.upgrade;
            }

            cooldownInSeconds = cooldownInSeconds / (1 + speedUpgrade);

//            Calculates the correct cooldown in minutes
            double cooldownInMinute = cooldownInSeconds / 60d;

//            Adds the items generated
            HashMap<String, Double> items = new HashMap<>();
            for (Map.Entry<String, Double> en : drops.entrySet()) {
                items.put(en.getKey(), (1 / (2d * cooldownInMinute)) * en.getValue());
            }

//            Adds the fuel in subtracted amount
            if (fuel != null && fuel.duration != -1) {
                items.put(fuel.id, -fuel.amountNeeded(1));
            }


//            Totals the amount of items produced
            int baseItemsProduced = 0;
            for (double itemQuantity : items.values()) {
                baseItemsProduced += itemQuantity;
            }

//            Adds the gifts generated by Krampus helm
            if (upgradesList.contains(Upgrade.KRAMPUS_HELMET)) {
                if (Arrays.asList(kraumpusSpeedIncrease).contains(id)) {
                    items.put("RED_GIFT",  baseItemsProduced * 0.0045/100d * 4d);
                }
                else {
                    items.put("RED_GIFT",  baseItemsProduced * 0.0045/100d);
                }
            }

//            Adds the diamonds generated by diamond spreading
            if (upgradesList.contains(Upgrade.DIAMOND_SPREADING)) {
                items.put("DIAMOND", baseItemsProduced * 0.1);
            }

//            Adds the potato generated by potato spreading
            if (upgradesList.contains(Upgrade.POTATO_SPREADING)) {
                items.put("POTATO_ITEM", baseItemsProduced * 0.05);
            }

//            Added the soulflow generated by the soulflow engines
            if (upgradesList.contains(Upgrade.SOULFLOW_ENGINE) || upgradesList.contains(Upgrade.LESSER_SOULFLOW_ENGINE)) {
                items.put("RAW_SOULFLOW", 1d/3);
            }


            return items;
        }

        public double getTotalProfitPerMinute(int tier, Upgrade[] uprgades, MinionFuel fuel) {
            double totalProfit = 0;

            for (Map.Entry<String, Double> en : this.getBaseItemsPerMinute(tier, uprgades, fuel).entrySet()) {
                String itemId = en.getKey();
                double amount = en.getValue();
                double price = 0;
                try {
                    price = SkyblockItem.getItem(itemId).getBestPrice();
                } catch (NullPointerException e) {
                    Utils.log(Level.WARN, itemId + ": DOES NOT HAVE PRICE");
                }


                totalProfit += price * amount;
            }

            return totalProfit;
        }

        public String costBreakdown(int tier, double hours, Upgrade[] upgrades, MinionFuel fuel) {
//            Creates a colour for each prefix
            String colourPrefix = "";
            switch(this.category) {
                case "COMBAT":
                    colourPrefix = "§c";
                    break;

                case "FARMING":
                    colourPrefix = "§a";
                    break;

                case "MINING":
                    colourPrefix = "§9";
                    break;

                case "FORAGING":
                    colourPrefix = "§d";
                    break;

                case "FISHING":
                    colourPrefix = "§b";
                    break;

                default:
                    colourPrefix = "§7";

            }

            String str = colourPrefix + this.displayName + "§:";

            for (Map.Entry<String, Double> en2 : this.getBaseItemsPerMinute(this.maxTier, upgrades, fuel).entrySet()) {
//                Individual price of the item
                double price = SkyblockItem.getItem(en2.getKey()).getBestPrice();
                price = Utils.round(price, 1); // rounded to 1 decimal place

//                Total amount of money made by the item
                double totalItemProfit = en2.getValue();
                totalItemProfit *= 60 * hours;
                totalItemProfit = Utils.round(totalItemProfit, 1); // rounded to 1 decimal place

                str += "\n§7   §6x" + StringUtils.formatNumber(totalItemProfit) + "§7 §6" + SkyblockItem.getItem(en2.getKey()).getName() + "§7 for " + StringUtils.formatNumber(price) + " coins each.";
            }

//            Total amount of money made in given hours by the minion
            double totalMinionProfit = this.getTotalProfitPerMinute(tier, upgrades, fuel);
            totalMinionProfit *= 60 * hours;
            totalMinionProfit = Utils.round(totalMinionProfit, 1); // rounded to 1 decimal place

            str += "\n§7   Total: §6" + StringUtils.formatNumber(totalMinionProfit) + "§7 coins in " + StringUtils.formatNumber(hours) + " hours.";

            return str;
        }
    }


    public static class MinionFuel {
//        Skyblock item id for the fuel
        public final String id;
//        Duration time in minutes
        public final double duration;
//        Upgrade speed
        public final double  upgrade;

        public MinionFuel(String id, double duration, double upgrade) {
            this.duration = duration;
            this.upgrade = upgrade;
            this.id = id;
        }

//        Creates a new minion fuel from the json object in the public data repo
        public MinionFuel(String id, JsonObject object) {
            this(id, Utils.getJsonFromPath(object, "duration").getAsDouble() , Utils.getJsonFromPath(object, "speed_upgrade").getAsDouble());
        }

//        Returns the amount of fuel needed for the duration specified (in minutes)
        public double amountNeeded(double minuteDuration) {
            if (duration == -1) {
                return -1;
            }
            return minuteDuration / duration ;
        }
    }
}
