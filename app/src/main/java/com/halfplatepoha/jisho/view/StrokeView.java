//package com.halfplatepoha.jisho.view;
//
//import android.graphics.Path;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by surjo on 20/12/17.
// */
//
//public class StrokeView {
//
//    private static final Pattern svgInstructionPattern = Pattern.compile("([a-zA-Z])([^a-zA-Z]+)");
//    private static final Pattern svgCoordiantePattern = Pattern.compile("-?\\d*\\.?\\d*");
//    private static final Pattern svgPathPattern = Pattern.compile("\"\"<path .*(?<= )d=\"([^\"]+)\".*/>\"\"");
//
//    public static void extractPathData(String input) {
//        collect(input, 1);
//    }
//
//    public static Path buildPath(String pathData) {
//        Path path = new Path();
//
//        Matcher matcher = svgInstructionPattern.matcher(pathData);
//
//        float lastX = 0f;
//        float lastY = 0f;
//        float lastX1 = 0f;
//        float lastY1 = 0f;
//        float subPathStartX = 0f;
//        float subPathStartY = 0f;
//        boolean curve = false;
//
//        while(matcher.find()) {
//            char command = matcher.group(1).charAt(0);
//            String coordinateStr = matcher.group(2);
//
//            switch (Character.toLowerCase(command)) {
//                case 'm':
//                    String[] mSplit = coordinateStr.split(",");
//                    float mx = Float.valueOf(mSplit[0]);
//                    float my = Float.valueOf(mSplit[1]);
//                    if(Character.isUpperCase(command)) {
//                        subPathStartX = mx;
//                        subPathStartY = my;
//                        path.rMoveTo(mx, my);
//                        lastX = mx;
//                        lastY = my;
//                    } else {
//                        subPathStartX += mx;
//                        subPathStartY += my;
//                        path.rMoveTo(mx, my);
//                        lastX += mx;
//                        lastY += my;
//                    }
//                    break;
//
//                case 'l':
//                    String[] lSplit = coordinateStr.split(",");
//                    float lx = Float.valueOf(lSplit[0]);
//                    float ly = Float.valueOf(lSplit[0]);
//
//                    if(Character.isUpperCase(command)) {
//                        path.lineTo(lx, ly);
//                        lastX = lx;
//                        lastY = ly;
//                    } else {
//                        path.rLineTo(lx, ly);
//                        lastX += lx;
//                        lastY += ly;
//                    }
//                    break;
//
//                case 'v':
//                    List<List<Float>> vFloatsL = forEachCoordinateGroup(coordinateStr, 1);
//                    for(List<Float> vFloats : vFloatsL) {
//                        float vy = vFloats.get(0);
//                        if (Character.isUpperCase(command)) {
//                            path.lineTo(lastX, vy);
//                            lastY = vy;
//                        } else {
//                            path.rLineTo(0f, vy);
//                            lastY += vy;
//                        }
//                    }
//                    break;
//
//                case 'h':
//                    List<List<Float>> hFloatsL = forEachCoordinateGroup(coordinateStr, 1);
//
//                    for(List<Float> hFloats : hFloatsL) {
//                        float hx = hFloats.get(0);
//                        if (Character.isUpperCase(command)) {
//                            path.lineTo(hx, lastY);
//                            lastX = hx;
//                        } else {
//                            path.rLineTo(hx, 0);
//                            lastX += hx;
//                        }
//                    }
//                    break;
//
//                case 'c':
//                    curve = true;
//                    List<List<Float>> cFloatsL = forEachCoordinateGroup(coordinateStr, 6);
//
//                    for(List<Float> cFloats : cFloatsL) {
//                        float x1 = cFloats.get(0);
//                        float y1 = cFloats.get(1);
//
//                        float x2 = cFloats.get(2);
//                        float y2 = cFloats.get(3);
//
//                        float x = cFloats.get(4);
//                        float y = cFloats.get(5);
//
//                        if (Character.isLowerCase(command)) {
//                            x1 += lastX;
//                            x2 += lastX;
//                            x += lastX;
//                            y1 += lastY;
//                            y2 += lastY;
//                            y += lastY;
//                        }
//
//                        path.cubicTo(x1, y1, x2, y2, x, y);
//
//                        lastX1 = x2;
//                        lastY1 = y2;
//                        lastX = x;
//                        lastY = y;
//                    }
//                    break;
//
//                case 's':
//                    curve = true;
//                    List<List<Float>> sFloatsL = forEachCoordinateGroup(coordinateStr, 4);
//
//                    for(List<Float> sFloats : sFloatsL) {
//                        float sx2 = sFloats.get(0);
//                        float sy2 = sFloats.get(1);
//
//                        float sx = sFloats.get(2);
//                        float sy = sFloats.get(3);
//
//                        if (Character.isLowerCase(command)) {
//                            sx2 += lastX;
//                            sx += lastX;
//                            sy2 += lastY;
//                            sy += lastY;
//                        }
//
//                        float sx1 = 2 * lastX - lastX1;
//                        float sy1 = 2 * lastY - lastY1;
//
//                        path.cubicTo(sx1, sy1, sx2, sy2, sx, sy);
//                        lastX1 = sx2;
//                        lastY1 = sy2;
//                        lastX = sx;
//                        lastY = sy;
//                    }
//                    break;
//
//                case 'z':
//                    path.close();
//                    path.moveTo(subPathStartX, subPathStartY);
//                    lastX = subPathStartX;
//                    lastY = subPathStartY;
//                    lastX1 = subPathStartX;
//                    lastY1 = subPathStartY;
//                    curve = true;
//                    break;
//
//                default:
//                     throw new IllegalArgumentException("Unknown command " + command + " for input " + pathData);
//            }
//
//            if(!curve) {
//                lastX1 = lastX;
//                lastY1 = lastY;
//            }
//        }
//
//        return path;
//    }
//
//    private static List<String> collect(String input, int groupIndex) {
//        Matcher matcher = svgPathPattern.matcher(input);
//        ArrayList<String> list = new ArrayList<>();
//
//        while(matcher.find()) {
//            String value = matcher.group(groupIndex);
//            if(value != null && value.length() != 0)
//                list.add(value);
//        }
//
//        return list;
//    }
//
//    private static List<List<Float>> forEachCoordinateGroup(String input, int groupSize) {
//        List<String> strs = collect(input, 0);
//        List<List<Float>> fll = new ArrayList<>();
//
//        for(int i=0; i<strs.size(); i++) {
//            int index = i/groupSize;
//            if(fll.get(index) == null) {
//                fll.add(index, new ArrayList<Float>());
//            }
//
//            fll.get(index).add(Float.valueOf(strs.get(i)));
//        }
//
//        return fll;
//    }
//}
