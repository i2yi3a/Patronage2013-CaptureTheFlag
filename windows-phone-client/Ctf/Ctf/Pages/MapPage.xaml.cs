using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Shell;
using Microsoft.Phone.Maps.Toolkit;
using System.Device.Location;
using Microsoft.Phone.Maps.Controls;
using Windows.Devices.Geolocation;
using Microsoft.Phone.Maps.Services;
using System.Windows.Shapes;
using System.Windows.Media;
using System.Windows.Input;
using Microsoft.Phone.Controls.Maps.Platform;
using Ctf.ApplicationTools;
using System.Diagnostics;

namespace Ctf
{

    public partial class MapPage : PhoneApplicationPage
    {
        MapLayer centerlayer;
        MapLayer redFlag;
        MapLayer blueFlag;
        GeoCoordinateCollection locations = new GeoCoordinateCollection();
        MapPolyline myMapPolyline = new MapPolyline();
        double Radius = 1;



        public MapPage()
        {
            InitializeComponent();
            MyMap.ZoomLevel = 10.0;
        }


        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            Zoom();
        }

        private async void Zoom()
        {
            List<double> latLng = await Geo.GetPhoneLocationAsync();
            Debug.WriteLine("My position is : " + latLng[0] + ", " + latLng[1]);
            MyMap.SetView(new GeoCoordinate(latLng[0], latLng[1]), MyMap.ZoomLevel);
        }

        private void MyMapCenter_Tap(object sender, System.Windows.Input.GestureEventArgs e)
        {
            if (centerlayer != null)
            {
                MyMap.Layers.Remove(centerlayer);
            }
            Point p = e.GetPosition(MyMap);
            GeoCoordinate s = MyMap.ConvertViewportPointToGeoCoordinate(p);

            Ellipse myCircle = new Ellipse();
            myCircle.Fill = new SolidColorBrush(Colors.Green);
            myCircle.Height = 10;
            myCircle.Width = 10;
            myCircle.Opacity = 20;

            MapOverlay myLocationOverlay = new MapOverlay();
            myLocationOverlay.Content = myCircle;
            myLocationOverlay.PositionOrigin = new Point(0, 0);
            myLocationOverlay.GeoCoordinate = s;
            center.Text = s.ToString();

            centerlayer = new MapLayer();
            centerlayer.Add(myLocationOverlay);

            MyMap.Layers.Add(centerlayer);
            ApplicationBarIconButton b = (ApplicationBarIconButton)ApplicationBar.Buttons[1];
            b.IsEnabled = true;


            RadiusPlusButton.IsEnabled = true;
            RadiusMinusButton.IsEnabled = true;

            if (locations != null)
            {
                MyMap.MapElements.Remove(myMapPolyline);
            }




            locations = CreateCircle(s, Radius);

            myMapPolyline.Path = locations;
            myMapPolyline.StrokeThickness = 5;
            myMapPolyline.StrokeColor = Colors.Red;


            MyMap.MapElements.Add(myMapPolyline);

        }

