package demo.workFunctions;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import demo.enums.UserStatus;
import demo.model.Utilisateur;
import demo.repository.UtilisateurRepository;

public class UserManager {
	
	public boolean isUserConnected(Cookie cookie, UtilisateurRepository userRepository){
		
		if(cookie != null && tryParseInteger(cookie.getValue())){
			int userId = Integer.parseInt(cookie.getValue());		
			Utilisateur user;
			
			user = userRepository.findOne(userId);
					
			if(user != null && user.getCurrentUserStatus() != UserStatus.BLOCKED)
				return true;
			else
				return false;
		}		
		return false;
	}
	
	private boolean tryParseInteger(String value)
	{
		try{
			int intValue = Integer.parseInt(value);			
			return true;
		}
		catch(Exception exception){
			return false;
		}
	}
	
}
