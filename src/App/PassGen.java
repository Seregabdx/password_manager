package App;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
/**
 * generate password
 * Created by HOME on 04.12.2020.
 */
public class PassGen {
    private String str;
    private int randInt;
    private StringBuilder sb;
    private List<Integer> l;
    private int length;
    /**
     * @param  lg password length
     * init components to gen new password
     */
    public PassGen(int lg) {
        length = lg;
        this.l = new ArrayList<>();
        this.sb = new StringBuilder();
        buildPassword();
    }
    /**
     * compile password from components
     */
    private void buildPassword() {

        //Add ASCII numbers of characters commonly acceptable in passwords
        for (int i = 33; i < 127; i++) {
            l.add(i);
        }

        //Remove characters /, \, and " as they're not commonly accepted
        l.remove(new Integer(34));
        l.remove(new Integer(47));
        l.remove(new Integer(92));

        /*Randomise over the ASCII numbers and append respective character
          values into a StringBuilder*/
        for (int i = 0; i < length; i++) {
            randInt = l.get(new SecureRandom().nextInt(91));
            sb.append((char) randInt);
        }

        str = sb.toString();
    }
    /**
     * @return string with generated password
     * generate password
     */
    public String generatePassword() {
        return str;
    }
}
