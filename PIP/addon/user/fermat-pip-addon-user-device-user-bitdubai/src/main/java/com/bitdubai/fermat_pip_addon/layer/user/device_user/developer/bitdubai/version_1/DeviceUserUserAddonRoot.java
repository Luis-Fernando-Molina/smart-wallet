package com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.osa_android.file_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantGetDeviceUserPersonalImageException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantPersistDeviceUserException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantPersistDeviceUserPersonalImageFileException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.structure.DeviceUserUser;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.DeviceUserCreatedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.DeviceUserLoggedInEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.DeviceUserLoggedOutEvent;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantCreateNewDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetDeviceUserListException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantSetImageException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.IncorrectUserOrPasswordException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.LoginFailedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */

/**
 * The User Manager knows the users managed by the current device.
 * <p/>
 * It is responsible for login in users to the current device.
 */

public class DeviceUserUserAddonRoot implements Addon, DealsWithErrors, DealsWithEvents, DealsWithPlatformDatabaseSystem, DealsWithPlatformFileSystem, DeviceUserManager, Service {


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;


    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PlatformDatabaseSystem platformDatabaseSystem;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;


    /**
     * DeviceUserManager Interface member variables.
     */
    DeviceUser mLoggedInDeviceUser;

    final String DEVICE_USER_PUBLIC_KEYS_FILE_NAME = "deviceUserPublicKeys";

    final String PERSONAL_IMAGE_SUFFIX = "_personal_image";

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;



    /**
     * DeviceUserManager Interface implementation.
     */

    @Override
    public DeviceUser getLoggedInDeviceUser() throws CantGetLoggedInDeviceUserException{
        if (mLoggedInDeviceUser == null)
            throw new CantGetLoggedInDeviceUserException(CantGetLoggedInDeviceUserException.DEFAULT_MESSAGE, null, "There's no device user logged in.", "");
        return mLoggedInDeviceUser;
    }

    @Override
    public String createNewDeviceUser(String alias, String password) throws CantCreateNewDeviceUserException {
        try {
            ECCKeyPair keyPair = new ECCKeyPair();
            DeviceUserUser deviceUser = new DeviceUserUser(alias, password, keyPair.getPrivateKey(), keyPair.getPublicKey());
            try {
                persistNewUserInDeviceUserPublicKeysFile(deviceUser.getPublicKey());
                persistUser(deviceUser);
            } catch (CantPersistDeviceUserException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Cannot persist the device user information file.", null);
            }

            /**
             * Now I fire the User Created event.
             */
            raiseDeviceUserCreatedEvent(deviceUser.getPublicKey());

            return deviceUser.getPublicKey();
        } catch (Exception e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Unexpected error creating an user.", null);
        }
    }

