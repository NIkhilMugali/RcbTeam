package com.vagrant.rcb;

import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RcbTest {

    private static final String INDIA = "India";
    private static final String WICKET_KEEPER = "Wicket-keeper";

    @Test
    public void validateNumberOfForeignPlayers() throws Exception{
        JSONObject teamObj = getJson();
        JSONArray playersList = teamObj.optJSONArray("player");
        long numberOfForeignPlayers = IntStream.range(0, playersList.length())
                .filter(i -> !playersList.optJSONObject(i).optString("country").equals(INDIA))
                .count();
        Assert.assertTrue("Team should have only 4 foreign players.", numberOfForeignPlayers == 4);
    }

    @Test
    public void validateWicketKeeper() throws Exception {
        JSONObject teamObj = getJson();
        JSONArray playersList = teamObj.optJSONArray("player");
        long numberOfForeignPlayers = IntStream.range(0, playersList.length())
                .filter(i -> playersList.optJSONObject(i).optString("role").equals(WICKET_KEEPER))
                .count();
        Assert.assertTrue("Team should have at least one wicket keeper.", numberOfForeignPlayers >= 1);
    }

    public JSONObject getJson() throws Exception {
        String path = "./src/test/resources/TeamRCB.json";
        File file = new File(path);
        String jsonText = null;
        if(file.exists()){
            try(InputStream in = new FileInputStream(path)){
                jsonText = new BufferedReader(new InputStreamReader(in))
                        .lines().collect(Collectors.joining("\n"));
            }
        }
        return Objects.isNull(jsonText) ? new JSONObject() : new JSONObject(jsonText);
    }
}