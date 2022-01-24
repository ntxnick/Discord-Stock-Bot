import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Commands extends ListenerAdapter {
	
	public String prefix = "!";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		int count = args.length;
		
		if(count == 1) {
			
			if(event.getMessage().getContentRaw().equalsIgnoreCase("!invite")) {
				String url = "https://discord.com/api/oauth2/authorize?client_id=928425993918623775&permissions=8&scope=bot";
				event.getChannel().sendMessage(String.format(url, event.getJDA().getSelfUser().getId())).queue();
			}
			
			if(args[0].equalsIgnoreCase("!help")) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Bot Information", "");
				embed.setDescription("Commands for the bot");
				embed.addField("Help command", "Type '!help' to get this page again", false);
				embed.addField("Add to another channel", "Type '!invite' for an invite link", false);
				embed.addField("Stock information", "Used to get stock information from the last 24 hrs.\nUse the command '!stonks' followed by the ticker. Example - '!stonks aapl' ", false);
				embed.setFooter("Created by Nick Vitale", null);
				event.getChannel().sendMessageEmbeds(embed.build()).queue();
			}
			
			if (args[0].equalsIgnoreCase(prefix + "test")) {
			
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Stonks Thang", "");
				embed.setDescription("this is the description");
				embed.addField("Embed Feild 1", "This is the field", false);
				embed.addField("Embed Feild 2", "This is the field 2", false);
				embed.setFooter("Created by Nick Vitale", null);
				event.getChannel().sendMessageEmbeds(embed.build()).queue();
			}
		} else if(count == 2) {
			if (args[0].equalsIgnoreCase(prefix + "stonks")) {
				try {
					args[1] = args[1].toUpperCase();
					
					String start = "https://finance.yahoo.com/quote/";
					String middle = "?p=";
					String end = "&.tsrc=fin-srch";
					
					String yahooLink = start + args[1] + middle + args[1] + end;
					
					StonkAPI stonks = new StonkAPI();
					String[] stockData = new String[5];
					stockData = stonks.stonksInfo(args[1]);
					
					EmbedBuilder embed = new EmbedBuilder();
					embed.setTitle(args[1] + " Information", "");
					embed.setDescription("The time zone used is " + stockData[1] + "\n" + "The date for this information is: " + stockData[0]);
					
					embed.addField("Yesterdays high: ", "$" + stockData[2], false);
					embed.addField("Yesterdays low: ", "$" + stockData[3], false);
					embed.addField("Yesterday closed at: ", "$" + stockData[4], false);
					
					embed.addField("Yahoo finance link to this stock: ", (yahooLink) , false);
					
					embed.setFooter("Created by Nick Vitale", null);
					event.getChannel().sendMessageEmbeds(embed.build()).queue();
	
				}catch(Exception e){
					event.getChannel().sendMessage("error has occured, please try again").queue();
				}
			}
		}
		
	}
	
}
