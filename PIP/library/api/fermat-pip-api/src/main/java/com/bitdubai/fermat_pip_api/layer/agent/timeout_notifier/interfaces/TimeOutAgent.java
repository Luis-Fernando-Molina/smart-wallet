package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces;

import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantResetTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStartTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStopTimeOutAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;

import java.util.UUID;

/**
 * Created by rodrigo on 3/26/16.
 */
public interface TimeOutAgent {

    /**
     * notifies if the agent is running.
     * @return true if is running, false if not.
     */
    boolean isRunning();

    /**
     * Starts the timeout Agent monitoring process.
     * @throws CantStartTimeOutAgentException
     */
    void startTimeOutAgent() throws CantStartTimeOutAgentException;

    /**
     * Resets the counter of the agent if still running. A reset  means that the timeout duration counter
     * will start again. For example, if TimeOutDuration was 60 minutes and the reset occurs at minute 39,
     * another 60 minutes must passed for a new notification to be raised.
     * @throws CantResetTimeOutAgentException
     */
    void resetTimeOutAgent() throws CantResetTimeOutAgentException;

    /**
     * Stops the Timeout Agent. No monitoring is done, meaning that no notifications will be raised
     * at this state.
     * @throws CantStopTimeOutAgentException
     */
    void stopTimeOutAgent() throws CantStopTimeOutAgentException;

    /**
     * gets the internal UUID of the agent
     * @return the internal UUID
     */
    UUID getUUID();

    /**
     * the start time of the Agent as an epoch time in milliseconds.
     * @return the start time of the agent.
     */
    long getEpochTime();

    /**
     * The duration that the Agent will be monitoring for. When this duration is reached since the start time
     * an event will be triggered.
     * @return the duration of the time out
     */
    long getTimeOutDuration();

    /**
     * the agent given name for identification.
     * @return the Agent given name
     */
    String getAgentName();

    /**
     * The agent given Description.
     * @return the Description of the agent
     */
    String getAgentDescription();

    /**
     * The actor type that created this agent
     * @return null if not provided.
     */
    Actors getOwnerType();

    /**
     * The current agent status.
     * @return the current agent status.
     */
    AgentStatus getAgentStatus();

    /**
     * The notification Protocol status of the raised event.
     * The event will continue to be raised until someone consumes it.
     * @return the Protocol Status
     */
    ProtocolStatus getNotificationProtocolStatus();

    /**
     * Marks the notification of an event as Read. After a notification has been acknowledge, we don't raise it
     * anymore.
     */
    void markEventNotificationAsRead();
}