    @Override
    public String createNewDeviceUser(String alias, String password, byte[] personalImage) throws CantCreateNewDeviceUserException {
        try {
            ECCKeyPair keyPair = new ECCKeyPair();
            try {
                persistUserPersonalImageFile(keyPair.getPrivateKey(), personalImage);
                DeviceUserUser deviceUser = new DeviceUserUser(alias, password, keyPair.getPrivateKey(), keyPair.getPublicKey());
                try {
                    persistNewUserInDeviceUserPublicKeysFile(deviceUser.getPublicKey());
                    persistUser(deviceUser);
                } catch (CantPersistDeviceUserException e) {
                    errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                    throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Cannot persist the device user information file.", null);
                }

                /**
                 * Now I fire the User Created event.
                 */
                raiseDeviceUserCreatedEvent(deviceUser.getPublicKey());

                return deviceUser.getPublicKey();
            } catch (CantPersistDeviceUserPersonalImageFileException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Error trying to persist the device user personal image.", null);
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Unexpected error creating an user.", null);
        }
    }

    private void persistNewUserInDeviceUserPublicKeysFile(String deviceUserPublicKey) throws CantPersistDeviceUserException {
        try {
            PlatformTextFile file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    DEVICE_USER_PUBLIC_KEYS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserPublicKeysFile = file.getContent();
            file.setContent((deviceUserPublicKeysFile != null ? deviceUserPublicKeysFile : "")+ deviceUserPublicKey + ";");

        } catch (FileNotFoundException|CantCreateFileException e) {
            throw new CantPersistDeviceUserException(CantPersistDeviceUserException.DEFAULT_MESSAGE, e, "Error getting device user public keys file.", null);
        }
    }

    private void persistUser(DeviceUserUser deviceUser) throws CantPersistDeviceUserException {
        PlatformTextFile file;
        try {
            file = this.platformFileSystem.createFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    deviceUser.getPublicKey(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(deviceUser.getAlias() + ";" + deviceUser.getPassword() + ";" + deviceUser.getPrivateKey());

            try {
                file.persistToMedia();
            } catch (CantPersistFileException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantPersistDeviceUserException(CantPersistDeviceUserException.DEFAULT_MESSAGE, e, "Error persisting file.", null);
            }
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantPersistDeviceUserException(CantPersistDeviceUserException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        }
    }

    private void persistUserPersonalImageFile(String publicKey, byte[] personalImage) throws CantPersistDeviceUserPersonalImageFileException {
        try {
            PlatformBinaryFile file = this.platformFileSystem.createBinaryFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey + PERSONAL_IMAGE_SUFFIX,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(personalImage);

            try {
                file.persistToMedia();
            } catch (CantPersistFileException e) {
                throw new CantPersistDeviceUserPersonalImageFileException(CantPersistDeviceUserPersonalImageFileException.DEFAULT_MESSAGE, e, "Error persisting device user personal image file.", null);
            }
        } catch (CantCreateFileException e) {
            throw new CantPersistDeviceUserPersonalImageFileException(CantPersistDeviceUserPersonalImageFileException.DEFAULT_MESSAGE, e, "Error creating device user personal image file.", null);
        }
    }

    private void raiseDeviceUserCreatedEvent(String publicKey) {
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.DEVICE_USER_CREATED);
        ((DeviceUserCreatedEvent) platformEvent).setPublicKey(publicKey);
        platformEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
        eventManager.raiseEvent(platformEvent);
    }

    @Override
    public List<DeviceUser> getAllDeviceUsers() throws CantGetDeviceUserListException {
        try {
            PlatformTextFile file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    DEVICE_USER_PUBLIC_KEYS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserPublicKeysFile = file.getContent();
            String[] deviceUserPublicKeysFileSplit = deviceUserPublicKeysFile.split(";");

            List<DeviceUser> deviceUserList = new ArrayList<>();

            try {
                for (String deviceUserPublicKey : deviceUserPublicKeysFileSplit) {
                    deviceUserList.add(getDeviceUser(deviceUserPublicKey));
                }
            } catch (CantGetDeviceUserException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantGetDeviceUserListException(CantGetDeviceUserListException.DEFAULT_MESSAGE, e, "Error getting device user information file.", null);
            }

            return deviceUserList;

        } catch (FileNotFoundException|CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantGetDeviceUserListException(CantGetDeviceUserListException.DEFAULT_MESSAGE, e, "Error getting device user public keys file.", null);
        }
    }

    @Override
    public DeviceUser getDeviceUser(String publicKey) throws CantGetDeviceUserException {
        PlatformTextFile file;
        try {
            file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserString = file.getContent();
            String[] deviceUserStringSplit = deviceUserString.split(";");
            byte[] personalImage = null;
            try {
                personalImage = getDeviceUserPersonalImage(publicKey);
            } catch (CantGetDeviceUserPersonalImageException e) {
                // TODO QUE HAGO SI NO CONSIGO IMAGEN?
            }
            return new DeviceUserUser(deviceUserStringSplit[0], deviceUserStringSplit[1], deviceUserStringSplit[2], personalImage);

        } catch (FileNotFoundException|CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantGetDeviceUserException(CantGetDeviceUserException.DEFAULT_MESSAGE, e, "Error getting device user information file.", null);
        }
    }

    private byte[] getDeviceUserPersonalImage(String publicKey) throws CantGetDeviceUserPersonalImageException {
        PlatformBinaryFile file;
        try {
            file = this.platformFileSystem.getBinaryFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey + PERSONAL_IMAGE_SUFFIX,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            return file.getContent();
        } catch (FileNotFoundException|CantCreateFileException e) {
            throw new CantGetDeviceUserPersonalImageException(CantGetDeviceUserPersonalImageException.DEFAULT_MESSAGE, e, "Error trying to get device user personal image file.", null);
        }
    }

    @Override
    public void login(String publicKey, String password) throws LoginFailedException, IncorrectUserOrPasswordException {
        try {
            PlatformTextFile file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserString = file.getContent();
            String[] deviceUserStringSplit = deviceUserString.split(";");

            if (deviceUserStringSplit[1].equals(password)) {
                byte[] personalImage = null;
                try {
                    personalImage = getDeviceUserPersonalImage(publicKey);
                } catch (CantGetDeviceUserPersonalImageException e) {
                    // TODO QUE HAGO SI NO CONSIGO IMAGEN?
                }
                mLoggedInDeviceUser = new DeviceUserUser(deviceUserStringSplit[0], deviceUserStringSplit[1], deviceUserStringSplit[2], personalImage);

                /**
                 * If all goes ok i send the event of User logged in
                 */
                PlatformEvent platformEvent = eventManager.getNewEvent(EventType.DEVICE_USER_LOGGED_IN);
                ((DeviceUserLoggedInEvent) platformEvent).setPublicKey(publicKey);
                platformEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
                eventManager.raiseEvent(platformEvent);
            } else {
                throw new IncorrectUserOrPasswordException(IncorrectUserOrPasswordException.DEFAULT_MESSAGE, null, "Bad credentials", "");
            }
        } catch (FileNotFoundException|CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new LoginFailedException(LoginFailedException.DEFAULT_MESSAGE, e, "Error trying to login.", null);
        }
    }

    @Override
    public void logout() {
        /**
         * Raise event user logged out to platform.
         */
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.DEVICE_USER_LOGGED_OUT);
        ((DeviceUserLoggedOutEvent) platformEvent).setPublicKey(mLoggedInDeviceUser.getPublicKey());
        platformEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        /**
         * Set loggedin User null
         */
        mLoggedInDeviceUser = null;
    }

    @Override
    public void setPersonalImage(byte[] personalImage) throws CantSetImageException {
        try {
            persistUserPersonalImageFile(mLoggedInDeviceUser.getPublicKey(), personalImage);
        } catch(CantPersistDeviceUserPersonalImageFileException e) {
            throw new CantSetImageException(CantSetImageException.DEFAULT_MESSAGE, e, "Cant persist device user personal image.", "");
        }
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPlatformFileSystem Interface implementation.
     */
    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }
}
