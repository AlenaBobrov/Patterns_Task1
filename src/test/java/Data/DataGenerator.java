package Data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static final List<String> cities = List.of(
            "Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста",
            "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск",
            "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары",
            "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь",
            "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань",
            "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Орёл",
            "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск",
            "Санкт-Петербург", "Липецк", "Магадан", "Москва", "Мурманск", "Нижний Новгород",
            "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Пенза", "Псков", "Салехард",
            "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург",
            "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск",
            "Ярославль", "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь"
    );

    private DataGenerator() {
    }

    public static String generateDate(int plusDays) {
        String date = LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        String city = cities.get(new Random().nextInt(cities.size()));
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
