package bearmaps.proj2c;

import bearmaps.hw4.MyTrieSet;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;


import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private List<Node> nodes;
    private List<Point> points;
    private Map<Point, Node> ptn;
    private MyTrieSet cleanName;
    private Map<String, List<Node>> rawName;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        nodes = this.getNodes();
        points = new ArrayList<>();
        ptn = new HashMap<>();
        cleanName = new MyTrieSet();
        rawName = new HashMap<>();
        for (Node n : nodes) {
            Point p = new Point(n.lon(), n.lat());
            ptn.put(p, n);
            String rn = n.name();
            if (rn == null) {
                points.add(p);
            } else {
                String cn = cleanString(rn);
                if (!rawName.containsKey(cn)) {
                    List<Node> tmp = new ArrayList<>();
                    tmp.add(n);
                    rawName.put(cn, tmp);
                    cleanName.add(cn);
                } else {
                    rawName.get(cn).add(n);
                }
            }

        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        KDTree kdt = new KDTree(points);
        return ptn.get(kdt.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> ret = new ArrayList<>();
        for (String s : cleanName.keysWithPrefix(prefix)) {
            ret.add(rawName.get(s).get(0).name());
        }
        return ret;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> ret = new ArrayList<>();
        String cln = cleanString(locationName);
        for (Node n : rawName.get(cln)) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("lat", n.lat());
            tmp.put("lon", n.lon());
            tmp.put("name", n.name());
            tmp.put("id", n.id());
            ret.add(tmp);
        }
        return ret;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
