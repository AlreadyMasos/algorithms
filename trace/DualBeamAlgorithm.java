import java.util.LinkedList;
import java.util.List;
public class DualBeamAlgorithm {
    public int point1;
    public int point2;
    public static DualBeam start;
    public static DualBeam finish;
    public DualBeamAlgorithm(Location st, Location end)
    {
        this.start = new DualBeam(st);
        this.finish = new DualBeam(end);
        schet();
        NSWE();
    }
    public void schet() {
        if (start.Location().xCoord - finish.Location().xCoord >= 0)
            point1= 1;
        else
            point1= 0;

        if (start.Location().yCoord - finish.Location().yCoord >=0)
            point2 = 1;
        else
            point1 = 0;
    }
    public static List<Location> schetPuti(Map2D map)
    {
        DualBeam.Map(map);

        while (!peresechenie() && !stop())
        {
            start.sledShag();
            finish.sledShag();
        }
        return allPoints();
    }
    public void NSWE() { //устанавливаем направление пути
        if (point1 == 0)
            start.shagX(1);
        else
            start.shagX(-1);
        if (point2 == 0)
            start.shagY(1);
        else
            start.shagY(-1);
        finish.shagX(start.shagX() * -1);
        finish.shagY(start.shagY() * -1);
    }
    public static boolean peresechenie() {
        List<Location> startXBeam = start.getA2().poset();
        List<Location> startYBeam = start.getB2().poset();
        List<Location> finishXBeam = finish.getA2().poset();
        List<Location> finishYBeam = finish.getB2().poset();
        for (Location loc : startXBeam) {
            if (finishXBeam.contains(loc))
            {
                start.getA2().pere1(startXBeam.subList(0, startXBeam.indexOf(loc) + 1));
                finish.getB2().pere1(finishXBeam.subList(0, finishXBeam.indexOf(loc) + 1));
                return true;
            }
            if (finishYBeam.contains(loc))
            {
                start.getA2().pere1(startXBeam.subList(0, startXBeam.indexOf(loc) + 1));
                finish.getB2().pere1(finishYBeam.subList(0, finishYBeam.indexOf(loc) + 1));

                return true;
            }
        }
        for (Location loc : startYBeam) {
            if (finishXBeam.contains(loc))
            {
                start.getA2().pere1(startYBeam.subList(0, startYBeam.indexOf(loc) + 1));
                finish.getA2().pere1(finishXBeam.subList(0, finishXBeam.indexOf(loc) + 1));
                return true;
            }
            if (finishYBeam.contains(loc))
            {
                start.getB2().pere1(startYBeam.subList(0, startYBeam.indexOf(loc) + 1));
                finish.getB2().pere1(finishYBeam.subList(0, finishYBeam.indexOf(loc) + 1));

                return true;
            }
        }

        return false;
    }
    public List<Location> vsePeresechenie() {
        List<Location> allPoints = new LinkedList<Location>(start.getA2().peresech());
        allPoints.addAll(start.getB2().peresech());
        allPoints.addAll(finish.getA2().peresech());
        allPoints.addAll(finish.getB2().peresech());

        return allPoints;
    }
    public DualBeam getStart() {
        return start;
    }
    public DualBeam getFinish() {
        return finish;
    }

    public static List<Location> allPoints() {
        List<Location> allPoints = new LinkedList<Location>(start.getA2().poset());
        allPoints.addAll(start.getB2().poset());
        allPoints.addAll(finish.getA2().poset());
        allPoints.addAll(finish.getB2().poset());
        return allPoints;
    }
    public static boolean stop() {
        return start.getA2().stop() && start.getB2().stop()
                && finish.getA2().stop() && finish.getB2().stop();
    }
}