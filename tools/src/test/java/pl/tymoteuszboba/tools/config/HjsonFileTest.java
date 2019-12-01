package pl.tymoteuszboba.tools.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.config.ToolsConfiguration;

public class HjsonFileTest {

    private ToolsConfiguration configuration;

    @Before
    public void setUp() {
        MockBukkit.mock();
        WhippyTools plugin = MockBukkit.load(WhippyTools.class);

        configuration = new ToolsConfiguration(plugin);
    }

    @Test
    public void testUsingHjsonFile() {
        String requested = "en";
        Assertions.assertEquals(configuration.getLocale(), requested);
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }

}
