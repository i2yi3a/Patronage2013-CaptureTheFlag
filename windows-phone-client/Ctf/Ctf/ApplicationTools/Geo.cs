using System;
using System.Collections.Generic;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Devices.Geolocation;

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

        //Check if working properly
        public async Task<List<double>> GetPhoneLocationAsync(uint accuracyMeters = 10, int maximumAge = 10, int timeout = 60)
        {
            return await Task.Run(async () =>
            {
                List<double> latLng = new List<double>();
                Geolocator geolocator = new Geolocator();
                //geolocator.DesiredAccuracy = PositionAccuracy.High;
                geolocator.DesiredAccuracyInMeters = accuracyMeters;

                Geoposition geoposition = await geolocator.GetGeopositionAsync(TimeSpan.FromMinutes(maximumAge), TimeSpan.FromSeconds(timeout));

                latLng.Add(geoposition.Coordinate.Latitude);
                latLng.Add(geoposition.Coordinate.Longitude);
                return latLng;
            });
        }

    }
}
