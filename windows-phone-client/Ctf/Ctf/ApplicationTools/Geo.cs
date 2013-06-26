using System;
using System.Collections.Generic;
using System.Device.Location;
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

        public static async Task<Geoposition> GetPhoneGeopositionAsync(uint accuracyMeters = 10, int maximumAge = 10, int timeout = 60)
        {
            return await Task.Run(async () =>
            {
                Geolocator geolocator = new Geolocator();
                geolocator.DesiredAccuracyInMeters = accuracyMeters;
                return await geolocator.GetGeopositionAsync(TimeSpan.FromMinutes(maximumAge), TimeSpan.FromSeconds(timeout));
            });
        }

        public static async Task<List<double>> GetPhoneLocationListAsync(uint accuracyMeters = 10, int maximumAge = 10, int timeout = 60)
        {
            return await Task.Run(async () =>
            {
                List<double> latLng = new List<double>();
                Geoposition geoPosition = await GetPhoneGeopositionAsync(accuracyMeters, maximumAge, timeout);
                latLng.Add(geoPosition.Coordinate.Latitude);
                latLng.Add(geoPosition.Coordinate.Longitude);
                return latLng;
            });
        }

        public static async Task<GeoCoordinate> GetPhoneGeoCoordinateAsync(uint accuracyMeters = 10, int maximumAge = 10, int timeout = 60)
        {
            return await Task.Run(async () =>
            {
                Geoposition geoPosition = await GetPhoneGeopositionAsync(accuracyMeters, maximumAge, timeout);
                return new GeoCoordinate(geoPosition.Coordinate.Latitude, geoPosition.Coordinate.Longitude);
            });
        }
    }
}
