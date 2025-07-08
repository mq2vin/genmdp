import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class GenerateurDeMdp {

    private final String[] args;

    private final int tailleMdp;

    public GenerateurDeMdp(String[] args){
        this.args = args;
        this.tailleMdp = definirTailleMdp(args);
    }

    public void lancement(){
        String mdp = genererMdp();
        System.out.println(mdp);

    }

    private String genererMdpNonMelanger(){
        String LETTRE = "abcdefghijklmnopqrstuvwxyz";
        String CHIFFRE = "0123456789";
        String CAR_SPECIAUX = "!#$%&()*+,./:;<=>?@[\\]_{|}~";

        if (contientArg(args, "-c")) {
            return LETTRE + CHIFFRE;
        }
        else if (contientArg(args, "-s")) {
            return LETTRE + CAR_SPECIAUX;
        }
        else {
            return LETTRE + CHIFFRE + CAR_SPECIAUX;
        }
    }

    public String genererMdp(){

        String mdpNonMelanger = genererMdpNonMelanger();

        ArrayList<Character> melange = new ArrayList<>();
        for (char c: mdpNonMelanger.toCharArray()) {
            melange.add(c);
        }

        Collections.shuffle(melange);

        StringBuilder sb = new StringBuilder();

        for (char c: melange){
            sb.append(c);
        }

        return sb.substring(0,this.tailleMdp);
    }

    private int definirTailleMdp(String[] args){
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-n") && estUnNombre(args[i+1])) {
                return Integer.parseInt(args[i + 1]);
            }
        }
        return genererTailleRandom();
    }

    private int genererTailleRandom(){
        SecureRandom sr = new SecureRandom();
        return sr.nextInt(20 - 8 + 1) + 8;
    }

    private boolean contientArg(String[] args, String argument){
        for (String arg : args) {
            if (arg.equals(argument)) return true;
        }
        return false;
    }

    private boolean estUnNombre(String nombre){
        try {
            Integer.parseInt(nombre);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
