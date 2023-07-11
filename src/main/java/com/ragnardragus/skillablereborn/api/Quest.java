package com.ragnardragus.skillablereborn.api;

import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.AttributeCapability;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.IJobData;
import com.ragnardragus.skillablereborn.common.capabilities.jobs.JobDataCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.attributes.StatsRefreshMsg;
import com.ragnardragus.skillablereborn.common.network.job.JobRefreshMsg;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Quest {

    public static Map<Jobs, Map<Integer, Objective>> QUESTS = new HashMap<>(){{

        // NONE //
        put(Jobs.NONE, new HashMap<>(){{
            put(0, new Objective(ObjectiveType.NONE, "", Objective.NO_REWARD));
        }});

        // MASON //
        put(Jobs.FARMER, new HashMap<>(){{
            put(1, new Objective(ObjectiveType.BLOCK_BREAK, "Cut Trees.", 15, ItemTags.LOGS, new ArrayList<>(){{
                add(new Reward(Stats.STRENGTH, 1));
                add(new Reward(Stats.CULTIVATION, 1));
            }}));

            put(2, new Objective(ObjectiveType.CRAFT_A, "Craft Bonemeal.", 180, Items.BONE_MEAL, new ArrayList<>(){{
                add(new Reward(Stats.CULTIVATION, 2));
            }}));
            put(3, new Objective(ObjectiveType.CROP_BREAK, "Break Wheat.", 100, Blocks.WHEAT, 7, new ArrayList<>(){{
                add(new Reward(Stats.STRENGTH, 2));
            }}));
            put(4, new Objective(ObjectiveType.PICKUP,"Pickup Polished Andesites.", 1200, Items.POLISHED_ANDESITE, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 2));
            }}));
            put(5, new Objective(ObjectiveType.CRAFT_A, "Craft a Beacon.", 1, Items.BEACON, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 1));
                add(new Reward(Stats.CULTIVATION, 1));
            }}));
        }});


        // MASON //
        put(Jobs.MASON, new HashMap<>(){{
            put(1, new Objective(ObjectiveType.BLOCK_BREAK, "Cut Trees.", 15, ItemTags.LOGS, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 1));
                add(new Reward(Stats.CULTIVATION, 1));
            }}));

            put(2, new Objective(ObjectiveType.CRAFT_A, "Craft Scaffolding.", 180, Items.SCAFFOLDING, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 1));
                add(new Reward(Stats.CULTIVATION, 1));
            }}));
            put(3, new Objective(ObjectiveType.BLOCK_BREAK, "Break Clay Blocks.", 100, Blocks.CLAY, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 2));
            }}));
            put(4, new Objective(ObjectiveType.PICKUP,"Pickup Polished Andesites.", 1200, Items.POLISHED_ANDESITE, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 2));
            }}));
            put(5, new Objective(ObjectiveType.CRAFT_A, "Craft a Beacon.", 1, Items.BEACON, new ArrayList<>(){{
                add(new Reward(Stats.CRAFTING, 1));
                add(new Reward(Stats.CULTIVATION, 1));
            }}));
        }});

    }};

    public static class Reward {

        private Stats stats;
        private int value;

        public Reward(Stats stats, int value) {
            this.stats = stats;
            this.value = value;
        }

        public Stats getStats() {
            return stats;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


    public static class Objective {
        private ObjectiveType type;
        private String descriptionKey;

        private List<Reward> rewards;

        public static final List<Reward> NO_REWARD = new ArrayList<>();

        private int quantity = 1;

        private Block block;
        private TagKey<Item> tag;

        private Item item;

        private int cropAge;

        public boolean IS_BLOCK_TYPE;
        public boolean IS_TAG_TYPE;
        public boolean IS_ITEM_TYPE;

        public Objective(ObjectiveType type, String descriptionKey, List<Reward> rewards) {
            this.type = type;
            this.descriptionKey = descriptionKey;
            this.rewards = rewards;
            setItemType();
        }

        public Objective(ObjectiveType type, String descriptionKey, int quantity, Item item, List<Reward> rewards) {
            this.type = type;
            this.descriptionKey = descriptionKey;
            this.quantity = quantity;
            this.item = item;
            this.rewards = rewards;
            setItemType();
        }

        public Objective(ObjectiveType type, String descriptionKey, int quantity, Block block, List<Reward> rewards) {
            this.type = type;
            this.descriptionKey = descriptionKey;
            this.quantity = quantity;
            this.block = block;
            this.rewards = rewards;
            setItemType();
        }

        public Objective(ObjectiveType type, String descriptionKey, int quantity, Block crop, int cropAge, List<Reward> rewards) {
            this.type = type;
            this.descriptionKey = descriptionKey;
            this.quantity = quantity;
            this.block = crop;
            this.cropAge = cropAge;
            this.rewards = rewards;
            setItemType();
        }

        public Objective(ObjectiveType type, String descriptionKey, int quantity, TagKey tag, List<Reward> rewards) {
            this.type = type;
            this.descriptionKey = descriptionKey;
            this.quantity = quantity;
            this.tag = tag;
            this.rewards = rewards;
            setItemType();
        }

        public ObjectiveType getType() {
            return type;
        }

        public void setType(ObjectiveType type) {
            this.type = type;
        }

        public String getDescriptionKey() {
            return descriptionKey;
        }

        public void setDescriptionKey(String descriptionKey) {
            this.descriptionKey = descriptionKey;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Block getBlock() {
            return block;
        }

        public void setBlock(Block block) {
            this.block = block;
        }

        public TagKey<Item> getTag() {
            return tag;
        }

        public void setTag(TagKey<Item> tag) {
            this.tag = tag;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public int getCropAge() {
            return cropAge;
        }

        public void setCropAge(int cropAge) {
            this.cropAge = cropAge;
        }

        public List<Reward> getRewards() {
            return rewards;
        }

        public void setRewards(List<Reward> rewards) {
            this.rewards = rewards;
        }

        private void setItemType() {
            this.IS_TAG_TYPE = this.tag != null && this.item == null && this.block == null;
            this.IS_BLOCK_TYPE = this.block != null && this.tag == null && this.item == null;
            this.IS_ITEM_TYPE = this.item != null && this.tag == null && this.block == null;

        }
    }

    public enum ObjectiveType {
        NONE,
        BLOCK_BREAK,
        CROP_BREAK,
        EAT_DRINK,
        CRAFT_A,
        PICKUP,
        KILL_A_MOB,
        CUSTOM,
        CLICK_ITEM_IN_BLOCK
    }

    public static void blockBreakObjective(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();

            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {

                // Init job/objective data //
                Jobs currentJob = Jobs.getJobByIndex(jobData.getCurrentJobIndex());
                int currentLevel = jobData.getJobLevel(currentJob);
                Objective objective = QUESTS.get(currentJob).get(currentLevel);

                // BREAK EVENT //
                if(objective.getType() == ObjectiveType.BLOCK_BREAK) {
                    Block block = event.getState().getBlock();

                    // BREAK BY TAG //
                    if(objective.IS_TAG_TYPE) {
                        ItemStack stack = new ItemStack(block.asItem());
                        Stream<TagKey<Item>> tags = stack.getTags();
                        boolean isMatch = tags.anyMatch(tag -> tag == objective.getTag());

                        if(isMatch) {
                            jobData.increaseJobProgressAmount(currentJob);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }

                    // BREAK BY BLOCK //
                    else if (objective.IS_BLOCK_TYPE) {
                        if(objective.getBlock().equals(block)) {
                            jobData.increaseJobProgressAmount(currentJob);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }
                }

                // BREAK CROPS BLOCKS
                else if (objective.getType() == ObjectiveType.CROP_BREAK) {
                    Block block = event.getState().getBlock();

                    if(block instanceof CropBlock && objective.getCropAge() > 0) {
                        CropBlock cropBlock = (CropBlock) block;
                        int age = event.getState().getValue(cropBlock.getAgeProperty());
                        System.out.println(age);

                        if(age == objective.getCropAge()) {
                             jobData.increaseJobProgressAmount(currentJob);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }
                }

                PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
            });
        }
    }

    public static void craftItemObjective(PlayerEvent.ItemCraftedEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();

            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {

                // Init job/objective data //
                Jobs currentJob = Jobs.getJobByIndex(jobData.getCurrentJobIndex());
                int currentLevel = jobData.getJobLevel(currentJob);
                Objective objective = QUESTS.get(currentJob).get(currentLevel);

                // CRAFT EVENT //
                if(objective.type == ObjectiveType.CRAFT_A) {
                    ItemStack itemStack = event.getCrafting();
                    int craftedQuantity = itemStack.getCount();

                    // CRAFT BY TAG //
                    if(objective.IS_TAG_TYPE) {
                        Stream<TagKey<Item>> tags = itemStack.getTags();
                        boolean isMatch = tags.anyMatch(tag -> tag == objective.getTag());

                        if(isMatch) {
                            jobData.setJobProgressAmount(currentJob, jobData.getJobProgressAmount(currentJob) + craftedQuantity);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }

                    // CRAFT BY ITEM //
                    else if(objective.IS_ITEM_TYPE) {
                        Item item = objective.getItem();
                        if(itemStack.getItem().equals(item)) {
                            jobData.setJobProgressAmount(currentJob, jobData.getJobProgressAmount(currentJob) + craftedQuantity);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }
                }

                PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
            });
        }
    }

    public static void pickupItem(PlayerEvent.ItemPickupEvent event) {
        if(event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();

            player.getCapability(JobDataCapability.INSTANCE).ifPresent(jobData -> {

                // Init job/objective data //
                Jobs currentJob = Jobs.getJobByIndex(jobData.getCurrentJobIndex());
                int currentLevel = jobData.getJobLevel(currentJob);
                Objective objective = QUESTS.get(currentJob).get(currentLevel);

                // PICKUP EVENT //
                if(objective.type == ObjectiveType.PICKUP) {
                    ItemStack itemStack = event.getStack();
                    int pickQuantity = itemStack.getCount();

                    // PICKUP BY TAG //
                    if(objective.getTag() != null) {
                        Stream<TagKey<Item>> tags = itemStack.getTags();
                        boolean isMatch = tags.anyMatch(tag -> tag == objective.getTag());
                        if(isMatch) {
                            jobData.setJobProgressAmount(currentJob, jobData.getJobProgressAmount(currentJob) + pickQuantity);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }

                    // PICKUP BY ITEM //
                    else if (objective.IS_ITEM_TYPE) {
                        Item item = objective.getItem();
                        if(itemStack.getItem().equals(item)) {
                            jobData.setJobProgressAmount(currentJob, jobData.getJobProgressAmount(currentJob) + pickQuantity);

                            if (jobData.getJobProgressAmount(currentJob) >= objective.getQuantity()) {
                                Quest.upgradeJob(player, jobData, currentJob, objective);
                            }
                        }
                    }

                }
                PacketHandler.sendToPlayer(new JobRefreshMsg(jobData.serializeNBT()), player);
            });
        }
    }


    public static void furnaceCraftItem(ItemStack itemStack, ServerPlayer player) {

    }

    public static void brewingStandCraftItem(ItemStack itemStack, ServerPlayer player) {

    }

    private static void giveRewards(ServerPlayer player, Objective objective) {
        player.getCapability(AttributeCapability.INSTANCE).ifPresent(attribute -> {
            for (Reward reward: objective.rewards) {
                int actual = attribute.getAttributeLevel(reward.stats);
                attribute.setAttributeLevel(reward.stats, actual + reward.value);

                PacketHandler.sendToPlayer(new StatsRefreshMsg(Attribute.get(player).serializeNBT()), player);
            }
        });
    }

    private static void upgradeJob(ServerPlayer player, IJobData jobData, Jobs currentJob, Objective objective) {
        Quest.giveRewards(player, objective);
        jobData.increaseJobLevel(currentJob);
        jobData.setJobProgressAmount(currentJob, 0);
    }
}
