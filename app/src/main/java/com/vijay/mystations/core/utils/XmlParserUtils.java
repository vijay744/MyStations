package com.vijay.mystations.core.utils;

import android.util.Xml;

import com.vijay.mystations.data.models.ModelStation;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class XmlParserUtils {
    private static final String ns = null;


    public static ArrayList<ModelStation> parseStationData(String data) throws XmlPullParserException, SAXException, IOException {
        ArrayList<ModelStation> stationArrayList = new ArrayList<>();
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            parser.require(XmlPullParser.START_TAG, ns, Constants.LIST_OF_ITEMS);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals(Constants.ITEM)) {
                    ModelStation station = readEntry(parser);
                    if (station.getId() != null)
                        stationArrayList.add(station);
                } else {
                    skip(parser);
                }
            }

        } finally {
            in.close();
        }
        return stationArrayList;
    }

    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private static ModelStation readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.ITEM);
        String id = null;
        String stationName = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(Constants.STATION_ID)) {
                id = readId(parser);
            } else if (name.equals(Constants.STATION_NAME)) {
                stationName = readName(parser);
            } else if (name.equals(Constants.STATION_URL)) {
                link = readLink(parser);
            } else {
                skip(parser);
            }
        }
        return new ModelStation(id, stationName, link);
    }

    // Processes title tags in the feed.
    private static String readId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.STATION_ID);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, Constants.STATION_ID);
        return title;
    }

    private static String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.STATION_NAME);
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, Constants.STATION_NAME);
        return summary;
    }

    private static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.STATION_URL);
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, Constants.STATION_URL);
        return summary;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
