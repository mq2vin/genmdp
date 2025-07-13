import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class GenerateurDeMdp {

    private final String[] args;

    private final int tailleMdp;

    /**
     * Constructeur
     * @param args Tableau de String
     */
    public GenerateurDeMdp(String[] args){
        this.args      = args;
        this.tailleMdp = this.genererTailleMdp();
    }


    /**
     * Méthode principale de la classe GenerateurMdp.
     */
    public void lancement(){
        if (args.length == 0 && contientArg("-h")) {
            this.afficherAide();
        }
        else {
            String mdp = this.genererMdp();
            System.out.println(mdp);
        }
    }


    /**
     * Méthode qui génère la base du mot de passe
     * en fonction des arguments.
     * @return String.
     */
    private String genererMdpNonMelanger(){
        String LETTRE       = "abcdefghijklmnopqrstuvwxyz";
        String CHIFFRE      = "0123456789";
        String CAR_SPECIAUX = "!#$%&()*+,./:;<=>?@[\\]_{|}~";

        if (contientArg("-c")) {
            return LETTRE + CHIFFRE;
        }
        else if (contientArg("-s")) {
            return LETTRE + CAR_SPECIAUX;
        }
        else {
            return LETTRE + CHIFFRE + CAR_SPECIAUX;
        }
    }


    /**
     * Méthode qui permet de modifier l'ordre des caracteres dans le mot de passe.
     * @param melange Arraylist de caractére vide
     * @param baseMdp String
     * @return Arraylist de caractére
     */
    private ArrayList<Character> melangerMdp(ArrayList<Character> melange, String baseMdp){
        for(char c: baseMdp.toCharArray()){
            melange.add(c);
        }
        Collections.shuffle(melange);
        return melange;
    }

    /**
     * Méthode qui génère le mot de passe
     * @return String
     */
    public String genererMdp(){
        String baseMdp   = genererMdpNonMelanger();
        StringBuilder sb = new StringBuilder();

        for (char c: melangerMdp(new ArrayList<>(), baseMdp)) {
            sb.append(c);
        }

        return sb.substring(0,this.tailleMdp);
    }


    /**
     * Méthode qui génère une taille pour le mot de passe.
     * (Le taille donner en argument ou une taille génèrer aléatoirement)
     * @return int
     */
    private int genererTailleMdp(){
        if(contientArg("-n") && args.length > 1) {
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


    /**
     * Méthode qui génère aléatoirement un entier entre 20 et 8.
     * @return int
     */
    private int genererTailleRandom(){
        SecureRandom sr = new SecureRandom();
        return sr.nextInt(20 - 8 + 1) + 8;
    }


    /**
     * Méthode qui permet de savoir si parmis les arguments passer par l'utillisataur
     * on retrouve un argument en particulier.
     * @param argument String
     * @return boolean
     */
    private boolean contientArg(String argument){
        for (String arg : args) {
            if (arg.equals(argument)) return true;
        }
        return false;
    }


    /**
     * Méthode qui permet de savoir si un String est un nombre.
     * @param nombre String
     * @return boolean
     */
    private boolean estUnNombre(String nombre){
        try {
            Integer.parseInt(nombre);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }


    /**
     * Méthode qui affiche les différentes arguments.
     */
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
