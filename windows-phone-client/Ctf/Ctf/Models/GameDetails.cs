using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models
{
    public class GameDetails : GameHeader
    {
        private string description;
        private int duration;
        private Localization localization;
        private string time_start;
        private int points_max;
        private int players_max;

        public string Description { get; set; }
        public int Duration { get; set; }
        public Localization Localization { get; set; }
        public string Time_start { get; set; }
        public int Points_max { get; set; }
        public int Players_max { get; set; }
    }

    public class Localization : BaseModel
    {
        private string name;
        private double radius;
        private List<object> latLng;

        public string Name { get; set; }
        public double Radius { get; set; }
        public List<object> LatLng { get; set; } //TODO IObservableCollection
    }
}
