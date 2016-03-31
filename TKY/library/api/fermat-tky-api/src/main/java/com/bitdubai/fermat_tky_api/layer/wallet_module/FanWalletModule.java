package com.bitdubai.fermat_tky_api.layer.wallet_module;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetBotException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.Bot;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDeleteSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantDownloadSongException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongListException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantGetSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantSynchronizeWithExternalAPIException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongDevicePathException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions.CantUpdateSongStatusException;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/17/16.
 */
public interface FanWalletModule extends FermatManager{

    //Song Wallet
    /**
     * This method returns a songs list by SongStatus enum
     * @param songStatus
     * @return
     * @throws CantGetSongListException
     */
    List<WalletSong> getSongsBySongStatus(SongStatus songStatus) throws CantGetSongListException;

    /**
     * This method returns a available songs list.
     * SongStatus: AVAILABLE
     * @return
     * @throws CantGetSongListException
     */
    List<WalletSong> getAvailableSongs() throws CantGetSongListException;

    /**
     * This method returns a deleted songs list.
     * SongStatus: DELETED
     * @return
     * @throws CantGetSongListException
     */
    List<WalletSong> getDeletedSongs() throws CantGetSongListException;

    /**
     * This method returns a SongStatus by songId.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @return
     * @throws CantGetSongStatusException
     */
    SongStatus getSongStatus(UUID songId) throws CantGetSongStatusException;

    /**
     * This method starts the synchronize songs process.
     * This checks the time passed between the method execution and the last update, if the actual
     * time - last updated is less than the default update interval, this method not synchronize
     * with external API.
     * @param tokenlyUsername
     * @throws CantSynchronizeWithExternalAPIException
     */
    void synchronizeSongs(String tokenlyUsername) throws CantSynchronizeWithExternalAPIException;

    /**
     * This method starts the synchronize songs process.
     * In this case, the synchronize process is started by the user.
     * This method doesn't check the last update field.
     * @param username
     * @throws CantSynchronizeWithExternalAPIException
     */
    void synchronizeSongsByUser(String username) throws CantSynchronizeWithExternalAPIException;

    /**
     * This method deletes a song from the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @throws CantDeleteSongException
     */
    void deleteSong(UUID songId) throws
            CantDeleteSongException,
            CantUpdateSongStatusException;

    /**
     * This method downloads a song to the wallet and the device storage.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @param songId
     * @throws CantDownloadSongException
     */
    void downloadSong(UUID songId) throws
            CantDownloadSongException,
            CantUpdateSongDevicePathException,
            CantUpdateSongStatusException;

    //Fan Identity

    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the fan
     * identities linked to the current logged device user.
     * @return
     * @throws CantListFanIdentitiesException
     */
    List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException;

    //External API
    /**
     * This method returns String that contains a swap bot by botId
     * @param botId represents the bot Id in swapbot site.
     * @return
     */
    Bot getBotByBotId(String botId) throws CantGetBotException;

    /**
     * This method returns String that contains a swap bot by tokenly username
     * @param username
     * @return
     * @throws CantGetBotException
     */
    Bot getBotBySwapbotUsername(String username) throws CantGetBotException;
}
