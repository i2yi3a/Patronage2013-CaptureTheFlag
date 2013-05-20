using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    //JSON:
    //{
    //    "id":"517551a8da062683a9db1a22",
    //    "name": "Lahim fancy example CTF game EDITED",
    //    "description": "First open for all simple urban game in Szczecin",
    //    "time_start": "01-06-2013 10:13:00",
    //    "duration": 5400000,
    //    "points_max": 10,
    //    "players_max": 12,
    //    "status": "IN_PROGRESS",
    //    "localization": {
    //        "name": "Jasne Blonia, Szczecin, Poland",
    //        "latLng": {
    //            "lat": 53.44018,
    //            "lng": 14.540062
    //        },
    //        "radius": 2800
    //    }
    //}


    public class GameInfoFull : ServerJsonResponse
    {
        public string id { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public string time_start { get; set; }
        public int duration { get; set; }
        public int points_max { get; set; }
        public int players_max { get; set; }
        public string status { get; set; }
        public Localization localization { get; set; }
    }

    public class LatLng
    {
        public double lat { get; set; }
        public double lng { get; set; }
    }

    public class Localization
    {
        public string name { get; set; }
        public LatLng latLng { get; set; }
        public int radius { get; set; }
    }
}
