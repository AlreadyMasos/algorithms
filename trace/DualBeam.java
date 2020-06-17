import java.util.LinkedList;
import java.util.List;
public class DualBeam {
    public int a1;
    public int b1;
    public Location loc;
    public Beam a2;
    public Beam b2;
    public static Map2D map;
    public static void Map(Map2D map2D) {
        map = map2D;
    }
    public Beam getA2() {

        return a2;
    }
    public Beam getB2() {

        return b2;
    }
    public static class Beam {
        public List<Location> visited = new LinkedList<Location>();
        public List<Location> intersected = new LinkedList<Location>();
        public Location current;
        public boolean end = false;
        public Beam(Location loc)
        {
            current = new Location(loc.xCoord, loc.yCoord);
        }
        public void shag(int x, int y, int yn)
        {
            if (end)
                return;
            visited.add(new Location(current.xCoord, current.yCoord));
            if (yn== 1)
            {
                if (current.xCoord + x<0 || current.xCoord + x> map.getWidth() - 1)
                {
                    end = true;
                    return;
                }
                if (map.getCellValue(current.xCoord + x, current.yCoord) != 0)
                {
                    if (current.yCoord + y<0 || current.yCoord + y> map.getHeight() - 1)
                    {
                        end = true;
                        return;
                    }
                    if (map.getCellValue(current.xCoord, current.yCoord + y) != 0)
                    {
                        end = true;
                        return;
                    }
                    current.yCoord += y;
                    return;
                }
                current.xCoord += x;
            }
            else
            {
                if (current.yCoord + y<0 || current.yCoord + y> map.getHeight() - 1)
                {
                    end = true;
                    return;
                }
                if (map.getCellValue(current.xCoord, current.yCoord + y) != 0)
                {
                    if (current.xCoord + x<0 || current.xCoord + x> map.getWidth() - 1)
                    {
                        end = true;
                        return;
                    }
                    if (map.getCellValue(current.xCoord + x, current.yCoord) != 0)
                    {
                        end = true;
                        return;
                    }
                    current.xCoord += x;
                    return;
                }
                current.yCoord += y;
            }
        }
        public List<Location> poset() {
            return visited;
        }
        public List<Location> peresech() {
            return intersected;
        }
        public void pere1(List<Location> list) {
            intersected = list;
        }
        public boolean stop() {
            return end;
        }

    }
    public DualBeam(Location loc)
    {
        this.loc = loc;
        a2 = new Beam(loc);
        b2 = new Beam(loc);
    }
    public void setLocation(Location loc) {
        this.loc = loc;
    }
    public Location Location() {
        return loc;
    }
    public void shagX(int sence) {
        a1 =sence;
    }
    public void shagY(int sence) {
        b1 =sence;
    }
    public int shagX() {

        return a1;
    }
    public int shagY() {
        return b1;
    }

    public void sledShag() {
        a2.shag(a1, b1, 1);
        b2.shag(a1, b1, 2);

    }
}
