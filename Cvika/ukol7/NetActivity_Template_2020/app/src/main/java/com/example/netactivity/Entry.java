package com.example.netactivity;

import java.io.Serializable;

public class Entry implements Serializable {
    public final String kod;
    public final String mena;
    public final String zeme;
    public final String kurz;

    // TODO 3. Rozsirit dalsi udaje ve tride, ktere se budou vest pro kazdou menu
    // TODO 3. To zahrnuje i upraveni konstruktoru

    Entry(String kod, String mena, String zeme, String kurz) {
        this.kod = kod;
        this.mena = mena;
        this.zeme = zeme;
        this.kurz = kurz;
    }
}
