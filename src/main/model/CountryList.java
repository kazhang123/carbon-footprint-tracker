package model;

import java.util.HashMap;

// HashMap representing country names and their average carbon footprint in tonnes of CO2 per year
public class CountryList {
    public static HashMap<String, Double> countries = new HashMap<String, Double>();

    // EFFECTS: adds countries and returns list of all countries and their average footprints
    public static HashMap<String, Double> getCountries() {
        addCountriesAtoBo();
        addCountriesBotoCr();
        addCountriesCutoGa();
        addCountriesGetoIr();
        addCountriesIrtoMa();
        addCountriesMatoNe();
        addCountriesNetoRw();
        addCountriesRwtoSu();
        addCountriesSutoUs();
        addCountriesUztoZ();

        return countries;
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with A through Bo
    public static void addCountriesAtoBo() {
        countries.put("AFGHANISTAN", 0.30);
        countries.put("ALBANIA", 1.98);
        countries.put("ALGERIA", 3.72);
        countries.put("ANDORRA", 5.83);
        countries.put("ANGOLA", 1.29);
        countries.put("ARGENTINA", 4.75);
        countries.put("ARMENIA", 1.90);
        countries.put("ARUBA", 8.41);
        countries.put("AUSTRALIA", 15.37);
        countries.put("AUSTRIA", 6.87);
        countries.put("AZERBAIJAN", 3.93);
        countries.put("BAHAMAS", 6.32);
        countries.put("BAHRAIN", 23.45);
        countries.put("BANGLADESH", 0.46);
        countries.put("BARBADOS", 4.49);
        countries.put("BELARUS", 6.70);
        countries.put("BELGIUM", 8.33);
        countries.put("BELIZE", 1.41);
        countries.put("BENIN", 0.61);
        countries.put("BERMUDA", 8.84);
        countries.put("BHUTAN", 1.29);
        countries.put("BOLIVIA", 1.93);
        countries.put("BOSNIA AND HERZEGOVINA", 6.23);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Bo to Cr
    public static void addCountriesBotoCr() {
        countries.put("BOTSWANA", 3.24);
        countries.put("BRAZIL", 2.59);
        countries.put("BRUNEI DARUSSALAM", 22.12);
        countries.put("BULGARIA", 5.87);
        countries.put("BURKINA FASO", 0.16);
        countries.put("BURUNDI", 0.04);
        countries.put("CAMBODIA", 0.44);
        countries.put("CAMEROON", 0.31);
        countries.put("CANADA", 15.12);
        countries.put("CAPE VERDE", 0.93);
        countries.put("CAYMAN ISLANDS", 9.17);
        countries.put("CENTRAL AFRICAN REPUBLIC", 0.07);
        countries.put("CHAD", 0.05);
        countries.put("CHILE", 4.69);
        countries.put("CHINA", 7.54);
        countries.put("COLOMBIA", 1.76);
        countries.put("COMOROS", 0.20);
        countries.put("CONGO", 0.64);
        countries.put("DEMOCRATIC REPUBLIC OF CONGO", 0.06);
        countries.put("COOK ISLANDS", 1.06);
        countries.put("COSTA RICA", 1.63);
        countries.put("COTE D'IVOIRE", 0.49);
        countries.put("CROTIA", 3.97);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Cu to Ga
    public static void addCountriesCutoGa() {
        countries.put("CUBA", 3.05);
        countries.put("CYPRUS", 5.26);
        countries.put("CZECH REPUBLIC", 9.17);
        countries.put("DJIBOUTI", 0.79);
        countries.put("DOMINICA", 1.86);
        countries.put("DOMINICAN REPUBLIC", 2.07);
        countries.put("EAST TIMOR", 0.39);
        countries.put("ECUADOR", 2.76);
        countries.put("EGYPT", 2.20);
        countries.put("EL SALVADOR", 1.00);
        countries.put("ESTONIA", 14.85);
        countries.put("ETHIOPIA", 0.12);
        countries.put("FALKLAND ISLANDS", 4.97);
        countries.put("FAROE ISLANDS", 12.24);
        countries.put("FIJI", 1.32);
        countries.put("FINLAND", 8.66);
        countries.put("FRANCE", 4.57);
        countries.put("FRENCH GUIANA", 3.06);
        countries.put("FRENCH POLYNESIA", 2.92);
        countries.put("GABON", 2.77);
        countries.put("GAMBIA", 0.27);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Ge to Ir
    public static void addCountriesGetoIr() {
        countries.put("GEORGIA", 2.41);
        countries.put("GERMANY", 8.89);
        countries.put("GHANA", 0.54);
        countries.put("GIBRALTAR", 15.51);
        countries.put("GREECE", 6.18);
        countries.put("GREENLAND", 8.99);
        countries.put("GRENADA", 2.28);
        countries.put("GUADELOUPE", 3.06);
        countries.put("GUATEMALA", 1.15);
        countries.put("GUINEA", 0.21);
        countries.put("GUINEA-BISSAU", 0.16);
        countries.put("GUYANA", 2.63);
        countries.put("HAITI", 0.27);
        countries.put("HONDURAS", 1.08);
        countries.put("HONG KONG", 6.39);
        countries.put("HUNGARY", 4.27);
        countries.put("ICELAND", 6.06);
        countries.put("INDIA", 1.73);
        countries.put("INDONESIA", 1.82);
        countries.put("IRAN", 8.28);
        countries.put("IRAQ", 4.81);
        countries.put("IRELAND", 7.31);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Ir to Ma
    public static void addCountriesIrtoMa() {
        countries.put("ISRAEL", 7.86);
        countries.put("ITALY", 5.27);
        countries.put("JAMAICA", 2.59);
        countries.put("JAPAN", 9.54);
        countries.put("JORDAN", 3.00);
        countries.put("KAZAKHSTAN", 14.36);
        countries.put("KENYA", 0.31);
        countries.put("KIRIBATI", 0.56);
        countries.put("NORTH KOREA", 1.61);
        countries.put("SOUTH KOREA", 11.57);
        countries.put("KUWAIT", 25.22);
        countries.put("KYRGYZSTAN", 1.65);
        countries.put("LAOS", 0.30);
        countries.put("LATVIA", 3.50);
        countries.put("LEBANON", 4.30);
        countries.put("LESOTHO", 1.15);
        countries.put("LIBERIA", 0.21);
        countries.put("LIBYA", 9.19);
        countries.put("LIECHTENSTEIN", 1.19);
        countries.put("LITHUANIA", 4.38);
        countries.put("MACAU", 2.18);
        countries.put("MACEDONIA", 17.36);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Ma to Ne
    public static void addCountriesMatoNe() {
        countries.put("MADAGASCAR", 0.13);
        countries.put("MALAWI", 0.07);
        countries.put("MALAYSIA", 8.03);
        countries.put("MALDIVES", 3.27);
        countries.put("MALI", 0.08);
        countries.put("MALTA", 5.40);
        countries.put("MARSHALL ISLANDS", 1.94);
        countries.put("MARTINIQUE", 3.06);
        countries.put("MAURITANIA", 0.67);
        countries.put("MAURITIUS", 3.35);
        countries.put("MEXICO", 3.87);
        countries.put("MICRONESIA", 1.45);
        countries.put("MOLDOVA", 1.39);
        countries.put("MONGOLA", 7.13);
        countries.put("MONTENEGRO", 3.56);
        countries.put("MONTSERRAT", 3.06);
        countries.put("MOROCCO", 1.74);
        countries.put("MOZAMBIQUE", 0.31);
        countries.put("MYANMAR", 0.42);
        countries.put("NAMIBIA", 1.58);
        countries.put("NAURU", 4.02);
        countries.put("NEPAL", 0.28);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Ne to Rw
    public static void addCountriesNetoRw() {
        countries.put("NETHERLANDS", 9.92);
        countries.put("NEW CALEDONIA", 3.87);
        countries.put("NEW ZEALAND", 7.69);
        countries.put("NICARAGUA", 0.81);
        countries.put("NIGER", 0.11);
        countries.put("NIGERIA", 0.55);
        countries.put("NIUE", 1.06);
        countries.put("NORWAY", 9.27);
        countries.put("OMAN", 15.44);
        countries.put("PAKISTAN", 0.90);
        countries.put("PALAU", 12.34);
        countries.put("PANAMA", 2.25);
        countries.put("PAPUA NEW GUINEA", 0.81);
        countries.put("PARAGUAY", 0.87);
        countries.put("PERU", 1.99);
        countries.put("PHILIPPINES", 1.06);
        countries.put("POLAND", 7.52);
        countries.put("PORTUGAL", 4.33);
        countries.put("QATAR", 45.42);
        countries.put("REUNION", 3.27);
        countries.put("ROMANIA", 3.52);
        countries.put("RUSSIA", 11.86);
        countries.put("RWANDA", 0.07);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Rw to Su
    public static void addCountriesRwtoSu() {
        countries.put("SAINT KITTS AND NEVIS", 4.30);
        countries.put("SAINT LUCIA", 2.31);
        countries.put("SAINT VINCENT AND THE GRENADINES", 1.91);
        countries.put("SAMOA", 1.03);
        countries.put("SAO TOME AND THE PRINCIPE", 0.59);
        countries.put("SAUDI ARABIA", 19.53);
        countries.put("SENEGAL", 0.61);
        countries.put("SERBIA", 5.28);
        countries.put("SEYCHELLES", 5.42);
        countries.put("SIERRA LEONE", 0.18);
        countries.put("SINGAPORE", 10.31);
        countries.put("SINT MAARTEN", 19.46);
        countries.put("SLOVAKIA", 5.66);
        countries.put("SLOVENIA", 6.21);
        countries.put("SOLOMAN ISLANDS", 0.35);
        countries.put("SOMALIA", 0.05);
        countries.put("SOUTH AFRICA", 8.98);
        countries.put("SOUTH SUDAN", 0.13);
        countries.put("SPAIN", 5.03);
        countries.put("SRI LANKA", 0.89);
        countries.put("ST. HELENA", 4.97);
        countries.put("ST. PIERRE AND MIQUELON", 4.97);
        countries.put("SUDAN", 0.30);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Su to Us
    public static void addCountriesSutoUs() {
        countries.put("SURINAME", 3.63);
        countries.put("SWAZILAND", 0.93);
        countries.put("SWEDEN", 4.48);
        countries.put("SWITZERLAND", 4.31);
        countries.put("SYRIAN ARAB REPUBLIC", 1.60);
        countries.put("TAJIKISTAN", 0.62);
        countries.put("TANZANIA", 0.22);
        countries.put("THAILAND", 4.62);
        countries.put("TOGO", 0.36);
        countries.put("TRINIDAD", 34.16);
        countries.put("TUNISIA", 2.59);
        countries.put("TURKEY", 4.49);
        countries.put("TURKMENISTAN", 12.52);
        countries.put("TURKS AND CAICOS ISLANDS", 6.09);
        countries.put("TUVALU", 1.01);
        countries.put("UGANDA", 0.13);
        countries.put("UKRAINE", 5.02);
        countries.put("UNITED ARAB EMIRATES", 23.30);
        countries.put("UNITED KINGDOM", 6.50);
        countries.put("URUGUAY", 1.97);
        countries.put("USA", 16.49);
    }

    // MODIFIES: this
    // EFFECTS: adds countries with names starting with Uz to Z
    public static void addCountriesUztoZ() {
        countries.put("UZBEKISTAN", 3.42);
        countries.put("VANUATU", 0.59);
        countries.put("VENEZUELA", 6.03);
        countries.put("VIETNAM", 1.80);
        countries.put("VIRGIN ISLANDS", 6.07);
        countries.put("YEMEN", 0.86);
        countries.put("ZAMBIA", 0.29);
        countries.put("ZIMBABWE", 0.78);
    }
}
