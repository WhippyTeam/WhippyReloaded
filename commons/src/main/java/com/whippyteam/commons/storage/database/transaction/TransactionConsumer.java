package com.whippyteam.commons.storage.database.transaction;

import com.whippyteam.commons.storage.database.DatabaseConsumer;

import java.sql.PreparedStatement;

public interface TransactionConsumer extends DatabaseConsumer<PreparedStatement> {
}
