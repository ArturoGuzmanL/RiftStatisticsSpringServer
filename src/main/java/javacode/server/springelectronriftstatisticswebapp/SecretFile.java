package javacode.server.springelectronriftstatisticswebapp;

import no.stelar7.api.r4j.basic.APICredentials;

public class SecretFile
{
    public static final String LEAGUE_KEY     = System.getenv("RIOT_API_KEY");
    public static final String TOURNAMENT_KEY = System.getenv("RIOT_API_KEY");
    public static final String LOR_KEY        = System.getenv("RIOT_API_KEY");
    public static final String TFT_KEY        = System.getenv("RIOT_API_KEY");
    public static final String VAL_KEY        = System.getenv("RIOT_API_KEY");

    public static final APICredentials CREDS = new APICredentials(LEAGUE_KEY, TOURNAMENT_KEY, TFT_KEY, LOR_KEY, VAL_KEY);
}