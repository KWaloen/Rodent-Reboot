package earlybirds.Model.Map.String;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.badlogic.gdx.utils.GdxRuntimeException;

import earlybirds.Model.Map.Grid.GridMap;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class for building a map string from a grid of rooms.
 * Takes a grid of rooms and builds a map string from it.
 */
public class StringMap {
    private String mapString;
    private static List<String> namedRooms = new ArrayList<>();
    private static List<String> namesOfRooms = new ArrayList<>();
    private static List<String> numberedRooms = new ArrayList<>();
    private static List<String> testRooms = new ArrayList<>();
    private static List<String> namesOfTestRooms = new ArrayList<>();
    private GridMap grid;

    // Changed to run static block to load rooms from XML
    static {
        loadRoomsFromXML();
    }

    /**
     * Constructs a StringMap with a grid of rooms and builds a map string from it.
     * Always contains the starting room and the exit room.
     *
     * @param roomsToAdd the number of randomly generated rooms to add to the grid
     */
    public StringMap(int roomsToAdd) {

        grid = new GridMap(roomsToAdd);
        mapString = buildMapString(grid);

        addEdgeWalls();

    }

    /**
     * Constructor for getting testRooms or namedRooms only.
     *
     * @param mapName the name of the map to build
     * @param testing if true the map is a test map, otherwise it is a named map
     */
    public StringMap(String mapName, boolean testing) {
        List<String> namesList = testing ? namesOfTestRooms : namesOfRooms;
        List<String> roomsList = testing ? testRooms : namedRooms;

        int index = namesList.indexOf(mapName);
        if (index != -1) {
            mapString = roomsList.get(index);
        } else {
            throw new IllegalArgumentException(
                    "Map name not found in " + (testing ? "test rooms: " : "named rooms: ") + mapName);
        }
    }

    /**
     * Build the map string from the grid by creating a string of each column, then
     * concatenating two neighboring columns.
     *
     * @param grid the grid to build the map string from
     * @return the completed map string
     */
    private String buildMapString(GridMap grid) {
        StringBuilder mapString = new StringBuilder();
        StringBuilder tempString = new StringBuilder();

        for (int col = 0; col < grid.getGridSize(); col++) {
            for (int row = 0; row < grid.getGridSize(); row++) {
                String roomString = determineRoom(grid.getRoom(row, col));

                if (col == 0) {// the first column is added to an empty string, therefore its treated separatly
                    mapString.append(roomString);
                } else {
                    tempString.append(roomString);
                }
            }

            if (col != 0) {

                // The code needs to combine line by line from the two strings, therefore they
                // are first split into lines then the mapString is rebuildt line by line

                String[] tempLines = mapString.toString().split("\n");
                String[] tempLines2 = tempString.toString().split("\n");

                mapString = new StringBuilder();
                tempString = new StringBuilder();

                for (int i = 0; i < tempLines.length; i++) {
                    String line = tempLines[i];
                    String line2 = tempLines2[i];
                    mapString.append(line + line2);
                    mapString.append("\n");

                }
            }
        }
        return mapString.toString();
    }

    /**
     * Determine the room string to add to the map string based on the room type.
     * R = Random room, S = Start room, E = Exit room, Empty = Empty room
     *
     * @param roomType the type of room to add
     * @return the room string to add to the map string
     */
    private String determineRoom(char roomType) {
        String room;
        switch (roomType) {
            case 'R':
                int randomIndex = new Random().nextInt(numberedRooms.size());
                room = numberedRooms.get(randomIndex);
                break;
            case 'S':
                int startIndex = namesOfRooms.indexOf("Start");
                room = namedRooms.get(startIndex);
                break;
            case 'E':
                int exitIndex = namesOfRooms.indexOf("Exit");
                room = namedRooms.get(exitIndex);
                break;
            default:
                int emptyIndex = namesOfRooms.indexOf("Empty");
                room = namedRooms.get(emptyIndex);
                break;
        }
        return room.replaceAll(" ", "") + "\n";
    }

    /**
     * Load the room strings from the XML file.
     */
    private static void loadRoomsFromXML() {
        try {
            File file = new File("src/main/java/earlybirds/Model/Map/String/Rooms.xml");
            InputStream inputStream = new FileInputStream(file);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            // Parse named rooms
            parseRooms(doc, "namedRooms", namesOfRooms, namedRooms, true);

            // Parse numbered rooms
            parseRooms(doc, "numberedRooms", null, numberedRooms, false);

            // Parse test rooms
            parseRooms(doc, "testRooms", namesOfTestRooms, testRooms, true);

        } catch (GdxRuntimeException e) {
            System.err.println("Error: File 'Rooms.xml' not found.");
        } catch (IOException e) {
            System.err.println("Error reading file 'Rooms.xml': " + e.getMessage());
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println("Error parsing XML: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse the rooms from the XML file and add them to the lists.
     *
     * @param doc         the document to parse
     * @param tagName     the tag name to parse
     * @param nameList    the list to add the names to
     * @param roomList    the list to add the rooms to
     * @param isNamedRoom whether the rooms have names
     */
    private static void parseRooms(Document doc, String tagName, List<String> nameList, List<String> roomList,
            boolean isNamedRoom) {
        NodeList roomNodes = doc.getElementsByTagName(tagName);
        Node roomsNode = roomNodes.item(0);
        NodeList roomNodeList = roomsNode.getChildNodes();
        for (int i = 0; i < roomNodeList.getLength(); i++) {
            Node roomNode = roomNodeList.item(i);
            if (roomNode.getNodeType() == Node.ELEMENT_NODE) {
                Element roomElement = (Element) roomNode;
                String roomString = roomElement.getTextContent().trim();
                roomList.add(roomString);
                if (isNamedRoom) {
                    String roomName = roomElement.getAttribute("name");
                    nameList.add(roomName);
                }
            }
        }
    }

    /**
     * Get the map string generated by the MapBuilder
     *
     * @return the map string
     */
    public String getMapString() {
        return mapString;
    }

    /**
     * Creates walls at the edges of the map and replaces "F" with "X" if they are
     * next to "-". Needed to stop the player from being able to leave the map.
     */
    private void addEdgeWalls() {
        StringBuilder mapStringBuilder = new StringBuilder(mapString);
        String[] rows = mapString.split("\n");

        for (int i = 0; i < rows.length; i++) {
            StringBuilder rowStringBuilder = new StringBuilder(rows[i]);
            for (int j = 0; j < rows[i].length(); j++) {
                char currentChar = rows[i].charAt(j);

                if (currentChar != 'X' && currentChar != '-') {
                    boolean isLeftEdge = (j == 0 || rows[i].charAt(j - 1) == '-');
                    boolean isRightEdge = (j == rows[i].length() - 1 || rows[i].charAt(j + 1) == '-');
                    boolean isTopEdge = (i == 0 || rows[i - 1].charAt(j) == '-');
                    boolean isBottomEdge = (i == rows.length - 1 || rows[i + 1].charAt(j) == '-');

                    if (isLeftEdge || isRightEdge || isTopEdge || isBottomEdge) {
                        rowStringBuilder.setCharAt(j, 'X');
                    }
                }
            }
            rows[i] = rowStringBuilder.toString();
        }
        mapStringBuilder = new StringBuilder();
        for (String row : rows) {
            mapStringBuilder.append(row).append("\n");
        }
        mapString = mapStringBuilder.toString();
    }

}
