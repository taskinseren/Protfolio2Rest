package dhbw.spotify;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Florian on 03.03.2018.
 */
public class AuthentificationTest {

    @Test
    public void testAuthCreation(){
        Authentification authentification = Authentification.getInstance();
        System.out.println("Api Token: " + authentification.getAuthKey());
        assertTrue(!authentification.getAuthKey().equals(""));
    }

}