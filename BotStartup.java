import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class BotStartup {
	public static void main(String[] args) throws LoginException {
		JDABuilder jda = JDABuilder.createDefault("");			//put your discord bots token here 
		jda.setActivity(Activity.playing("with Stonks"));
		jda.setStatus(OnlineStatus.ONLINE);
		jda.addEventListeners(new Commands());
		jda.build();
	}
}
