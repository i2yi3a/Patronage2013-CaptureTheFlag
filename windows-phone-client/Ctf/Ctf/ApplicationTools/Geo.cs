using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools
{
    public class Geo
    {
        public static double DegreeToRadian(double angle)
        {
            return Math.PI * angle / 180.0;
        }

        public static double Distance(List<double> latLng1, List<double> latLng2)
        {

            double lat1 = DegreeToRadian(latLng1[0]), lon1 = DegreeToRadian(latLng1[1]), lat2 = DegreeToRadian(latLng2[0]), lon2 = DegreeToRadian(latLng2[1]);
            double R = 6371.0; // km
            double d = Math.Acos(Math.Sin(lat1) * Math.Sin(lat2) +
                  Math.Cos(lat1) * Math.Cos(lat2) *
                  Math.Cos(lon2 - lon1)) * R;
            return Math.Round(d, 2);
        }
    }
}
