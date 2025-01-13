package org.example;

import java.util.List;

public interface RankingStrategy {
    List<Show> rank(List<Show> shows);
}

