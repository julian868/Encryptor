import java.security.SecureRandom;

import javax.crypto.spec.IvParameterSpec;

public class IvGen {
    public IvParameterSpec ivGen() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
