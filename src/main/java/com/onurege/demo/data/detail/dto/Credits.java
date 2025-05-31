package com.onurege.demo.data.detail.dto;

import java.util.List;

public class Credits {
    public List<CastDto> cast;
    public List<Crew> crew;

    public List<CastDto> getCast() {
        return cast;
    }

    public void setCast(List<CastDto> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
