package com.udem.fruname.juegos;

public class Pregunta {
    String pregunta;
    String opcion1;
    String opcion2;
    String opcion3;
    String opcion4;
    String correcta;

    public String getPregunta() {
        return pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public String getCorrecta() {
        return correcta;
    }


    public Pregunta(String pregunta, String opcion1, String opcion2, String opcion3, String opcion4, String correcta) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.correcta = correcta;
    }


}
