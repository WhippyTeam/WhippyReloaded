package com.whippyteam.api.storage.database.mysql.system.transaction;

import com.whippyteam.api.storage.database.mysql.system.DatabaseConsumer;
import java.sql.PreparedStatement;

public interface TransactionConsumer extends DatabaseConsumer<PreparedStatement> {
}
