package com.academyofdata.cassj;


import com.datastax.driver.core.ConsistencyLevel;
import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.Consistency;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;

import java.util.List;

@Table(table="movies")
@Consistency(read= ConsistencyLevel.ONE, write=ConsistencyLevel.QUORUM, serial = ConsistencyLevel.SERIAL)

public class Movie {
    @PartitionKey
    private Integer mid;

    @Column
    private String title;

    @Column
    private List<String> genres;

    public Movie(){}

    public Movie(Integer mid, String title, List genres){
        this.mid = mid;
        this.genres = genres;
        this.title = title;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Integer getMid() {
        return mid;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getTitle() {
        return title;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
