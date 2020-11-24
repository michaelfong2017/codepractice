package com.michael.test;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer[]> resultRightWrists = new ArrayList<>();
        resultRightWrists.add(new Integer[]{980, 220});
        resultRightWrists.add(new Integer[]{990, 225});
        resultRightWrists.add(new Integer[]{970, 210});
        resultRightWrists.add(new Integer[]{980, 200});
        resultRightWrists.add(new Integer[]{880, 250});

        resultRightWrists.add(new Integer[]{980, 620});
        resultRightWrists.add(new Integer[]{990, 625});
        resultRightWrists.add(new Integer[]{970, 610});
        resultRightWrists.add(new Integer[]{980, 595});
        resultRightWrists.add(new Integer[]{880, 650});

        resultRightWrists.add(new Integer[]{820, 520});
        resultRightWrists.add(new Integer[]{800, 525});
        resultRightWrists.add(new Integer[]{810, 510});
        resultRightWrists.add(new Integer[]{780, 500});
        resultRightWrists.add(new Integer[]{840, 550});

        resultRightWrists.add(new Integer[]{1135, 520});
        resultRightWrists.add(new Integer[]{1100, 545});
        resultRightWrists.add(new Integer[]{1110, 510});
        resultRightWrists.add(new Integer[]{1080, 500});
        resultRightWrists.add(new Integer[]{1210, 495});

        resultRightWrists.add(new Integer[]{950, 620});
        resultRightWrists.add(new Integer[]{935, 620});
        resultRightWrists.add(new Integer[]{970, 610});
        resultRightWrists.add(new Integer[]{980, 595});
        resultRightWrists.add(new Integer[]{880, 650});

        int sampleSize = 5;
        int[] xCoordinates = new int[]{960,960,810,1110,960};
        int[] yCoordinates = new int[]{320,625,500,500,625};

        int[][] closestPoints = findClosestPoints(resultRightWrists, sampleSize, xCoordinates, yCoordinates);
        System.out.println(closestPoints);
        System.out.println(findAngleInCross(closestPoints[0][0],closestPoints[0][1],closestPoints[1][0],closestPoints[1][1],closestPoints[2][0],closestPoints[2][1],closestPoints[3][0],closestPoints[3][1]));

    }

    /**
     * Return the acute angle
     */
    public static int findAngleInCross(int topX, int topY, int bottomX, int bottomY,
                                          int leftX, int leftY, int rightX, int rightY) {
        int[] line1 = new int[]{bottomX-topX, bottomY-topY};
        int[] line2 = new int[]{rightX-leftX, rightY-leftY};

        double magLine1 = Math.sqrt(Math.pow(line1[0], 2) + Math.pow(line1[1], 2));
        double magLine2 = Math.sqrt(Math.pow(line2[0], 2) + Math.pow(line2[1], 2));

        double cosAngle = (line1[0]*line2[0]+line1[1]*line2[1])/(magLine1*magLine2);
        double angle = Math.acos(cosAngle);
        double angleDegree = angle*180/Math.PI;
        if (angleDegree > 90) {
            angleDegree = 180 - angleDegree;
        }
        return (int)angleDegree;
    }

    /**
     * 及子 & 阿孟 have tolerance in y-axis
     */
    private static int[][] findClosestPoints(List<Integer[]> resultRightWrists, int sampleSize, int[] xCoordinates, int[] yCoordinates) {
        /** 及子 / 阿孟 **/
        int yTolerance = 20;

        int[][] closestPoints = new int[5][2];
        for (int i=0; i<5; i++) {
            int xReal = xCoordinates[i];
            int yReal = yCoordinates[i];
            Double min = null;
            int minXSample = -1;
            int minYSample = -1;
            for (int j=0; j<sampleSize; j++) {
                int resultIndex = i*sampleSize+j;
                int xSample = resultRightWrists.get(resultIndex)[0];
                int ySample = resultRightWrists.get(resultIndex)[1];
                int xTemp = xSample;
                int yTemp = ySample;
                /** 及子 / 阿孟 **/

                if (i==1 || i==4) {
                    if (Math.abs(yReal-ySample) <= 20) {
                        ySample = yReal;
                    }
                    else {
                        if (ySample > yReal) {
                            ySample -= 20;
                        }
                        else {
                            ySample += 20;
                        }
                    }

                    double distance = Math.sqrt(Math.pow(xReal-xSample, 2) + Math.pow(yReal-ySample, 2));
                    if (min == null || distance < min) {
                        min = distance;
                        minXSample = xTemp;
                        minYSample = yTemp;
                    }
                }
                /** 因父 / 及聖神 / 之名 **/
                else {
                    double distance = Math.sqrt(Math.pow(xReal-xSample, 2) + Math.pow(yReal-ySample, 2));
                    if (min == null || distance < min) {
                        min = distance;
                        minXSample = xSample;
                        minYSample = ySample;
                    }
                }
            }
            closestPoints[i] = new int[]{minXSample, minYSample};

        }
        return closestPoints;
    }
}

