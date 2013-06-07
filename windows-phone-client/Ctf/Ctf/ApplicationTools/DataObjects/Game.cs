using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;
using Ctf.Communication.DataObjects;

namespace Ctf.ApplicationTools.DataObjects
{

    public class Game : ServerResponse
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
        public TeamBase red_team_base { get; set; }
        public TeamBase blue_team_base { get; set; }
        //public int flag_quantity { get; set; }
        //public Flag flag { get; set; }

        public Game()
        {

        }
        public Game(string name, string description, string id, string time_start, int duration, int points_max, int players_max, Localization localization, TeamBase red_team_base, TeamBase blue_team_base)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.time_start = time_start;
            this.duration = duration;
            this.points_max = points_max;
            this.players_max = players_max;
            this.localization = localization;
            this.red_team_base = red_team_base;
            this.blue_team_base = blue_team_base;
            //this.flag_quantity = flag_quantity;
            //this.flag = flag;
        }


        public Game(string name, string description, string time_start, int duration, int points_max, int players_max, Localization localization, TeamBase red_team_base, TeamBase blue_team_base)
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
        public List<double> latLng { get; set; }
        public int radius { get; set; }


        public Localization()
        {

        }

        public Localization(string name, List<double> latLng, int radius)
        {
            this.name = name;
            this.latLng = latLng;
            this.radius = radius;
        }
    }

    public class TeamBase
    {
        public string name { get; set; }
        public List<double> latLng { get; set; }

        public TeamBase()
        { }

        public TeamBase(string name, List<double> latLng)
        {
            this.name = name;
            this.latLng = latLng;
        }
    }


    //public class Flag
    //{
    //    public string team { get; set; }
    //    public LatLng latLng { get; set; }

    //    public Flag()
    //    {

    //    }

    //    public Flag(string team, LatLng latLng)
    //    {
    //        this.team = team;
    //        this.latLng = latLng;
    //    }
    //}


}
