import ru.cazmusw.json.advanced.JsonSection;

public class Obj2 {
    @JsonSection(title = "name")
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
