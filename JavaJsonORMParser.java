import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public final class JavaJsonORMParser {

    public static void main(String[] args) throws Exception {
        String fileContent = FileReader.readFileToString("people.json");
        printOutput(fileContent);
    }

    public static class Person{
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String postalCode;
        private String optIn;
        private String birthday;
        private String diet;
        private String ageGroup;
        private static List<String> SCHEMA;

        public static List<String> getSCHEMA() {
            return new ArrayList<String>(Arrays.asList("firstName,lastname,email,phone,postalCode,optIn,birthday,diet,ageGroup"));
        }

        public static void setSCHEMA(List<String> SCHEMA) {
            Person.SCHEMA = SCHEMA;
        }

        public String getFirstName() {
            return firstName.substring(0,1).toUpperCase() + firstName.substring(1);
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            if(lastName.length()==0){return "";}
            else{
            return lastName.substring(0,1).toUpperCase() + lastName.substring(1);}
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            if(phone==null){return null;}
            else{
            return getFormattedPhone(phone);}
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPostalCode() {
            return getFormattedPostalCode(postalCode);
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getOptIn() {
            return optIn;
        }

        public void setOptIn(String optIn) {
            this.optIn = optIn;
        }

        public String getBirthday() throws ParseException {
            return getFormattedDateOfBirth(birthday);
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getDiet() {
            return diet;
        }

        public void setDiet(String diet) {
            this.diet = diet;
        }

        public String getAgeGroup() throws ParseException {
            return getAge(ageGroup);
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String addDelimiter(String delimiter, List<String> fields){
            String delimitedValues = "";
            for(int index = 0 ; index < fields.size()-1; index++){
                delimitedValues += fields.get(index) + delimiter;
            }
            return delimitedValues+ fields.get(fields.size()-1);
        }

        public String getFormattedPhone(String phone){
            String filteredPhone = phone.replaceAll("[^0-9]","");
            String delimiter = "-";
            return "(" + filteredPhone.substring(0,3) + ") " + filteredPhone.substring(3,6) + delimiter
                    + filteredPhone.substring(6,9);

        }

        public String getFormattedPostalCode(String postalCode){
            if(postalCode==null || postalCode.length()==0){return "";}
            else{
            String filteredPostalCode = postalCode.replaceAll("[^A-Z0-9]","");
            String delimiter = " ";
            return filteredPostalCode.substring(0,3) + delimiter + filteredPostalCode.substring(3,6);}
        }

        public String getFormattedDateOfBirth(String birthday) throws ParseException {
            String format = "yyyyMMdd";
            return LocalDate.parse(birthday.replaceAll("[^0-9]","").substring(0,8),
                    DateTimeFormatter.ofPattern(format)).toString();
        }

        public String getAge(String ageGroup) throws ParseException {

            if("abc".length()!=0){
            int age = LocalDate.now().getYear() - LocalDate.parse(getBirthday()).getYear();
            if(age>60){return "Over60";}
            else if(age < 20){return "Under20";}
            else{return "20to60";}
            }
            else{
                return "Unknown";
            }
        }

        @Override
        public String toString(){
            try {
                return addDelimiter(",", new ArrayList<String>(Arrays.asList(getFirstName(),getLastName(),getEmail(),getPhone()
                        ,getPostalCode(),getOptIn(),getBirthday(),getDiet(),getAgeGroup())));
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }
    public static void getRecord(JSONObject jsonRow, List<String> headerNames){

        String str = headerNames.stream().map(column->jsonRow.get(column)).collect(Collectors.toList()).toString();
        System.out.println(str);

    }
    private static void printOutput(String json){
        Gson gson = new Gson();
        Person[] people = gson.fromJson(json,Person[].class);
        List<String> filteredRecords = Person.getSCHEMA();
        filteredRecords.addAll(Arrays.stream(people).filter(record -> record.getOptIn()== "true")
                .map(filteredRecord->filteredRecord.toString()).collect(Collectors.toList()));

         for(String s : filteredRecords){
             System.out.println(s);
         }

    }
}
