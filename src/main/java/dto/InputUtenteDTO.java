package dto;



public class InputUtenteDTO {

	private String nome;
    private String cognome;
    private String username;
    private String email;
    private String dataNascita;
    private String password;
    private String confermaPassword;

    public InputUtenteDTO(String nome,String cognome,String username,String email,String dataNascita,String password,String confermaPassword) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.dataNascita = dataNascita;
        this.password = password;
        this.confermaPassword = confermaPassword;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public String getPassword() {
        return password;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }
}
