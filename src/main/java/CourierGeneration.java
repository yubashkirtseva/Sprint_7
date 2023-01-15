public class CourierGeneration {
    public static Courier getDefault(){
        return new Courier("the_fastest_courier", "1703", "Michael");
    }

    public static Courier getIncompleteDate(){
        return new Courier("the_fastest_courier", null, "Michael");
    }
}
