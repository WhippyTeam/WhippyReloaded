package pl.tymoteuszboba.tools.system;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.hjson.JsonObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.system.i18n.MessageBundler;
import pl.tymoteuszboba.whippytools.system.i18n.enums.MessageTarget;

public class MessageBundlerChatTest {

    private JsonObject messageObject;

    private WhippyTools plugin;
    private PlayerMock player;

    @Before
    public void setUp() {
        ServerMock mock = MockBukkit.mock();
        this.plugin = MockBukkit.load(WhippyTools.class);
        this.player = mock.addPlayer();

        this.messageObject = this.plugin.getWhippyConfig().getLocaleFile().get("random").asObject();
    }


    @Test
    public void testUsingMessageBundlerOnChat() {
        MessageBundler.send(this.plugin, this.messageObject, "commandListIndex")
            .target(MessageTarget.CHAT)
            .to(player);

        player.assertSaid("All Whippy commands");
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }
}
