/*
 * @#TemplateNetworkServiceDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TemplateNetworkServiceDatabaseConstants {

    /**
     * Represent the name of the data base
     */
    public static final String DATA_BASE_NAME  = "template_network_service_data_base";

    /**
     * Incoming messages database table definition.
     */
    static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";
    static final String INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME = "id";
    static final String INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME = "sender_id";
    static final String INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    static final String INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME = "text_content";
    static final String INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME = "type";
    static final String INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    static final String INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    static final String INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME = "status";

    /**
     * Outgoing messages database table definition.
     */
    static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";
    static final String OUTGOING_MESSAGES_TABLE_ID_COLUMN_NAME = "id";
    static final String OUTGOING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME = "sender_id";
    static final String OUTGOING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    static final String OUTGOING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME = "text_content";
    static final String OUTGOING_MESSAGES_TABLE_TYPE_COLUMN_NAME = "type";
    static final String OUTGOING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    static final String OUTGOING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    static final String OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME = "status";

}
