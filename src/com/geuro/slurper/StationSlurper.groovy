package com.geuro.slurper

import groovy.json.JsonSlurper

/**
 * Created by enricomariotti on 3/14/17.
 */
class StationSlurper {

    static def stations(def name) {
        def jsonSlurper = new JsonSlurper()
        jsonSlurper.parseText(new File(name).text)
    }

    static def getOldCities = stations('getCities_old.txt')
    static def getNewCities = stations('getCities_new.txt')
    static def missingStations = [];

    static def compare(oldCity) {
        for (def city : getNewCities) {
            if (oldCity.Name.equalsIgnoreCase(city.Name)) {
                println("update station_code set stn_code = \"${city.Id}\" where stn_code = \"${oldCity.Id}\"; -- ${oldCity.Name} ")
                return
            }
        }
        missingStations << oldCity;
    }

    static void main(String[] args) {
        getOldCities.each{city -> compare(city) }
        println("====== MISSING STATIONS ======")
        missingStations.each {println(it)}
    }

//        getNewCities.each {city ->   ("${city.Name}".equalsIgnoreCase("${oldCity.Name}")) ?
//                println("update station_code set stn_code = \"${city.Id}\" where stn_code = \"${oldCity.Id}\"; -- ${oldCity.Name} " ) :
//                ""}
}
