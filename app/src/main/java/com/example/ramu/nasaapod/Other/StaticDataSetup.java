package com.example.ramu.nasaapod.Other;

import com.example.ramu.nasaapod.Model.OneDayData;
import com.example.ramu.nasaapod.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramu on 1/24/2017.
 */

public class StaticDataSetup {

    public List<OneDayData> listDays;


        String[] date = {"2017-01-19","2017-01-21","2017-01-23","2017-01-24","2017-01-25"};
        String[] explanation = {"In 2015, after the nine-year-long, 3 billion mile commute, the New Horizons spacecraft finally reached Pluto, performing a 12 hour flyby that yielded spectacular data, releasing beautifully vivid images of Pluto that showed us for the first time what the dwarf planet looked like. Now, NASA has taken more than 100 images taken over the course of six weeks during the mission and edited them together to make a video of a simulated landing. The probe originally sent back photos in black and white, so the team figured out the colors of Pluto based on spectroscopic data and edited the images to get the best visual of the dwarf planet.",
                 "Scientists planning the the next phase of NASA’s New Horizons mission, a robotic craft that completed the first exploration of Pluto in 2015, are going into the flyby of a frozen, faraway city-sized clump of rock on New Year’s Day 2019 armed with little knowledge of the target lurking around 4 billion miles (6.4 billion kilometres) from Earth." ,
                 "Asteroid impacts have the distinction of being one of the few sci-fi concepts that will definitely happen at some point. But despite the clear and present (although potentially far off) danger of getting smacked by an asteroid, we’ve devoted few resources to averting such a catastrophe.",
                 "Our modern-day solar system is a result billions of years in the making. Unfortunately, rewinding time to witness the formation of the solar system is out of the question. To determine our solar system’s origins, astronomers instead study “planetary leftovers” for clues about the makeup of the original solar nebula around our infant Sun. NASA’s newest Discovery-class missions, Lucy and Psyche, will provide valuable insight into the past lives of the planetary building blocks that share our local neighborhood.",
                 "2016 WF9 is relatively large, roughly 0.3 to 0.6 mile (0.5-1 km) across, and is rather dark, reflecting only a few percent of the light that falls on its surface. The object resembles a comet in its reflectivity and orbit, but appears to lack the characteristic dust and gas cloud that defines a comet.It is in an orbit that takes it on a scenic tour of the Solar System. At its farthest distance from the Sun, it approaches Jupiter’s orbit."};

        String[] hdurl = {"http://apod.nasa.gov/apod/image/1701/WinterHexagon_Dai_1000.jpg",
                "http://apod.nasa.gov/apod/image/1701/WinterHexagon_Dai_1000.jpg",
                "http://apod.nasa.gov/apod/image/1701/WinterHexagon_Dai_1000.jpg",
                "http://apod.nasa.gov/apod/image/1701/WinterHexagon_Dai_1000.jpg",
                "http://apod.nasa.gov/apod/image/1701/WinterHexagon_Dai_1000.jpg"};

        String[] media_type = {"image",
                "image",
                "image",
                "image",
                "image"};
        String[] service_version ={"v1",
                "v1",
                "v1",
                "v1",
                "v1"};
        String[] title = {"New NASA video visualizes landing on Pluto",
                "New Horizons to continue mission of discovery with Kuiper Belt encounter",
                "NASA Has the Asteroid Protection Plan, But Where’s the Money?",
                "Exploring our solar system’s origins with two new NASA missions",
                "Nasa's wise Spacecraft spots two new near-earth object"};
        String[] url = {"http://apod.nasa.gov/apod/image/1701/WinterHexagon_Dai_1000_annotated.jpg",
                "https://twistedsifter.files.wordpress.com/2016/03/best-pics-from-year-in-space-nasa-scott-kelly-42.jpg",
                "https://www.nasa.gov/sites/default/files/thumbnails/image/iss043-e-86375.jpg",
                "https://www.nasa.gov/sites/default/files/iss040e081424.jpg",
                "http://www.jpl.nasa.gov/images/spitzer/20160107/PIA20063-16.jpg"};



    String[] copyright={"@ nasa.gov","@ nasa.gov","@ nasa.gov","@ nasa.gov","@ nasa.gov"};


    public StaticDataSetup(){
            listDays = new ArrayList<>();
        for (int i=0;i<title.length;i++){
            OneDayData aDay= new OneDayData(i,title[i],explanation[i], url[i], hdurl[i],copyright[i],date[i],media_type[i],service_version[i]);
            listDays.add(aDay);
        }
    }

    public List<OneDayData> getData(){
        return listDays;
    }
}
