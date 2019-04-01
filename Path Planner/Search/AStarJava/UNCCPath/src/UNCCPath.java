import java.util.Comparator;
import java.util.LinkedList;

class point {

    private point parent;
    private double[] cord = new double[2];
    private double g, h, f;

    //Create point object with self, a parent point, x,y coordinates, and a heuristic value
    point(point p,double lat,double lon,double heu) {
        parent = p;
        cord[0] = lat;
        cord[1] = lon;
        g = 1;
        h = heu;
        f = g + h;
    }

    point(point p){
        parent = p.parent;
        cord[0] = p.cord[0]; cord[1] = p.cord[1];
        g = 1; h = p.h; f = p.f;
    }

    //Return the parent point of the calling point
    point getParent() { return parent; }

    //Return (x,y) coordinates of calling point
    double[] getCord(){ return cord; }

    double getH(){ return h; }
}

public class UNCCPath {
    //get names of starting and ending buildings
    //get lats and lons of buildings

    private double[] goal = new double[2];
    private point start;

    UNCCPath(double startLat, double startLon, double endLat, double endLon) {
        goal[0] = endLat;
        goal[1] = endLon;
        double[] startLoc = {startLat, startLon};
        start = new point(null, startLat, startLon, calcH(startLoc, goal)); //starting building
    }

    private double calcH(double[] cord1, double[] cord2) {
        double dlon = cord2[1] - cord1[1];
        double dlat = cord2[0] - cord1[0];
        dlon = dlon * Math.PI / 180.0;
        dlat = dlat * Math.PI / 180.0;
        double a = Math.pow((Math.sin(dlat / 2)), 2) + Math.cos(cord1[0]) * Math.cos(cord2[0]) * Math.pow((Math.sin(dlon / 2)), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3961 * c;  //3961 is the approximate radius of Earth in miles
    }

    private LinkedList<double[]> getChildren(double[] loc){
        LinkedList<double[]> children = new LinkedList<>();
        //QUERY DATABASE FOR CLOSE POINTS//
        return children;
    }

    private LinkedList<double[]> genPath(point node, point start) {
        LinkedList<double[]> path = new LinkedList<>();
        while (node.getCord() != start.getCord()) {
            path.add(node.getCord());
            //get next node
            node = node.getParent();
        }
        return path;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //														BEGIN A*												    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkedList<double[]> getPath() {

        LinkedList<point> openlist = new LinkedList<>();
        LinkedList<point> closedlist = new LinkedList<>();

        point kid;

        openlist.add(start);

        point node = new point(start);
        int states = 0;

        while(node.getCord()[0] != goal[0] && node.getCord()[1] != goal[1]){
            states += 1;

            if (node.getCord()[0] != goal[0] && node.getCord()[1] != goal[1]){
                closedlist.add(node);

                LinkedList<double[]> family = getChildren(node.getCord());

                for (double[] c : family){
                    if (!openlist.contains(c) && !closedlist.contains(c)){
                        kid = new point(node, c[0], c[1], calcH(c, goal));
                        openlist.add(kid);
                    }
                }
                openlist.sort(new Comparator<point>() {
                    @Override
                    public int compare(point o1, point o2) {
                        return (int)Math.round(o1.getH() - o2.getH());
                    }
                });
            }
            node = openlist.getFirst();
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //														END A*												    //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return genPath(node, start);
    }


}
