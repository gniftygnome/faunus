package cybercat5555.faunus.util;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class ConfigRegistry implements SimpleConfig.DefaultConfig{
    private String configContent = "";

    public List<Pair> getConfigsList() {
        return configsList;
    }

    private final List<Pair> configsList = new ArrayList<>();

    public void addComment(String comment) {
        configContent += "#" + comment + "\n";
    }

    public void addPairData(Pair keyValuePair, String comment) {
        configsList.add(keyValuePair);
        configContent += keyValuePair.getFirst() + "=" + keyValuePair.getSecond() + " #" + comment + " | default: "
                + keyValuePair.getSecond() + "\n";
    }

    @Override
    public String get(String namespace) {
        return configContent;
    }
}
