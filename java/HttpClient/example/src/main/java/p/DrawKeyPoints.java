package p;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DrawKeyPoints {

    private static List<List<Integer>> bodyJoints = new ArrayList<>();

    private static float minConfidence = 0.05f;

    private static int strokeWidth = 6;
    private static int circleRadius = 3;

    public static void initialize() {
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_WRIST.ordinal(), BodyPart.LEFT_ELBOW.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_ELBOW.ordinal(), BodyPart.LEFT_SHOULDER.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_SHOULDER.ordinal(), BodyPart.RIGHT_SHOULDER.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.RIGHT_SHOULDER.ordinal(), BodyPart.RIGHT_ELBOW.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.RIGHT_ELBOW.ordinal(), BodyPart.RIGHT_WRIST.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_SHOULDER.ordinal(), BodyPart.LEFT_HIP.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_HIP.ordinal(), BodyPart.RIGHT_HIP.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.RIGHT_HIP.ordinal(), BodyPart.RIGHT_SHOULDER.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_HIP.ordinal(), BodyPart.LEFT_KNEE.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.LEFT_KNEE.ordinal(), BodyPart.LEFT_ANKLE.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.RIGHT_HIP.ordinal(), BodyPart.RIGHT_KNEE.ordinal())));
        bodyJoints.add(new ArrayList<>(Arrays.asList(BodyPart.RIGHT_KNEE.ordinal(), BodyPart.RIGHT_ANKLE.ordinal())));
    }

    public static Person deserializeResponseToPerson(String jsonString) throws IOException {
        Person person = new Person();
        float totalScore = 0.0f;
        System.out.println(jsonString);
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("predictions");
        JSONObject personJson = (JSONObject)arr.get(0);
        Iterator<String> keys = personJson.keys();
        System.out.println(person);

        List<KeyPoint> keyPoints = new ArrayList<>();
        while(keys.hasNext()) {
            String key = keys.next();
            if (personJson.get(key) instanceof JSONObject) {
                System.out.println(personJson.getJSONObject(key));
                KeyPoint keyPoint = new KeyPoint();
                // body part
                keyPoint.setBodyPart(convertToBodyPart(key));
                // position
                Position position = new Position();
                int x = (int)personJson.getJSONObject(key).getDouble("coordinate_y");
                position.setX(x);
                int y = (int)(personJson.getJSONObject(key).getDouble("coordinate_x"));
                position.setY(y);
                keyPoint.setPosition(position);
                // score
                float score = (float)personJson.getJSONObject(key).getDouble("score");
                keyPoint.setScore(score);

                totalScore += score;

                // key point
                keyPoints.add(keyPoint);
            }
        }
        Collections.sort(keyPoints, new KeyPointComparator());

        if (keyPoints.size() != 0) {
            totalScore /= (float)keyPoints.size();
        }
        person.setKeyPoints(keyPoints);
        person.setScore(totalScore);

        return person;
    }

    private static BodyPart convertToBodyPart(String bodyPart) {
        switch (bodyPart) {
            case "nose":
                return BodyPart.NOSE;
            case "leftEye":
                return BodyPart.LEFT_EYE;
            case "rightEye":
                return BodyPart.RIGHT_EYE;
            case "leftEar":
                return BodyPart.LEFT_EAR;
            case "rightEar":
                return BodyPart.RIGHT_EAR;
            case "leftShoulder":
                return BodyPart.LEFT_SHOULDER;
            case "rightShoulder":
                return BodyPart.RIGHT_SHOULDER;
            case "leftElbow":
                return BodyPart.LEFT_ELBOW;
            case "rightElbow":
                return BodyPart.RIGHT_ELBOW;
            case "leftWrist":
                return BodyPart.LEFT_WRIST;
            case "rightWrist":
                return BodyPart.RIGHT_WRIST;
            case "leftHip":
                return BodyPart.LEFT_HIP;
            case "rightHip":
                return BodyPart.RIGHT_HIP;
            case "leftKnee":
                return BodyPart.LEFT_KNEE;
            case "rightKnee":
                return BodyPart.RIGHT_KNEE;
            case "leftAnkle":
                return BodyPart.LEFT_ANKLE;
            case "rightAnkle":
                return BodyPart.RIGHT_ANKLE;
            default:
                return null;
        }
    }

    public static BufferedImage drawKeyPoints(Person person, String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));

        if (person == null || person.getKeyPoints().size() == 0) {
            return bufferedImage;
        }

        // get the Graphics context for this single BufferedImage object
        Graphics g = bufferedImage.getGraphics();
        Graphics2D g2 = (Graphics2D)g;

        g2.setStroke(new BasicStroke(strokeWidth));
        g2.setColor(Color.RED);

        for (KeyPoint keyPoint : person.getKeyPoints()) {
            if (keyPoint.getScore() > minConfidence) {
                Position position = keyPoint.getPosition();
                float centerX = position.getX();
                float centerY = position.getY();
                Shape theCircle = new Ellipse2D.Double(centerX - circleRadius, centerY - circleRadius, 2.0 * circleRadius, 2.0 * circleRadius);
                g2.draw(theCircle);
            }
        }

        for (List<Integer> line : bodyJoints) {
            if (
                    (person.getKeyPoints().get(line.get(0)).getScore() > minConfidence) &&
                            (person.getKeyPoints().get(line.get(1)).getScore() > minConfidence)
            ) {
                g2.drawLine(
                        person.getKeyPoints().get(line.get(0)).getPosition().getX(),
                        person.getKeyPoints().get(line.get(0)).getPosition().getY(),
                        person.getKeyPoints().get(line.get(1)).getPosition().getX(),
                        person.getKeyPoints().get(line.get(1)).getPosition().getY()
                );
            }
        }

        return bufferedImage;
    }

    private static float transformCoordinate(int coordinate, int source, int destination) {
        return ((float)coordinate) / ((float)source) * ((float)destination);
    }
}

enum BodyPart {
    NOSE,
    LEFT_EYE,
    RIGHT_EYE,
    LEFT_EAR,
    RIGHT_EAR,
    LEFT_SHOULDER,
    RIGHT_SHOULDER,
    LEFT_ELBOW,
    RIGHT_ELBOW,
    LEFT_WRIST,
    RIGHT_WRIST,
    LEFT_HIP,
    RIGHT_HIP,
    LEFT_KNEE,
    RIGHT_KNEE,
    LEFT_ANKLE,
    RIGHT_ANKLE
}

class Position {
    int x = 0;
    int y = 0;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
}

class KeyPoint {
    BodyPart bodyPart = BodyPart.NOSE;
    Position position = new Position();
    float score = 0.0f;

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void setScore(float score) {
        this.score = score;
    }
    public BodyPart getBodyPart() {
        return this.bodyPart;
    }
    public Position getPosition() { return this.position; }
    public float getScore() { return this.score; }
}

class Person {
    List<KeyPoint> keyPoints = new ArrayList<>();
    float score = 0.0f;
    public void setKeyPoints(List<KeyPoint> keyPoints) {
        this.keyPoints = keyPoints;
    }
    public void setScore(float score) {
        this.score = score;
    }
    public List<KeyPoint> getKeyPoints() { return this.keyPoints; }
    public float getScore() { return this.score; }
}

class KeyPointComparator implements Comparator<KeyPoint> {
    @Override
    public int compare(KeyPoint o1, KeyPoint o2) {
        if (o1.getBodyPart().ordinal() > o2.getBodyPart().ordinal()) {
            return 1;
        }
        else if (o1.getBodyPart().ordinal() < o2.getBodyPart().ordinal()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
