import ru.cazmusw.json.JsonFile;
import ru.cazmusw.json.utils.JsonObject;

import java.io.File;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        JsonFile jsonFile = new JsonFile(new File("simple.json"));

        JsonObject object = jsonFile.readAsJsonObject();

        System.out.println("Исходный объект: " + object.toString());

        object.addObject("newobvxvcj", new Random().nextInt() % 200);

        jsonFile.saveFile(object);

        object = jsonFile.readAsJsonObject();

        System.out.println("После изменений объект: " + object.toString());

        jsonFile = new JsonFile(new File("simple2.json"));

        Obj obj = new Obj();

        jsonFile.readObject(obj);

        System.out.println("Пример чтения данных и автоматической записи их в объект " + obj.obj2.name + " " + obj.gg + " " + obj.hh);

        for (int[][] f : obj.megaArray) {
            for (int[] f1 : f) {
                for (int f2 : f1) {
                    System.out.print(f2 + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

    }
}