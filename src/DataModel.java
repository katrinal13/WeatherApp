public class DataModel
{
    private double tempC;
    private double tempF;
    private String condition;
    private String icon;

    public DataModel(double tempC, double tempF, String condition, String icon)
    {
        this.tempC = tempC;
        this.tempF = tempF;
        this.condition = condition;
        this.icon = icon;
    }

    public double getTempC()
    {
        return tempC;
    }

    public double getTempF()
    {
        return tempF;
    }

    public String getCondition()
    {
        return condition;
    }

    public String getIcon()
    {
        return icon;
    }
}