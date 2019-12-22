package pl.tymoteuszboba.whippytools.storage.database.transaction;

import java.sql.PreparedStatement;
import pl.tymoteuszboba.whippytools.storage.database.DatabaseConsumer;

public interface TransactionConsumer extends DatabaseConsumer<PreparedStatement> {
}
