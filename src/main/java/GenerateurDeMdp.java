import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class GenerateurDeMdp {

    private final String[] args;

    private final int tailleMdp;


    public GenerateurDeMdp(String[] args){
        this.args = args;
        this.tailleMdp = this.definirTailleMdp();
    }

    public void lancement(){
        if (args.length == 0 && contientArg(args, "-h")) {
            this.afficherAide();
        }
        else {
            String mdp = this.genererMdp();
            System.out.println(mdp);
        }
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

    private ArrayList<Character> melangerMdp(ArrayList<Character> melange, String baseMdp){
        for(char c: baseMdp.toCharArray()){
            melange.add(c);
        }
        Collections.shuffle(melange);
        return melange;
    }

    public String genererMdp(){
        String baseMdp = genererMdpNonMelanger();
        StringBuilder sb = new StringBuilder();

        for (char c: melangerMdp(new ArrayList<Character>(), baseMdp)) {
            sb.append(c);
        }

        return sb.substring(0,this.tailleMdp);
    }

    private int definirTailleMdp(){
        if(contientArg(args, "-n") && args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-n") ) {
                    if (estUnNombre(args[i + 1])) {
                        return Integer.parseInt(args[i + 1]);
                    }
                    else {
                        System.out.println("Erreur: L'argument pour -n doit être un nombre.");
                        System.exit(1);
                    }
                }
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

    private void afficherAide(){
        System.out.println(""" 
                    Options:
                    -n <nombre> : Définit la taille du mot de passe (par défaut entre 8 et 20)
                    -c : Inclut les chiffres dans le mot de passe
                    -s : Inclut les caractères spéciaux dans le mot de passe
                    -h : Affiche cette aide
                    """);
    }
}
