package com.whippyteam.whippytools.storage.database.transaction;

import com.whippyteam.whippytools.storage.database.DatabaseConsumer;
import java.sql.PreparedStatement;

public interface TransactionConsumer extends DatabaseConsumer<PreparedStatement> {
}
