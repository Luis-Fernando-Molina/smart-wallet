package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.music;

import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyRequestMethod;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.HTTPErrorResponseException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.RemoteJSonProcessor;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetAlbumException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.MusicUser;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.TokenlyConfiguration;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.config.swapbot.TokenlySongAttNames;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.exceptions.CantGenerateTokenlyAuthSignatureException;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors.AbstractTokenlyProcessor;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music.SongRecord;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.structure.TokenlyAuthenticationComponentGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class TokenlySongProcessor extends AbstractTokenlyProcessor {

    private static String swabotTokenlyURL= TokenlyConfiguration.URL_TOKENLY_MUSIC_API;

    /**
     * Represents the URL to use if is necessary to get the songs owned by a authenticated user.
     */
    private static String mySongsTokenlyURL =
            TokenlyConfiguration.URL_TOKENLY_MUSIC_API_SONGS_BY_AUHTENTICATED_USER;

    /**
     * This method returns a song from tokenly API by a request URL.
     * @param id
     * @return
     * @throws CantGetSongException
     */
    public static Song getSongById(String id) throws CantGetSongException {
        //Request URL to get a song by tokenly Id.
        String requestedURL=swabotTokenlyURL+"catalog/songs/"+id;
        try{
            JsonObject jSonObject = RemoteJSonProcessor.getJSonObject(requestedURL);
            return getSongFromJsonObject(jSonObject);
        } catch (CantGetJSonObjectException e) {
            throw new CantGetSongException(
                    e,
                    "Getting swap bot from given Id",
                    "Cannot get JSon from tokenly API using URL "+requestedURL);
        }

    }

    public static JsonArray getSongsJsonArrayByAlbumId(String albumId)
            throws CantGetJSonObjectException {
        //Request URL to get a song by tokenly Id.
        String requestedURL=swabotTokenlyURL+"catalog/songs/"+albumId;
        JsonArray jSonArray = RemoteJSonProcessor.getJSonArray(requestedURL);
        return jSonArray;
    }

    /**
     * This method returns a song array owned by a valid Tokenly user.
     * @param musicUser
     * @return
     */
    public static Song[] getSongsyAuthenticatedUser(MusicUser musicUser)
            throws CantGetAlbumException {
        try{
            HashMap<String, String> parameters = TokenlyConfiguration.getMusicAuthenticationParameters();
            //I'll remove the "Content-Type", is necessary to get an album
            parameters.remove("Content-Type");
            //X-Tokenly-Auth-Nonce
            long nonce = TokenlyAuthenticationComponentGenerator.convertTimestamp(
                    System.currentTimeMillis());
            //Generate auth signature
            String signature = TokenlyAuthenticationComponentGenerator.generateTokenlyAuthSignature(
                    musicUser,
                    mySongsTokenlyURL,
                    nonce,
                    TokenlyRequestMethod.GET);
            //Put cURL parameters
            parameters.put("X-Tokenly-Auth-Api-Token", musicUser.getApiToken());
            parameters.put("X-Tokenly-Auth-Nonce", ""+nonce);
            parameters.put("X-Tokenly-Auth-Signature", signature);
            //Get remote Json (in this version I don't have to use http url parameters.
            JsonElement response = RemoteJSonProcessor.getJsonElementByGETCURLRequest(
                    mySongsTokenlyURL,
                    parameters,
                    "");
            //The response is a Json array
            JsonArray jSonArray= response.getAsJsonArray();
            int jSonArraySize = jSonArray.size();
            Song song;
            Song[] songs = new Song[jSonArraySize];
            //Recover all the songs from the array
            int loopCounter = 0;
            for(JsonElement jsonElement : jSonArray){
                song = getSongFromJsonObject(jsonElement.getAsJsonObject());
                songs[loopCounter] = song;
                loopCounter++;
            }
            return songs;
        } catch (CantGenerateTokenlyAuthSignatureException e) {
            throw new CantGetAlbumException(
                    e,
                    "Getting album from tokenly protected api",
                    "Cannot generate Auth signature for "+musicUser);
        } catch (CantGetJSonObjectException e) {
            throw new CantGetAlbumException(
                    e,
                    "Getting album from tokenly protected api",
                    "Cannot get the Json object from "+mySongsTokenlyURL);
        } catch (HTTPErrorResponseException e) {
            throw new CantGetAlbumException(
                    e,
                    "Getting album from tokenly protected api",
                    "Error response from Tokenly Api:\n" +
                            "Error Code: "+e.getErrorCode()+"\n" +
                            "Error message: "+e.getErrorMessage());
        }

    }

    public static Song getSongFromJsonObject(
            JsonObject jsonObject) throws CantGetJSonObjectException {
        //Id
        String id = getStringFromJsonObject(jsonObject, TokenlySongAttNames.ID);
        //Name
        String name = getStringFromJsonObject(jsonObject, TokenlySongAttNames.NAME);
        //Tokens
        String[] tokens = getArrayStringFromJsonObject(jsonObject, TokenlySongAttNames.TOKENS);
        //Performers
        String performers = getStringFromJsonObject(jsonObject, TokenlySongAttNames.PERFORMERS);
        //Composers
        String composers = getStringFromJsonObject(jsonObject, TokenlySongAttNames.COMPOSERS);
        //Release date
        Date releaseDate = getDateFromJsonObject(jsonObject, TokenlySongAttNames.RELEASE_DATE);
        //Lyrics
        String lyrics = getStringFromJsonObject(jsonObject, TokenlySongAttNames.LYRICS);
        //Credits
        String credits = getStringFromJsonObject(jsonObject, TokenlySongAttNames.CREDITS);
        //Copyrights
        String copyright = getStringFromJsonObject(jsonObject, TokenlySongAttNames.COPYRIGHT);
        //OWNERSHIP
        String ownership = getStringFromJsonObject(jsonObject, TokenlySongAttNames.OWNERSHIP);
        //Usage Rights
        String usageRights = getStringFromJsonObject(jsonObject, TokenlySongAttNames.USAGE_RIGHTS);
        //Usage prohibitions
        String usageProhibitions = getStringFromJsonObject(
                jsonObject,
                TokenlySongAttNames.USAGE_PROHIBITIONS);
        //BTC address
        String bitcoinAddress = getStringFromJsonObject(jsonObject, TokenlySongAttNames.BTC_ADDRESS);
        //Other
        String other = getStringFromJsonObject(jsonObject, TokenlySongAttNames.OTHER);
        //Download url
        String downloadUrl = getStringFromJsonObject(jsonObject, TokenlySongAttNames.DOWNLOAD_URL);
        //Create record
        Song song = new SongRecord(
                id,
                name,
                tokens,
                performers,
                composers,
                releaseDate,
                lyrics,
                credits,
                copyright,
                ownership,
                usageRights,
                usageProhibitions,
                bitcoinAddress,
                other,
                downloadUrl);
        return song;

    }

}
