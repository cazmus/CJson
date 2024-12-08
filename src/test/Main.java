import ru.cazmusw.json.JsonFile;
import ru.cazmusw.json.utils.JsonObject;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        JsonFile jsonFile = new JsonFile(new File("simple.json"));

        JsonObject object = jsonFile.readAsJsonObject();
        jsonFile.saveFile(object);


       // Obj obj = new Obj();

     //   jsonFile.readObject(obj);

     //   System.out.println(obj.obj2.name);

    }
}