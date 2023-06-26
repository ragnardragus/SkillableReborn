package com.ragnardragus.skillablereborn.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.Attribute;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.AttributeCapability;
import com.ragnardragus.skillablereborn.common.capabilities.attributes.IAttribute;
import com.ragnardragus.skillablereborn.common.capabilities.level.LevelCapability;
import com.ragnardragus.skillablereborn.common.network.PacketHandler;
import com.ragnardragus.skillablereborn.common.network.attributes.AttributesSync;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.command.EnumArgument;

public class ModCommands {

    public ModCommands() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skillable").requires(source -> source.hasPermission(2))
            .then(Commands.literal("add")

                .then(Commands.literal("skill_point")
                    .then(Commands.argument("targetPlayer", EntityArgument.player())
                            .executes(context -> addSkillPoint(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer"), false))
                    )
                    .then(Commands.argument("targetPlayer", EntityArgument.player())
                        .then(Commands.literal("silent")
                            .executes(context -> addSkillPoint(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer"), true))
                        )
                    )
                )

                .then(Commands.literal("stats")
                    .then(Commands.argument("stats_name", EnumArgument.enumArgument(Stats.class))
                        .then(Commands.argument("targetPlayer", EntityArgument.player())
                            .executes(context -> addStats(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer"), context.getArgument("stats_name", Stats.class)))
                        )
                    )
                )

                .then(Commands.literal("level")
                    .then(Commands.argument("targetPlayer", EntityArgument.player())
                            .executes(context -> addLevel(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer")))
                    )
                )
            )
            .then(Commands.literal("set")

                .then(Commands.literal("skill_point")
                    .then(Commands.argument("value", IntegerArgumentType.integer(0, 256))
                        .then(Commands.argument("targetPlayer", EntityArgument.player())
                            .executes(context -> setSkillPoint(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer"), IntegerArgumentType.getInteger(context, "value")))
                        )
                    )
                )

                .then(Commands.literal("stats")
                    .then(Commands.argument("stats_name", EnumArgument.enumArgument(Stats.class))
                        .then(Commands.argument("value", IntegerArgumentType.integer(1, 32))
                            .then(Commands.argument("targetPlayer", EntityArgument.player())
                                .executes(context -> setStats(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer"), context.getArgument("stats_name", Stats.class), IntegerArgumentType.getInteger(context, "value")))
                            )
                        )
                    )
                )
            )
            .then(Commands.literal("remove")

                .then(Commands.literal("skill_point")
                    .then(Commands.argument("targetPlayer", EntityArgument.player())
                        .executes(context -> removeSkillPoint(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer")))
                    )
                )

                .then(Commands.literal("stats")
                    .then(Commands.argument("stats_name", EnumArgument.enumArgument(Stats.class))
                        .then(Commands.argument("targetPlayer", EntityArgument.player())
                            .executes(context -> removeStats(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer"), context.getArgument("stats_name", Stats.class)))
                        )
                    )
                )
            )
            .then(Commands.literal("clear_level")
                .then(Commands.argument("targetPlayer", EntityArgument.player())
                    .executes(context -> clearLevel(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer")))
                )
            )
            .then(Commands.literal("reset")
                .then(Commands.argument("targetPlayer", EntityArgument.player())
                    .executes(context -> reset(context.getSource(), EntityArgument.getPlayer(context, "targetPlayer")))
                )
            )
        );
    }

    private static int addSkillPoint(CommandSourceStack source, ServerPlayer player, boolean silent) {
        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
            skillPoint.addSkillPoints();
            skillPoint.sync(player);

            if(!silent) {
                source.sendSuccess(new TranslatableComponent("commands.add_skill.success", player.getName()), true);
            }
        });
        return 1;
    }

    private static int setSkillPoint(CommandSourceStack source, ServerPlayer player, int amount) {
        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
            skillPoint.setSkillPoints(amount);
            skillPoint.sync(player);
            source.sendSuccess(new TranslatableComponent("commands.set_skill.success", amount, player.getName()), true);
        });

        return 1;
    }

    private static int removeSkillPoint(CommandSourceStack source, ServerPlayer player) {
        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
            skillPoint.subtractSkillPoints();
            skillPoint.sync(player);
            source.sendSuccess(new TranslatableComponent("commands.rem_skill.success", player.getName()), true);
        });

        return 1;
    }

    private static int addStats(CommandSourceStack source, ServerPlayer player, Stats stats) {

        player.getCapability(AttributeCapability.INSTANCE).ifPresent( i -> {
            if(Attribute.get(player).getAttributeLevel(stats) < 32) {
                IAttribute attribute = Attribute.get(player);
                attribute.increaseAttributeLevel(stats);

                PacketHandler.sendToPlayer(new AttributesSync(attribute.serializeNBT()), player);
                source.sendSuccess(new TranslatableComponent("commands.add_stats.success", new TranslatableComponent(stats.displayName), player.getName()), true);
            }
        });

        return 1;
    }

    private static int setStats(CommandSourceStack source, ServerPlayer player, Stats stats, int amount) {

        player.getCapability(AttributeCapability.INSTANCE).ifPresent( i -> {
            if(Attribute.get(player).getAttributeLevel(stats) >= 0 && Attribute.get(player).getAttributeLevel(stats) <= 32) {
                IAttribute attribute = Attribute.get(player);
                attribute.setAttributeLevel(stats, amount);

                PacketHandler.sendToPlayer(new AttributesSync(attribute.serializeNBT()), player);
                source.sendSuccess(new TranslatableComponent("commands.set_stats.success", amount, new TranslatableComponent(stats.displayName), player.getName()), true);
            }
        });

        return 1;
    }

    private static int removeStats(CommandSourceStack source, ServerPlayer player, Stats stats) {

        player.getCapability(AttributeCapability.INSTANCE).ifPresent( i -> {
            if(Attribute.get(player).getAttributeLevel(stats) > 1) {
                IAttribute attribute = Attribute.get(player);
                attribute.decreaseAttributeLevel(stats);

                PacketHandler.sendToPlayer(new AttributesSync(attribute.serializeNBT()), player);
                source.sendSuccess(new TranslatableComponent("commands.rem_stats.success", new TranslatableComponent(stats.displayName), player.getName()), true);
            }
        });

        return 1;
    }

    private static int addLevel(CommandSourceStack source, ServerPlayer player) {

        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {

            int oldLevelsNeed = skillPoint.getMcLevelsNeed();
            skillPoint.setMcLevelsNeed(oldLevelsNeed + 1);

            skillPoint.addPlayerModLevel();
            skillPoint.addSkillPoints();

            skillPoint.sync(player);

            source.sendSuccess(new TranslatableComponent("commands.add_level.success", player.getName()), true);
        });

        return 1;
    }

    private static int clearLevel(CommandSourceStack source, ServerPlayer player) {
        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {

            skillPoint.setMcLevelsNeed(1);
            skillPoint.setPlayerModLevel(0);

            skillPoint.sync(player);

            source.sendSuccess(new TranslatableComponent("commands.clear_level.success", player.getName()), true);
        });

        return 1;
    }

    private static int reset(CommandSourceStack source, ServerPlayer player) {
        player.getCapability(LevelCapability.INSTANCE).ifPresent(skillPoint -> {
            player.getCapability(AttributeCapability.INSTANCE).ifPresent( attr -> {

                skillPoint.setMcLevelsNeed(1);
                skillPoint.setPlayerModLevel(0);
                skillPoint.setSkillPoints(0);

                for (Stats stat : Stats.values()) {
                    IAttribute attribute = Attribute.get(player);
                    attribute.setAttributeLevel(stat, 1);

                    PacketHandler.sendToPlayer(new AttributesSync(attribute.serializeNBT()), player);
                }

                skillPoint.sync(player);

                source.sendSuccess(new TranslatableComponent("commands.reset.success", player.getName()), true);
            });
        });

        return 1;
    }
}
