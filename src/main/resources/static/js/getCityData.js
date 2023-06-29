function getCityData(){
    let lng_el = sessionStorage.getItem("lng_el");
    let lat_el = sessionStorage.getItem("lat_el");
    if(!(lng_el === "") || !(lat_el === "")){
        let api = "https://api.nlsc.gov.tw/other/TownVillagePointQuery/" + lng_el + "/" + lat_el + "/4326";
        // console.log(api);
        fetch(api)
            .then(res => res.text())
            .then(res => {
                const cityData = xml2json(parseXml(res), '')
                sessionStorage.setItem("cityData", cityData);
            })
    }
}
