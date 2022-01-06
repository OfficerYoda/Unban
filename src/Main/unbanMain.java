package Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;


public class unbanMain extends JavaPlugin implements Listener{

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {	

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only Players can use this command!");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.GRAY + "Usage" + ChatColor.DARK_GRAY + ": " + ChatColor.BLUE + "/unban <playername>");

			return true;
		}

		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "Only " + ChatColor.BOLD + ChatColor.ITALIC + "OPERATORS " + ChatColor.RESET + ChatColor.RED + "can do this! \n"
					+ "Ask one if you think a player shouldn't be banned.");
			return false;
		}

		OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);

		if (target == null) {
			sender.sendMessage("That is not a player.");
		}

		if(!target.isBanned()) {
			sender.sendMessage(ChatColor.RED + "This player isn't banned");

			return false;
		}

		Bukkit.getBanList(Type.NAME).pardon(args[0]);
		sender.sendMessage(args[0] + " is now unbanned!");


		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> arguments = new ArrayList<>();
		if(args.length == 1) {
			if(sender.isOp()) {
				for(OfflinePlayer player : Bukkit.getBannedPlayers())
					arguments.add(player.getName());
			} else {
				arguments.add("");
			}

		} else {
			arguments.add("");
		}
		return StringUtil.copyPartialMatches(args[0], arguments, new ArrayList<>());
	}
}