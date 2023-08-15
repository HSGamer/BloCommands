package me.hsgamer.blocommands.command.sub;

import me.hsgamer.blocommands.BloCommands;
import me.hsgamer.blocommands.api.block.BlockLocation;
import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateCommand extends SubCommand {
    private final BloCommands plugin;

    public CreateCommand(BloCommands plugin) {
        super("create", "Create a new block", "/<label> create <id>", null, true);
        this.plugin = plugin;
    }

    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        if (plugin.getBlockManager().getBlock(args[0]).isPresent()) {
            MessageUtils.sendMessage(sender, "&cThe block is already created");
        } else {
            BlockLocation blockLocation = plugin.getBlockManager().createBlock(args[0]);
            if (sender instanceof Player) {
                Block block = ((Player) sender).getTargetBlock(null, 10);
                if (block.getType().isBlock()) {
                    blockLocation.setLocation(block.getLocation());
                    plugin.getBlockManager().loadBlockByLocation();
                    MessageUtils.sendMessage(sender, "&aThe block is created at your target block");
                }
            }
            plugin.getBlockManager().save();
            MessageUtils.sendMessage(sender, "&aThe block is created");
        }
    }

    @Override
    public boolean isProperUsage(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return args.length >= 1;
    }
}
