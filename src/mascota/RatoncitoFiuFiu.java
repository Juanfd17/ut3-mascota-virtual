package mascota;

public class RatoncitoFiuFiu {
    //
    // La clase mascota.RatoncitoFiuFiu aun no tiene ningun atributo
    //

    private final int INFANCIA = 150;
    private final  int ADULTO = 800;
    private String nombre; //nombre de la mascota
    private int edad; //tiempo en segundos de la mascota
    private float peso; //peso en gramos de la mascota
    private float hambre; //hambre que tiene la mascota entre 0 (sin hambre) y 10 (mucha hambre)
    private float suciedad; //nivel de suciedad de la mascota entre 0(limpio) y 100(sucio)
    private float salud; //salud de la mascota entre 0(muero) y 100(sano)
    private float energia; //energia de la mascota entre 0(apatico) y 100 (activo)
    private boolean duerme;
    private int tiempoJuego;
    private float vezesCurada;

    public RatoncitoFiuFiu(String nombre, int peso, byte hambre, byte suciedad, byte salud, byte energia) {
        this.nombre = nombre;
        this.peso = peso;
        this.hambre = hambre;
        this.suciedad = suciedad;
        this.salud = salud;
        this.energia = energia;
        this.duerme = false;
        this.tiempoJuego = 0;
        this.vezesCurada = 0;
    }

    public void alimentar(float cantidadAlimento) {
        this.aumentarHambre(-(cantidadAlimento / 5));
        if (!this.estasEnfermo()){
            this.ganarPeso(cantidadAlimento/8);
        }

        if (tienesHambre()){
            this.aumentarSalud(3);
        }

        if (!tienesHambre()){
            this.aumentarSalud(-3);
        }
    }

    public void curar(float cantidadMedicina) {
        if (vezesCurada > 3){
            this.aumentarSalud(-(cantidadMedicina));
        } else if (!this.estasEnfermo()){
            this.aumentarSalud(-(cantidadMedicina / 3));
        } else {
            this.aumentarSalud(-(cantidadMedicina / 5));
        }
        if(vezesCurada < 0){
            vezesCurada = 0;
        }
        vezesCurada++;
    }
    public void limpiar(float esfuerzoHigienico) {
        this.aumentarSuciedad(-(esfuerzoHigienico / 5));
    }

    public String estadisticas() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Peso: ").append(this.peso);
        sb.append("\nHambre: ").append(this.hambre);
        sb.append("\nSuciedad: ").append(this.suciedad);
        sb.append("\nSalud: ").append(this.salud);
        sb.append("\nEnergia").append(this.energia);
        return sb.toString();
    }

    public void envejecer(int tiempo) {
        this.aumentarTiempo(tiempo);
        if(this.queTramoEdad() == 0){
            this.aumentarEnergia((float) - tiempo / 8);
            this.aumentarHambre((float) tiempo / 15);
            this.aumentarSalud((float) - tiempo / 50);
        } else if (this.queTramoEdad() == 1) {
            this.aumentarEnergia((float) - tiempo / 15);
            this.aumentarHambre((float) tiempo / 10);
            this.aumentarSalud((float) - tiempo / 50);
        } else {
            this.aumentarEnergia((float) - tiempo / 25);
            this.aumentarHambre((float) tiempo / 20);
            this.aumentarSalud((float) - tiempo / 25);
        }

        this.aumentarSuciedad((float) tiempo / 15);
        this.aumentarTiempoJuego((float) - tiempo);
        this.vezesCurada -= ((float) - tiempo / 5);

        if (this.estasEnfermo()){
            this.ganarPeso((float) - tiempo / 90);
        } else {
            this.ganarPeso((float) - tiempo / 60);
        }
    }

    public boolean estasSucio() {
        if (this.suciedad > 45){
            this.aumentarSalud(-2);
            return true;
        }
        return false;
    }

    public boolean estasEnfermo() {
        if (this.salud < 75){
            return true;
        }
        return false;
    }

    public boolean estasMuerto() {
        if (this.salud <= 0){
            return true;
        }
        return false;
    }

    public boolean tienesHambre(){
        if (this.hambre > 4){
            if (this.hambre >= 6){
                this.aumentarSalud(-2);
            }
            if (this.hambre >= 8){
                this.aumentarSalud(-5);
            }
            if (this.hambre >= 10){
                this.aumentarSalud(-6);
            }
            return true;
        }
        return false;
    }

    public boolean estasFeliz(){
        if (!tienesHambre() && !estasEnfermo() && estasEntretenido()){
            return true;
        }
        return false;
    }

    private void ganarPeso(float cantidad){
        this.peso += cantidad;

        if (peso < 0){
            this.peso = 0;
        }

        if (this.peso > 60 || this.peso < 40){
            this.aumentarSalud(-2);
        }
    }

    public void aumentarEnergia(float cantidad){
        this.energia += cantidad;

        if (this.energia < 0){
            this.energia = 0;
        }

        if (this.energia > 100){
            this.energia = 100;
        }
    }

    private void aumentarSalud(float cantidad){
        if (!estasMuerto()) {
            this.salud += cantidad;
        }

        if (this.salud < 0){
            this.salud = 0;
        }

        if (this.salud > 100){
            this.salud = 100;
        }
    }

    private void aumentarSuciedad(float cantidad){
        this.suciedad += cantidad;

        if(this.suciedad < 0){
            this.suciedad = 0;
        }

        if (this.suciedad > 100){
            this.suciedad = 100;
        }
    }

    private void aumentarHambre(float cantidad){
        this.hambre += cantidad;
        this.aumentarEnergia(cantidad / 5);

        if (this.hambre < 0){
            this.hambre = 0;
        }

        if (this.hambre > 10){
            this.hambre = 10;
        }
    }

    private void aumentarTiempo(int segundos){
        this.edad += segundos;
    }
    public boolean estasDormido() {
        if (this.energia < 30){
            this.aumentarSalud(-2);

            if (this.energia < 20){
                this.aumentarSalud(-5);
            }
        }

        if (this.energia < 50){
            this.duerme = true;
            return true;
        }

        if (this.energia > 75){
            this.duerme = false;
            return false;
        }

        if (this.energia > 50){
            return this.duerme;
        }

        return this.duerme;
    }

    public int queTramoEdad() {
        if (this.edad < INFANCIA){
            return 0;
        }

        if (this.edad < ADULTO){
            return 1;
        }

        return 2;
    }

    public boolean tienesQuejas() {
        if (this.tienesHambre() && !this.estasMuerto() && !this.estasEnfermo()){
            return true;
        }

        return false;
    }

    public boolean estasEntretenido(){
        if (this.tiempoJuego < 0){
            this.tiempoJuego = 0;
        }
        if (this.tiempoJuego > 5){
            return true;
        }
        return false;
    }

    public void aumentarTiempoJuego(float segundos){
        this.tiempoJuego += segundos;
    }
    public boolean jugar (float cantidadDiversion){
        this.aumentarEnergia(-cantidadDiversion/4);
        this.aumentarHambre(cantidadDiversion/10);
        this.aumentarTiempoJuego(cantidadDiversion);
        return true;
    }

    public int getTiempoJuego() {
        return tiempoJuego;
    }
}