/*  Crie uma classe Show seguindo todas as regras apresentadas no slide unidade00l conceitosBasicos introducaoOO.pdf. Sua classe ter´a os atributos privado show id:
string, type: string, title: string, director: string, cast: string[], country:
string, date added: date, release year: int, rating: string, duration: string, listed in: string[]. Sua classe tamb´em ter´a pelo menos dois construtores, e os m´etodos gets,
sets, clone, imprimir e ler.
O m´etodo imprimir mostra os atributos do registro (ver cada linha da sa´ıda padr˜ao) e o ler lˆe
os atributos de um registro. Aten¸c˜ao para o arquivo de entrada, pois em alguns registros faltam
valores e esse deve ser substitu´ıdo pelo valor NaN. A entrada padr˜ao ´e composta por v´arias linhas
e cada uma cont´em um n´umero inteiro indicando o show id do Show a ser lido.
A ´ultima linha da entrada cont´em a palavra FIM. A sa´ıda padr˜ao tamb´em cont´em v´arias
linhas, uma para cada registro contido em uma linha da entrada padr˜ao, no seguinte formato: [=> id ## type ## title ## director ## [cast] ## country ## date added ##
release year ## rating ## duration ## [listed in].*/
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class tp02_q01{

public class Show{
    private:
    String SHOW_ID;
    String TYPE, TITLE, DIRECTOR, COUNTRY, RATING, DURATION;
    LocalDate DATE_ADDED;
    int RELASE_YEAR;
    String[] CAST = new String[];
    String[] LISTED_IN = new String[];

}

 public show() {
        this.SHOW_ID = "";
        this.TYPE = "";
        this.TITLE = "";
        this.DIRECTOR = "";
        this.COUNTRY = "";
        this.RATING = "";
        this.DURATION = "";
        this.DATE_ADDED = LocalDate.now();
        this.RELASE_YEAR = 0;
        this.CAST = new String[0];
        this.LISTED_IN = new String[0];
        
    }

    public show(String SHOW_ID, String TYPE, String TITLE, String DIRECTOR, String COUNTRY, String RATING, String DURATION, LocalDate DATE_ADDED, int RELASE_YEAR, String[] CAST, String[] LISTED_IN) {

        this.SHOW_ID = SHOW_ID;
        this.TYPE = TYPE;
        this.TITLE = TITLE;
        this.DIRECTOR = DIRECTOR;
        this.COUNTRY = COUNTRY;
        this.RATING = RATING;
        this.DURATION = DURATION;
        this.DATE_ADDED = DATE_ADDED;
        this.RELASE_YEAR = RELASE_YEAR;
        this.CAST = CAST;
        this.LISTED_IN = LISTED_IN;

    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String[] getAlternativeNames() { return alternativeNames; }
    public String getHouse() { return house; }
    public String getAncestry() { return ancestry; }
    public String getSpecies() { return species; }
    public String getPatronus() { return patronus; }
    public Boolean getHogwartsStaff() { return hogwartsStaff; }
    public Boolean getHogwartsStudent() { return hogwartsStudent; }
    public String getActorName() { return actorName; }
    public Boolean getAlive() { return alive; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public int getYearOfBirth() { return yearOfBirth; }
    public String getEyeColour() { return eyeColour; }
    public String getGender() { return gender; }
    public String getHairColour() { return hairColour; }
    public Boolean getWizard() { return wizard; }
    
    // Métodos set
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAlternativeNames(String[] alternativeNames) { this.alternativeNames = alternativeNames; }
    public void setHouse(String house) { this.house = house; }
    public void setAncestry(String ancestry) { this.ancestry = ancestry; }
    public void setSpecies(String species) { this.species = species; }
    public void setPatronus(String patronus) { this.patronus = patronus; }
    public void setHogwartsStaff(Boolean hogwartsStaff) { this.hogwartsStaff = hogwartsStaff; }
    public void setHogwartsStudent(Boolean hogwartsStudent) { this.hogwartsStudent = hogwartsStudent; }
    public void setActorName(String actorName) { this.actorName = actorName; }
    public void setAlive(Boolean alive) { this.alive = alive; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setYearOfBirth(int yearOfBirth) { this.yearOfBirth = yearOfBirth; }
    public void setEyeColour(String eyeColour) { this.eyeColour = eyeColour; }
    public void setGender(String gender) { this.gender = gender; }
    public void setHairColour(String hairColour) { this.hairColour = hairColour; }
    public void setWizard(Boolean wizard) { this.wizard = wizard; }

}