using Ctf.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class LocationFilter : BindableBase
    {
        private double p1;
        private double p2;
        private double p3;
        private GameStatus gameStatus;

        public List<double> latLng { get; set; }
        public double range { get; set; }
        public string status { get; set; }

        public LocationFilter(double latitude, double longitude, double range, string status)
        {
            this.latLng = new List<double>();
            this.latLng.Add(latitude);
            this.latLng.Add(longitude);
            this.range = range;
            this.status = status;
        }
    }
}
