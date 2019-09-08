package ba.unsa.etf.rs.t5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public Slider sliderGodinaRodjenja;

    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
         });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty() );
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");

            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().bindBidirectional( newKorisnik.godinaRodjenjaProperty() );
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() >= 3 && provjeraImena(newIme)) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() >= 3 && provjeraImena(newIme)) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.contains("@") && provjeraMaila(newIme)) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() <= 16 && provjeraUsername(newIme)) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && newIme.length() >= 8) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public boolean provjeraImena(String ime){
        if(ime.length() == 0) return false;
        int i;

        for(i = 0 ; i<ime.length() ; i++) {
            if (!((ime.charAt(i) >= 'A' && ime.charAt(i) <= 'Z') || (ime.charAt(i) >= 'a' && ime.charAt(i) <= 'z') ||
                    ime.charAt(i) == ' ' || ime.charAt(i) == '-')) return false;
        }

    return true;
    }

    public boolean provjeraUsername(String username){
        if(username.length() == 0) return false;
        int i;

        for(i = 0 ; i<username.length() ; i++) {
            if (!((username.charAt(i) >= 'A' && username.charAt(i) <= 'Z') || (username.charAt(i) >= 'a' && username.charAt(i) <= 'z')
                     || (username.charAt(i) >= '0' && username.charAt(i)<='9') || username.charAt(i) == '_')) return false;
        }

        return true;
    }

    public boolean provjeraMaila(String mail){
        if(mail.length() == 0) return false;
        int i;
        int index = mail.indexOf('@');
        if(index == -1 || index == 0 || index == mail.length()-1) return false;
        for(i = 0 ; i<mail.length() ; i++){
            if(mail.charAt(i) == index){
                if(!((mail.charAt(index-1) >= 'A' && mail.charAt(index-1) <='Z') || (mail.charAt(index-1)>='a' && mail.charAt(index-1) <='z')))
                    return false;
                else {
                    if(!((mail.charAt(index+1) >= 'A' && mail.charAt(index+1) <='Z') || (mail.charAt(index+1)>='a' && mail.charAt(index+1) <='z')))
                        return false;
                }
            }

        }
        return true;
    }



    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void GenerisiAction(ActionEvent actionEvent) {
        for(Korisnik k : model.getKorisnici()) {
            if (k.getIme().length() == 0) {
                k.setUsername("");
                continue;
            }

            String username = k.getIme().charAt(0) + k.getPrezime();

            username = username.toLowerCase();

            String novi = "";
            for (int i = 0; i < username.length(); i++) {
                if (username.charAt(i) == 'č' )
                    novi += "c";
                else if (username.charAt(i) == 'ć')
                    novi += "c";
                 else if (username.charAt(i) == 'đ')
                    novi += "d";
                 else if (username.charAt(i) == 'ž')
                    novi += "z";
                else if (username.charAt(i) == 'š')
                    novi += "s";
                else
                    novi += username.charAt(i);
                k.setUsername(novi);
            }
        }
    }

}
