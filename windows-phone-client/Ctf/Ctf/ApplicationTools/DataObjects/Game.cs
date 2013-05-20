using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;

namespace Ctf.ApplicationTools.DataObjects
{
    
    public class Game
    {
        
        public string name { get; set; }
        public string description { get; set; }
        public string time_start { get; set; }
        public int duration { get; set; }
        public int points_max { get; set; }
        public int players_max { get; set; }
        public Localization localization { get; set; }
        public int flag_quantity { get; set; }
        public Flag flag { get; set; }

        public Game(string name, string description, string time_start, int duration, int points_max, int players_max, Localization localization)
        {
            this.name = name;
            this.description = description;
            this.time_start = time_start;
            this.duration = duration;
            this.points_max = points_max;
            this.players_max = players_max;
            this.localization = localization;
            //this.flag_quantity = flag_quantity;
            //this.flag = flag;
        }
    }

    public class Localization
    {
        public string name { get; set; }
        public LatLng latLng { get; set; }
        public int radius { get; set; }

        public Localization(string name, LatLng latLng, int radius)
        {
                this.name = name;
                this.latLng = latLng;
                this.radius = radius;
        }
    }

    public class LatLng
    {
        public double lat { get; set; }
        public double lng { get; set; }

        public LatLng(float lat, float lng)
        {
            this.lat = lat;
            this.lng = lng;
        }
    }

    public class Flag
    {
        public string team { get; set; }
        public LatLng latLng { get; set; }

        public Flag(string team, LatLng latLng)
        {
            this.team = team;
            this.latLng = latLng;
        }
    }


}
