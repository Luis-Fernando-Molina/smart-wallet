package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
/**
 * Created by yordin on 7/08/15.
 */
public class CantGetActorAddressBookRegistryTestException extends FermatException{
    private Plugins plugin;

    //private static final long serialVersionUID = -4797409301346577158L;

    public static final String DEFAULT_MESSAGE = "CAN'T START ADDRESS BOOK CRYPTO MODULE PLUGINS ROOT";

    public CantGetActorAddressBookRegistryTestException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetActorAddressBookRegistryTestException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetActorAddressBookRegistryTestException(final String message) {
        this(message, null);
    }

    public CantGetActorAddressBookRegistryTestException(final Exception exception, final Plugins plugin) {
        this(plugin.toString() + " " + exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetActorAddressBookRegistryTestException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetActorAddressBookRegistryTestException(final Plugins plugin){
        this(plugin.toString());
    }

    public CantGetActorAddressBookRegistryTestException() {
        this(DEFAULT_MESSAGE);
    }
}