        private void MyMapRed_Tap(object sender, System.Windows.Input.GestureEventArgs e)
        {
            if (blueFlag != null)
            {
                MyMap.Layers.Remove(redFlag);
            }
            Point p = e.GetPosition(MyMap);
            GeoCoordinate s = MyMap.ConvertViewportPointToGeoCoordinate(p);

            Ellipse myCircle = new Ellipse();
            myCircle.Fill = new SolidColorBrush(Colors.Red);
            myCircle.Height = 20;
            myCircle.Width = 20;
            myCircle.Opacity = 50;

            MapOverlay myLocationOverlay = new MapOverlay();
            myLocationOverlay.Content = myCircle;
            myLocationOverlay.PositionOrigin = new Point(0, 0);
            myLocationOverlay.GeoCoordinate = s;
            redflag.Text = s.ToString();

            redFlag = new MapLayer();
            redFlag.Add(myLocationOverlay);

            MyMap.Layers.Add(redFlag);
            ApplicationBarIconButton b = (ApplicationBarIconButton)ApplicationBar.Buttons[2];
            b.IsEnabled = true;
        }
        private void MyMapBlue_Tap(object sender, System.Windows.Input.GestureEventArgs e)
        {
            if (blueFlag != null)
            {
                MyMap.Layers.Remove(blueFlag);
            }
            Point p = e.GetPosition(MyMap);
            GeoCoordinate s = MyMap.ConvertViewportPointToGeoCoordinate(p);

            Ellipse myCircle = new Ellipse();
            myCircle.Fill = new SolidColorBrush(Colors.Blue);
            myCircle.Height = 20;
            myCircle.Width = 20;
            myCircle.Opacity = 50;

            MapOverlay myLocationOverlay = new MapOverlay();
            myLocationOverlay.Content = myCircle;
            myLocationOverlay.PositionOrigin = new Point(0, 0);
            myLocationOverlay.GeoCoordinate = s;
            blueflag.Text = s.ToString();

            blueFlag = new MapLayer();
            blueFlag.Add(myLocationOverlay);

            MyMap.Layers.Add(blueFlag);
            ApplicationBarIconButton b = (ApplicationBarIconButton)ApplicationBar.Buttons[3];
            b.IsEnabled = true;

        }
        private void ApplicationBarIconButton_Click_1(object sender, EventArgs e)
        {
            MyMap.Tap -= MyMapBlue_Tap;
            MyMap.Tap -= MyMapRed_Tap;
            MyMap.Tap += MyMapCenter_Tap;

        }

        private void ApplicationBarIconButton_Click_2(object sender, EventArgs e)
        {
            MyMap.Tap -= MyMapBlue_Tap;
            MyMap.Tap -= MyMapCenter_Tap;
            MyMap.Tap += MyMapRed_Tap;
        }

        private void ApplicationBarIconButton_Click_3(object sender, EventArgs e)
        {
            MyMap.Tap -= MyMapRed_Tap;
            MyMap.Tap -= MyMapCenter_Tap;
            MyMap.Tap += MyMapBlue_Tap;
        }

        private void okAppBarButton_Click(object sender, EventArgs e)
        {
            string uri = string.Format("/Pages/CreateGamePage.xaml?parametr1=" + center.Text + "&parametr2=" + redflag.Text + "&parametr3=" + blueflag.Text);

            NavigationService.Navigate(new Uri(uri, UriKind.Relative));
        }

        private void MyMap_ZoomLevelChanged(object sender, MapZoomLevelChangedEventArgs e)
        {
            if (MyMap.ZoomLevel > 16)
            {
                MyMap.ZoomLevel = 16;
            }
        }












        //dodane teraz
        public static GeoCoordinateCollection CreateCircle(GeoCoordinate center, double radius)
        {
            var earthRadius = 6367.0; // radius w kilometrach
            var lat = center.Latitude * Math.PI / 180.0;
            var lng = center.Longitude * Math.PI / 180.0; //radians                                                         
            var d = radius / earthRadius;
            var locations = new GeoCoordinateCollection();

            for (var x = 0; x <= 360; x++)
            {
                var brng = x * Math.PI / 180.0; ;
                var latRadians = Math.Asin(Math.Sin(lat) * Math.Cos(d) + Math.Cos(lat) * Math.Sin(d) * Math.Cos(brng));
                var lngRadians = lng + Math.Atan2(Math.Sin(brng) * Math.Sin(d) * Math.Cos(lat), Math.Cos(d) - Math.Sin(lat) * Math.Sin(latRadians));
                locations.Add(new GeoCoordinate(180.0 * latRadians / Math.PI, 180.0 * lngRadians / Math.PI));
            }

            return locations;
        }



        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            Radius = Radius + 0.5;
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            if (Radius >= 1)
            {
                Radius = Radius - 0.5;

            }
            else
            {
                Radius = 0.5;
            }

        }



    }
}